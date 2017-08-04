package com.fecmobile.jiubeirobot.bean;

/**
 * 类描述    :订单状态信息
 * 包名      : com.fecmobile.jiubeirobot.bean
 * 类名称    : OrderStateInfoBean
 * 创建人    : ghy
 * 创建时间  : 2017/3/21 16:21
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */

public class OrderStateInfoBean {
    private String payStatus;
    private String msg;

    public String getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(String payStatus) {
        this.payStatus = payStatus;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
