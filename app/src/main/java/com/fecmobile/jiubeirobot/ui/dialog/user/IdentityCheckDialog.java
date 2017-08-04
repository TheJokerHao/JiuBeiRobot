package com.fecmobile.jiubeirobot.ui.dialog.user;

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
import com.fecmobile.jiubeirobot.bean.IdentityCheckResultBean;
import com.fecmobile.jiubeirobot.bean.IsRegister;
import com.fecmobile.jiubeirobot.bean.ObjBean;
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
 * 类描述    :身份验证
 * 包名      : com.fecmobile.jiubeirobot.ui.dialog.user
 * 类名称    : IdentityCheckDialog
 * 创建人    : ghy
 * 创建时间  : 2017/3/2 10:15
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class IdentityCheckDialog extends BaseDialogFragment {
    @BindView(R.id.tv_immediately_register)
    TextView tvImmediatelyRegister;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.btn_identity_check)
    Button btnIdentityCheck;
    @BindView(R.id.tv_get_phone_check)
    TextView tvGetPhoneCheck;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_phone_code)
    EditText etPhoneCode;
    private RegisterUserDialog registerUserDialog;
    private int clickType;
    private String code = "";
    private String uuid = "";
    private CheckResult checkResult;

    public void setCheckResult(CheckResult checkResult) {
        this.checkResult = checkResult;
    }

    @Override
    protected int layout() {
        return R.layout.dialog_identiry_check;
    }

    @Override
    protected ViewGroup.LayoutParams setLayoutParams() {
        return new FrameLayout.LayoutParams((int) (DetectTool.getResolutionX(getContext()) * 0.517), LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void initView() {

    }

    @OnClick({R.id.tv_immediately_register, R.id.iv_close, R.id.btn_identity_check, R.id.tv_get_phone_check})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_immediately_register:
                if (registerUserDialog == null) {
                    registerUserDialog = new RegisterUserDialog();
                }
                registerUserDialog.show(getFragmentManager(), "registerUserDialog");
                break;
            case R.id.iv_close:
                dismiss();
                break;
            case R.id.btn_identity_check:
                clickType = R.id.btn_identity_check;
                if (inputCheck(true)) {
                    if (uuid == null) {
                        T.showToCenter("");
                    }
                    APIManager.getInstance().checkIdentity(getContext(), this, etPhone.getText() + "", uuid, etPhoneCode.getText() + "");
                }
                break;
            case R.id.tv_get_phone_check:
                clickType = R.id.tv_get_phone_check;
                if (inputCheck(false)) {
                    APIManager.getInstance().checkAccountIsRegister(getContext(), this, etPhone.getText() + "");
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
                if (isRegister.getState() == 1) {
                    if (clickType == R.id.tv_get_phone_check) {
                        tvGetPhoneCheck.setEnabled(false);
                        new TimeCount(getContext(), 60000, 1000, tvGetPhoneCheck).start();
                    }
                    APIManager.getInstance().getCheckCode(getContext(), this, etPhone.getText() + "", "2");
                } else {
                    T.showToCenter(isRegister.getMsg());
                }
                break;
            case FLags.FLAG_SEND_CHECK_CODE:
                BaseBean<ObjBean<CheckCodeBean>> codeBean = bean;
                code = codeBean.getData().getObj().getCode();
                uuid = codeBean.getData().getObj().getUuid();
                if (BuildConfig.DEBUG) {
                    T.showToCenter(code);
                }
                L.i(code);
                L.i(uuid);
                break;
            case FLags.FLAG_CHECK_IDNETITY:
                BaseBean<ObjBean<IdentityCheckResultBean>> chekBean = bean;
                BaseData.getBaseData().setIdentityCheckResultBean(chekBean.getData().getObj());
                if (checkResult != null) {
                    checkResult.result(chekBean.getData().getObj());
                }

                break;
        }
        ((BaseActivity) getActivity()).dismissHUD();
    }


    private boolean inputCheck(boolean checkPhoneCode) {
        if (!RegexUtils.checkMobile(etPhone.getText() + "")) {
            T.showToCenter(getString(R.string.common_point_phone_format_error));
            return false;
        }
        if (checkPhoneCode) {
            if ((etPhoneCode.getText() + "").trim().length() == 0) {
                T.showToCenter(getString(R.string.common_point_check_code));
                return false;
            }
        }
        return true;
    }

    public interface CheckResult {
        void result(IdentityCheckResultBean bean);
    }
}
