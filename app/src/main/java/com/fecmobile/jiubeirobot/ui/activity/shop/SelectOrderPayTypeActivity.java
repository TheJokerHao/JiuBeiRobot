package com.fecmobile.jiubeirobot.ui.activity.shop;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseActivity;
import com.fecmobile.jiubeirobot.base.BaseApplication;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.common.Activitys;
import com.fecmobile.jiubeirobot.common.Constants;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类描述    :订单支付方式
 * 包名      : com.fecmobile.jiubeirobot.ui.activity.shop
 * 类名称    : SelectOrderPayTypeActivity
 * 创建人    : ghy
 * 创建时间  : 2017/3/2 15:20
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class SelectOrderPayTypeActivity extends BaseActivity {
    @BindView(R.id.tv_back_txt)
    TextView tvBackTxt;
    @BindView(R.id.llyt_back)
    LinearLayout llytBack;
    @BindView(R.id.iv_search_icon)
    ImageView ivSearchIcon;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.llyt_alipay)
    LinearLayout llytAlipay;
    @BindView(R.id.llyt_wechat_pay)
    LinearLayout llytWechatPay;
    @BindView(R.id.llyt_cash_pay)
    LinearLayout llytCashPay;
    private String bid;
    private String oid;
    private String moeny;
    private String code;

    @Override
    public int pageLayout() {
        return R.layout.activity_select_order_pay_type;
    }

    @Override
    public void initTitle() {
        ivSearchIcon.setVisibility(View.GONE);
        tvBackTxt.setVisibility(View.GONE);
        tvTitle.setText(getString(R.string.shopping_pay_type));
    }

    @Override
    public void initView() {
        bid = getIntent().getStringExtra(Constants.INTENT_OID);
        oid = getIntent().getStringExtra(Constants.INTENT_MEMBER_ID);
        moeny = getIntent().getStringExtra(Constants.INTENT_PAY_MONEY);
        code = getIntent().getStringExtra(Constants.INTENT_ORDER_CODE);
    }

    @Override
    public void onSuccess(final BaseBean bean, int flag) {

    }


    @OnClick({R.id.llyt_alipay, R.id.llyt_wechat_pay, R.id.llyt_cash_pay, R.id.llyt_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llyt_alipay:
                //支付宝支付
                Activitys.toQRPay(this, Constants.ALIPAY_TYPE, oid, bid);
                break;
            case R.id.llyt_wechat_pay:
                //微信支付
                Activitys.toQRPay(this, Constants.WECHATPAY_TYPE, oid, bid);
                break;
            case R.id.llyt_cash_pay:
                //线下支付
                Activitys.toOrderPayResult(this, Constants.CASH_TYPE, code, moeny);
                break;
            case R.id.llyt_back:
                finish();
                break;
        }
    }
}


