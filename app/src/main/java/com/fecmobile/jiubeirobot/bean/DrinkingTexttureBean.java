package com.fecmobile.jiubeirobot.bean;

import java.util.List;

/**
 * 文件名：
 * 描    述：
 * 作    者：ghy
 * 时    间：2017/3/10 18:42
 * 配餐建议
 */

public class DrinkingTexttureBean {
    private List<DrinkingTextture> drinking_cateringAdvise;

    public List<DrinkingTextture> getDrinking_cateringAdvise() {
        return drinking_cateringAdvise;
    }

    public void setDrinking_cateringAdvise(List<DrinkingTextture> drinking_cateringAdvise) {
        this.drinking_cateringAdvise = drinking_cateringAdvise;
    }

    private List<DrinkingTextture> drinking_textture;

    public List<DrinkingTextture> getDrinking_textture() {
        return drinking_textture;
    }

    public void setDrinking_textture(List<DrinkingTextture> drinking_textture) {
        this.drinking_textture = drinking_textture;
    }


    public static class DrinkingTextture extends FilterBean implements IShopFilterInteface {
        /**
         * itemName : 圆润
         * itemValue : 3
         */

        private String itemName;
        private String itemValue;

        public DrinkingTextture() {
        }

        public DrinkingTextture(String itemName, String itemValue) {
            this.itemName = itemName;
            this.itemValue = itemValue;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getItemValue() {
            return itemValue;
        }

        public void setItemValue(String itemValue) {
            this.itemValue = itemValue;
        }

        @Override
        public String getId() {
            return itemValue;
        }

        @Override
        public String getName() {
            return itemName;
        }

        @Override
        public String getClassName() {
            return itemName;
        }

        @Override
        public String getClassId() {
            return itemValue;
        }
    }


}
