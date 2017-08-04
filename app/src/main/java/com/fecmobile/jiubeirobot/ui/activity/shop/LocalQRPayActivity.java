package com.fecmobile.jiubeirobot.ui.activity.shop;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseActivity;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.bean.LocalPayInfoBean;
import com.fecmobile.jiubeirobot.bean.ObjBean;
import com.fecmobile.jiubeirobot.common.Activitys;
import com.fecmobile.jiubeirobot.common.Constants;
import com.fecmobile.jiubeirobot.data.net.APIManager;
import com.fecmobile.jiubeirobot.data.net.FLags;
import com.fecmobile.jiubeirobot.utils.GlideImageLoadImpl;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人：TheJoker丶豪 on
 * 创建时间：2017/6/12.
 */

public class LocalQRPayActivity extends BaseActivity {
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
    @BindView(R.id.tv_pay_money)
    TextView tvPayMoney;
    @BindView(R.id.btn_finish_order)
    Button btnFinish;
    private int payType;
    private String localmoney;

    @Override
    public int pageLayout() {
        return R.layout.activity_local_qrpay;
    }

    @Override
    public void initTitle() {
        ivSearchIcon.setVisibility(View.GONE);
        tvBackTxt.setVisibility(View.GONE);
        tvTitle.setText(getString(R.string.shopping_pay_type));
    }

    @Override
    public void initView() {
        payType = getIntent().getIntExtra(Constants.INTENT_PAY_TYPE, -1);
        localmoney = getIntent().getStringExtra(Constants.INTENT_LOCAL_PAY_MONEY);
        requestPayInfo();
        tvPayMoney.setText(localmoney + "元");
    }

    /**
     * 请求支付
     */
    private void requestPayInfo() {
        APIManager.getInstance().getLocalPayInfo(this);
    }

    @Override
    public void onSuccess(final BaseBean bean, int flag) {
        switch (flag) {
            case FLags.FLAG_LOCAL_PAY_INFO:
                BaseBean<ObjBean<LocalPayInfoBean>> opbean = bean;
                LocalPayInfoBean payInfo = opbean.getData().getObj();
                if (payType == Constants.ALIPAY_TYPE) {
                    //支付宝
                    GlideImageLoadImpl.getInstance().load(this, payInfo.getAlipayQrCode(), ivPayQr);//支付宝
                    ivPayIcon.setImageResource(R.mipmap.alipay_icon);
                    tvPayDes.setText(getString(R.string.shopping_pay_scan_alipay));
                }

                if (payType == Constants.WECHATPAY_TYPE) {
                    //微信
                    //使用Glide来加载的微信支付的二维码图片
                    GlideImageLoadImpl.getInstance().load(this, payInfo.getWxpayQrCode(), ivPayQr);
                    ivPayIcon.setImageResource(R.mipmap.wechat_pay);
                    tvPayDes.setText(getString(R.string.shopping_pay_scan_wechatpay));
                }

                dismissHUD();
        }
    }


    @OnClick({R.id.llyt_back, R.id.btn_finish_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llyt_back:
                finish();
                break;
            case R.id.btn_finish_order:
                Activitys.toLocalBuyList(LocalQRPayActivity.this);
                finish();
                break;

        }
    }
}
