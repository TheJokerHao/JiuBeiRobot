package com.fecmobile.jiubeirobot.data.net;

import android.content.Context;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.common.core.HUDCallBack;
import com.fecmobile.jiubeirobot.utils.L;
import com.fecmobile.jiubeirobot.utils.OkHttpHelper;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/2/18.
 * 实现了网络数据加载的接口 进而对相应的返回数据进行判定
 */


public class NetLoadImpl implements INetLoad {

    /**
     * 这个方法就是拼接  和请求的地方
     *
     * @param flag        请求返回数据的flag
     * @param relativeUrl 本地拼接的URL
     * @param params      参数
     * @param context     当前的上下文（分为activity  或者是 fragment）
     * @param iNetResult  返回数据监听的剪口 和上下文有相互联系
     * @param beanClz     TypeToken  返回需要的数据类型
     */
    @Override
    public void loadData(int flag, String relativeUrl, JSONObject params, Context context, final INetResult iNetResult, final Type beanClz) {

        //这里是定义的使用OKhttp来请求数据
        OkHttpHelper.getInstance().post(context, relativeUrl, params, new HUDCallBack<BaseBean>(context, flag) {
            @Override
            public void requestBefore(Request request) {
                iNetResult.requestBefore(flag);
                L.i("requestBefore");
            }

            @Override
            public void onFailure(Call call, IOException e) {
                iNetResult.onError(context.getString(R.string.error_call_back_no_error), flag);
                L.i("onFailure");
            }

            @Override
            public void onSuccess(Response response, BaseBean obj, int flag) {
                if (obj != null) {
                    L.i("obj!=null onSuccess");
                    iNetResult.onSuccess(obj, flag);
                } else {
                    L.i("obj==null onSuccess");
                    iNetResult.onError(context.getString(R.string.error_call_back_no_error), flag);
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                String error = null;
                if (900 == code) {
                    L.i("code == 900 state == 1");
                    try {
                        error = e.getMessage();
                    } catch (Exception e1) {
                        L.i("  line error " + e1);
                        error = context.getString(R.string.error_call_back_no_error);
                    }
                } else {
                    switch (response.code()) {
                        case 500:
                            error = context.getString(R.string.error_call_back_server_error);
                            break;
                        case 404:
                            error = context.getString(R.string.error_call_back_no_param);
                            break;
                        case 400:
                            error = context.getString(R.string.error_call_back_param_error);
                            break;
                        case 403:
                            error = context.getString(R.string.error_call_back_no_permission);
                            break;
                        case 10000:  //保存的用户信息过期
                            error = context.getString(R.string.error_call_back_user_info_outtime);
                            break;
                        case 10001:  //没有操作权限
                            error = context.getString(R.string.error_call_back_no_permission);
                            break;
                        default:
                            error = context.getString(R.string.error_call_back_no_error);
                            break;
                    }
                }
                iNetResult.onError(error, flag);
            }

            @Override
            public void onResponse(Response response) {
                L.i("onResponse");
            }
        }, beanClz);

    }

    /**
     * 本地购买的网络数据
     *
     * @param flag
     * @param relativeUrl
     * @param params
     * @param context
     * @param iNetResult
     * @param beanClz
     */
    @Override
    public void loadData2(int flag, String relativeUrl, JSONObject params, Context context, final INetResult iNetResult, Type beanClz) {
        //这里是定义的使用OKhttp来请求数据
        OkHttpHelper.getInstance().post2(context, relativeUrl, params, new HUDCallBack<BaseBean>(context, flag) {
            @Override
            public void requestBefore(Request request) {
                iNetResult.requestBefore(flag);
                L.i("requestBefore");
            }

            @Override
            public void onFailure(Call call, IOException e) {
                iNetResult.onError(context.getString(R.string.error_call_back_no_error), flag);
                L.i("onFailure");
            }

            @Override
            public void onSuccess(Response response, BaseBean obj, int flag) {
                if (obj != null) {
                    //这里可以判断是数据是否成功
                    L.i("obj!=null onSuccess");
                    iNetResult.onSuccess(obj, flag);
                    L.i("obj!=null onSuccess" + obj + "flag==" + flag);
                } else {
                    L.i("obj==null onSuccess");
                    iNetResult.onError(context.getString(R.string.error_call_back_no_error), flag);
                }
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                String error = null;
                if (900 == code) {
                    L.i("code == 900 state == 1");
                    try {
                        error = e.getMessage();
                    } catch (Exception e1) {
                        L.i("  line error " + e1);
                        error = context.getString(R.string.error_call_back_no_error);
                    }
                } else {
                    switch (response.code()) {
                        case 500:
                            error = context.getString(R.string.error_call_back_server_error);
                            break;
                        case 404:
                            error = context.getString(R.string.error_call_back_no_param);
                            break;
                        case 400:
                            error = context.getString(R.string.error_call_back_param_error);
                            break;
                        case 403:
                            error = context.getString(R.string.error_call_back_no_permission);
                            break;
                        case 10000:  //保存的用户信息过期
                            error = context.getString(R.string.error_call_back_user_info_outtime);
                            break;
                        case 10001:  //没有操作权限
                            error = context.getString(R.string.error_call_back_no_permission);
                            break;
                        default:
                            error = context.getString(R.string.error_call_back_no_error);
                            break;
                    }
                }
                iNetResult.onError(error, flag);
            }

            @Override
            public void onResponse(Response response) {
                L.i("onResponse");
            }
        }, beanClz);
    }

}
