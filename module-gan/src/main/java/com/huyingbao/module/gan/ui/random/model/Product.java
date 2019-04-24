package com.huyingbao.module.gan.ui.random.model;

/**
 * Created by liujunfeng on 2019/1/1.
 */
public class Product {
    /**
     * createdAt : 2018-06-08T01:36:11.740Z
     * desc : 2018-06-08
     * publishedAt : 2018-06-08T00:00:00.0Z
     * source : web
     * type : 福利
     * url : http://ww1.sinaimg.cn/large/0065oQSqly1fs34w0jx9jj30j60ootcn.jpg
     * used : true
     * who : lijinshanmx
     */
    private String createdAt;
    private String desc;
    private String publishedAt;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }
}