package com.fecmobile.jiubeirobot.bean;

/**
 * 类描述    :支付二维码
 * 包名      : com.fecmobile.jiubeirobot.bean
 * 类名称    : PayInfoBean
 * 创建人    : ghy
 * 创建时间  : 2017/3/21 16:22
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */

public class PayInfoBean {

    /**
     * orderCodes : SO1488833128538
     * qr_img : http://img.madameunion.com/group1/M00/00/05/Ch7YGljQyLqAKHyyAAAF_DPQd1U066.png
     */

    private String orderCodes;
    private String qr_img;
    private String orderAmout;

    public String getOrderAmout() {
        return orderAmout;
    }

    public void setOrderAmout(String orderAmout) {
        this.orderAmout = orderAmout;
    }

    public String getOrderCodes() {
        return orderCodes;
    }

    public void setOrderCodes(String orderCodes) {
        this.orderCodes = orderCodes;
    }

    public String getQr_img() {
        return qr_img;
    }

    public void setQr_img(String qr_img) {
        this.qr_img = qr_img;
    }
}
