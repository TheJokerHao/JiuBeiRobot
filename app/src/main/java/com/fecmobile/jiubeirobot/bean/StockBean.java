package com.fecmobile.jiubeirobot.bean;

import com.fecmobile.jiubeirobot.utils.Arith;

import java.io.Serializable;

/**
 * 类描述    :本地购物车
 * 包名      : com.fecmobile.jiubeirobot.bean
 * 类名称    : StockBean
 * 创建人    : ghy
 * 创建时间  : 2017/3/10 10:22
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */

public class StockBean implements Serializable {
    private String id;
    private String mainPicUrl;
    private String drinkingName;
    private String drinkingNameEn;
    private String lsPrice;
    private String originName;
    private String chateauName;
    private String productStock;
    private int num;

    public String getMainPicUrl() {
        return mainPicUrl;
    }

    public void setMainPicUrl(String mainPicUrl) {
        this.mainPicUrl = mainPicUrl;
    }

    public String getDrinkingName() {
        return drinkingName;
    }

    public void setDrinkingName(String drinkingName) {
        this.drinkingName = drinkingName;
    }

    public String getDrinkingNameEn() {
        return drinkingNameEn;
    }

    public void setDrinkingNameEn(String drinkingNameEn) {
        this.drinkingNameEn = drinkingNameEn;
    }

    public String getLsPrice() {
        return Arith.get2Decimal(lsPrice);
    }

    public void setLsPrice(String lsPrice) {
        this.lsPrice = lsPrice;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    public String getChateauName() {
        return chateauName;
    }

    public void setChateauName(String chateauName) {
        this.chateauName = chateauName;
    }

    public String getProductStock() {
        return productStock;
    }

    public void setProductStock(String productStock) {
        this.productStock = productStock;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
