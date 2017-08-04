package com.fecmobile.jiubeirobot.base;

import android.app.Application;
import android.content.Intent;

import com.fecmobile.jiubeirobot.BuildConfig;
import com.fecmobile.jiubeirobot.service.ScannerService;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.lzy.okhttputils.OkHttpUtils;


import butterknife.ButterKnife;

/**
 * 类描述    :应用上下文，应用生命周期管理，在这里初始化等
 * 包名      : com.fecmobile.jiubeirobot.base
 * 类名称    : BaseApplication
 * 创建人    : ghy
 * 创建时间  : 2017/2/21 19:59
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class BaseApplication extends Application {
    private ActivityControl activityControl;
    private static BaseApplication baseApplication;
    public static int currentMode;//定义的购买模式
    public final static int FAST_BUY = 0;//快速购买
    public final static int MEMBER_BUY = 1;//会员购买
    public final static int LOCAL_BUY = 2;//新增的本地购买'
//    private RefWatcher refWatcher;

    public static BaseApplication getInstance() {
        return baseApplication;
    }


//    public static RefWatcher getRefWatcher(Context context) {
//        BaseApplication application = (BaseApplication) context.getApplicationContext();
//        return application.refWatcher;
//    }

    @Override
    public void onCreate() {
        super.onCreate();
        baseApplication = this;
        activityControl = ActivityControl.getActivityControl();
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + "=591d53bb");

        ButterKnife.setDebug(BuildConfig.DEBUG);

        //启动检测扫码枪的后台服务
        Intent intent = new Intent(this, ScannerService.class);
        startService(intent);
        OkHttpUtils.init(this);
        if (BuildConfig.DEBUG) {
//            com.wanjian.sak.SAK.init(this);
//            refWatcher = LeakCanary.install(this);
        }

    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        exitApp();
        Intent intent = new Intent(this, ScannerService.class);
        stopService(intent);
    }

    /**
     * 描述      : 退出APP
     * 方法名    :  exitApp
     * param    :   无
     * 返回类型  : void
     * 创建人    : ghy
     * 创建时间  : 2017/2/21 20:00
     * 修改人    :
     * 修改时间
     * 修改备注
     * throws
     */
    public void exitApp() {
        activityControl.finishiAll();
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }

}