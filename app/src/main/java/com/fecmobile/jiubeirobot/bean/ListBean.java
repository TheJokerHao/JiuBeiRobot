package com.fecmobile.jiubeirobot.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/2/20.
 */

public class ListBean<T> implements Serializable {
    private String state;

    @SerializedName("list")
    private List<T> list;

    private int rowCount;

    private String msg;

    public void setState(String state) {
        this.state = state;
    }

    public String getState() {
        return this.state;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public List<T> getList() {
        return this.list;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getRowCount() {
//        return 50;
        return this.rowCount;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }
}
