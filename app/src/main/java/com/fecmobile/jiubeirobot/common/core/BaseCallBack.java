package com.fecmobile.jiubeirobot.common.core;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 类作用：
 * Created by liubp on 16/1/10.
 */
public abstract class BaseCallBack<T> {

    public int flag;

    public abstract void requestBefore(Request request);

    public abstract void onFailure(Call call, IOException e);

    public abstract void onSuccess(Response response, T t, int flag);

    public abstract void onError(Response response, int code, Exception e);

    public abstract void onResponse(Response response);

}
