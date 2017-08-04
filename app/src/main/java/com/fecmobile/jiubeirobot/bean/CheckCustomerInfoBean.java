package com.fecmobile.jiubeirobot.bean;

/**
 * 创建人：TheJoker丶豪 on
 * 创建时间：2017/6/7.
 * 检验客户地址信息
 */

public class CheckCustomerInfoBean {

    /**
     * customerId : 50
     * state : 0
     * msg : success
     */

    private String customerId;
    private int state;
    private String msg;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
