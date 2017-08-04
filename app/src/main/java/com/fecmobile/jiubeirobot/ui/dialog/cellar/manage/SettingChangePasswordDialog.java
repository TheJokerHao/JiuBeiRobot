package com.fecmobile.jiubeirobot.ui.dialog.cellar.manage;


import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.base.BaseDialogFragment;
import com.fecmobile.jiubeirobot.bean.CheckCodeBean;
import com.fecmobile.jiubeirobot.bean.ObjBean;
import com.fecmobile.jiubeirobot.data.net.APIManager;
import com.fecmobile.jiubeirobot.data.net.INetResult;
import com.fecmobile.jiubeirobot.utils.BasicTool;
import com.fecmobile.jiubeirobot.utils.DetectTool;
import com.fecmobile.jiubeirobot.utils.L;
import com.fecmobile.jiubeirobot.utils.RegexUtils;
import com.fecmobile.jiubeirobot.utils.T;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类描述    : 修改密码之前的验证｜找回密码之前的验证
 * 包名      : com.fecmobile.jiubeirobot.ui.dialog.cellar.manage
 * 类名称    : SettingChangePasswordDialog
 * 创建人    : wangxing
 * 创建时间  :  2017/3/14   17:07
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */

public class SettingChangePasswordDialog extends BaseDialogFragment {


    @BindView(R.id.tv_password_title)
    TextView mTvPasswordTitle;
    @BindView(R.id.et_phone_number)
    EditText mEtPhoneNumber;
    @BindView(R.id.btn_exit)
    Button mBtnExit;

    private SettingChangePasswordDetailDialog mSettingChangePasswordDetailDialog;
    private String mAccount;
    private String mType;
    private String flag;
    private String mAid;
    private String mPhone_number;

    @Override
    protected int layout() {
        return R.layout.dialog_change_password_dialog;
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
        mAid = arguments.getString("aid");
        mTvPasswordTitle.setText("修改登录密码");
        flag = mType;

    }


    @OnClick(R.id.btn_exit)
    public void onClick() {
        mPhone_number = mEtPhoneNumber.getText().toString().trim();
        if (checkNext(mPhone_number)) {

            //发送验证码
            APIManager.getInstance().getCheckCode(getActivity(), this, mPhone_number, flag);
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
        if (!BasicTool.isNotEmpty(phone_number)) {
            T.showToCenter(getString(R.string.cellar_manage_setting_phone_number_not_null));
            return false;
        }
        if (!RegexUtils.checkMobile(phone_number)) {
            T.showToCenter(getString(R.string.cellar_manage_setting_phone_number_format_fail));
            return false;
        }
        return true;
    }

    @Override
    public void onSuccess(BaseBean bean, int flag) {
        BaseBean<ObjBean<CheckCodeBean>> checkCodeBean = bean;

        String state = checkCodeBean.getData().getState();
        String uuid = checkCodeBean.getData().getObj().getUuid();

        SettingChangePasswordDialog.this.dismiss();
        if (mSettingChangePasswordDetailDialog == null) {
            mSettingChangePasswordDetailDialog = new SettingChangePasswordDetailDialog();
        }
        mSettingChangePasswordDetailDialog.show(getFragmentManager(), "settingfindpassworddialog");
        Bundle bundle = new Bundle();
        bundle.putString("account", mPhone_number);
        bundle.putString("type", mType);
        bundle.putString("uuid", uuid);
        bundle.putString("aid", mAid);
        mSettingChangePasswordDetailDialog.setArguments(bundle);

    }
}
