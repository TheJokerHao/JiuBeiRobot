package com.fecmobile.jiubeirobot.base;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * 类描述    :接口基本格式
 * 包名      : com.fecmobile.jiubeirobot.base
 * 类名称    : BaseBean
 * 创建人    : ghy
 * 创建时间  : 2017/2/21 20:01
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class BaseBean<T> implements Serializable {
    private String message;

    @SerializedName("data")
    private T data;

    private String code;

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }

    public void setData(T data) {
        this.data = data;
    }

    public T getData() {
        return this.data;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return this.code;
    }

}
