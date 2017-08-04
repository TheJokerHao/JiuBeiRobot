package com.fecmobile.jiubeirobot.common.pullableview;

@Deprecated
public interface Pullable {
    /**
     * 判断是否可以下拉，如果不需要下拉功能可以直接return false
     *
     * @return true如果可以下拉否则返回false
     */
    boolean canPullDown();

    /**
     * 判断是否可以上拉，如果不需要上拉功能可以直接return false
     *
     * @return true如果可以上拉否则返回false
     */
    boolean canPullUp();

    /**
     * 设置是否可以上啦加载更多
     *
     * @param canPullUp true 可以 false 不可以
     */
    void setPullUp(boolean canPullUp);

    /**
     * 设置是否可以下拉刷新
     *
     * @param canPullDown true 可以 false 不可以
     */
    void setPullDown(boolean canPullDown);
}
