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

public class ProgressResponseBody extends ResponseBody {
    private int mRefreshTime = 120;
    private ResponseBody mResponseBody;
    private ProgressInfo mProgressInfo;
    /**
     * BufferedSource 是okio库中的输入流，这里就当作inputStream来使用。
     */
    private BufferedSource mBufferedSource;

    public ProgressResponseBody(ResponseBody responseBody, String tag) {
        mResponseBody = responseBody;
        mProgressInfo = new ProgressInfo(tag);
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
            private long totalLengthRead = 0L;
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
                    RxError rxError = RxError.newInstance(mProgressInfo.getTag(), e);
                    EventBus.getDefault().postSticky(rxError, mProgressInfo.getTag());
                    throw e;
                }
                if (mProgressInfo.getContentLength() == 0) {
                    //避免重复调用 contentLength()
                    mProgressInfo.setContentLength(contentLength());
                }
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                totalLengthRead += bytesRead != -1 ? bytesRead : 0;
                long curTime = SystemClock.elapsedRealtime();
                if (curTime - lastTime >= mRefreshTime || bytesRead == -1 || totalLengthRead == mProgressInfo.getContentLength()) {
                    mProgressInfo.setEachLength(bytesRead);
                    mProgressInfo.setCurrentLength(totalLengthRead);
                    mProgressInfo.setIntervalTime(curTime - lastTime);
                    mProgressInfo.setFinish(bytesRead == -1 && totalLengthRead == mProgressInfo.getContentLength());
                    lastTime = curTime;
                    //发送进度信息,带有tag(需要有tag的方法来接)
                    EventBus.getDefault().postSticky(mProgressInfo, mProgressInfo.getTag());
                }
                return bytesRead;
            }
        };
    }
}