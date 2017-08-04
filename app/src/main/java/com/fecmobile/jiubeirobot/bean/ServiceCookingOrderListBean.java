package com.fecmobile.jiubeirobot.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/2/20.
 */
@Deprecated
public class ServiceCookingOrderListBean implements Serializable {
    private String isComment;
    private String sumAmout;
    private String orderCode;
    private int cookingKindNum;
    private int orderStatus;
    private int bid;
    private String busiCompLogo;
    private String busiCompname;
    private int orderId;

    public void setIsComment(String isComment) {
        this.isComment = isComment;
    }

    public String getIsComment() {
        return this.isComment;
    }

    public void setSumAmout(String sumAmout) {
        this.sumAmout = sumAmout;
    }

    public String getSumAmout() {
        return this.sumAmout;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderCode() {
        return this.orderCode;
    }

    public void setCookingKindNum(int cookingKindNum) {
        this.cookingKindNum = cookingKindNum;
    }

    public int getCookingKindNum() {
        return this.cookingKindNum;
    }

    public void setOrderStatus(int orderStatus) {
        this.orderStatus = orderStatus;
    }

    public int getOrderStatus() {
        return this.orderStatus;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public int getBid() {
        return this.bid;
    }

    public void setBusiCompLogo(String busiCompLogo) {
        this.busiCompLogo = busiCompLogo;
    }

    public String getBusiCompLogo() {
        return this.busiCompLogo;
    }

    public void setBusiCompname(String busiCompname) {
        this.busiCompname = busiCompname;
    }

    public String getBusiCompname() {
        return this.busiCompname;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getOrderId() {
        return this.orderId;
    }
}
