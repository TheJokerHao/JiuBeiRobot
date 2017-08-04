package com.fecmobile.jiubeirobot.ui.activity.shop;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseActivity;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.common.Activitys;
import com.fecmobile.jiubeirobot.common.Constants;
import com.fecmobile.jiubeirobot.utils.Arith;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类描述    :订单支付结果
 * 包名      : com.fecmobile.jiubeirobot.ui.activity.shop
 * 类名称    : OrderPayResultActivity
 * 创建人    : ghy
 * 创建时间  : 2017/3/2 15:22
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class OrderPayResultActivity extends BaseActivity {
    @BindView(R.id.tv_back_txt)
    TextView tvBackTxt;
    @BindView(R.id.llyt_back)
    LinearLayout llytBack;
    @BindView(R.id.iv_search_icon)
    ImageView ivSearchIcon;
    @BindView(R.id.btn_to_home)
    Button btnToHome;
    @BindView(R.id.btn_continue_buy)
    Button btnContinueBuy;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.tv_felicitate)
    TextView tvFelicitate;
    private int type;
    private String orderCode;
    private String money;

    @Override
    public int pageLayout() {
        return R.layout.activity_order_pay_result;
    }

    @Override
    public void initTitle() {
        tvBackTxt.setVisibility(View.GONE);
        llytBack.setVisibility(View.GONE);
        ivSearchIcon.setVisibility(View.GONE);
        tvTitle.setText(getString(R.string.shopping_pay_order_pay));
    }

    @Override
    public void initView() {
        type = getIntent().getIntExtra(Constants.INTENT_PAY_TYPE, 0);
        orderCode = getIntent().getStringExtra(Constants.INTENT_ORDER_CODE);
        money = getIntent().getStringExtra(Constants.INTENT_PAY_MONEY);

        if (type == Constants.CASH_TYPE) {
            tvFelicitate.setText(getString(R.string.shopping_pay_cashpay_hint));
        }
        tvDes.setText(getString(R.string.shopping_order_code) + "：" + orderCode + "    " + getString(R.string.shopping_pay_moeny) + "￥" + Arith.get2Decimal(money));
    }

    @Override
    public void onSuccess(final BaseBean bean, int flag) {

    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Activitys.toShopList(this);
    }

    @OnClick({R.id.btn_to_home, R.id.btn_continue_buy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_to_home:
                Activitys.toMain(this);
                break;
            case R.id.btn_continue_buy:
                Activitys.toShopList(this);
                break;
        }
    }

}