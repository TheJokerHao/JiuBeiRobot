package com.fecmobile.jiubeirobot.bean;

/**
 * 创建人：TheJoker丶豪 on
 * 创建时间：2017/6/12.
 */

public class DefaultAddressBean {

    /**
     * state : 0
     * obj : {}
     * msg : 添加地址信息成功
     */

    private String state;
    private ObjBean obj;
    private String msg;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ObjBean getObj() {
        return obj;
    }

    public void setObj(ObjBean obj) {
        this.obj = obj;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class ObjBean {
    }
}
