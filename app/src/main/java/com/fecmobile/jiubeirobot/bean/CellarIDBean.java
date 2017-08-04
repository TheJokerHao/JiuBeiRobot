package com.fecmobile.jiubeirobot.bean;

/**
 * 类描述    :酒窖ID信息
 * 包名      : com.fecmobile.jiubeirobot.bean
 * 类名称    : CellarIDBean
 * 创建人    : ghy
 * 创建时间  : 2017/3/7 12:45
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class CellarIDBean {

    /**
     * {
     * data{
     * msg
     * state
     * obj{
     * <p>
     * }
     * }
     * }
     * cellarId : 6
     * bid : 76
     */

    private String cellerId;
    private String bid;
    private String robotCode;
    private String cellarName;

    public String getCellarName() {
        return cellarName;
    }

    public void setCellarName(String cellarName) {
        this.cellarName = cellarName;
    }

    public String getRobotCode() {
        return robotCode;
    }

    public void setRobotCode(String robotCode) {
        this.robotCode = robotCode;
    }

    public String getCellerId() {
        return cellerId;
    }

    public void setCellerId(String cellerId) {
        this.cellerId = cellerId;
    }

    public String getBid() {
        return bid;
    }

    public void setBid(String bid) {
        this.bid = bid;
    }
}
