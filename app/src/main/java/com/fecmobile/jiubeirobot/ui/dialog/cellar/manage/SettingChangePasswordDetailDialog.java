package com.fecmobile.jiubeirobot.ui.dialog.cellar.manage;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseActivity;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.base.BaseDialogFragment;
import com.fecmobile.jiubeirobot.bean.CheckCodeBean;
import com.fecmobile.jiubeirobot.bean.ObjBean;
import com.fecmobile.jiubeirobot.data.net.APIManager;
import com.fecmobile.jiubeirobot.data.net.FLags;
import com.fecmobile.jiubeirobot.utils.BasicTool;
import com.fecmobile.jiubeirobot.utils.DetectTool;
import com.fecmobile.jiubeirobot.utils.RegexUtils;
import com.fecmobile.jiubeirobot.utils.T;
import com.fecmobile.jiubeirobot.utils.TimeCount;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类描述    : 修改密码的详情｜找回密码的详情
 * 包名      : com.fecmobile.jiubeirobot.ui.dialog.cellar.manage
 * 类名称    : SettingChangePasswordDetailDialog
 * 创建人    : wangxing
 * 创建时间  :  2017/3/14   17:08
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */

public class SettingChangePasswordDetailDialog extends BaseDialogFragment {

    @BindView(R.id.tv_password_title)
    TextView mTvPasswordTitle;
    @BindView(R.id.tv_send_code)
    TextView mTvSendCode;
    @BindView(R.id.et_sms_code)
    EditText mEtSmsCode;
    @BindView(R.id.tv_get_code)
    TextView mTvGetCode;
    @BindView(R.id.et_setting_password)
    EditText mEtSettingPassword;
    @BindView(R.id.et_setting_password_again)
    EditText mEtSettingPasswordAgain;
    @BindView(R.id.btn_exit)
    Button mBtnExit;
    @BindView(R.id.iv_close_dialog)
    ImageView mIvCloseDialog;
    private String mAccount;
    private String mType;
    private String mHide_account;
    private String mCode;
    private String mUuid;
    private String mAid;

    @Override
    protected int layout() {
        return R.layout.dialog_setting_find_password_dialog;
    }

    @Override
    protected ViewGroup.LayoutParams setLayoutParams() {
        return new FrameLayout.LayoutParams((int) (DetectTool.getResolutionX(getContext()) * 0.517), LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void initView() {
        initIntent();

    }

    private void initIntent() {
        Bundle arguments = getArguments();
        mAccount = arguments.getString("account");
        mType = arguments.getString("type");
        mUuid = arguments.getString("uuid");
        mAid = arguments.getString("aid");

        mTvPasswordTitle.setText("修改登录密码");
//        mTvGetCode.setClickable(false);
//        TimeCount timeCount = new TimeCount(getContext(), 60000, 1000, mTvGetCode);
//        timeCount.onTick(60 * 1000);
//        timeCount.start();

    }

    @OnClick({R.id.btn_exit, R.id.tv_get_code, R.id.iv_close_dialog})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_exit:
                String sms_code = mEtSmsCode.getText().toString().trim();
                String password = mEtSettingPassword.getText().toString().trim();
                String password_agin = mEtSettingPasswordAgain.getText().toString().trim();
                if (checkChangePassword(sms_code, password, password_agin)) {
                    //修改密码
                    APIManager.getInstance().getChangePassword(getContext(), mAid, password, password_agin, sms_code, mUuid, this);
                }
                break;
            case R.id.tv_get_code:
                APIManager.getInstance().getCheckCode(getContext(), this, mAccount, mType);

                break;
            case R.id.iv_close_dialog:
                this.dismiss();
                break;
        }
    }

    private boolean checkChangePassword(String sms_code, String password, String password_agin) {
        char[] chars = password.toCharArray();
        char[] chars_agin = password_agin.toCharArray();
        if (!BasicTool.isNotEmpty(sms_code)) {
            T.showToCenter("验证码不能为空");
            return false;
        }

        if (!BasicTool.isNotEmpty(password)) {
            T.showToCenter("请设置新密码");
            return false;
        }
        if (RegexUtils.checkChinese(password) || chars.length < 6 || chars.length > 20) {
            T.showToCenter("密码格式错误,请设置6-20位数字、字母、符号的组合");
            return false;
        }
        if (!BasicTool.isNotEmpty(password_agin)) {
            T.showToCenter("请确认新密码");
            return false;
        }
        if (!password.equals(password_agin)) {
            T.showToCenter("密码不一致,请重新设置");
            return false;
        }

        if (RegexUtils.checkChinese(password_agin) || chars_agin.length < 6 || chars_agin.length > 20) {
            T.showToCenter("密码格式错误,请设置6-20位数字、字母、符号的组合");
            return false;
        }

        return true;
    }

    @Override
    public void onSuccess(BaseBean bean, int flag) {
        switch (flag) {
            case FLags.FLAG_SEND_CHECK_CODE:
                BaseBean<ObjBean<CheckCodeBean>> checkCodeBean = bean;
                String code = checkCodeBean.getData().getObj().getCode();
                String uuid = checkCodeBean.getData().getObj().getUuid();

                mHide_account = mAccount.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
                mTvSendCode.setText("已发送验证码至" + mHide_account);
                mCode = code;
                mUuid = uuid;
                mTvGetCode.setClickable(false);
                TimeCount timeCount = new TimeCount(getContext(), 60000, 1000, mTvGetCode);
                timeCount.onTick(60 * 1000);
                timeCount.start();
                ((BaseActivity) getActivity()).dismissHUD();
                break;
            case FLags.FLAG_CHANGE_PASSWORD:
                T.showToCenter(getString(R.string.cellar_manage_setting_password_change_success));
                SettingChangePasswordDetailDialog.this.dismiss();
                break;
        }
        ((BaseActivity) getActivity()).dismissHUD();
    }
}
