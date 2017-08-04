package com.fecmobile.jiubeirobot.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.fecmobile.jiubeirobot.data.net.INetResult;
import com.fecmobile.jiubeirobot.utils.L;
import com.fecmobile.jiubeirobot.utils.T;

import butterknife.ButterKnife;

/**
 * 类描述    :Fragment基类
 * 包名      : com.fecmobile.jiubeirobot.base
 * 类名称    : BaseFragment
 * 创建人    : ghy
 * 创建时间  : 2017/4/17 9:57
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public abstract class BaseFragment extends Fragment implements IUIBase, INetResult {
    BaseActivity activity;
    private View theContentView;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activity = (BaseActivity) getActivity();
        theContentView = inflater.inflate(pageLayout(), null);
        ButterKnife.bind(this, theContentView);
        initTitle();
        initView();
        theContentView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (getActivity().getCurrentFocus() != null && getActivity().getCurrentFocus().getWindowToken() != null) {
                        manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
                return false;
            }
        });
        return theContentView;
    }

    @Override
    public void onResume() {
        super.onResume();
        L.i("----当前运行的类：" + getClass().getName());//The Current Runing Class
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
//        RefWatcher refWatcher = BaseApplication.getRefWatcher(getActivity());
//        refWatcher.watch(this);
    }

    @Override
    public void requestBefore(int flag) {
        showHUD();
    }

    @Override
    public void onError(String error, int flag) {
        dismissHUD();
        T.showLong(error);
    }

    @Override
    public void dismissHUD() {
        if (activity != null) {
            activity.dismissHUD();
        }
    }

    @Override
    public void showHUD() {
        if (activity != null) {
            activity.showHUD();
        }
    }
}
