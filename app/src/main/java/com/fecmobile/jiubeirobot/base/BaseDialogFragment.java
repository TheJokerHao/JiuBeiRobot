package com.fecmobile.jiubeirobot.base;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.data.net.INetResult;
import com.fecmobile.jiubeirobot.utils.L;
import com.fecmobile.jiubeirobot.utils.T;

import butterknife.ButterKnife;


/**
 * 类描述    :Dialog弹框基类
 * 包名      : com.fecmobile.jiubeirobot.base
 * 类名称    : BaseDialogFragment
 * 创建人    : ghy
 * 创建时间  : 2017/2/24 17:38
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public abstract class BaseDialogFragment extends DialogFragment implements INetResult {
    private Dialog dialog;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(
                layout(), null);
        dialog = new Dialog(getContext(), R.style.DeleteStyle);
        dialog.setContentView(view);
        ButterKnife.bind(this, view);
        view.findViewById(R.id.lLayout_bg).setLayoutParams(setLayoutParams());
        initView();

        return dialog;
    }

    @Override
    public void requestBefore(int flag) {
        ((BaseActivity) getActivity()).showHUD();
    }

    @Override
    public void onError(String error, int flag) {
        ((BaseActivity) getActivity()).dismissHUD();
        T.showToCenter(error);
    }

    @Override
    public void onSuccess(BaseBean bean, int flag) {

    }

    @Override
    public void onResume() {
        super.onResume();
        L.i("----当前运行的类：" + getClass().getName());//The Current Runing Class
    }

    /**
     * 描述      :Dialog布局
     * 方法名    :  layout
     * param    :   void
     * 返回类型  : int 布局ID
     * 创建人    : ghy
     * 创建时间  : 2017/2/24 17:38
     * 修改人    :
     * 修改时间
     * 修改备注
     * throws
     */
    protected abstract int layout();

    /**
     * 描述      :设置布局参数
     * 方法名    :setLayoutParams
     * param    :void
     * 返回类型  :ViewGroup.LayoutParams 布局参数
     * 创建人    : ghy
     * 创建时间  : 2017/2/24 17:39
     * 修改人    :
     * 修改时间
     * 修改备注
     * throws
     */
    protected abstract ViewGroup.LayoutParams setLayoutParams();

    /**
     * 描述      :初始化页面
     * 方法名    :initView
     * param    :void
     * 返回类型  :void
     * 创建人    : ghy
     * 创建时间  : 2017/2/24 17:39
     * 修改人    :
     * 修改时间
     * 修改备注
     * throws
     */
    protected abstract void initView();
}
