package com.fecmobile.jiubeirobot.bean;

import java.io.Serializable;

/**
 * 类描述    :商品分类
 * 包名      : com.fecmobile.jiubeirobot.bean
 * 类名称    : ShopFilterBean
 * 创建人    : ghy
 * 创建时间  : 2017/3/10 10:51
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class ShopFilterBean implements Serializable, IShopFilterInteface {

    /**
     * classId : 88
     * className : 白葡萄
     */

    private String classId;
    private String className;

    public ShopFilterBean(String classId, String className) {
        this.classId = classId;
        this.className = className;
    }

    public ShopFilterBean() {
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
