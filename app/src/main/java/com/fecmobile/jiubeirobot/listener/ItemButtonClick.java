package com.fecmobile.jiubeirobot.listener;


/**
 * 类描述    : 条目中按钮操作回调
 * 包名      : com.fecmobile.jiubeirobot.listener
 * 类名称    : ItemButtonClick
 * 创建人    : lc
 * 创建时间  :  2017-03-16
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public interface ItemButtonClick {

    /**
     * 描述      :
     * 方法名    :  onItemButtonClick
     * param    :  postion :条目序号，type 当前操作类型
     * 返回类型  :  void
     * 创建人    : lc
     * 创建时间  : 2017-03-16
     * 修改人    :
     * 修改时间
     * 修改备注
     * throws
     */
    void onItemButtonClick(int position, Object type);
}
