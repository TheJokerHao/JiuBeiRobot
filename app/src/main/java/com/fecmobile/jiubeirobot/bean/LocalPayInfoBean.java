package com.fecmobile.jiubeirobot.bean;

/**
 * 创建人：TheJoker丶豪 on
 * 创建时间：2017/6/12.
 */

public class LocalPayInfoBean {

    /**
     * alipayQrCode : http://or2m8i9y1.bkt.clouddn.com/20170608/35010595caf41bac1ad3adb727fa71fe.jpg
     * wxpayQrCode : http://or2m8i9y1.bkt.clouddn.com/20170608/3859128cfcf7344024e8f33a0910b303.jpg
     */

    private String alipayQrCode;
    private String wxpayQrCode;

    public String getAlipayQrCode() {
        return alipayQrCode;
    }

    public void setAlipayQrCode(String alipayQrCode) {
        this.alipayQrCode = alipayQrCode;
    }

    public String getWxpayQrCode() {
        return wxpayQrCode;
    }

    public void setWxpayQrCode(String wxpayQrCode) {
        this.wxpayQrCode = wxpayQrCode;
    }

}
