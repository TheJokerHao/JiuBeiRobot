package com.fecmobile.jiubeirobot.ui.dialog.cellar.manage;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseDialogFragment;
import com.fecmobile.jiubeirobot.common.Activitys;
import com.fecmobile.jiubeirobot.utils.DetectTool;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类描述    : 系统管理的设置
 * 包名      : com.fecmobile.jiubeirobot.ui.dialog.cellar.manage
 * 类名称    : SystemManageSettingDialog
 * 创建人    : wangxing
 * 创建时间  :  2017/3/17   14:33
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */

public class SystemManageSettingDialog extends BaseDialogFragment {


    @BindView(R.id.tv_current_account)
    TextView mTvCurrentAccount;
    @BindView(R.id.llty_current_account)
    LinearLayout mLltyCurrentAccount;
    @BindView(R.id.llty_change_password)
    LinearLayout mLltyChangePassword;
    @BindView(R.id.btn_exit)
    Button mBtnExit;
    @BindView(R.id.tv_close_system)
    TextView mTvCloseSystem;
    @BindView(R.id.iv_close_dialog)
    ImageView mIvCloseDialog;
    @BindView(R.id.lLayout_bg)
    RelativeLayout mLLayoutBg;
    private String mAccount;
    private SettingChangePasswordDetailDialog mSettingChangePasswordDetailDialog;
    private SettingChangePasswordDialog mSettingChangePasswordDialog;
    private String mType;
    private String mAid;

    @Override
    protected int layout() {
        return R.layout.dialog_system_manage_setting_dialog;
    }

    @Override
    protected ViewGroup.LayoutParams setLayoutParams() {
        return new FrameLayout.LayoutParams((int) (DetectTool.getResolutionX(getContext()) * 0.517), LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void initView() {
        Bundle arguments = getArguments();
        mAccount = arguments.getString("account");
        mAid = arguments.getString("aid");
        mTvCurrentAccount.setText(mAccount);

    }


    @OnClick({R.id.llty_current_account, R.id.llty_change_password, R.id.btn_exit, R.id.tv_close_system, R.id.iv_close_dialog})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llty_current_account:
                break;
            case R.id.llty_change_password:
                this.dismiss();
//                if (mSettingChangePasswordDialog == null) {
//                    mSettingChangePasswordDialog = new SettingChangePasswordDialog();
//
//                }
//                Bundle bundle = new Bundle();
//                bundle.putString("account", mAccount);
//                bundle.putString("type", "4");
//                bundle.putString("aid", mAid);
//                mSettingChangePasswordDialog.setArguments(bundle);
//                mSettingChangePasswordDialog.show(getFragmentManager(),"settingfindpassworddialog");


                if (mSettingChangePasswordDetailDialog == null) {
                    mSettingChangePasswordDetailDialog = new SettingChangePasswordDetailDialog();
                }
                mSettingChangePasswordDetailDialog.show(getFragmentManager(), "settingfindpassworddialog");
                Bundle bundle = new Bundle();
                bundle.putString("account", mAccount);
                bundle.putString("type", "4");
                bundle.putString("aid", mAid);
                mSettingChangePasswordDetailDialog.setArguments(bundle);

                break;
            case R.id.btn_exit:
                Activitys.toMain(getActivity());
                break;
            case R.id.tv_close_system:

                break;
            case R.id.iv_close_dialog:
                this.dismiss();
                break;
        }
    }
}
