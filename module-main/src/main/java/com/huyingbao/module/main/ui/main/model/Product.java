package com.huyingbao.module.main.ui.main.model;

import com.huyingbao.module.main.ui.shop.model.Shop;

/**
 * Created by liujunfeng on 2017/12/7.
 */
public class Product {
    /**
     * shopId : {"shopId":2,"shopName":"正品外贸","code":23302268,"shopType":0,"longitude":"118.82","latitude":"32.0601","enableShowPro":0,"status":0,"createdAt":"2017-10-29T04:19:03.000Z","updatedAt":"2017-10-29T04:19:03.000Z"}
     * productId : 1
     * productName : 白色裤子
     * uuid : 241684f7-1194-4aa8-88d3-f09b6c4021f1
     * productType : 0
     * contentType : 0
     * price : 0
     * status : 0
     * createdAt : 2017-11-02T14:17:32.000Z
     * updatedAt : 2017-11-02T14:17:32.000Z
     */

    private Shop shopId;
    private int productId;
    private String productName;
    private String uuid;
    private int productType;
    private int contentType;
    private int price;
    private int status;
    private String createdAt;
    private String updatedAt;

    public Shop getShopId() {
        return shopId;
    }

    public void Shop(Shop shopId) {
        this.shopId = shopId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getProductType() {
        return productType;
    }

    public void setProductType(int productType) {
        this.productType = productType;
    }

    public int getContentType() {
        return contentType;
    }

    public void setContentType(int contentType) {
        this.contentType = contentType;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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
