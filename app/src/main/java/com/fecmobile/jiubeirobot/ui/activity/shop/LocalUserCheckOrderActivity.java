package com.fecmobile.jiubeirobot.ui.activity.shop;

import android.content.Context;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseActivity;
import com.fecmobile.jiubeirobot.base.BaseApplication;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.base.BaseData;
import com.fecmobile.jiubeirobot.bean.CheckCustomerInfoBean;
import com.fecmobile.jiubeirobot.bean.LocalQueryAddressOtherBean;
import com.fecmobile.jiubeirobot.bean.LocalSubmitOrderPramsBean;
import com.fecmobile.jiubeirobot.bean.ObjBean;
import com.fecmobile.jiubeirobot.bean.OrderResultInfoBean;
import com.fecmobile.jiubeirobot.bean.StockBean;
import com.fecmobile.jiubeirobot.common.Activitys;
import com.fecmobile.jiubeirobot.common.Constants;
import com.fecmobile.jiubeirobot.data.net.APIManager;
import com.fecmobile.jiubeirobot.data.net.FLags;
import com.fecmobile.jiubeirobot.ui.fragment.shop.LocalEditShippingAddressFragment;
import com.fecmobile.jiubeirobot.ui.fragment.shop.LocalSelectDistributionModeFragment;
import com.fecmobile.jiubeirobot.ui.fragment.shop.LocalSelectInvoiceTypeFragment;
import com.fecmobile.jiubeirobot.ui.fragment.shop.LocalShippingAddressFragment;
import com.fecmobile.jiubeirobot.ui.fragment.shop.ShopManifestFragment;
import com.fecmobile.jiubeirobot.utils.Arith;
import com.fecmobile.jiubeirobot.utils.DesensitizationUtil;
import com.fecmobile.jiubeirobot.utils.GlideImageLoadImpl;
import com.fecmobile.jiubeirobot.utils.L;
import com.fecmobile.jiubeirobot.utils.SharedPreferencesUtils;
import com.fecmobile.jiubeirobot.utils.T;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author TheJoker丶豪
 *         本地购买订单核对页面
 * @date 创建时间:2017/6/6
 */

public class LocalUserCheckOrderActivity extends BaseActivity {
    @Override
    public int pageLayout() {
        return R.layout.activity_local_user_check_order;
    }

