package com.huyingbao.core.progress;

import android.os.SystemClock;

import com.huyingbao.core.arch.model.RxError;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;

/**
 * 使用EventBus发送进度变化通知
 */
public class ProgressResponseBody extends ResponseBody {
    private int mRefreshTime = 120;
    private ResponseBody mResponseBody;
    private RxProgress mRxProgress;
    /**
     * BufferedSource 是OkIo库中的输入流，这里就当作inputStream来使用。
     */
    private BufferedSource mBufferedSource;

    public ProgressResponseBody(ResponseBody responseBody, String tag) {
        mResponseBody = responseBody;
        mRxProgress = RxProgress.newInstance(tag);
    }

    @Override
    public MediaType contentType() {
        return mResponseBody.contentType();
    }

    @Override
    public long contentLength() {
        return mResponseBody.contentLength();
    }

    @Override
    public BufferedSource source() {
        if (mBufferedSource == null) {
            mBufferedSource = Okio.buffer(source(mResponseBody.source()));
        }
        return mBufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            /**
             * 已经下载的数据长度
             */
            private long currentLength = 0L;
            /**
             * 最后一次刷新的时间
             */
            private long lastTime = 0L;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead;
                try {
                    bytesRead = super.read(sink, byteCount);
                } catch (IOException e) {
                    //发送异常,带有tag(需要有tag的方法来接)
                    RxError rxError = RxError.newInstance(mRxProgress.getTag(), e);
                    EventBus.getDefault().postSticky(rxError, rxError.getTag());
                    throw e;
                }
                if (mRxProgress.getContentLength() == 0) {
                    //避免重复调用 contentLength()
                    mRxProgress.setContentLength(contentLength());
                }
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                currentLength += bytesRead != -1 ? bytesRead : 0;
                long curTime = SystemClock.elapsedRealtime();
                if (curTime - lastTime >= mRefreshTime || bytesRead == -1 || currentLength == mRxProgress.getContentLength()) {
                    mRxProgress.setEachLength(bytesRead);
                    mRxProgress.setCurrentLength(currentLength);
                    mRxProgress.setIntervalTime(curTime - lastTime);
                    lastTime = curTime;
                    //发送进度信息,带有tag(需要有tag的方法来接)
                    EventBus.getDefault().postSticky(mRxProgress, mRxProgress.getTag());
                }
                return bytesRead;
            }
        };
    }
}