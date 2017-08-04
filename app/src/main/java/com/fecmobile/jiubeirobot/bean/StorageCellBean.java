package com.fecmobile.jiubeirobot.bean;

/**
 * 类描述    : 酒窖库存
 * 包名      : com.fecmobile.jiubeirobot.bean
 * 类名称    : StorageCellBean
 * 创建人    : wangxing
 * 创建时间  :  2017/3/13   14:35
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */

public class StorageCellBean {

    /**
     * id : 6
     * className : 新疆白葡萄
     * productName : 科瑞丝曼波尔多珍酿红葡萄酒
     * totalStock : 50
     * mainPic : http://192.168.0.56:89/group1/M00/03/13/wKgAD1jAbTmARrUGAAAZFbxkgXY397.png
     * chateauName : 埃菲尔红葡萄酒酒庄
     */

    private String id;
    private String className;
    private String productName;
    private String totalStock;
    private String mainPic;
    private String chateauName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTotalStock() {
        return totalStock;
    }

    public void setTotalStock(String totalStock) {
        this.totalStock = totalStock;
    }

    public String getMainPic() {
        return mainPic;
    }

    public void setMainPic(String mainPic) {
        this.mainPic = mainPic;
    }

    public String getChateauName() {
        return chateauName;
    }

    public void setChateauName(String chateauName) {
        this.chateauName = chateauName;
    }
}
