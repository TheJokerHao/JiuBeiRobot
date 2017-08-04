package com.fecmobile.jiubeirobot.bean;

import java.util.List;

/**
 * 类描述    : 酒品存储列表详情的bean
 * 包名      : com.fecmobile.jiubeirobot.bean
 * 类名称    : StorageCellarTabDetailBean
 * 创建人    : wangxing
 * 创建时间  :  2017/3/15   17:39
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */

public class StorageCellarTabDetailBean {


    /**
     * brandName : 长城（GreatWall）
     * chateauName : 大维酒庄
     * className : 普罗旺斯（Provence）
     * grapeVarietiesNames : 毛葡萄
     * listPic1_url : http://192.168.0.56:89/group1/M00/03/30/wKgAD1jInLCAL0zDAAAbETNvHqM250.jpg
     * listPic2_url :
     * listPic3_url :
     * listPic4_url :
     * listPic5_url :
     * originName : 西班牙
     * productName : 桃红葡萄酒
     * productNameEn : taohongWine
     * regionName : 南澳州的巴罗萨谷
     * regionPname : 澳大利亚（Australian）
     * stockCount : 90
     * stockList : [{"accountName":"六里铺酒窖","bid":"76","saleStock":"","stock":"90","storageTime":"2017-03-16 00:00:00","storageYears":"1"}]
     * unitValue : 瓶
     * years : 1970
     */

    private String brandName;
    private String chateauName;
    private String className;
    private String grapeVarietiesNames;
    private String listPic1_url;
    private String listPic2_url;
    private String listPic3_url;
    private String listPic4_url;
    private String listPic5_url;
    private String originName;
    private String productName;
    private String productNameEn;
    private String regionName;
    private String regionPname;
    private String stockCount;
    private String unitValue;
    private String years;
    private List<StockListBean> stockList;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getChateauName() {
        return chateauName;
    }

    public void setChateauName(String chateauName) {
        this.chateauName = chateauName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getGrapeVarietiesNames() {
        return grapeVarietiesNames;
    }

    public void setGrapeVarietiesNames(String grapeVarietiesNames) {
        this.grapeVarietiesNames = grapeVarietiesNames;
    }

    public String getListPic1_url() {
        return listPic1_url;
    }

    public void setListPic1_url(String listPic1_url) {
        this.listPic1_url = listPic1_url;
    }

    public String getListPic2_url() {
        return listPic2_url;
    }

    public void setListPic2_url(String listPic2_url) {
        this.listPic2_url = listPic2_url;
    }

    public String getListPic3_url() {
        return listPic3_url;
    }

    public void setListPic3_url(String listPic3_url) {
        this.listPic3_url = listPic3_url;
    }

    public String getListPic4_url() {
        return listPic4_url;
    }

    public void setListPic4_url(String listPic4_url) {
        this.listPic4_url = listPic4_url;
    }

    public String getListPic5_url() {
        return listPic5_url;
    }

    public void setListPic5_url(String listPic5_url) {
        this.listPic5_url = listPic5_url;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductNameEn() {
        return productNameEn;
    }

    public void setProductNameEn(String productNameEn) {
        this.productNameEn = productNameEn;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionPname() {
        return regionPname;
    }

    public void setRegionPname(String regionPname) {
        this.regionPname = regionPname;
    }

    public String getStockCount() {
        return stockCount;
    }

    public void setStockCount(String stockCount) {
        this.stockCount = stockCount;
    }

    public String getUnitValue() {
        return unitValue;
    }

    public void setUnitValue(String unitValue) {
        this.unitValue = unitValue;
    }

    public String getYears() {
        return years;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public List<StockListBean> getStockList() {
        return stockList;
    }

    public void setStockList(List<StockListBean> stockList) {
        this.stockList = stockList;
    }

    public static class StockListBean {
        /**
         * accountName : 六里铺酒窖
         * bid : 76
         * saleStock :
         * stock : 90
         * storageTime : 2017-03-16 00:00:00
         * storageYears : 1
         */

        private String accountName;
        private String bid;
        private String saleStock;
        private String stock;
        private String storageTime;
        private String storageYears;

        public String getAccountName() {
            return accountName;
        }

        public void setAccountName(String accountName) {
            this.accountName = accountName;
        }

        public String getBid() {
            return bid;
        }

        public void setBid(String bid) {
            this.bid = bid;
        }

        public String getSaleStock() {
            return saleStock;
        }

        public void setSaleStock(String saleStock) {
            this.saleStock = saleStock;
        }

        public String getStock() {
            return stock;
        }

        public void setStock(String stock) {
            this.stock = stock;
        }

        public String getStorageTime() {
            return storageTime;
        }

        public void setStorageTime(String storageTime) {
            this.storageTime = storageTime;
        }

        public String getStorageYears() {
            return storageYears;
        }

        public void setStorageYears(String storageYears) {
            this.storageYears = storageYears;
        }
    }
}
