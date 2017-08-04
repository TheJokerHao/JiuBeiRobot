package com.fecmobile.jiubeirobot.bean;

import java.io.Serializable;

/**
 * 类描述    : 会员地址
 * 包名      : com.fecmobile.jiubeirobot.bean
 * 类名称    : MemberAddressBean
 * 创建人    : ghy
 * 创建时间  : 2017/3/13 19:11
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class MemberAddressBean implements Serializable {

    /**
     * id : 27
     * isDefault : 0
     * deliveryPost :
     * deliveryTel : 13430674043
     * deliveryPhone :
     * deliveryArea : 天河
     * deliveryName : 123456
     * deliveryAddr : 12312131
     * deliveryEmail :
     * cid : 78
     * deliveryProv : 其他数据
     * deliveryCity : 广州
     */

    private String id;
    private String isDefault;
    private String deliveryPost;
    private String deliveryTel;
    private String deliveryPhone;
    private String deliveryArea;
    private String deliveryName;
    private String deliveryAddr;
    private String deliveryEmail;
    private String cid;
    private String deliveryProv;
    private String deliveryCity;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(String isDefault) {
        this.isDefault = isDefault;
    }

    public String getDeliveryPost() {
        return deliveryPost;
    }

    public void setDeliveryPost(String deliveryPost) {
        this.deliveryPost = deliveryPost;
    }

    public String getDeliveryTel() {
        return deliveryTel;
    }

    public void setDeliveryTel(String deliveryTel) {
        this.deliveryTel = deliveryTel;
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

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
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

    @Override
    public String toString() {
        return deliveryAddr + "" + deliveryName;
    }
}
