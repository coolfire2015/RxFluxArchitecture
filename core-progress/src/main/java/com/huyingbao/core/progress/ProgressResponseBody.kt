package com.huyingbao.core.progress

import android.os.SystemClock
import com.huyingbao.core.arch.model.Error
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.*
import org.greenrobot.eventbus.EventBus
import java.io.IOException

/**
 * 使用EventBus发送进度变化通知
 */
class ProgressResponseBody(private val mResponseBody: ResponseBody?, tag: String?) :
    ResponseBody() {
    private val mRefreshTime = 120
    private val mRxProgress: RxProgress

    /**
     * BufferedSource 是OkIo库中的输入流，这里就当作inputStream来使用。
     */
    private var mBufferedSource: BufferedSource? = null
    override fun contentType(): MediaType? {
        return mResponseBody!!.contentType()
    }

    override fun contentLength(): Long {
        return mResponseBody!!.contentLength()
    }

    override fun source(): BufferedSource {
        if (mBufferedSource == null) {
            mBufferedSource = source(mResponseBody!!.source()).buffer()
        }
        return mBufferedSource!!
    }

    private fun source(source: Source): Source {
        return object : ForwardingSource(source) {
            /**
             * 已经下载的数据长度
             */
            private var currentLength = 0L

            /**
             * 最后一次刷新的时间
             */
            private var lastTime = 0L

            @Throws(IOException::class)
            override fun read(sink: Buffer, byteCount: Long): Long {
                val bytesRead: Long
                bytesRead = try {
                    super.read(sink, byteCount)
                } catch (e: IOException) {
                    //发送异常,带有tag(需要有tag的方法来接)
                    val rxError: Error = Error.newInstance(progress, e)
                    EventBus.getDefault().postSticky(rxError, rxError.tag)
                    throw e
                }
                if (progress.contentLength == 0L) {
                    //避免重复调用 contentLength()
                    progress.contentLength = contentLength()
                }
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                currentLength += if (bytesRead != -1L) bytesRead else 0
                val curTime = SystemClock.elapsedRealtime()
                if (curTime - lastTime >= refreshTime || bytesRead == -1L || currentLength == progress.contentLength) {
                    progress.eachLength = bytesRead
                    progress.currentLength = currentLength
                    progress.intervalTime = curTime - lastTime
                    lastTime = curTime
                    //发送进度信息,带有tag(需要有tag的方法来接)
                    EventBus.getDefault().postSticky(progress, progress.tag)
                }
                return bytesRead
            }
        }
    }

    init {
        mRxProgress = RxProgress.Companion.newInstance(tag!!)
    }
}