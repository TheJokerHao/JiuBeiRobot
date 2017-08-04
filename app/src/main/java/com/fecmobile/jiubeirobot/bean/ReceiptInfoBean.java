package com.fecmobile.jiubeirobot.bean;

import java.util.List;

/**
 * 类描述    :小票信息
 * 包名      : com.fecmobile.jiubeirobot.bean
 * 类名称    : ReceiptInfoBean
 * 创建人    : ghy
 * 创建时间  : 2017/3/27 19:15
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */

public class ReceiptInfoBean {

    /**
     * shouldPay : 168.0
     * totalAmount : 168.0
     * list : [{"productQty":"1","productPrice":"168.0","productName":"奥瑞安骑士金标干红葡萄酒","productAmout":"168.0"}]
     * sellerName : 李白的酒窖
     * discount : 1
     */

    private String shouldPay;//应付的钱
    private String totalAmount;//总计
    private String sellerName;//酒窖名称
    private String discount;
    private String ticketTime;
    private String cellarName;
    private List<ListBean> list;

    public String getCellarName() {
        return cellarName;
    }

    public void setCellarName(String cellarName) {
        this.cellarName = cellarName;
    }

    public String getTicketTime() {
        return ticketTime;
    }

    public void setTicketTime(String ticketTime) {
        this.ticketTime = ticketTime;
    }

    public String getShouldPay() {
        return shouldPay;
    }

    public void setShouldPay(String shouldPay) {
        this.shouldPay = shouldPay;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * productQty : 1
         * productPrice : 168.0
         * productName : 奥瑞安骑士金标干红葡萄酒
         * productAmout : 168.0
         */

        private String productQty;
        private String productPrice;
        private String productName;
        private String productAmout;

        public String getProductQty() {
            return productQty;
        }

        public void setProductQty(String productQty) {
            this.productQty = productQty;
        }

        public String getProductPrice() {
            return productPrice;
        }

        public void setProductPrice(String productPrice) {
            this.productPrice = productPrice;
        }

        public String getProductName() {
            return productName;
        }

        public void setProductName(String productName) {
            this.productName = productName;
        }

        public String getProductAmout() {
            return productAmout;
        }

        public void setProductAmout(String productAmout) {
            this.productAmout = productAmout;
        }
    }
}
