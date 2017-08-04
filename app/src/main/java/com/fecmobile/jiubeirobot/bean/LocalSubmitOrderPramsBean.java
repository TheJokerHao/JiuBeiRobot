package com.fecmobile.jiubeirobot.bean;

import java.util.List;

/**
 * 创建人：TheJoker丶豪 on
 * 创建时间：2017/6/9.
 * TODO  这是本地购买下单的实体类   需要修改的地方直接修改
 */

public class LocalSubmitOrderPramsBean {
    /**
     * private String shipping;   运费
     * private String buyerBid; 买家客户ID
     * private String buyerAddressId; 地址ID  为空 （不需要配送的时候  可以不传  空值）
     * private String consigneerName;  收货人名称
     * private String consigneerTel;  收货人联系电话
     * private String consigneerPhone;    收货人手机号码
     * private String consigneerEmail;    收货人邮箱地址
     * private String consigneerAddress; 收货人详细地址
     * private String consigneerProvince; 收货人省份
     * private String consigneerCity;收货人城市
     * private String consigneerCountry;  收货人区县
     * private String consigneerPostcode; 收货人邮编
     * private String sellerBid;  卖家信息ID
     * private String buyerRemarks;   买家备注
     * private String isHaveAddress;  是否有收货地址
     * private String isHaveInvoice;  是否开发票
     * private String isQuickBuy;         是否快速购买
     * private String invoiceTitle;       发票抬头
     * private String companyName;    发票的单位名称
     * private String invoiceContent;     发票内容
     * private String ratepayerCode;      纳税人标识号
     * private String companyRegAddress;          公司注册地址
     * private String companyTel;     单位电话
     * private String depositBank;        开户银行
     * private String depositAccount;     开户账号
     * private String invoiceType;        发票类型
     * private String invoiceName;        收发票人姓名
     * private String invoiceTel;            收发票人电话
     * private String invoicePro;         收发票人的省份
     * private String invoiceCity;            收发票人的城市
     * private String invoiceArea;        收发票人的区县
     * private String invoiceAddress;收发票人的详细地址
     * private String deliveryCellerId;  发货酒窖 酒窖ID
     * private List<Shop> productList;
     * productId	商品id
     * productQty	商品数量
     */


    private String shipping;
    private String buyerBid;
    private String buyerAddressId;
    private String consigneerName;
    private String consigneerTel;
    private String consigneerPhone;
    private String consigneerEmail;
    private String consigneerAddress;
    private String consigneerProvince;
    private String consigneerCity;
    private String consigneerCountry;
    private String consigneerPostcode;
    private String sellerBid;
    private String buyerRemarks;
    private String isHaveAddress;
    private String isHaveInvoice;
    private String isQuickBuy;
    private String invoiceTitle;
    private String companyName;
    private String invoiceContent;
    private String ratepayerCode;
    private String companyRegAddress;
    private String companyTel;
    private String depositBank;
    private String depositAccount;
    private String invoiceType;
    private String invoiceName;
    private String invoiceTel;
    private String invoicePro;
    private String invoiceCity;
    private String invoiceArea;
    private String invoiceAddress;
    private String deliveryCellerId;
    private List<LocalShop> productList;

    public List<LocalShop> getProductList() {
        return productList;
    }

    public void setProductList(List<LocalShop> productList) {
        this.productList = productList;
    }

    public static class LocalShop {
        private String productId;
        private String productQty;

        public String getProductId() {
            return productId;
        }

        public void setProductId(String productId) {
            this.productId = productId;
        }

        public String getProductQty() {
            return productQty;
        }

        public void setProductQty(String productQty) {
            this.productQty = productQty;
        }
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getBuyerBid() {
        return buyerBid;
    }

    public void setBuyerBid(String buyerBid) {
        this.buyerBid = buyerBid;
    }

    public String getBuyerAddressId() {
        return buyerAddressId;
    }

    public void setBuyerAddressId(String buyerAddressId) {
        this.buyerAddressId = buyerAddressId;
    }

    public String getConsigneerName() {
        return consigneerName;
    }

    public void setConsigneerName(String consigneerName) {
        this.consigneerName = consigneerName;
    }

    public String getConsigneerTel() {
        return consigneerTel;
    }

    public void setConsigneerTel(String consigneerTel) {
        this.consigneerTel = consigneerTel;
    }

    public String getConsigneerPhone() {
        return consigneerPhone;
    }

    public void setConsigneerPhone(String consigneerPhone) {
        this.consigneerPhone = consigneerPhone;
    }

    public String getConsigneerEmail() {
        return consigneerEmail;
    }

    public void setConsigneerEmail(String consigneerEmail) {
        this.consigneerEmail = consigneerEmail;
    }

    public String getConsigneerAddress() {
        return consigneerAddress;
    }

    public void setConsigneerAddress(String consigneerAddress) {
        this.consigneerAddress = consigneerAddress;
    }

    public String getConsigneerProvince() {
        return consigneerProvince;
    }

    public void setConsigneerProvince(String consigneerProvince) {
        this.consigneerProvince = consigneerProvince;
    }

    public String getConsigneerCity() {
        return consigneerCity;
    }

    public void setConsigneerCity(String consigneerCity) {
        this.consigneerCity = consigneerCity;
    }

    public String getConsigneerCountry() {
        return consigneerCountry;
    }

    public void setConsigneerCountry(String consigneerCountry) {
        this.consigneerCountry = consigneerCountry;
    }

    public String getConsigneerPostcode() {
        return consigneerPostcode;
    }

    public void setConsigneerPostcode(String consigneerPostcode) {
        this.consigneerPostcode = consigneerPostcode;
    }

    public String getSellerBid() {
        return sellerBid;
    }

    public void setSellerBid(String sellerBid) {
        this.sellerBid = sellerBid;
    }

    public String getBuyerRemarks() {
        return buyerRemarks;
    }

    public void setBuyerRemarks(String buyerRemarks) {
        this.buyerRemarks = buyerRemarks;
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

    public String getIsQuickBuy() {
        return isQuickBuy;
    }

    public void setIsQuickBuy(String isQuickBuy) {
        this.isQuickBuy = isQuickBuy;
    }

    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getInvoiceContent() {
        return invoiceContent;
    }

    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent;
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

    public String getInvoiceType() {
        return invoiceType;
    }

    public void setInvoiceType(String invoiceType) {
        this.invoiceType = invoiceType;
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

    public String getDeliveryCellerId() {
        return deliveryCellerId;
    }

    public void setDeliveryCellerId(String deliveryCellerId) {
        this.deliveryCellerId = deliveryCellerId;
    }

}
