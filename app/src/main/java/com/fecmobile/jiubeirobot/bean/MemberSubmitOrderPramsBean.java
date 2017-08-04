package com.fecmobile.jiubeirobot.bean;

import java.util.List;

/**
 * 类描述    :会员下单接口
 * 包名      : com.fecmobile.jiubeirobot.bean
 * 类名称    : MemberSubmitOrderPramsBean
 * 创建人    : ghy
 * 创建时间  : 2017/3/13 14:33
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */

public class MemberSubmitOrderPramsBean {
    private String shipping;
    private String buyerBid;
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

    private List<Shop> productList;

    public static class Shop {
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

    public String getDeliveryCellerId() {
        return deliveryCellerId;
    }

    public void setDeliveryCellerId(String deliveryCellerId) {
        this.deliveryCellerId = deliveryCellerId;
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

    public List<Shop> getProductList() {
        return productList;
    }

    public void setProductList(List<Shop> productList) {
        this.productList = productList;
    }
}
