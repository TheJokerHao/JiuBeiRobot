package com.fecmobile.jiubeirobot.data.net;

import android.content.Context;

import org.json.JSONObject;

import java.lang.reflect.Type;

/**
 * Created by Administrator on 2017/2/18.
 */

public interface INetLoad {
    void loadData(int flag, String relativeUrl, JSONObject params, Context context, INetResult iNetResult, final Type beanClz);

    void loadData2(int flag, String relativeUrl, JSONObject params, Context context, INetResult iNetResult, final Type beanClz);
}
