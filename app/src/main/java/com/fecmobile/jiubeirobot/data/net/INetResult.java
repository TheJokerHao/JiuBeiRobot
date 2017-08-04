package com.fecmobile.jiubeirobot.data.net;

import com.fecmobile.jiubeirobot.base.BaseBean;

/**
 * Created by Administrator on 2017/2/18.
 * 定义的请求数据返回值判断的接口
 */

public interface INetResult {

    void requestBefore(int flag);

    void onSuccess(BaseBean bean, int flag);

    void onError(String error, int flag);

}
