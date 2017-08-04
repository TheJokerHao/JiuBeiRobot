package com.fecmobile.jiubeirobot.bean;

/**
 * 类描述    :订单结果
 * 包名      : com.fecmobile.jiubeirobot.bean
 * 类名称    : OrderResultInfoBean
 * 创建人    : ghy
 * 创建时间  : 2017/3/21 14:28
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */

public class OrderResultInfoBean {

    /**
     * orderCode : SO1488832897773
     * orderId : 53
     * orderAmout : 135.0
     */

    private String orderCode;
    private String orderId;
    private String orderAmout;

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderAmout() {
        return orderAmout;
    }

    public void setOrderAmout(String orderAmout) {
        this.orderAmout = orderAmout;

    }
}
