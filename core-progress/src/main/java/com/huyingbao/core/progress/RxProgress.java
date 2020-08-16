package com.huyingbao.core.progress;

import androidx.annotation.NonNull;

import org.greenrobot.eventbus.EventBusEvent;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public class RxProgress extends EventBusEvent {
    public static final String TAG = "tag";
    /**
     * 当前已上传或下载的总长度
     */
    private long mCurrentLength;
    /**
     * 数据总长度
     */
    private long mContentLength;
    /**
     * 本次调用距离上一次被调用所间隔的时间(毫秒)
     */
    private long mIntervalTime;
    /**
     * 本次调用距离上一次被调用的间隔时间内上传或下载的byte长度
     */
    private long mEachLength;

    private RxProgress(@NonNull String tag) {
        super(tag);
    }

    public static RxProgress newInstance(String tag) {
        return new RxProgress(tag);
    }

    public long getCurrentLength() {
        return mCurrentLength;
    }

    public void setCurrentLength(long currentLength) {
        this.mCurrentLength = currentLength;
    }

    public long getContentLength() {
        return mContentLength;
    }

    public void setContentLength(long contentLength) {
        this.mContentLength = contentLength;
    }

    public long getIntervalTime() {
        return mIntervalTime;
    }

    public void setIntervalTime(long intervalTime) {
        this.mIntervalTime = intervalTime;
    }

    public long getEachLength() {
        return mEachLength;
    }

    public void setEachLength(long eachLength) {
        this.mEachLength = eachLength;
    }

    /**
     * 获取百分比
     *
     * @return
     */
    public int getPercent() {
        if (getCurrentLength() <= 0 || getContentLength() <= 0) {
            return 0;
        }
        return (int) ((100 * getCurrentLength()) / getContentLength());
    }

    /**
     * 获取上传或下载网络速度,单位为byte/s
     *
     * @return
     */
    public long getSpeed() {
        if (getEachLength() <= 0 || getIntervalTime() <= 0) {
            return 0;
        }
        return getEachLength() * 1000 / getIntervalTime();
    }
}