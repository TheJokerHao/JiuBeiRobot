package com.fecmobile.jiubeirobot.base;

/**
 * Created by Administrator on 2017/2/21.
 */

public interface IMultiItemType<T> {

    int getLayoutId(int itemType);

    int getItemViewType(int position, T t);

}