    @BindView(R.id.llyt_back)
    LinearLayout llytBack;
    @BindView(R.id.iv_search_icon)
    ImageView ivSearchIcon;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_back_txt)
    TextView tvBackTxt;
    @BindView(R.id.btn_submit_order)
    Button btnSubmitOrder;
    @BindView(R.id.llyt_distribution_mode)
    LinearLayout llytDistributionMode;
    @BindView(R.id.dl_drawer)
    DrawerLayout dlDrawer;
    @BindView(R.id.llyt_shipping_address)
    LinearLayout llytShippingAddress;
    @BindView(R.id.tv_distribution)
    TextView tvDistribution;
    @BindView(R.id.llyt_shop_list)
    LinearLayout llytShopList;
    @BindView(R.id.llyt_invoice)
    LinearLayout llytInvoice;
    @BindView(R.id.iv_shop_1)
    ImageView ivShop1;
    @BindView(R.id.iv_shop_2)
    ImageView ivShop2;
    @BindView(R.id.iv_shop_3)
    ImageView ivShop3;
    @BindView(R.id.iv_shop_4)
    ImageView ivShop4;
    @BindView(R.id.frame_content)
    FrameLayout frameContent;
    @BindView(R.id.iv_more)
    ImageView ivMore;
    @BindView(R.id.tv_total_shop_num)
    TextView tvTotalShopNum;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.tv_freight)
    TextView tvFreight;
    @BindView(R.id.tv_be_paid)
    TextView tvBePaid;
    @BindView(R.id.tv_shop_total_money)
    TextView tvShopTotalMoney;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.et_buyer_remarks)
    EditText etBuyerRemarks;
    @BindView(R.id.tv_invoiceType)
    TextView tvInvoiceType;
    @BindView(R.id.ll_local_user_check)
    LinearLayout llLocalUserCheck;

    private LocalSelectDistributionModeFragment selectDistributionModeFragment; //配送方式
    private ShopManifestFragment shopManifestFragment;//商品清单
    private LocalSelectInvoiceTypeFragment selectInvoiceTypeFragment;
    private LocalShippingAddressFragment shippingAddressFragment;
    private LocalEditShippingAddressFragment editShippingAddressFragment;

    private String isHaveAddress = "1";
    private LocalSubmitOrderPramsBean invoiceType = new LocalSubmitOrderPramsBean();//本地下单发票数据
    private LocalQueryAddressOtherBean memberAddressBean;
    public static final String NOW_BUY = "NOW_BUY";//立即购买
    public static final String GENERAL_BUY = "GENERAL_BUY";//普通购买
    private List<StockBean> cardList = new ArrayList<>();//购物车
    private int shopTotalNum;//商品总数量
    private String shopTotalMoney;//商品总价
    private String bid;//订单ID
    private String customerId;
    List<LocalQueryAddressOtherBean> list;


    public void setInvoiceType(LocalSubmitOrderPramsBean invoiceType) {
        if ("1".equals(invoiceType.getInvoiceType())) {
            tvInvoiceType.setText(getString(R.string.shopping_check_no_invoice));
        } else if ("2".equals(invoiceType.getInvoiceType())) {
            tvInvoiceType.setText(getString(R.string.shopping_check_general_invoice));
        } else if ("3".equals(invoiceType.getInvoiceType())) {
            tvInvoiceType.setText(getString(R.string.shopping_check_add_val_invoice));
        }
        this.invoiceType = invoiceType;
    }

    @Override
    public void initTitle() {
        ivSearchIcon.setVisibility(View.GONE);
        tvBackTxt.setVisibility(View.GONE);
        tvTitle.setText(getString(R.string.shopping_check_order));
    }

    @Override
    public void initView() {
        bid = getIntent().getStringExtra(Constants.INTENT_MEMBER_ID);
        invoiceType.setIsHaveInvoice("1");

        String buyType = getIntent().getStringExtra(Constants.INTENT_NOW_BUY);
        int size = 0;
        if (NOW_BUY.equals(buyType)) {
            Serializable seri = getIntent().getSerializableExtra(Constants.INTENT_STOCK_BEAN);
            if (seri != null) {
                StockBean stockBean = (StockBean) seri;
                shopTotalNum = 1;
                size = 1;
                shopTotalMoney = (stockBean.getNum() * Double.parseDouble(stockBean.getLsPrice() + "")) + "";
                cardList.add(stockBean);
            }
        } else if (GENERAL_BUY.equals(buyType)) {
            cardList = BaseData.getBaseData().getCardShops();
            size = cardList.size();
            shopTotalNum = size;
            shopTotalMoney = BaseData.getBaseData().getTotalMoney();
        }

        if (size > 0) {
            GlideImageLoadImpl.getInstance().load(this, cardList.get(0).getMainPicUrl(), ivShop1);
            ivShop1.setVisibility(View.VISIBLE);
            if (size > 1) {
                GlideImageLoadImpl.getInstance().load(this, cardList.get(1).getMainPicUrl(), ivShop2);
                ivShop2.setVisibility(View.VISIBLE);
            }
            if (size > 2) {
                GlideImageLoadImpl.getInstance().load(this, cardList.get(2).getMainPicUrl(), ivShop3);
                ivShop2.setVisibility(View.VISIBLE);
            }
            if (size > 3) {
                GlideImageLoadImpl.getInstance().load(this, cardList.get(3).getMainPicUrl(), ivShop4);
                ivShop3.setVisibility(View.VISIBLE);
                ivMore.setVisibility(View.VISIBLE);
            }
        } else {
            ivMore.setVisibility(View.GONE);
        }

        tvTotalShopNum.setText("共" + shopTotalNum + "件商品");
        tvTotalMoney.setText("￥" + Arith.get2Decimal(shopTotalMoney));
        tvShopTotalMoney.setText("￥" + Arith.get2Decimal(shopTotalMoney));
        //tvFreight.setText("￥" + BaseData.getBaseData().getTotalMoney());
        tvBePaid.setText("￥" + Arith.get2Decimal(shopTotalMoney));
    }


    /**
     * 订单提交成功的数据返回回调
     * 订单提交成功之后需要对Flag 进行检测
     * 检测完成之后  成功则进入到选择支付方式的页面
     *
     * @param bean
     * @param flag
     */
    @Override
    public void onSuccess(final BaseBean bean, int flag) {
        switch (flag) {
            case FLags.FLAG_LOCAL_MEMBER_ORDER_SUBMIT:
                BaseBean<ObjBean<OrderResultInfoBean>> oobean = bean;
                OrderResultInfoBean resultBean = oobean.getData().getObj();
                BaseData.getBaseData().clearCartShops();
                Activitys.toLocalSelectOrderPayType(this, resultBean.getOrderId(), resultBean.getOrderAmout(), resultBean.getOrderCode(), shopTotalMoney);
                finish();

                break;
            case FLags.FLAG_LOCAL_CHECK_CUSTOMER_INFO:
                BaseBean<ObjBean<CheckCustomerInfoBean>> oobeancheck = bean;
                CheckCustomerInfoBean resultBeancheck = oobeancheck.getData().getObj();
                customerId = resultBeancheck.getCustomerId();
                SharedPreferencesUtils.putString(LocalUserCheckOrderActivity.this, "customerId", customerId);
//                Log.d("print", "onSuccess: 获取到的返回ID" + customerId);
                break;
        }

        dismissHUD();

    }

    public List<LocalQueryAddressOtherBean> getList() {
        return list;
    }

    @OnClick({R.id.ll_local_user_check, R.id.llyt_back, R.id.btn_submit_order, R.id.llyt_distribution_mode, R.id.llyt_shop_list, R.id.llyt_invoice, R.id.llyt_shipping_address})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_local_user_check:
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                break;
            case R.id.llyt_back:
                finish();
                break;
            case R.id.btn_submit_order:
                //提交订单
                if (!checkPage()) {
                    return;
                }
                if (invoiceType != null) {
                    invoiceType.setIsQuickBuy(BaseApplication.currentMode + "");
                    invoiceType.setIsHaveAddress(isHaveAddress);
                    invoiceType.setBuyerRemarks(etBuyerRemarks.getText() + "");
                    L.i("------------------下单" + memberAddressBean);
                    if ((BaseApplication.currentMode == BaseApplication.LOCAL_BUY) && memberAddressBean != null) {
                        invoiceType.setConsigneerName(memberAddressBean.getDeliveryName());
                        invoiceType.setConsigneerPhone(memberAddressBean.getDeliveryPhone());
                        invoiceType.setConsigneerAddress(memberAddressBean.getDeliveryAddr());
                        invoiceType.setConsigneerProvince(memberAddressBean.getDeliveryProv());
                        invoiceType.setConsigneerCity(memberAddressBean.getDeliveryCity());
                        invoiceType.setConsigneerCountry(memberAddressBean.getDeliveryArea());
                        L.i("------------------memberAddressBean != null");
                    }
                    invoiceType.setBuyerRemarks(etBuyerRemarks.getText() + "");
                    List<LocalSubmitOrderPramsBean.LocalShop> shops = new ArrayList<>();
                    for (int i = 0; i < cardList.size(); i++) {
                        LocalSubmitOrderPramsBean.LocalShop shop = new LocalSubmitOrderPramsBean.LocalShop();
                        shop.setProductId(cardList.get(i).getId());
                        shop.setProductQty(cardList.get(i).getNum() + "");
                        shops.add(shop);
                    }
                    invoiceType.setProductList(shops);
                    APIManager.getInstance().localOrderSubmit(this, this, invoiceType);
                }
                break;
            case R.id.llyt_distribution_mode:
                //配送方式
                showSelectDistributionMode();
                break;
            case R.id.llyt_shop_list:
                //显示商品清单
                showShopManifest();
                break;
            case R.id.llyt_invoice:
                //开发票
                showSelectInvoiceType();
                break;
            case R.id.llyt_shipping_address:
                //送货地址显示与不显示
                //点击的时候需要去请求
                /**
                 * TODO  第一步  点击的时候需要先判断当前手机号的客户是否存在地址信息 存在地址信息就直接显示出来    不存在地址信息就新增
                 *
                 */
                showShippingAddress();

                break;
        }
    }

    public void need() {
        isHaveAddress = "0";
        tvDistribution.setText(getString(R.string.shopping_check_need_delivery));
        llytShippingAddress.setVisibility(View.VISIBLE);
        tvName.setText("收货人：" + DesensitizationUtil.phoneDesensitization(SharedPreferencesUtils.getString(getApplicationContext(), "etphonenumber")));
    }

    public void noNeed() {
        isHaveAddress = "1";
        tvDistribution.setText(getString(R.string.shopping_check_no_need_delivery));
        llytShippingAddress.setVisibility(View.GONE);
    }

    public void close() {
        dlDrawer.closeDrawer(Gravity.RIGHT);
    }

    public void showSelectDistributionMode() {
        if (selectDistributionModeFragment == null) {
            selectDistributionModeFragment = new LocalSelectDistributionModeFragment();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_content, selectDistributionModeFragment);
        transaction.commit();

        if (dlDrawer.isDrawerOpen(Gravity.RIGHT)) {
            dlDrawer.closeDrawer(Gravity.RIGHT);
        } else {
            dlDrawer.openDrawer(Gravity.RIGHT);
        }
    }

    public void showShopManifest() {
        if (shopManifestFragment == null) {
            shopManifestFragment = new ShopManifestFragment();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_content, shopManifestFragment);
        transaction.commit();

        if (dlDrawer.isDrawerOpen(Gravity.RIGHT)) {
            dlDrawer.closeDrawer(Gravity.RIGHT);
        } else {
            dlDrawer.openDrawer(Gravity.RIGHT);
            shopManifestFragment.setList(cardList);
        }
    }

    public void showSelectInvoiceType() {
        if (selectInvoiceTypeFragment == null) {
            selectInvoiceTypeFragment = new LocalSelectInvoiceTypeFragment();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_content, selectInvoiceTypeFragment);
        transaction.commit();

        if (dlDrawer.isDrawerOpen(Gravity.RIGHT)) {
            dlDrawer.closeDrawer(Gravity.RIGHT);
        } else {
            dlDrawer.openDrawer(Gravity.RIGHT);
        }
    }

    public void showShippingAddress() {
        if (shippingAddressFragment == null) {
            shippingAddressFragment = new LocalShippingAddressFragment();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_content, shippingAddressFragment);
        transaction.commit();

        if (dlDrawer.isDrawerOpen(Gravity.RIGHT)) {
            dlDrawer.closeDrawer(Gravity.RIGHT);
        } else {
            dlDrawer.openDrawer(Gravity.RIGHT);
        }
    }

    public void AddShippingAddress() {
        showEditShippingAddress(new LocalQueryAddressOtherBean());
    }

    public void toEditAddress(LocalQueryAddressOtherBean memberAddressBean) {
        showEditShippingAddress(memberAddressBean);
    }


    /**
     * 添加新地址
     *
     * @param m
     */
    private void showEditShippingAddress(LocalQueryAddressOtherBean m) {
        if (editShippingAddressFragment == null) {
            editShippingAddressFragment = new LocalEditShippingAddressFragment();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.frame_content, editShippingAddressFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        editShippingAddressFragment.getContent(m);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void closeRefreshAddress() {
        onBackPressed();
        if (shippingAddressFragment != null) {
            shippingAddressFragment.onRefresh();
        }
    }

    public void showEditShippingAddressReplace() {
        if (editShippingAddressFragment == null) {
            editShippingAddressFragment = new LocalEditShippingAddressFragment();
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_content, editShippingAddressFragment);
        transaction.commit();
        if (dlDrawer.isDrawerOpen(Gravity.RIGHT)) {
            dlDrawer.closeDrawer(Gravity.RIGHT);
        } else {
            dlDrawer.openDrawer(Gravity.RIGHT);
        }
    }

    public void setAddrss(LocalQueryAddressOtherBean m) {
        L.i(m);
        this.memberAddressBean = m;
        if (m != null) {
            tvName.setText("收货人：" + m.getDeliveryName() + "        " + DesensitizationUtil.phoneDesensitization(m.getDeliveryPhone()));
            tvAddress.setText(m.getDeliveryProv() + "   " + m.getDeliveryCity() + "   " + m.getDeliveryArea() + "  " + m.getDeliveryAddr());
            L.i(m.toString());
        }
    }

    private boolean checkPage() {
        if (TextUtils.isEmpty(tvAddress.getText()) && isHaveAddress.equals("0")) {
            T.showToCenter(getString(R.string.shopping_check_please_address));
            return false;
        }
        return true;
    }
}
