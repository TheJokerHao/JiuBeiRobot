package com.fecmobile.jiubeirobot.bean;

/**
 * 类描述    :葡萄产地
 * 包名      : com.fecmobile.jiubeirobot.bean
 * 类名称    : GrapevineBean
 * 创建人    : ghy
 * 创建时间  : 2017/3/10 15:25
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class GrapevineBean extends FilterBean {

    /**
     * originId : 13
     * originName : 德国
     */

    private String originId;
    private String originName;

    public String getOriginId() {
        return originId;
    }

    public void setOriginId(String originId) {
        this.originId = originId;
    }

    public String getOriginName() {
        return originName;
    }

    public void setOriginName(String originName) {
        this.originName = originName;
    }

    @Override
    public String getId() {
        return originId;
    }

    @Override
    public String getName() {
        return originName;
    }
}
