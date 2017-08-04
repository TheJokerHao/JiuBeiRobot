package com.fecmobile.jiubeirobot.ui.activity.cellar.manager;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseActivity;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.common.Activitys;
import com.fecmobile.jiubeirobot.common.Constants;
import com.fecmobile.jiubeirobot.utils.RegexUtils;
import com.fecmobile.jiubeirobot.utils.T;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类描述    : 忘记密码
 * 包名      : com.fecmobile.jiubeirobot.ui.activity.cellar.manager
 * 类名称    : SystemForgetPassWordActivity
 * 创建人    : wangxing
 * 创建时间  :  2017/3/17   15:11
 * 修改人    :
 * 修改时间  :
 * 修改备注  :在手机号码的输入框为空的情况下任然可以点击下一步  这个操作
 * 修改为手机号为空的情况下 不能点击下一步的按钮 并且有判定当前输入的号码是否为手机号
 */

public class SystemForgetPassWordActivity extends BaseActivity {

    @BindView(R.id.llyt_back)
    LinearLayout mLlytBack;
    @BindView(R.id.et_phone_number)
    EditText mEtPhoneNumber;
    @BindView(R.id.btn_exit)
    Button mBtnExit;

    private String mAccount;
    private String mType;
    private String flag;
    private String mAid;
    private String mPhone_number;

    @Override
    public int pageLayout() {
        return R.layout.activity_system_forget_pass_word;
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {
        //1 注册2 验证 3找回密码 4. 修改密码
        flag = "3";
        mType = flag;
    }

    @Override
    public void onSuccess(BaseBean bean, int flag) {
//        BaseBean<ObjBean<CheckCodeBean>> checkCodeBean = bean;
//        String uuid = checkCodeBean.getData().getObj().getUuid();
//        mPhone_number = mEtPhoneNumber.getText().toString().trim();
//        Activitys.toSystemForgetPassWordFoundActivity(this,mPhone_number,uuid,mType);
//        dismissHUD();
    }


    @OnClick({R.id.llyt_back, R.id.btn_exit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llyt_back:
                this.finish();
                break;
            case R.id.btn_exit:
//                BaseBean<ObjBean<CheckCodeBean>> checkCodeBean = bean;
//                String uuid = checkCodeBean.getData().getObj().getUuid();
                //TODO  修改的操作  判定当前的输入框是否为空  以及当前所输入的号码是否是手机号码
                //我觉得可以修改的地方
                mPhone_number = mEtPhoneNumber.getText().toString().trim();
                if (checkNext(mPhone_number)) {
                    Activitys.toSystemForgetPassWordFoundActivity(this, mPhone_number, mType);
//                    dismissHUD();
                } else {
                    T.showToCenter("请输入手机号码");
                }
                break;
        }
    }

    /**
     * 描述      :   校验手机号码
     * 方法名    :  checkNext
     * param    :
     * 返回类型  :
     * 创建人    :wangxing
     * 创建时间  :
     * 修改人    :
     * 修改时间
     * 修改备注
     * throws
     */
    private boolean checkNext(String phone_number) {
        //成功TRUE   失败 FALSE
        if (RegexUtils.checkMobile(phone_number)) {
            return true;
        }
        T.showToCenter(getString(R.string.cellar_manage_setting_phone_number_format_fail));
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Constants.FORGET_PASSWORD_RESULT == requestCode && Constants.FORGET_PASSWORD_DETAIL_RESULT == resultCode) {
            this.finish();
        }
    }
}
