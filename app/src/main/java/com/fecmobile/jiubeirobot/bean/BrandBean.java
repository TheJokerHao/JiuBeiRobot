package com.fecmobile.jiubeirobot.bean;

/**
 * 类描述    :品牌
 * 包名      : com.fecmobile.jiubeirobot.bean
 * 类名称    : BrandBean
 * 创建人    : ghy
 * 创建时间  : 2017/3/10 18:19
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */

public class BrandBean extends FilterBean {

    /**
     * brandName : 拉图酒庄
     * brandId : 20
     */

    private String brandName;
    private String brandId;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandId() {
        return brandId;
    }

    public void setBrandId(String brandId) {
        this.brandId = brandId;
    }

    @Override
    public String getId() {
        return brandId;
    }

    @Override
    public String getName() {
        return brandName;
    }
}
