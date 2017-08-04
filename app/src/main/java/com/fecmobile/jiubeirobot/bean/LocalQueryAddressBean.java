package com.fecmobile.jiubeirobot.bean;

import java.io.Serializable;

/**
 * 创建人：TheJoker丶豪 on
 * 创建时间：2017/6/7.
 * 查询客户地址信息
 */

public class LocalQueryAddressBean implements Serializable {


    /**
     * id : 46
     * customerId : 50
     * isDefault : 1
     * deliveryTel : 18877914676
     * deliveryPost :
     * deliveryPhone :
     * deliveryArea : \u5357\u5c71\u533a
     * deliveryName : 152
     * deliveryAddr : \u5357\u5c97
     * deliveryEmail :
     * deliveryProv : \u5e7f\u4e1c\u7701
     * deliveryCity : \u6df1\u5733\u5e02
     */

    private String id;
    private String customerId;
    private String isDefault;
    private String deliveryTel;
    private String deliveryPost;
    private String deliveryPhone;
    private String deliveryArea;
    private String deliveryName;
    private String deliveryAddr;
    private String deliveryEmail;
    private String deliveryProv;
    private String deliveryCity;
    private String bid;

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getId() {
        return id;
    }

    public void setId(String bid) {
        this.id = bid;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getDeliveryTel() {
        return deliveryTel;
    }

    public void setDeliveryTel(String deliveryTel) {
        this.deliveryTel = deliveryTel;
    }

    public String getDeliveryPost() {
        return deliveryPost;
    }

    public void setDeliveryPost(String deliveryPost) {
        this.deliveryPost = deliveryPost;
    }

    public String getDeliveryPhone() {
        return deliveryPhone;
    }

    public void setDeliveryPhone(String deliveryPhone) {
        this.deliveryPhone = deliveryPhone;
    }

    public String getDeliveryArea() {
        return deliveryArea;
    }

    public void setDeliveryArea(String deliveryArea) {
        this.deliveryArea = deliveryArea;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getDeliveryAddr() {
        return deliveryAddr;
    }

    public void setDeliveryAddr(String deliveryAddr) {
        this.deliveryAddr = deliveryAddr;
    }

    public String getDeliveryEmail() {
        return deliveryEmail;
    }

    public void setDeliveryEmail(String deliveryEmail) {
        this.deliveryEmail = deliveryEmail;
    }

    public String getDeliveryProv() {
        return deliveryProv;
    }

    public void setDeliveryProv(String deliveryProv) {
        this.deliveryProv = deliveryProv;
    }

    public String getDeliveryCity() {
        return deliveryCity;
    }

    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity;
    }


}
