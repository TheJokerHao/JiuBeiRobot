package com.fecmobile.jiubeirobot.bean;


import com.google.gson.Gson;

import java.util.List;

/**
 * 类描述    : 订单详情数据模型
 * 包名      : com.fecmobile.jiubeirobot.bean
 * 类名称    : ManagerOrderDetailBean
 * 创建人    : lc
 * 创建时间  :  2017-03-15 11:00
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class ManagerOrderDetailBean {


    /**
     * consigneerName :
     * sumAmout : 1250
     * productAmoutQty : 5
     * orderCode : SO1488301701054
     * consigneerAddress :
     * invoiceType :
     * deliveryName :
     * invoiceTitle :
     * orderStatus : 0
     * buyerName : 线下人员
     * consigneerPhone :
     * orderlineList : [{"drinkingName":"科瑞丝曼波尔多珍酿红葡萄酒","productQty":"5","mainPic_url":"http://192.168.0.56:89/group1/M00/03/13/wKgAD1jAbVyAWhFyAAAQjUqNCCQ700.png","productPrice":"250.0","unitValue":"瓶","drinkingNameEn":"KRESSMANN","productId":"17","orderlineId":"18"}]
     * logisticsCode :
     * deliveryMobile :
     * logisticsCompanyName :
     * payStatus : 0
     * orderId : 18
     * postTime : 2017-03-15 10:54:07
     * invoiceContent :
     */

    private String consigneerName;
    private String sumAmout;
    private String productAmoutQty;
    private String isHaveAddress;
    private String orderCode;
    private String consigneerAddress;
    private String invoiceType;
    private String deliveryName;
    private String isHaveInvoice;
    private String invoiceTitle;
    private String orderStatus;
    private String buyerName;
    private String consigneerPhone;
    private String logisticsCode;
    private String deliveryMobile;
    private String logisticsCompanyName;
    private String payStatus;
    private String orderId;
    private String postTime;
    private String invoiceContent;
    private String payType;
    private String companyName;
    private String ratepayerCode;
    private String companyRegAddress;
    private String companyTel;
    private String depositBank;
    private String depositAccount;
    private String invoiceName;
    private String invoiceTel;
    private String invoicePro;
    private String invoiceCity;
    private String invoiceArea;
    private String invoiceAddress;
    private List<OrderlineListBean> orderlineList;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getRatepayerCode() {
        return ratepayerCode;
    }

    public void setRatepayerCode(String ratepayerCode) {
        this.ratepayerCode = ratepayerCode;
    }

    public String getCompanyRegAddress() {
        return companyRegAddress;
    }

    public void setCompanyRegAddress(String companyRegAddress) {
        this.companyRegAddress = companyRegAddress;
    }

    public String getCompanyTel() {
        return companyTel;
    }

    public void setCompanyTel(String companyTel) {
        this.companyTel = companyTel;
    }

    public String getDepositBank() {
        return depositBank;
    }

    public void setDepositBank(String depositBank) {
        this.depositBank = depositBank;
    }

    public String getDepositAccount() {
        return depositAccount;
    }

    public void setDepositAccount(String depositAccount) {
        this.depositAccount = depositAccount;
    }

    public String getInvoiceName() {
        return invoiceName;
    }

    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName;
    }

    public String getInvoiceTel() {
        return invoiceTel;
    }

    public void setInvoiceTel(String invoiceTel) {
        this.invoiceTel = invoiceTel;
    }

    public String getInvoicePro() {
        return invoicePro;
    }

    public void setInvoicePro(String invoicePro) {
        this.invoicePro = invoicePro;
    }

    public String getInvoiceCity() {
        return invoiceCity;
    }

    public void setInvoiceCity(String invoiceCity) {
        this.invoiceCity = invoiceCity;
    }

    public String getInvoiceArea() {
        return invoiceArea;
    }

    public void setInvoiceArea(String invoiceArea) {
        this.invoiceArea = invoiceArea;
    }

    public String getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(String invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }


    public static ManagerOrderDetailBean objectFromData(String str) {

        return new Gson().fromJson(str, ManagerOrderDetailBean.class);
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getIsHaveAddress() {
        return isHaveAddress;
    }

    public void setIsHaveAddress(String isHaveAddress) {
        this.isHaveAddress = isHaveAddress;
    }

    public String getIsHaveInvoice() {
        return isHaveInvoice;
    }

    public void setIsHaveInvoice(String isHaveInvoice) {
        this.isHaveInvoice = isHaveInvoice;
    }

    public String getConsigneerName() {
        return consigneerName;
    }

    public void setConsigneerName(String consigneerName) {
        this.consigneerName = consigneerName;
    }

    public String getSumAmout() {
        return sumAmout;
    }

    public void setSumAmout(String sumAmout) {
        this.sumAmout = sumAmout;
    }

    public String getProductAmoutQty() {
        return productAmoutQty;
    }

    public void setProductAmoutQty(String productAmoutQty) {
        this.productAmoutQty = productAmoutQty;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getConsigneerAddress() {
        return consigneerAddress;
    }

    public void setConsigneerAddress(String consigneerAddress) {
        this.consigneerAddress = consigneerAddress;
    }

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
    }

    public String getDeliveryName() {
        return deliveryName;
    }

    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getConsigneerPhone() {
        return consigneerPhone;
    }

    public void setConsigneerPhone(String consigneerPhone) {
        this.consigneerPhone = consigneerPhone;
    }

    public String getLogisticsCode() {
        return logisticsCode;
    }

    public void setLogisticsCode(String logisticsCode) {
        this.logisticsCode = logisticsCode;
    }

    public String getDeliveryMobile() {
        return deliveryMobile;
    }

    public void setDeliveryMobile(String deliveryMobile) {
        this.deliveryMobile = deliveryMobile;
    }

    public String getLogisticsCompanyName() {
        return logisticsCompanyName;
    }

    public void setLogisticsCompanyName(String logisticsCompanyName) {
        this.logisticsCompanyName = logisticsCompanyName;
    }

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPostTime() {
        return postTime;
    }

    public void setPostTime(String postTime) {
        this.postTime = postTime;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
    }

    public List<OrderlineListBean> getOrderlineList() {
        return orderlineList;
    }

    public void setOrderlineList(List<OrderlineListBean> orderlineList) {
        this.orderlineList = orderlineList;
    }

    public static class OrderlineListBean {
        /**
         * drinkingName : 科瑞丝曼波尔多珍酿红葡萄酒
         * productQty : 5
         * mainPic_url : http://192.168.0.56:89/group1/M00/03/13/wKgAD1jAbVyAWhFyAAAQjUqNCCQ700.png
         * productPrice : 250.0
         * unitValue : 瓶
         * drinkingNameEn : KRESSMANN
         * productId : 17
         * orderlineId : 18
         */

        private String drinkingName;
        private String productQty;
        private String mainPic_url;
        private String productPrice;
        private String unitValue;
        private String drinkingNameEn;
        private String productId;
        private String orderlineId;

        public static OrderlineListBean objectFromData(String str) {

            return new Gson().fromJson(str, OrderlineListBean.class);
        }

        public String getDrinkingName() {
            return drinkingName;
        }

        public void setDrinkingName(String drinkingName) {
            this.drinkingName = drinkingName;
        }

        public String getProductQty() {
            return productQty;
        }

        public void setProductQty(String productQty) {
            this.productQty = productQty;
        }

        public String getMainPic_url() {
            return mainPic_url;
        }

        public void setMainPic_url(String mainPic_url) {
            this.mainPic_url = mainPic_url;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
        }

        public String getUnitValue() {
            return unitValue;
        }

        public void setUnitValue(String unitValue) {
            this.unitValue = unitValue;
        }

        public String getDrinkingNameEn() {
            return drinkingNameEn;
        }

        public void setDrinkingNameEn(String drinkingNameEn) {
            this.drinkingNameEn = drinkingNameEn;
        }

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getOrderlineId() {
            return orderlineId;
        }

        public void setOrderlineId(String orderlineId) {
            this.orderlineId = orderlineId;
        }
    }
}
