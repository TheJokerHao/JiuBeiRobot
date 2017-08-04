package com.fecmobile.jiubeirobot.ui.activity.shop;

import android.os.CountDownTimer;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseActivity;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.bean.ObjBean;
import com.fecmobile.jiubeirobot.bean.OrderStateInfoBean;
import com.fecmobile.jiubeirobot.bean.PayInfoBean;
import com.fecmobile.jiubeirobot.common.Activitys;
import com.fecmobile.jiubeirobot.common.Constants;
import com.fecmobile.jiubeirobot.data.net.APIManager;
import com.fecmobile.jiubeirobot.data.net.FLags;
import com.fecmobile.jiubeirobot.utils.GlideImageLoadImpl;
import com.fecmobile.jiubeirobot.utils.L;
import com.fecmobile.jiubeirobot.utils.T;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类描述    :二维码支付
 * 包名      : com.fecmobile.jiubeirobot.ui.activity.shop
 * 类名称    : QRPayActivity
 * 创建人    : ghy
 * 创建时间  : 2017/3/2 15:25
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class QRPayActivity extends BaseActivity {
    @BindView(R.id.tv_back_txt)
    TextView tvBackTxt;
    @BindView(R.id.llyt_back)
    LinearLayout llytBack;
    @BindView(R.id.iv_search_icon)
    ImageView ivSearchIcon;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_pay_qr)
    ImageView ivPayQr;
    @BindView(R.id.iv_pay_icon)
    ImageView ivPayIcon;
    @BindView(R.id.tv_pay_des)
    TextView tvPayDes;
    private int payType;
    private String oid;
    private String bid;
    private String code;
    private String money;
    private long millisInFuture = 1800000;//30分钟
    private long countDownInterval = 1000;
    private CountDownTimer countDownTime; //计时类

    @Override
    public int pageLayout() {
        return R.layout.activity_qrpay;
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
        payType = getIntent().getIntExtra(Constants.INTENT_PAY_TYPE, -1);
        requestPayInfo();

        countDownTime = new CountDownTimer(millisInFuture, countDownInterval) {
            @Override
            public void onTick(long millisUntilFinished) {
                L.i("millisUntilFinished：" + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                T.showToCenter(getString(R.string.common_timeout_pay));
                Activitys.toShopList(QRPayActivity.this);
//                countDownTime.start();
            }
        };
    }

    /**
     * 请求支付
     */
    private void requestPayInfo() {
        if (payType == Constants.ALIPAY_TYPE) {
            APIManager.getInstance().getAliPayInfo(this, oid, bid);
        } else if (payType == Constants.WECHATPAY_TYPE) {
            APIManager.getInstance().getWechatPayInfo(this, oid, bid);
        }
    }

    @Override
    public void onSuccess(final BaseBean bean, int flag) {
        switch (flag) {
            case FLags.FLAG_WECHAT_PAY:
                BaseBean<ObjBean<PayInfoBean>> opbean = bean;
                PayInfoBean payInfo = opbean.getData().getObj();
                code = payInfo.getOrderCodes();
                money = payInfo.getOrderAmout();
                //使用Glide来加载的微信支付的二维码图片
                GlideImageLoadImpl.getInstance().load(this, payInfo.getQr_img(), ivPayQr);
                ivPayIcon.setImageResource(R.mipmap.wechat_pay);
                tvPayDes.setText(getString(R.string.shopping_pay_scan_wechatpay));
                break;
            case FLags.FLAG_ALI_PAY:
                opbean = bean;
                payInfo = opbean.getData().getObj();
                code = payInfo.getOrderCodes();
                money = payInfo.getOrderAmout();
                GlideImageLoadImpl.getInstance().load(this, payInfo.getQr_img(), ivPayQr);
                ivPayIcon.setImageResource(R.mipmap.alipay_pay_icon);
                tvPayDes.setText(getString(R.string.shopping_pay_scan_alipay));
                break;
            case FLags.FLAG_GET_ORDER_STATE:
                BaseBean<ObjBean<OrderStateInfoBean>> osiBean = bean;
                OrderStateInfoBean orderState = osiBean.getData().getObj();
                String state = orderState.getPayStatus();
                if ("0".equals(state)) {//继续查询订单状态
                    APIManager.getInstance().getOrderState(this, oid);
                } else {
                    Activitys.toOrderPayResult(this, payType, code, money);
                }
                break;
        }
        if (flag == FLags.FLAG_WECHAT_PAY || flag == FLags.FLAG_ALI_PAY) {
            countDownTime.start();
            APIManager.getInstance().getOrderState(this, oid);
        }
        dismissHUD();
    }


    @OnClick({R.id.llyt_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llyt_back:
                finish();
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (countDownTime != null) {
            countDownTime.cancel();
        }
    }
}