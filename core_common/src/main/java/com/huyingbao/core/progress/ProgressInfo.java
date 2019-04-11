package com.huyingbao.core.progress;

public class ProgressInfo {
    public static final String TAG = "tag";
    /**
     * 当前已上传或下载的总长度
     */
    private long currentLength;
    /**
     * 数据总长度
     */
    private long contentLength;
    /**
     * 本次调用距离上一次被调用所间隔的时间(毫秒)
     */
    private long intervalTime;
    /**
     * 本次调用距离上一次被调用的间隔时间内上传或下载的byte长度
     */
    private long eachLength;
    /**
     * 当前进度信息所属对象的标志
     */
    private String tag;
    /**
     * 进度是否完成
     */
    private boolean finish;


    public ProgressInfo(String tag) {
        this.tag = tag;
    }

    public long getCurrentLength() {
        return currentLength;
    }

    public void setCurrentLength(long currentLength) {
        this.currentLength = currentLength;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    public long getIntervalTime() {
        return intervalTime;
    }

    public void setIntervalTime(long intervalTime) {
        this.intervalTime = intervalTime;
    }

    public long getEachLength() {
        return eachLength;
    }

    public void setEachLength(long eachLength) {
        this.eachLength = eachLength;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isFinish() {
        return finish;
    }

    public void setFinish(boolean finish) {
        this.finish = finish;
    }

    /**
     * 获取百分比,该计算舍去了小数点,如果你想得到更精确的值,请自行计算
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
     * 获取上传或下载网络速度,单位为byte/s,如果你想得到更精确的值,请自行计算
     *
     * @return
     */
    public long getSpeed() {
        if (getEachLength() <= 0 || getIntervalTime() <= 0) {
            return 0;
        }
        return getEachLength() * 1000 / getIntervalTime();
    }

    @Override
    public String toString() {
        return "ProgressInfo{" +
                "tag=" + tag +
                ", currentLength=" + currentLength +
                ", contentLength=" + contentLength +
                ", eachLength=" + eachLength +
                ", intervalTime=" + intervalTime +
                ", percent=" + getPercent() + "%" +
                ", speed=" + getSpeed() +
                ", finish=" + finish +
                '}';
    }
}