package com.fecmobile.jiubeirobot.bean;

import java.io.Serializable;

/**
 * 类描述    : 管理员登录
 * 包名      : com.fecmobile.jiubeirobot.bean
 * 类名称    : LoginManagerBean
 * 创建人    : wangxing
 * 创建时间  :  2017/3/10   19:51
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */

public class LoginManagerBean implements Serializable {


    /**
     * uid : 124
     * account : 13825250263
     * userName : 杜先生
     * aid : 140
     * busiCompName : 圣卡罗酒窖
     * bid : 100
     * busiCompLogo_url :
     */

    private String uid;
    private String account;
    private String userName;
    private String aid;
    private String busiCompName;
    private String bid;
    private String busiCompLogo_url;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAid() {
        return aid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public String getBusiCompName() {
        return busiCompName;
    }

    public void setBusiCompName(String busiCompName) {
        this.busiCompName = busiCompName;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }

    public String getBusiCompLogo_url() {
        return busiCompLogo_url;
    }

    public void setBusiCompLogo_url(String busiCompLogo_url) {
        this.busiCompLogo_url = busiCompLogo_url;
    }
}
