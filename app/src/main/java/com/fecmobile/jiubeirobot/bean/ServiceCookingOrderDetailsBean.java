package com.fecmobile.jiubeirobot.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/2/20.
 */
@Deprecated
public class ServiceCookingOrderDetailsBean implements Serializable {
    private String createTime;

    private String orderCode;

    private int deposit;

    private String isDiscount;

    private String discountRadio;

    private List<SList> list;

    private String type;

    private String orderStatus;

    private int cookingKindNum;

    private String orderId;

    private String discount;

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderCode() {
        return this.orderCode;
    }

    public void setDeposit(int deposit) {
        this.deposit = deposit;
    }

    public int getDeposit() {
        return this.deposit;
    }

    public void setIsDiscount(String isDiscount) {
        this.isDiscount = isDiscount;
    }

    public String getIsDiscount() {
        return this.isDiscount;
    }

    public void setDiscountRadio(String discountRadio) {
        this.discountRadio = discountRadio;
    }

    public String getDiscountRadio() {
        return this.discountRadio;
    }

    public void setList(List<SList> list) {
        this.list = list;
    }

    public List<SList> getList() {
        return this.list;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatus() {
        return this.orderStatus;
    }

    public void setCookingKindNum(int cookingKindNum) {
        this.cookingKindNum = cookingKindNum;
    }

    public int getCookingKindNum() {
        return this.cookingKindNum;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return this.orderId;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getDiscount() {
        return this.discount;
    }

    class SList {
        private double scPrice;

        private int number;

        private String cookingId;

        private String cookingName;

        private String lineId;

        public void setScPrice(double scPrice) {
            this.scPrice = scPrice;
        }

        public double getScPrice() {
            return this.scPrice;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public int getNumber() {
            return this.number;
        }

        public void setCookingId(String cookingId) {
            this.cookingId = cookingId;
        }

        public String getCookingId() {
            return this.cookingId;
        }

        public void setCookingName(String cookingName) {
            this.cookingName = cookingName;
        }

        public String getCookingName() {
            return this.cookingName;
        }

        public void setLineId(String lineId) {
            this.lineId = lineId;
        }

        public String getLineId() {
            return this.lineId;
        }

    }
}
