package com.fecmobile.jiubeirobot.bean;

/**
 * 类描述    :品种
 * 包名      : com.fecmobile.jiubeirobot.bean
 * 类名称    : GrapeVarietyBean
 * 创建人    : ghy
 * 创建时间  : 2017/3/10 16:26
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */

public class GrapeVarietyBean extends FilterBean {

    /**
     * grapeName : 佳美（Gamay）
     * grapeId : 47
     */

    private String grapeName;
    private String grapeId;

    public String getGrapeName() {
        return grapeName;
    }

    public void setGrapeName(String grapeName) {
        this.grapeName = grapeName;
    }

    public String getGrapeId() {
        return grapeId;
    }

    public void setGrapeId(String grapeId) {
        this.grapeId = grapeId;
    }

    @Override
    public String getId() {
        return grapeId;
    }

    @Override
    public String getName() {
        return grapeName;
    }
}
