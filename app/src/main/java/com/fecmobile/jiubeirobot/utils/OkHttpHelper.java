package com.fecmobile.jiubeirobot.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.fecmobile.jiubeirobot.BuildConfig;
import com.fecmobile.jiubeirobot.common.core.BaseCallBack;
import com.fecmobile.jiubeirobot.common.core.HUDCallBack;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.FileCallback;
import com.orhanobut.logger.Logger;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


/**
 * 类作用：okhttp联网
 * Created by liubp on 16/1/10.
 */
public class OkHttpHelper {
    private static OkHttpHelper okHttpHelper;

    private static OkHttpClient okHttpClient;

    private Handler handler;
    private Gson gson;

    private OkHttpHelper() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder()
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS);
        okHttpClient = builder.build();

        gson = new Gson();
        handler = new Handler(Looper.getMainLooper());//获取主线程的Looper对象来构造一个handler

    }

    public static synchronized OkHttpHelper getInstance() {
        if (null == okHttpHelper) {
            okHttpHelper = new OkHttpHelper();
        }
        return okHttpHelper;
    }

    /**
     * get方式联网请求数据
     */
    public void get(Context context, String relativeUrl, JSONObject params, HUDCallBack baseCallBack, Type clz) {
        try {
            Request request = buildRequest(context, getAbsoluteUrl(context, relativeUrl), params, HttpMethodType.GET);
            doRequest(context, request, baseCallBack, clz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * get方式联网请求数据
     */
    public void get2(Context context, String relativeUrl, JSONObject params, HUDCallBack baseCallBack, Type clz) {
        try {
            Request request = buildRequest(context, getAbsoluteUrl2(context, relativeUrl), params, HttpMethodType.GET);
            doRequest(context, request, baseCallBack, clz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * post方式联网请求数据
     */
    public void post(Context context, String relativeUrl, JSONObject params, HUDCallBack baseCallBack, Type clz) {
        try {
            Request request = buildRequest(context, getAbsoluteUrl(context, relativeUrl), params, HttpMethodType.POST);
            doRequest(context, request, baseCallBack, clz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * post方式联网请求数据
     */
    public void post2(Context context, String relativeUrl, JSONObject params, HUDCallBack baseCallBack, Type clz) {
        try {
//            Request request = buildRequest(context, getAbsoluteUrl(context, relativeUrl), params, HttpMethodType.POST);  //改了一下
            Request request = buildRequest(context, getAbsoluteUrl2(context, relativeUrl), params, HttpMethodType.POST);
            doRequest(context, request, baseCallBack, clz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void postNoEncrypt(Context context, String relativeUrl, JSONObject params, HUDCallBack baseCallBack, Type clz) {
        try {
            Request request = buildRequestNo(context, getAbsoluteUrl(context, relativeUrl), params, HttpMethodType.POST);
            doRequest(context, request, baseCallBack, clz);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Request buildRequestNo(Context context, String url, JSONObject params, HttpMethodType httpMethodType) {
        try {

            Request.Builder builder = new Request.Builder();

            if (httpMethodType == HttpMethodType.GET) {
                if (!url.endsWith("?")) {
                    url += "?";
                }
                if (null != params) {
                    Iterator<String> it = params.keys();
                    while (it.hasNext()) {
                        String key = it.next();
                        url += key + "=" + params.getString(key) + "&";
                    }

                    url = url.substring(0, url.length() - 1);
                }

                builder.url(url);
                builder.get();
                L.i("OkHttp GET url:" + url);

            } else if (httpMethodType == HttpMethodType.POST) {
                FormBody.Builder formBodyBuilder = new FormBody.Builder();
                String relativeUrl = "";
                if (null != params) {
                    Iterator<String> it = params.keys();
                    while (it.hasNext()) {
                        String key = it.next();
                        formBodyBuilder.add(key, params.getString(key));
                        relativeUrl += key + "=" + params.getString(key) + "&";
                    }
                    relativeUrl = relativeUrl.substring(0, relativeUrl.length() - 1);
                }

                RequestBody body = formBodyBuilder.build();

                builder.url(url);
                builder.post(body);

                L.i("OkHttp POST url:" + url + "?" + relativeUrl);


                L.i("\n\n\n\n-------------------------------------------------------------------------------------------------------------------------------");
                L.i("PRETTYLOGGER url:" + url + "  params:" + params.toString());
                L.i("-------------------------------------------------------------------------------------------------------------------------------\n\n\n\n");
            }
            return builder.build();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void doRequest(final Context context, Request request, final HUDCallBack baseCallBack, final Type clz) {
        baseCallBack.requestBefore(request);
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        baseCallBack.onFailure(call, e);
                    }
                });
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                baseCallBack.onResponse(response);
                if (response.isSuccessful()) {
                    try {
                        String resStr = response.body().string();

                        L.e("\n\n\nURL：" + response.request().url() + "\n\n\n");
                        L.e("\n\n\nresStr：" + resStr);

                        JSONObject jsonObject = new JSONObject(resStr);

                        if (BuildConfig.DEBUG) {
                            Logger.json(resStr);
                        }

                        if (!jsonObject.getString("code").equals("000")) {
                            callBackFail(context, jsonObject.getString("message"));
                            return;
                        }
                        if ((jsonObject.getJSONObject("data").getString("state")).equals("0")) {
                            callBackSuccess(response, baseCallBack, gson.fromJson(resStr, clz));
                        } else {
                            callBackError(context, response, baseCallBack, jsonObject.getJSONObject("data").getString("msg"));
                        }

                    } catch (JsonSyntaxException e) {
                        L.i(e.toString());
                        callBackError(context, response, baseCallBack, "数据错误");
                    } catch (Exception e) {
                        L.i(e.toString());
                        callBackError(context, response, baseCallBack, "数据错误");
                    }
                } else {
                    callBackError(context, response, baseCallBack, "连接超时");
                }
            }
        });
    }

    /**
     * 根据网络请求方式构建联网请求时所需的request
     *
     * @param httpMethodType 联网请求方式
     * @return 联网所需的request
     */
    private Request buildRequest(Context context, String url, JSONObject params, HttpMethodType httpMethodType) throws Exception {

        String si = null;
        String pa = null;

        HashMap<String, String> param = new HashMap<String, String>();
        if (null == params) {
            params = new JSONObject();
        }
        JSONObject paObject = getPaObject(context, params);


        Iterator<String> it = paObject.keys();
        while (it.hasNext()) {
            String key = it.next().toString();
//            param.put(key, DetectTool.chinaToUnicode(paObject.getString(key)));
            param.put(key, paObject.getString(key));
        }

        si = DetectTool.getSign(param);//签名
        pa = EncryptUtil.encrypt(paObject.toString());//DES加密

        Request.Builder builder = new Request.Builder();

        builder.url(url);

        if (httpMethodType == HttpMethodType.GET) {
            if (null != params) {

                url += "si=" + si + "&pa=" + pa;
            }

            builder.url(url);
            builder.get();
            L.i("OkHttp GET url:" + url);

        } else if (httpMethodType == HttpMethodType.POST) {


            FormBody.Builder encodingBuilder = new FormBody.Builder();

            encodingBuilder.add("si", si);
            encodingBuilder.add("pa", pa);

            RequestBody body = encodingBuilder.build();


            builder.url(url);
            builder.post(body);

            L.i("OkHttp POST url:" + url + "?si=" + si + "&pa=" + pa);

        }

        return builder.build();
    }


    private void callBackSuccess(final Response response, final BaseCallBack baseCallBack, final Object object) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                baseCallBack.onSuccess(response, object, baseCallBack.flag);
            }
        });
    }

    private void callBackError(final Context context, final Response response, final BaseCallBack baseCallBack, final String error) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                baseCallBack.onError(response, 900, new Exception(error));
            }
        });
    }

    private void callBackFail(final Context context, final String message) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                T.showShort(message);
            }
        });
    }


    /**
     * 根据相对路径获取全路径
     * TODO  这里可以修改网络请求的数据URL的拼接
     *
     * @param context     上下文对象
     * @param relativeUrl 相对路径
     * @return
     */
    private static String getAbsoluteUrl(Context context, String relativeUrl) {
        Log.d("print", "getAbsoluteUrl: " + BuildConfig.SERVER_IP);
        return BuildConfig.SERVER_IP + relativeUrl;

    }

    /**
     * 定义本地购买的拼接
     *
     * @param context
     * @param relativeUrl
     * @return
     */
    private static String getAbsoluteUrl2(Context context, String relativeUrl) {
        Log.d("print", "getAbsoluteUrl2: " + BuildConfig.SERVER_IP2);
        return BuildConfig.SERVER_IP2 + relativeUrl;
    }

    private static JSONObject getPaObject(Context context, JSONObject params) throws JSONException {
        JSONObject allDataObject = new JSONObject();
        Iterator<String> it = params.keys();
        String v = null;
        while (it.hasNext()) {
            String key = it.next().toString();
//            allDataObject.put(key, DetectTool.chinaToUnicode(params.getString(key)));
            allDataObject.put(key, params.getString(key));
            L.i("***************************key：" + key + "    val：" + params.getString(key));
            if ("v".equals(key)) {
                v = params.getString(key);
            }
        }

        String ts = DetectTool.getTS();

        L.i("--------------------------------------时间" + ts);
        allDataObject.put("m", DetectTool.getType());
        allDataObject.put("p", DetectTool.getAppType());
        allDataObject.put("u", DetectTool.getToken());
        //TODO  修改来获取版本名称的获取方式
//        allDataObject.put("v", v != null ? v : DetectTool.getVersionName());
        allDataObject.put("v", v != null ? v : DetectTool.getAPPVersionCodeFromAPP(context));
        allDataObject.put("i", DetectTool.getIMEI(context));
        allDataObject.put("t", ts);
        return allDataObject;
    }

    /**
     * 下载文件  基本的get请求
     *
     * @param context
     * @param appUrl
     * @param 下载失败
     */
    public void downloadFile(Context context, String appUrl, FileCallback 下载失败) {
        OkHttpUtils.getInstance()
                .get(appUrl)
                .tag(context)
                .connTimeOut(10000)      // 设置当前请求的连接超时时间
                .readTimeOut(10000)      // 设置当前请求的读取超时时间
                .writeTimeOut(10000)     // 设置当前请求的写入超时时间
                .cacheKey("downloadCacheKey")    // 设置当前请求的缓存key,建议每个不同功能的请求设置一个
                .cacheTime(5000)
                .execute(下载失败);

    }

//    public static String chinaToUnicode(String str) {
//        String result = "";
//        for (int i = 0; i < str.length(); i++) {
//            int chr1 = (char) str.charAt(i);
//            if (chr1 >= 19968 && chr1 <= 171941) {
//                result += "\\u" + Integer.toHexString(chr1);
//            } else {
//                result += str.charAt(i);
//            }
//        }
//        return result;
//    }

    /**
     * 枚举，联网请求的联网方式
     */
    enum HttpMethodType {
        GET, POST
    }
}