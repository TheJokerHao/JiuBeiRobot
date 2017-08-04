package com.fecmobile.jiubeirobot.ui.fragment.cellar.manage;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseActivity;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.base.BaseFragment;
import com.fecmobile.jiubeirobot.bean.ManagerOrderDetailBean;
import com.fecmobile.jiubeirobot.bean.ManagerOrderDetailBean.OrderlineListBean;
import com.fecmobile.jiubeirobot.bean.ObjBean;
import com.fecmobile.jiubeirobot.data.net.APIManager;
import com.fecmobile.jiubeirobot.data.net.FLags;
import com.fecmobile.jiubeirobot.ui.activity.cellar.manager.OrderListActivity;
import com.fecmobile.jiubeirobot.ui.dialog.AlertDialog;
import com.fecmobile.jiubeirobot.ui.dialog.ConfirmDropPointDialog;
import com.fecmobile.jiubeirobot.ui.dialog.cellar.manage.OrderSendAndPayDialog;
import com.fecmobile.jiubeirobot.utils.Arith;
import com.fecmobile.jiubeirobot.utils.BasicTool;
import com.fecmobile.jiubeirobot.utils.GlideImageLoadImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;


/**
 * 类描述    : 订单详情
 * 包名      : com.fecmobile.jiubeirobot.ui.fragment.cellar.manage
 * 类名称    : OrderDetailFragment
 * 创建人    : lc
 * 创建时间  : 2017-03-13
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class OrderDetailFragment extends BaseFragment {

    @BindView(R.id.order_detail_code)
    TextView tvCode;
    @BindView(R.id.order_detail_pay_status)
    TextView tvPayStatus;
    @BindView(R.id.tv_order_detail_member_name)
    TextView tvMemberName;
    @BindView(R.id.tv_order_detail_time)
    TextView tvTime;
    @BindView(R.id.lin_order_detail_product_list)
    LinearLayout linProductList;
    @BindView(R.id.tv_order_detail_payment_method)
    TextView tvPaymentMethod;
    @BindView(R.id.tv_order_detail_receiver)
    TextView tvReceiver;
    @BindView(R.id.tv_order_detail_address)
    TextView tvAddress;
    @BindView(R.id.tv_order_detail_sender)
    TextView tvSender;
    @BindView(R.id.tv_order_detail_carrier_company)
    TextView tvCarrierCompany;
    @BindView(R.id.tv_order_detail_courier_number)
    TextView tvCourierNumber;
    @BindView(R.id.tv_order_detail_invoice_type)
    TextView tvInvoiceType;
    @BindView(R.id.tv_order_detail_invoice_head)
    TextView tvInvoiceHead;
    @BindView(R.id.tv_order_detail_invoice_content)
    TextView tvInvoiceContent;
    @BindView(R.id.tv_order_detail_product_number)
    TextView tvProductNumber;
    @BindView(R.id.tv_order_detail_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.tv_order_detail_operator)
    TextView tvOperator;
    @BindView(R.id.lin_order_detail_receive_info)
    LinearLayout linReceiveInfo;
    @BindView(R.id.lin_order_detail_invoice_info)
    LinearLayout linInvoiceInfo;
    @BindView(R.id.line_receive)
    View lineReceive;
    @BindView(R.id.line_invoice)
    View lineInvoice;
    @BindView(R.id.line7)
    LinearLayout line7;
    @BindView(R.id.line8)
    LinearLayout line8;
    @BindView(R.id.line11)
    LinearLayout line11;
    @BindView(R.id.tv_order_detail_gongsi_name)
    TextView gongsi_name;
    @BindView(R.id.tv_order_detail_gongsi_biaoshi)
    TextView gongsi_biaoshi;
    @BindView(R.id.tv_order_detail_gongsi_dizhi)
    TextView gongsi_dizhi;
    @BindView(R.id.tv_order_detail_gongsi_guhua)
    TextView gongsi_guhua;
    @BindView(R.id.tv_order_detail_gongsi_bankName)
    TextView gongsi_bankName;
    @BindView(R.id.tv_order_detail_gongsi_backNum)
    TextView gongsi_backNum;
    @BindView(R.id.line12)
    LinearLayout line12;
    @BindView(R.id.tv_order_detail_shoupiao_name)
    TextView shoupiao_name;
    @BindView(R.id.tv_order_detail_shoupiao_dizhi)
    TextView shoupiao_dizhi;
    @BindView(R.id.tv_order_detail_shoupiao_phone)
    TextView shoupiao_phone;

    private String orderId;
    private LayoutInflater inflater;
    private String orderStatus;
    private String orderCode;
    private OrderSendAndPayDialog orderSendAndPayDialog;
    private ConfirmDropPointDialog confirmDialog;
    private String invoiceType;
    private int postion;
    private Map<String, String> states = new HashMap<String, String>() {
        {
            put("0", "已提交");
            put("1", "已支付");
            put("2", "已财务审核");
            put("3", "已发货");
            put("4", "已收货");
            put("5", "已完成");
            put("6", "已取消");
        }
    };


    @Override
    public int pageLayout() {
        return R.layout.fragment_order_detail;
    }

    public void setId(String orderId) {
        this.orderId = orderId;
    }

    public void setPostion(int postion) {
        this.postion = postion;
    }

    @Override
    public void initTitle() {
        inflater = LayoutInflater.from(getActivity());
    }

    @Override
    public void initView() {

    }

    @Override
    public void onResume() {
        super.onResume();
        if (orderId != null) {
            //TODO  获取item的详情
            APIManager.getInstance().getOrderDetail(getActivity(), this, orderId);
        }
    }

    @Override
    public void onSuccess(BaseBean bean, int flag) {
        switch (flag) {
            case FLags.FLAG_ORDER_DETAIL:
                BaseBean<ObjBean<ManagerOrderDetailBean>> baseBean = bean;
                ManagerOrderDetailBean managerOrderDetailBean = baseBean.getData().getObj();
                initOrderDetail(managerOrderDetailBean);
                dismissHUD();
                break;
            case FLags.FLAG_ORDER_FINISH:
                ((BaseActivity) getActivity()).onSuccess(bean, flag);
                break;
        }
    }


    /**
     * 初始化订单详情
     */
    private void initOrderDetail(ManagerOrderDetailBean bean) {
        orderCode = bean.getOrderCode();
        tvCode.setText(getString(R.string.order_detail_ordercode) + bean.getOrderCode());
        String orderStatus = bean.getOrderStatus();
        tvPayStatus.setText(states.get(orderStatus));

        String buyName = bean.getBuyerName();
        if (BasicTool.isNotEmpty(buyName)) {
            tvMemberName.setText(buyName);
        } else {
            tvMemberName.setText("游客");
        }

        tvTime.setText(bean.getPostTime());
        List<OrderlineListBean> orderlineList = bean.getOrderlineList();
        initProductLineList(orderlineList);
        String isHaveAddress = bean.getIsHaveAddress();
        if (isHaveAddress.equals("0")) {
            //包含收货地址
            linReceiveInfo.setVisibility(View.VISIBLE);
            lineReceive.setVisibility(View.VISIBLE);
            initReceiveInfo(bean);
        } else {
            //不包含
            lineReceive.setVisibility(View.GONE);
            linReceiveInfo.setVisibility(View.GONE);
        }
        String isHaveInvoice = bean.getIsHaveInvoice();
        if (isHaveInvoice.equals("0")) {
            //包含发票信息
            lineInvoice.setVisibility(View.VISIBLE);
            linInvoiceInfo.setVisibility(View.VISIBLE);
            initInvoiceInfo(bean);
        } else {
            //不包含
            lineInvoice.setVisibility(View.GONE);
            linInvoiceInfo.setVisibility(View.GONE);
        }
        initMoneyInfo(bean);
        initOrderStatus(bean);

        String payType = bean.getPayType();
        tvPaymentMethod.setText(getPayType(payType));
    }

    private String getPayType(String payType) {
        if (payType.equals("1")) {
            //支付宝
            return getString(R.string.order_paytype_alipay);
        } else if (payType.equals("2")) {
            //微信
            return getString(R.string.order_paytype_wechat);
        } else if (payType.equals("3")) {
            //现金支付
            return getString(R.string.order_paytype_cash);
        } else if (payType.equals("4")) {
            //刷卡支付
            return getString(R.string.order_paytype_card);
        }
        return getString(R.string.order_paytype_default);
    }

    /**
     * 根据订单状态判断是否显示按钮
     */
    private void initOrderStatus(ManagerOrderDetailBean bean) {
        orderStatus = bean.getOrderStatus();
        tvOperator.setVisibility(View.VISIBLE);
        if (orderStatus.equals("0")) {
            //已支付,执行收款
            tvOperator.setText(R.string.order_status_operator_receivable);
        } else if (orderStatus.equals("2")) {
            //已经财务审核，执行发货操作
            tvOperator.setText(R.string.order_status_operator_send);
        } else if (orderStatus.equals("3") || orderStatus.equals("4")) {
            //已收货，操作完成订单
            tvOperator.setText(R.string.order_status_operator_finish);
        } else if (orderStatus.equals("5")) {
            //已收货，操作完成订单
            tvOperator.setText(R.string.order_status_operator_print);
        } else {
            tvOperator.setVisibility(View.GONE);
        }
    }

    /**
     * 显示价格数量信息
     */
    private void initMoneyInfo(ManagerOrderDetailBean bean) {
        String sumAmout = bean.getSumAmout();
        tvTotalMoney.setText("￥" + Arith.get2Decimal(sumAmout + ""));
        String productAmoutQty = bean.getProductAmoutQty();
        tvProductNumber.setText(String.format(getString(R.string.order_list_item_total2), productAmoutQty + ""));
    }


    /**
     * 展示发票信息
     */
    private void initInvoiceInfo(ManagerOrderDetailBean bean) {
        invoiceType = bean.getInvoiceType();
        tvInvoiceType.setText(getInvoiceType(invoiceType));
        if (invoiceType.equals("2")) {
            line7.setVisibility(View.VISIBLE);
            line8.setVisibility(View.VISIBLE);
            line11.setVisibility(View.GONE);
            line12.setVisibility(View.GONE);
            String invoiceTitle = bean.getInvoiceTitle();
            if (invoiceTitle.equals("0")) {
                tvInvoiceHead.setText("个人");
            } else if (invoiceTitle.equals("1")) {
                tvInvoiceHead.setText(bean.getCompanyName());
            }
            String invoiceContent = bean.getInvoiceContent();
            tvInvoiceContent.setText(invoiceContent);
        } else if (invoiceType.equals("3")) {
            line7.setVisibility(View.GONE);
            line8.setVisibility(View.GONE);
            line11.setVisibility(View.VISIBLE);
            line12.setVisibility(View.VISIBLE);
            //公司信息
            gongsi_name.setText(bean.getCompanyName());
            gongsi_biaoshi.setText(bean.getRatepayerCode());
            gongsi_dizhi.setText(bean.getCompanyRegAddress());
            gongsi_guhua.setText(bean.getCompanyTel());
            gongsi_bankName.setText(bean.getDepositBank());
            gongsi_backNum.setText(bean.getDepositAccount());

            //收货人信息
            shoupiao_name.setText(bean.getInvoiceName());
            shoupiao_phone.setText(bean.getInvoiceTel());
            shoupiao_dizhi.setText(bean.getInvoicePro() + bean.getInvoiceCity() + bean.getInvoiceArea() + bean.getInvoiceAddress());
        }

    }

    /**
     * 根据发票状态返回对应的内容
     */
    private String getInvoiceType(String invoiceType) {
        if (invoiceType != null && invoiceType.length() > 0) {
            if (invoiceType.equals("2")) {
                //普通发票
                return getString(R.string.order_detail_invoice_normal);
            } else if (invoiceType.equals("3")) {
                //增值税发票
                return getString(R.string.order_detail_invoice_value);
            }
        }
        return getString(R.string.order_detail_invoice_none);
    }

    /**
     * 展示配送信息
     */
    private void initReceiveInfo(ManagerOrderDetailBean bean) {
        String consigneerName = bean.getConsigneerName();
        String phone = bean.getConsigneerPhone();
        tvReceiver.setText(consigneerName + "/" + phone);
        String consigneerAddress = bean.getConsigneerAddress();
        tvAddress.setText(consigneerAddress);
        String deliveryName = bean.getDeliveryName();
        String deliveryMobile = bean.getDeliveryMobile();
        tvSender.setText(deliveryName + "/" + deliveryMobile);
        String companyName = bean.getLogisticsCompanyName();
        tvCarrierCompany.setText(companyName);
        String logisticsCode = bean.getLogisticsCode();
        tvCourierNumber.setText(logisticsCode);
    }

    private void initProductLineList(List<OrderlineListBean> orderlineList) {
        if (orderlineList == null || orderlineList.size() == 0) {
            return;
        }
        linProductList.removeAllViews();
        for (int i = 0; i < orderlineList.size(); i++) {
            OrderlineListBean orderlineListBean = orderlineList.get(i);
            View view = inflater.inflate(R.layout.item_order_detail_product_list, linProductList, false);
            ImageView productIco = (ImageView) view.findViewById(R.id.item_order_detail_product_ico);
            TextView tvProductName = (TextView) view.findViewById(R.id.item_order_detail_product_name);
            TextView tvProductIntro = (TextView) view.findViewById(R.id.item_order_detail_product_introduce);
            TextView tvPrice = (TextView) view.findViewById(R.id.item_order_detail_product_price);
            TextView tvNumber = (TextView) view.findViewById(R.id.item_order_detail_product_number);
            GlideImageLoadImpl.getInstance().load(this, orderlineListBean.getMainPic_url(), productIco);
            tvProductName.setText(orderlineListBean.getDrinkingName());
            tvProductIntro.setText(orderlineListBean.getDrinkingNameEn());
            tvPrice.setText("￥" + Arith.get2Decimal(orderlineListBean.getProductPrice()));
            tvNumber.setText(getString(R.string.order_detail_number_tip) + orderlineListBean.getProductQty());
            linProductList.addView(view);
        }
    }


    @OnClick(R.id.tv_order_detail_operator)
    public void onClick() {
        if (orderStatus.equals("0")) {
            //收款
            initSelectOperator();
        } else if (orderStatus.equals("2")) {
            //发货
            initSelectOperator();
        } else if (orderStatus.equals("4") || orderStatus.equals("3")) {
            //完成
            showConfirmDialog();
        } else if (orderStatus.equals("5")) {
            //打印小票
            if (getActivity() != null) {
                OrderListActivity orderListActivity = (OrderListActivity) getActivity();
                orderListActivity.print(postion);
            }
        }
    }


    /**
     * 打开操作选择对话框
     */
    private void initSelectOperator() {
        if (orderSendAndPayDialog == null) {
            orderSendAndPayDialog = new OrderSendAndPayDialog();
        }
        Bundle bundle = new Bundle();
        if (orderStatus.equals("0")) {
            bundle.putInt("type", 1);
        } else if (orderStatus.equals("2")) {
            bundle.putInt("type", 2);
        }

        bundle.putString("orderId", orderId);
        bundle.putString("orderCode", orderCode);
        orderSendAndPayDialog.setArguments(bundle);
        orderSendAndPayDialog.show(getFragmentManager(), "orderPayOrSend");
    }


    /**
     * 展示完成提示对话框
     */
    private void showConfirmDialog() {
        if (confirmDialog == null) {
            confirmDialog = new ConfirmDropPointDialog();
        }
        confirmDialog.setClickLisener(new ConfirmDropPointDialog.ClickLisener() {
            @Override
            public void onLeftClick(AlertDialog dialog) {
                //取消
                APIManager.getInstance().setOrderFinish(getActivity(), OrderDetailFragment.this, orderId);
                confirmDialog.dismiss();
            }

            @Override
            public void onRightClick(AlertDialog dialog) {
                //确定
                confirmDialog.dismiss();
            }
        });
        confirmDialog.show(getActivity(), getString(R.string.order_finish_title)
                , getString(R.string.common_cancel)
                , getString(R.string.common_comfirm));

    }

}
