package com.fecmobile.jiubeirobot.ui.dialog.cellar.manage;

import android.text.TextUtils;
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
import com.fecmobile.jiubeirobot.base.BaseData;
import com.fecmobile.jiubeirobot.base.BaseDialogFragment;
import com.fecmobile.jiubeirobot.data.net.APIManager;
import com.fecmobile.jiubeirobot.utils.DetectTool;
import com.fecmobile.jiubeirobot.utils.RegexUtils;
import com.fecmobile.jiubeirobot.utils.T;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 类描述    : 订单发货信息填写对话框
 * 包名      : com.fecmobile.jiubeirobot.ui.dialog.cellar.manage
 * 类名称    : OrderDeliverInfoDialog
 * 创建人    : lc
 * 创建时间  :  2017-03-15 16:56
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class OrderDeliverInfoDialog extends BaseDialogFragment {
    @BindView(R.id.dialog_order_consigor)
    EditText tvConsigor;
    @BindView(R.id.dialog_order_mobile)
    EditText etMobile;
    @BindView(R.id.dialog_order_logistic)
    EditText etLogistic;
    @BindView(R.id.dialog_order_logistic_code)
    EditText etLogisticCode;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.iv_close)
    ImageView ivClose;

    private String orderId;
    private String orderCode;
    private String userName;
    private String moblie;

    @Override
    protected int layout() {
        return R.layout.dialog_order_deliver_info;
    }

    @Override
    protected ViewGroup.LayoutParams setLayoutParams() {
        return new FrameLayout.LayoutParams((int) (DetectTool.getResolutionX(getContext()) * 0.459), LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void initView() {
        /**获取输入的发货人姓名*/
        userName = BaseData.getBaseData().getLoginManagerBean().getUserName();
        moblie = BaseData.getBaseData().getLoginManagerBean().getAccount();
        tvConsigor.setText(userName);
        etMobile.setText(moblie);
    }

    @Override
    public void onSuccess(BaseBean bean, int flag) {
        super.onSuccess(bean, flag);
        T.showShort(getString(R.string.order_success_success));
        ((BaseActivity) getActivity()).dismissHUD();
        ((BaseActivity) getActivity()).onSuccess(bean, flag);
        dismiss();
    }

    public void setOrderInfo(String orderId, String orderCode) {
        this.orderId = orderId;
        this.orderCode = orderCode;
    }


    @OnClick({R.id.btn_confirm, R.id.iv_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_confirm:
                if (getParams()) {
                    APIManager.getInstance().getOrderSend(getActivity(), this, orderId, orderCode, "2", tvConsigor.getText().toString(), getMobile(), getLogisticCompany(), getLogisticCode());
                }
                break;
            case R.id.iv_close:
                dismiss();
                break;
        }
    }

    /**
     * 获取输入的发货人电话
     */
    private String getMobile() {
        return etMobile.getText().toString().trim();
    }

    /**
     * 获取输入的发货人姓名
     */
    private String getLogisticCompany() {
        return etLogistic.getText().toString().trim();
    }

    /**
     * 获取输入的发货人姓名
     */
    private String getLogisticCode() {
        return etLogisticCode.getText().toString().trim();
    }


    private boolean getParams() {
        if (TextUtils.isEmpty(tvConsigor.getText().toString())) {
            T.showShort(getString(R.string.order_detail_receive_hint));
            return false;
        }
        if (TextUtils.isEmpty(getMobile())) {
            T.showShort(getString(R.string.order_detail_phone_hint));
            return false;
        }
        if (!RegexUtils.checkMobile(getMobile())) {
            T.showShort(getString(R.string.order_detail_mobile_format_error));
            return false;
        }
        if (TextUtils.isEmpty(getLogisticCompany())) {
            T.showShort(getString(R.string.order_detail_logistic_hint));
            return false;
        }
        if (TextUtils.isEmpty(getLogisticCode())) {
            T.showShort(getString(R.string.order_detail_logistic_code_hint));
            return false;
        }
        return true;
    }
}
