package com.fecmobile.jiubeirobot.ui.dialog.user;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fecmobile.jiubeirobot.BuildConfig;
import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseActivity;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.base.BaseData;
import com.fecmobile.jiubeirobot.base.BaseDialogFragment;
import com.fecmobile.jiubeirobot.bean.IsRegister;
import com.fecmobile.jiubeirobot.bean.ObjBean;
import com.fecmobile.jiubeirobot.data.net.APIManager;
import com.fecmobile.jiubeirobot.data.net.FLags;
import com.fecmobile.jiubeirobot.utils.DetectTool;
import com.fecmobile.jiubeirobot.utils.GlideImageLoadImpl;
import com.fecmobile.jiubeirobot.utils.RegexUtils;
import com.fecmobile.jiubeirobot.utils.T;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类描述    :注册对话框
 * 包名      : com.fecmobile.jiubeirobot.ui.dialog.user
 * 类名称    : RegisterUserDialog
 * 创建人    : ghy
 * 创建时间  : 2017/2/24 17:09
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */

public class RegisterUserDialog extends BaseDialogFragment {
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.iv_qr)
    ImageView ivQr;

    private RegisterPhoneCodeCheckDialog registerPhoneCodeCheckDialog;

    @Override
    protected int layout() {
        return R.layout.dialog_user_register;
    }

    @Override
    protected ViewGroup.LayoutParams setLayoutParams() {
        return new FrameLayout.LayoutParams((int) (DetectTool.getResolutionX(getContext()) * 0.6), LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void initView() {
        GlideImageLoadImpl.getInstance().load(this, BaseData.getBaseData().getProtocolsInfoBean().getSite_wx_pic(), ivQr);
        if (BuildConfig.DEBUG) {
            Logger.d("print", "initView: 加载公众号的网址" + BaseData.getBaseData().getProtocolsInfoBean().getSite_wx_pic());
        }
    }

    @OnClick({R.id.iv_close, R.id.btn_next})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.btn_next:
                if (RegexUtils.checkMobile(etPhone.getText() + "")) {
                    APIManager.getInstance().checkAccountIsRegister(getContext(), this, etPhone.getText() + "");
                } else {
                    T.showToCenter(getString(R.string.common_point_phone_format_error));
                }
                break;
        }
    }

    @Override
    public void onSuccess(BaseBean bean, int flag) {
        super.onSuccess(bean, flag);
        switch (flag) {
            case FLags.FLAG_ACCOUT_IS_REGSITER:
                BaseBean<ObjBean<IsRegister>> isRegBea = bean;
                IsRegister isRegister = isRegBea.getData().getObj();
                if (isRegister.getState() == 0) {
                    if (getActivity() != null) {
                        if (registerPhoneCodeCheckDialog == null) {
                            registerPhoneCodeCheckDialog = new RegisterPhoneCodeCheckDialog();
                        }
                        registerPhoneCodeCheckDialog.setPhone(etPhone.getText() + "");
                        registerPhoneCodeCheckDialog.show(getActivity().getSupportFragmentManager(), "rpcc");
                    }
                    dismiss();
                } else {
                    T.showToCenter(isRegister.getMsg());
                }
                break;
        }
        ((BaseActivity) getActivity()).dismissHUD();
    }
}