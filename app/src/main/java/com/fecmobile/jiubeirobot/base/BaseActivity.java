package com.fecmobile.jiubeirobot.base;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.common.core.HUDFactory;
import com.fecmobile.jiubeirobot.data.net.INetResult;
import com.fecmobile.jiubeirobot.receivers.NetReceivers;
import com.fecmobile.jiubeirobot.utils.L;
import com.fecmobile.jiubeirobot.utils.T;
import com.kaopiz.kprogresshud.KProgressHUD;

import butterknife.ButterKnife;


/**
 * 类描述    :所有Activity公共部分，基类
 * 包名      : com.fecmobile.jiubeirobot.base
 * 类名称    : BaseActivity
 * 创建人    : ghy
 * 创建时间  : 2017/2/21 19:59
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public abstract class BaseActivity extends AppCompatActivity implements IUIBase, INetResult {
    private NetReceivers mReceiver = new NetReceivers();//网络广播
    private IntentFilter mFilter = new IntentFilter();//注册器
    public KProgressHUD kProgressHUD;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityControl.getActivityControl().addActivity(this);
        mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(mReceiver, mFilter);
        ActivityControl.getActivityControl().setCurrentAtivity(this);
        int layout = pageLayout();
        if (layout != -1) {
            setContentView(layout);
        }
        ButterKnife.bind(this);
        initTitle();
        initView();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        L.i(keyCode + "        " + event);
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void showHUD() {
        if (kProgressHUD == null) {
            kProgressHUD = HUDFactory.getInstance().creatHUD(this);
        }

        kProgressHUD.setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(getString(R.string.loading))
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.3f).show();
    }

    @Override
    public void requestBefore(int flag) {
        showHUD();
    }

    @Override
    public void onError(String error, int flag) {
        dismissHUD();
        if (error.equals("获取商品信息失败")) {
//            T.showToCenter("此酒品未录入库存或未上架");
        } else {
            Log.d("print", "onError: " + error);
            T.showToCenter(error);
        }
    }

    @Override
    public void dismissHUD() {
        if (null != kProgressHUD && kProgressHUD.isShowing()) {
            kProgressHUD.dismiss();
        } else {
            kProgressHUD.dismiss();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        L.i("--当前运行的类：" + getClass().getName());//The Current Runing Class
    }

    /**
     * 在OnDetory方法中获取当前所有的点击加载过的activity 并全部移除
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
        ActivityControl.getActivityControl().removeActivity(this);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getCurrentFocus() != null && getCurrentFocus().getWindowToken() != null) {
                manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return super.onTouchEvent(event);
    }
}
