package com.huyingbao.module.main.ui.shop.model;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public class Shop {
    /**
     * shopId : 2
     * shopName : 正品外贸
     * code : 23302268
     * shopType : 0
     * longitude : 118.82
     * latitude : 32.0601
     * enableShowPro : 0
     * status : 0
     * createdAt : 2017-10-29T04:19:03.000Z
     * updatedAt : 2017-10-29T04:19:03.000Z
     */

    private int shopId;
    private String shopName;
    private int code;
    private int shopType;
    private String longitude;
    private String latitude;
    private int enableShowPro;
    private int status;
    private String createdAt;
    private String updatedAt;

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getShopType() {
        return shopType;
    }

    public void setShopType(int shopType) {
        this.shopType = shopType;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public int getEnableShowPro() {
        return enableShowPro;
    }

    public void setEnableShowPro(int enableShowPro) {
        this.enableShowPro = enableShowPro;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }
}
