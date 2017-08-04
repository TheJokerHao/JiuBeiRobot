package com.fecmobile.jiubeirobot.bean;

/**
 * 类描述    :身份校验结果
 * 包名      : com.fecmobile.jiubeirobot.bean
 * 类名称    : IdentityCheckResultBean
 * 创建人    : ghy
 * 创建时间  : 2017/3/13 14:53
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class IdentityCheckResultBean {

    /**
     * state : 0
     * msg : 验证通过
     * bid : 78
     */

    private String state;
    private String msg;
    private String bid;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }
}

