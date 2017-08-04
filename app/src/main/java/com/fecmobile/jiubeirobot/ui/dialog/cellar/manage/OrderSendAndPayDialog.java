package com.fecmobile.jiubeirobot.ui.dialog.cellar.manage;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseActivity;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.base.BaseDialogFragment;
import com.fecmobile.jiubeirobot.data.net.APIManager;
import com.fecmobile.jiubeirobot.utils.DetectTool;
import com.fecmobile.jiubeirobot.utils.T;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 发货方式选择和支付方式选择
 * Created by Administrator on 2017/3/15.
 */

public class OrderSendAndPayDialog extends BaseDialogFragment {

    @BindView(R.id.tv_order_detail_send_type_title)
    TextView tvTitle;
    @BindView(R.id.btn_operator1)
    Button btnOperator1;
    @BindView(R.id.btn_operator2)
    Button btnOperator2;
    @BindView(R.id.iv_close)
    ImageView ivClose;

    private String orderId;
    private String orderCode;

    private int type;
    private OrderDeliverInfoDialog deliverInfoDialog;


    @Override
    protected int layout() {
        return R.layout.dialog_order_send_and_pay;
    }

    @Override
    protected ViewGroup.LayoutParams setLayoutParams() {
        return new FrameLayout.LayoutParams((int) (DetectTool.getResolutionX(getContext()) * 0.459), LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void initView() {
        deliverInfoDialog = new OrderDeliverInfoDialog();
    }


    @Override
    public void setArguments(Bundle args) {
        super.setArguments(args);
        if (args != null) {
            this.type = args.getInt("type", 1);
            this.orderId = args.getString("orderId");
            this.orderCode = args.getString("orderCode");
        }
    }

    /**
     * 设置显示类型 1.收款  2.发货
     */
    public void setType(int type) {

        if (type == 1) {
            tvTitle.setText(R.string.order_dialog_type_title_receive);
            btnOperator1.setText(R.string.order_dialog_type_button1_receive);
            btnOperator2.setText(R.string.order_dialog_type_button2_receive);
        } else if (type == 2) {
            tvTitle.setText(R.string.order_dialog_type_title_send);
            btnOperator1.setText(R.string.order_dialog_type_button1_send);
            btnOperator2.setText(R.string.order_dialog_type_button2_send);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        setType(type);
    }

    @OnClick({R.id.btn_operator1, R.id.btn_operator2, R.id.iv_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_operator1:
                //第一个按钮的操作
                if (type == 1) {
                    //现金收款
                    APIManager.getInstance().getOrderReceivable(getActivity(), this, orderId, "3");
                } else if (type == 2) {
                    //自提
                    APIManager.getInstance().getOrderSend(getActivity(), this, orderId, orderCode, "1", null, null, null, null);
                }
                break;
            case R.id.btn_operator2:
                //第二个按钮的操作
                if (type == 1) {
                    //刷卡
                    APIManager.getInstance().getOrderReceivable(getActivity(), this, orderId, "4");
                } else if (type == 2) {
                    //配送，跳转填写发货信息
                    deliverInfoDialog.show(getFragmentManager(), "delivery");
                    deliverInfoDialog.setOrderInfo(orderId, orderCode);
                    dismiss();
                }
                break;
            case R.id.iv_close:
                //关闭
                dismiss();
                break;
        }
    }

    @Override
    public void onSuccess(BaseBean bean, int flag) {
        super.onSuccess(bean, flag);
        T.showShort(getString(R.string.order_operator_success));
        ((BaseActivity) getActivity()).dismissHUD();
        ((BaseActivity) getActivity()).onSuccess(bean, flag);
        dismiss();
    }
}
