package com.fecmobile.jiubeirobot.ui.activity.cellar.manager;


import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseActivity;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.bean.CheckCodeBean;
import com.fecmobile.jiubeirobot.bean.ObjBean;
import com.fecmobile.jiubeirobot.common.Constants;
import com.fecmobile.jiubeirobot.data.net.APIManager;
import com.fecmobile.jiubeirobot.data.net.FLags;
import com.fecmobile.jiubeirobot.utils.BasicTool;
import com.fecmobile.jiubeirobot.utils.RegexUtils;
import com.fecmobile.jiubeirobot.utils.T;
import com.fecmobile.jiubeirobot.utils.TimeCount;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 找回密码
 */
public class SystemForgetPassWordFoundActivity extends BaseActivity {

    @BindView(R.id.llyt_back)
    LinearLayout mLlytBack;
    @BindView(R.id.llyt_forget_title)
    LinearLayout mLlytForgetTitle;
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
    private String mAccount;
    private String mUuid;
    private String mType;

    @Override
    public int pageLayout() {
        return R.layout.activity_system_forget_pass_word_found;
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {
        initIntent();

//        mTvGetCode.setEnabled(false);
//        TimeCount timeCount = new TimeCount(this, 60 * 1000, 1000, mTvGetCode);
//        timeCount.start();

    }

    private void initIntent() {
        mAccount = getIntent().getStringExtra("account");
//        mUuid = getIntent().getStringExtra("uuid");
        mType = getIntent().getStringExtra("type");
    }

    @Override
    public void onSuccess(BaseBean bean, int flag) {
        switch (flag) {
            case FLags.FLAG_SEND_CHECK_CODE:

                BaseBean<ObjBean<CheckCodeBean>> checkCodeBean = bean;
                String uuid = checkCodeBean.getData().getObj().getUuid();
                mUuid = uuid;

//                mTvGetCode.setClickable(false);
//                TimeCount timeCount = new TimeCount(this, 60000, 1000, mTvGetCode);
//                timeCount.onTick(6 * 1000);
//                timeCount.start();

                break;
            case FLags.FLAG_FORGET_PASSWORD:
                T.showToCenter(getString(R.string.cellar_manage_setting_password_change_success));
                Intent intent = new Intent();
                setResult(Constants.FORGET_PASSWORD_DETAIL_RESULT, intent);
                finish();

                break;
        }
        dismissHUD();

    }

    @OnClick({R.id.llyt_back, R.id.btn_exit, R.id.tv_get_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llyt_back:
                this.finish();
                break;

            case R.id.btn_exit:
                String sms_code = mEtSmsCode.getText().toString().trim();
                String password = mEtSettingPassword.getText().toString().trim();
                String password_agin = mEtSettingPasswordAgain.getText().toString().trim();
                if (checkChangePassword(sms_code, password, password_agin)) {
                    APIManager.getInstance().getforgetPassword(this, mAccount, password, password_agin, sms_code, mUuid);
                }
                break;
            case R.id.tv_get_code:
                TimeCount timeCount = new TimeCount(this, 60 * 1000, 1000, mTvGetCode);
                timeCount.start();
                APIManager.getInstance().getCheckCode(this, this, mAccount, mType);
                break;
        }
    }

    private boolean checkChangePassword(String sms_code, String password, String password_agin) {
        char[] chars = password.toCharArray();
        char[] chars_agin = password_agin.toCharArray();
        if (!BasicTool.isNotEmpty(sms_code)) {
            T.showToCenter(getString(R.string.cellar_manage_check_code_not_null));
            return false;
        }

        if (!BasicTool.isNotEmpty(password)) {
            T.showToCenter(getString(R.string.cellar_manage_please_setting_new_password));
            return false;
        }
        if (RegexUtils.checkChinese(password) || chars.length < 6 || chars.length > 20) {
            T.showToCenter(getString(R.string.cellar_manage_password_rule_not_please_setting));
            return false;
        }
        if (!BasicTool.isNotEmpty(password_agin)) {
            T.showToCenter(getString(R.string.cellar_manage_please_confirm_new_password));
            return false;
        }
        if (!password.equals(password_agin)) {
            T.showToCenter(getString(R.string.cellar_manage_password_not_same_please_setting));
            return false;
        }

        if (RegexUtils.checkChinese(password_agin) || chars_agin.length < 6 || chars_agin.length > 20) {
            T.showToCenter(getString(R.string.cellar_manage_password_rule_not_please_setting));
            return false;
        }

        return true;
    }
}
