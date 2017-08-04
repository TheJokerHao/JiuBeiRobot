package com.fecmobile.jiubeirobot.bean;

import com.google.gson.Gson;

import java.util.List;

/**
 * Created by Administrator on 2017/3/14.
 */

public class ManagerOrderListBean {

    /**
     * amountCount : 5500
     * rowCount : 6
     * orderList : [{"id":"7","sumAmout":"1250.0","orderCode":"SO1488225485508","orderAccount":"","orderStatus":"0","picList":"[http://192.168.0.56:89/group1/M00/03/13/wKgAD1jAbVyAWhFyAAAQjUqNCCQ700.png]"},{"id":"6","sumAmout":"750.0","orderCode":"SO1488219068832","orderAccount":"线下人员","orderStatus":"0","picList":"[http://192.168.0.56:89/group1/M00/03/13/wKgAD1jAbVyAWhFyAAAQjUqNCCQ700.png]"},{"id":"5","sumAmout":"500.0","orderCode":"SO1488218880603","orderAccount":"线下人员","orderStatus":"0","picList":"[http://192.168.0.56:89/group1/M00/03/13/wKgAD1jAbVyAWhFyAAAQjUqNCCQ700.png]"},{"id":"4","sumAmout":"500.0","orderCode":"SO1488218496066","orderAccount":"线下人员","orderStatus":"0","picList":"[http://192.168.0.56:89/group1/M00/03/13/wKgAD1jAbVyAWhFyAAAQjUqNCCQ700.png]"},{"id":"3","sumAmout":"1250.0","orderCode":"SO1487978401977","orderAccount":"","orderStatus":"0","picList":"[http://192.168.0.56:89/group1/M00/03/13/wKgAD1jAbVyAWhFyAAAQjUqNCCQ700.png]"},{"id":"2","sumAmout":"1250.0","orderCode":"SO1487975996593","orderAccount":"","orderStatus":"5","picList":"[http://192.168.0.56:89/group1/M00/03/13/wKgAD1jAbVyAWhFyAAAQjUqNCCQ700.png]"}]
     * orderCount : 6
     */

    private double amountCount;
    private int rowCount;
    private int orderCount;
    private List<OrderListBean> orderList;

    public static ObjBean objectFromData(String str) {

        return new Gson().fromJson(str, ObjBean.class);
    }

    public double getAmountCount() {
        return amountCount;
    }

    public void setAmountCount(double amountCount) {
        this.amountCount = amountCount;
    }

    public int getRowCount() {
        return rowCount;
    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public void setOrderCount(int orderCount) {
        this.orderCount = orderCount;
    }

    public List<OrderListBean> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderListBean> orderList) {
        this.orderList = orderList;
    }

    public static class OrderListBean {

        /**
         * id : 2
         * sumAmout : 1250.0
         * orderCode : SO1487975996593
         * orderAccount :
         * orderLineCount : 1
         * orderStatus : 5
         * picList : [{"pic":"http://192.168.0.56:89/group1/M00/03/13/wKgAD1jAbVyAWhFyAAAQjUqNCCQ700.png"}]
         */

        private String id; //当前ID
        private String sumAmout;//小计
        private String orderCode;//订单号
        private String orderAccount;
        private String orderLineCount;
        private String orderStatus;//订单状态
        private List<PicListBean> picList;//图片集合

        public static OrderListBean objectFromData(String str) {

            return new Gson().fromJson(str, OrderListBean.class);
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSumAmout() {
            return sumAmout;
        }

        public void setSumAmout(String sumAmout) {
            this.sumAmout = sumAmout;
        }

        public String getOrderCode() {
            return orderCode;
        }

        public void setOrderCode(String orderCode) {
            this.orderCode = orderCode;
        }

        public String getOrderAccount() {
            return orderAccount;
        }

        public void setOrderAccount(String orderAccount) {
            this.orderAccount = orderAccount;
        }

        public String getOrderLineCount() {
            return orderLineCount;
        }

        public void setOrderLineCount(String orderLineCount) {
            this.orderLineCount = orderLineCount;
        }

        public String getOrderStatus() {
            return orderStatus;
        }

        public void setOrderStatus(String orderStatus) {
            this.orderStatus = orderStatus;
        }

        public List<PicListBean> getPicList() {
            return picList;
        }

        public void setPicList(List<PicListBean> picList) {
            this.picList = picList;
        }

        public static class PicListBean {
            /**
             * pic : http://192.168.0.56:89/group1/M00/03/13/wKgAD1jAbVyAWhFyAAAQjUqNCCQ700.png
             */

            private String pic;

            public static PicListBean objectFromData(String str) {

                return new Gson().fromJson(str, PicListBean.class);
            }

            public String getPic() {
                return pic;
            }

            public void setPic(String pic) {
                this.pic = pic;
            }
        }
    }
}
