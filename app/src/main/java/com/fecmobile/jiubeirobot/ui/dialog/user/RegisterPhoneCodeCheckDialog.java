package com.fecmobile.jiubeirobot.ui.dialog.user;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.BuildConfig;
import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseActivity;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.base.BaseData;
import com.fecmobile.jiubeirobot.base.BaseDialogFragment;
import com.fecmobile.jiubeirobot.bean.CheckCodeBean;
import com.fecmobile.jiubeirobot.bean.ObjBean;
import com.fecmobile.jiubeirobot.common.Activitys;
import com.fecmobile.jiubeirobot.data.net.APIManager;
import com.fecmobile.jiubeirobot.data.net.FLags;
import com.fecmobile.jiubeirobot.utils.DetectTool;
import com.fecmobile.jiubeirobot.utils.L;
import com.fecmobile.jiubeirobot.utils.RegexUtils;
import com.fecmobile.jiubeirobot.utils.T;
import com.fecmobile.jiubeirobot.utils.TimeCount;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类描述    :注册短信验证
 * 包名      : com.fecmobile.jiubeirobot.ui.dialog.user
 * 类名称    : RegisterPhoneCodeCheckDialog
 * 创建人    : ghy
 * 创建时间  : 2017/2/24 19:27
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */

public class RegisterPhoneCodeCheckDialog extends BaseDialogFragment {
    @BindView(R.id.btn_register)
    Button btnRegister;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_protocol)
    TextView tvProtocol;
    @BindView(R.id.tv_phone_code)
    TextView tvPhoneCode;
    @BindView(R.id.et_check_code)
    EditText etCheckCode;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.et_rePwd)
    EditText etRePwd;
    @BindView(R.id.tv_check_phone)
    TextView tvCheckPhonre;

    private String phone;
    private BaseBean<ObjBean<CheckCodeBean>> checkCodeBean;

    public void setPhone(String phone) {
        this.phone = new String(phone);
    }

    @Override
    protected int layout() {
        return R.layout.dialog_register_phone_code_check;
    }

    @Override
    protected ViewGroup.LayoutParams setLayoutParams() {
        return new FrameLayout.LayoutParams((int) (DetectTool.getResolutionX(getContext()) * 0.46), LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void initView() {
        tvCheckPhonre.setText(RegexUtils.dismissPhoneNumber4(phone));
    }

    @OnClick({R.id.btn_register, R.id.iv_close, R.id.tv_protocol, R.id.tv_phone_code})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                if (etPwd.getText().length() < 6 || etRePwd.getText().length() < 6) {
                    T.showToCenter(getString(R.string.user_pwd_format_error));
                    return;
                }
                if (!((etPwd.getText() + "").equals(etRePwd.getText() + ""))) {
                    T.showToCenter(getString(R.string.user_pwd_repwd_no_fit));
                    return;
                }
                if (checkCodeBean == null) {
                    T.showToCenter(getString(R.string.user_send_check_code));
                    return;
                }
                APIManager.getInstance().register(getContext(), this, phone, etPwd.getText() + "", etRePwd.getText() + "", etCheckCode.getText() + "", "", checkCodeBean.getData().getObj().getUuid());
                break;
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.tv_protocol:
                Activitys.toWebView(getActivity(), getString(R.string.user_service_clause), BaseData.getBaseData().getProtocolsInfoBean().getSite_reg_info());
                break;
            case R.id.tv_phone_code:
                new TimeCount(getContext(), 60000, 1000, tvPhoneCode).start();
                APIManager.getInstance().getCheckCode(getContext(), this, phone, "1");
                break;
        }
    }

    @Override
    public void onSuccess(BaseBean bean, int flag) {
        L.i(bean + "   " + flag);
        switch (flag) {
            case FLags.FLAG_SEND_CHECK_CODE:
                checkCodeBean = bean;
                T.showToCenter(getString(R.string.user_send_check_to_phone) + phone);
                if (BuildConfig.DEBUG) {
                    T.showToCenter(checkCodeBean.getData().getObj().getCode());
                }
                break;
            case FLags.FLAG_REGISTER:
                T.showToCenter(getString(R.string.user_register));
                dismiss();
                break;
        }
        ((BaseActivity) getActivity()).dismissHUD();
    }
}
