package com.fecmobile.jiubeirobot.bean;

/**
 * 类描述    :订单数量
 * 包名      : com.fecmobile.jiubeirobot.bean
 * 类名称    : PendingOrderNumBean
 * 创建人    : ghy
 * 创建时间  : 2017/3/20 15:12
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */

public class PendingOrderNumBean {
    /**
     * unfilledOrders : 1
     * ownWineNum : 1
     * generationWineNum : 0
     * nonPayment : 1
     */

    private int unfilledOrders;
    private int ownWineNum;
    private int generationWineNum;
    private int nonPayment;

    public int getUnfilledOrders() {
        return unfilledOrders;
    }

    public void setUnfilledOrders(int unfilledOrders) {
        this.unfilledOrders = unfilledOrders;
    }

    public int getOwnWineNum() {
        return ownWineNum;
    }

    public void setOwnWineNum(int ownWineNum) {
        this.ownWineNum = ownWineNum;
    }

    public int getGenerationWineNum() {
        return generationWineNum;
    }

    public void setGenerationWineNum(int generationWineNum) {
        this.generationWineNum = generationWineNum;
    }

    public int getNonPayment() {
        return nonPayment;
    }

    public void setNonPayment(int nonPayment) {
        this.nonPayment = nonPayment;
    }

}
