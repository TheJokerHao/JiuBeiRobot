package com.fecmobile.jiubeirobot.common.core;

import android.content.Context;

/**
 * 类作用：
 * Created by liubp on 16/1/10.
 */
public abstract class HUDCallBack<T> extends BaseCallBack<T> {
    public Context context;

    /**
     * 联网成功的回调
     *
     * @param context 上下文对象
     */
    public HUDCallBack(Context context, int flag) {
        this.context = context;
        this.flag = flag;
    }

}
