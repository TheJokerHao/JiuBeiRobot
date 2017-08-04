package com.fecmobile.jiubeirobot.bean;

/**
 * zhangh
 * Created by admin on 2017/3/29.
 * 获取版本号  更新版本
 */

public class VersionCodeBean {
    private String id; //apk记录id
    private String versionCode;  //apk版本号
    private String versionUrl;  //apk版本地址
    private String versionName;  //apk版本名称
    private String versionDescript;  //apk版本描述

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(String versionCode) {
        this.versionCode = versionCode;
    }

    public String getVersionUrl() {
        return versionUrl;
    }

    public void setVersionUrl(String versionUrl) {
        this.versionUrl = versionUrl;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public String getVersionDescript() {
        return versionDescript;
    }

    public void setVersionDescript(String versionDescript) {
        this.versionDescript = versionDescript;
    }
}
