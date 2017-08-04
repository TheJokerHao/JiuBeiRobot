package com.fecmobile.jiubeirobot.ui.activity.cellar.manager;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseActivity;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.base.BaseData;
import com.fecmobile.jiubeirobot.bean.ManagerOrderListBean;
import com.fecmobile.jiubeirobot.bean.ObjBean;
import com.fecmobile.jiubeirobot.bean.ReceiptInfoBean;
import com.fecmobile.jiubeirobot.common.Activitys;
import com.fecmobile.jiubeirobot.common.Constants;
import com.fecmobile.jiubeirobot.data.net.APIManager;
import com.fecmobile.jiubeirobot.data.net.FLags;
import com.fecmobile.jiubeirobot.listener.ItemButtonClick;
import com.fecmobile.jiubeirobot.listener.ItemClick;
import com.fecmobile.jiubeirobot.ui.adapter.cellar.manage.OrderListApater;
import com.fecmobile.jiubeirobot.ui.dialog.cellar.manage.OrderSendAndPayDialog;
import com.fecmobile.jiubeirobot.ui.dialog.cellar.manage.SelectBluetoothAdapterDialog;
import com.fecmobile.jiubeirobot.ui.fragment.cellar.manage.OrderDetailFragment;
import com.fecmobile.jiubeirobot.ui.popupwindow.ListPopupWindow;
import com.fecmobile.jiubeirobot.utils.Arith;
import com.fecmobile.jiubeirobot.utils.BluetoothAdapterUtils;
import com.fecmobile.jiubeirobot.utils.L;
import com.fecmobile.jiubeirobot.utils.PageUtils;
import com.fecmobile.jiubeirobot.utils.RegexUtils;
import com.fecmobile.jiubeirobot.utils.SpannableStringUtils;
import com.fecmobile.jiubeirobot.utils.T;
import com.fecmobile.receiptsprint.BluetoothService;
import com.fecmobile.receiptsprint.TextFormatUtil;
import com.fecmobile.xrecyclerview.XRecyclerView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类描述    : 订单列表  系统管理点击之后的订单列表显示
 * 包名      : com.fecmobile.jiubeirobot.ui.activity.cellar.manager
 * 类名称    : OrderListActivity
 * 创建人    : lc
 * 创建时间  :  2017-03-13
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class OrderListActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener,
        DrawerLayout.DrawerListener,
        XRecyclerView.LoadingListener,
        ItemButtonClick,
        SelectBluetoothAdapterDialog.SelectDriverLinsener {
    @BindView(R.id.llyt_back)
    LinearLayout llytBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_search_icon)
    ImageView ivSearchIcon;
    @BindView(R.id.rg_order_list_filter)
    RadioGroup rgOrderListFilter;

    @BindView(R.id.rv_order_list)
    XRecyclerView rvOrderList;

    @BindView(R.id.dl_order_list)
    DrawerLayout dlOrderList;
    /***
     * 订单完成订单总量
     */
    @BindView(R.id.tv_order_finish_number)
    TextView tvFinishNumber;
    /***
     * 订单完成标签展示订单总额
     */
    @BindView(R.id.tv_order_finish_money)
    TextView tvFinishMoney;
    /**
     * 订单完成总额展示
     */
    @BindView(R.id.rl_order_finish_total)
    RelativeLayout rlFinishTotal;
    @BindView(R.id.tv_order_list_select_time)
    TextView tvSelectTime;

    private List<ManagerOrderListBean.OrderListBean> mData;
    private OrderDetailFragment orderDetailFragment;

    /**
     * 筛选时间 1.全部 2.六个月 3.三个月 4.一个月 5.一个星期
     */
    private String timeSection = "1";
    private int filterStatus;
    private OrderListApater mAdapter;
    private String searchContent;

    private OrderSendAndPayDialog sendAndPayDialog;
    private ListPopupWindow listPopupWindow;
    private List<Map<String, Object>> mTimeData;
    private PageUtils pageUtils = new PageUtils();

    private BluetoothAdapterUtils bluetoos = new BluetoothAdapterUtils();
    private SelectBluetoothAdapterDialog selectBluetoothAdapterDialog = new SelectBluetoothAdapterDialog();

    private int printPos;

    @Override
    public int pageLayout() {
        return R.layout.activity_order_list;
    }

    @Override
    public void initTitle() {
        tvTitle.setText(R.string.order_list_title);
        rgOrderListFilter.setOnCheckedChangeListener(this);
    }

    @Override
    public void initView() {
        rvOrderList.setPullRefreshEnabled(true);// 下拉刷新
        rvOrderList.setLoadingMoreEnabled(false);//加载更多
        rvOrderList.setLoadingListener(this);//加载监听
        pageUtils.setPageSize(10);

        dlOrderList.addDrawerListener(this);
//        dlOrderList.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        mData = new ArrayList<ManagerOrderListBean.OrderListBean>();
        Drawable dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider_sample);
        rvOrderList.addItemDecoration(rvOrderList.new DividerItemDecoration(dividerDrawable));
        rvOrderList.setLayoutManager(new LinearLayoutManager(this, OrientationHelper.VERTICAL, false));
        mAdapter = new OrderListApater(this, mData);
        mAdapter.setItemClick(this);
        rvOrderList.setAdapter(mAdapter);

        int filterStatus = getOrderStatus();//获取传过来的值  判断当前应该显示那个tab
        if (filterStatus == 0) {
            //全部
            rgOrderListFilter.check(R.id.rb_order_list_all);
        } else if (filterStatus == 1) {
            //未付款
            rgOrderListFilter.check(R.id.rb_order_list_unpay);
        } else if (filterStatus == 2) {
            //未发货
            rgOrderListFilter.check(R.id.rb_order_list_unsend);
        }
        selectBluetoothAdapterDialog.setSelectDriverLinsener(this);
    }

    @Override
    public void onSuccess(BaseBean bean, int flag) {
        switch (flag) {
            case FLags.FLAG_ORDER_LIST:
                BaseBean<ObjBean<ManagerOrderListBean>> beanBaseBean = bean;
                ManagerOrderListBean obj = beanBaseBean.getData().getObj();
                initObj(obj);
                initListData(obj);
                dismissHUD();
                break;
            case FLags.FLAG_ORDER_FINISH:
                //刷新数据
                rvOrderList.refresh();
                if (orderDetailFragment != null) {
                    orderDetailFragment.onResume();
                }
                break;
            case FLags.FLAG_ORDER_RECEIABLE:
                //订单收款
                rvOrderList.refresh();
                if (orderDetailFragment != null) {
                    orderDetailFragment.onResume();
                }
                break;
            case FLags.FLAG_ORDER_SEND:
                //订单发货
                rvOrderList.refresh();
                if (orderDetailFragment != null) {
                    orderDetailFragment.onResume();
                }
                break;
            case FLags.FLAG_RECEIPT_INFO:
                //打印小票
                BaseBean<ObjBean<ReceiptInfoBean>> receBaseBean = bean;
                ReceiptInfoBean receiBean = receBaseBean.getData().getObj();
                printReceipt(receiBean);
                break;
        }
        dismissHUD();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onError(String error, int flag) {
        super.onError(error, flag);
        switch (flag) {
            case FLags.FLAG_ORDER_LIST:

                break;
        }
    }

    private void initListData(ManagerOrderListBean obj) {
        pageUtils.setTotalNum(obj.getRowCount());
        rvOrderList.setLoadingMoreEnabled(pageUtils.isMore());
        if (pageUtils.isCurentLoadMode()) {
            rvOrderList.loadMoreComplete();
        } else {
            mData.clear();
            rvOrderList.refreshComplete();
        }
        mData.addAll(obj.getOrderList());
        mAdapter.notifyDataSetChanged();
    }


    private void initObj(ManagerOrderListBean obj) {
        if (obj == null) {
            return;
        }

        double amountCount = obj.getAmountCount();
        int orderCount = obj.getOrderCount();
        String totalCount = String.format("<font color='#666666'>" + getString(R.string.order_list_total_count) + "</>",
                "<font color='#333333'>" + orderCount + "</font>");
        tvFinishNumber.setText(Html.fromHtml(totalCount));
//        tvFinishMoney.setText(Html.fromHtml(totalMoney));
        SpannableStringUtils.Builder builder = SpannableStringUtils.getBuilder(getString(R.string.order_list_total_money));
        builder.setForegroundColor(Color.parseColor("#666666"));
        builder.append("￥" + amountCount);
        builder.setForegroundColor(Color.parseColor("#CB2E48"));
        builder.setProportion(1.3f);
        SpannableStringBuilder spannableStringBuilder = builder.create();
        tvFinishMoney.setText(spannableStringBuilder);
    }


    /**
     * 获取跳转的订单状态 判断是显示那个页面
     */
    private int getOrderStatus() {
        return getIntent().getIntExtra(Constants.ORDER_LIST_STATUS, 0);
    }


    @OnClick({R.id.llyt_back, R.id.iv_search_icon, R.id.tv_order_list_select_time, R.id.fl_order_detail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llyt_back:
                finish();
                break;
            case R.id.iv_search_icon:
                Activitys.toOrderSearch(this, searchContent);
                break;
            case R.id.fl_order_detail:
                orderDetailFragment.onResume();
                dlOrderList.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                break;
            case R.id.tv_order_list_select_time:
                //初始化popupwindow数据
                if (listPopupWindow == null) {
                    mTimeData = new ArrayList<Map<String, Object>>();
                    String[] stringArray = getResources().getStringArray(R.array.orderTimeList);
                    for (int i = 0; i < stringArray.length; i++) {
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("checked", false);
                        map.put("name", stringArray[i]);
                        map.put("postion", (i + 1));
                        mTimeData.add(map);
                    }
                    listPopupWindow = new ListPopupWindow(this, mTimeData);
                    listPopupWindow.setItemClick(new ItemClick() {
                        @Override
                        public void onItemClick(int postion) {
                            timeSection = String.valueOf(mTimeData.get(postion).get("postion"));
                            tvSelectTime.setText(String.valueOf(mTimeData.get(postion).get("name")));
                            rvOrderList.refresh();
                        }
                    });
                }
                listPopupWindow.show(view);
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        rlFinishTotal.setVisibility(View.GONE);
        switch (checkedId) {
            case R.id.rb_order_list_all:
                //全部订单
                filterStatus = 0;
                break;
            case R.id.rb_order_list_unpay:
                //未支付订单
                filterStatus = 1;
                break;
            case R.id.rb_order_list_unsend:
                //未发货
                filterStatus = 2;
                break;
            case R.id.rb_order_list_finish:
                //已完成
                filterStatus = 3;
                rlFinishTotal.setVisibility(View.VISIBLE);
                break;
        }
        rvOrderList.refresh();
    }


    /**
     * TODO   发送显示订单的联网请求
     */
    private void setLoading() {
        APIManager.getInstance().getOrderList(this, filterStatus, timeSection, pageUtils.getPageIndex(), pageUtils.getPageSize(), searchContent);
    }


    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {

    }

    @Override
    public void onDrawerClosed(View drawerView) {
        switch (drawerView.getId()) {
            case R.id.fl_shop_details:
                //  dlOrderList.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                // onRefresh();
                break;
        }
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }


    private void setDetailId(int position) {
        ManagerOrderListBean.OrderListBean bean = mData.get(position);
        dlOrderList.openDrawer(Gravity.RIGHT);
        if (bean != null) {
            orderDetailFragment.setId(bean.getId());
            orderDetailFragment.setPostion(position);
        }
    }

    /**
     * item 点击显示订单详情
     *
     * @param position
     */

    private void addOrderDetailFragment(int position) {
        if (orderDetailFragment == null) {
            orderDetailFragment = new OrderDetailFragment();
            FragmentManager manager = getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.fl_order_detail, orderDetailFragment);
            transaction.commit();
            setDetailId(position);
        } else {
            setDetailId(position);
            orderDetailFragment.onResume();
        }
    }

    @Override
    public void onRefresh() {
        pageUtils.setCurentLoadMode(false);
        setLoading();
    }

    @Override
    public void onLoadMore() {
        pageUtils.setCurentLoadMode(true);
        setLoading();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            switch (requestCode) {
                case Constants.ORDER_SEARCH:
                    //搜索内容
                    pageUtils.setPageIndex(0);
                    searchContent = data.getStringExtra(Constants.ORDER_LIST_SEARCH);
                    setLoading();
                    break;
            }
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        orderDetailFragment = null;
        if (listPopupWindow != null) {
            listPopupWindow.dismiss(true);
        }
    }

    @Override
    public void onItemButtonClick(int position, Object type) {
        if (mData == null || mData.size() == 0) {
            return;
        }
        String method = String.valueOf(type);
        L.i(method);
        ManagerOrderListBean.OrderListBean orderListBean = mData.get(position);
        if (method.equals("item")) {
            if (dlOrderList.isDrawerOpen(Gravity.RIGHT)) {
                dlOrderList.closeDrawer(Gravity.RIGHT);
            } else {
                addOrderDetailFragment(position);
            }
        } else if (method.equals("finish")) {
            //TODO 订单完成  发送请求的信息
            L.d("OrderListActivity", method);
            APIManager.getInstance().setOrderFinish(this, this, orderListBean.getId());
        } else if (method.equals("receive") || method.equals("send")) {
            //收款和发货操作
            sendAndPayDialog = new OrderSendAndPayDialog();
            Bundle bundle = new Bundle();
            if (method.equals("receive")) {
                bundle.putInt("type", 1);
            } else {
                bundle.putInt("type", 2);
            }
            bundle.putString("orderId", orderListBean.getId());
            bundle.putString("orderCode", orderListBean.getOrderCode());
            sendAndPayDialog.setArguments(bundle);
            sendAndPayDialog.show(getSupportFragmentManager(), "SendOrPay");
        } else if ("print".equals(method)) {
            printPos = position;
            print(position);
        }
    }

    /**
     * 点击打印  开始检测蓝牙
     *
     * @param position
     */
    public void print(int position) {
        if (!bluetoos.isSupportBluetooth()) {
            T.showToCenter(getString(R.string.common_adapter_no_support));
            return;
        }
        if (!bluetoos.isEnabled()) {
            bluetoos.openBluetooth(this);
            return;
        }

        L.i("getService：" + bluetoos.getService(this).getState());

        if (bluetoos.getService(this).getState() != BluetoothService.STATE_CONNECTED) {
            T.showToCenter(getString(R.string.common_please_connec_driver));
            selectBluetoothAdapterDialog.show(getSupportFragmentManager(), "selectBluetoothAdapterDialog");
        } else {
            APIManager.getInstance().getReceiptByOrderID(this, mData.get(position).getId());
        }
    }

    /**
     * 描述      :打印小票
     * 方法名    :  printReceipt
     * param    :   无
     * 返回类型  : 无
     * 创建人    : ghy
     * 创建时间  : 2017/3/24 14:23
     * 修改人    :
     * 修改时间
     * 修改备注
     * throws
     */
    private void printReceipt(ReceiptInfoBean receiBean) {
        TextFormatUtil t = new TextFormatUtil();
        String cellarName = BaseData.getBaseData().getCellarInfoBean().getCellarName();
        String account = mData.get(printPos).getOrderAccount();
        String orderCode = mData.get(printPos).getOrderCode();
        if (account == null || "null".equals(account) || account.length() == 0) {
            account = "";
        }
        sendMessage(t.getLineTitle(cellarName));
        sendMessage(" \n");
        sendMessage("机器号：" + BaseData.getBaseData().getCellarInfoBean().getRobotCode() + "\t服务员：" + RegexUtils.dismissPhoneNumber6(BaseData.getBaseData().getLoginManagerBean().getAccount()));
        sendMessage(" \n");
        sendMessage("订单号：" + orderCode);
        sendMessage(" \n");
        sendMessage("日期：" + receiBean.getTicketTime());
        sendMessage(" \n =============================\n");
        int len = receiBean.getList().size();
        for (int i = 0; i < len; i++) {
            sendMessage("商品名称：" + receiBean.getList().get(i).getProductName());
            sendMessage(" \n");
            sendMessage("数 量：" + receiBean.getList().get(i).getProductQty());
            sendMessage(" \n");
            sendMessage("单 价：￥" + Arith.get2Decimal(receiBean.getList().get(i).getProductPrice()));
            sendMessage(" \n");
            sendMessage("金 额：￥" + Arith.get2Decimal(receiBean.getList().get(i).getProductAmout()));
            if (i != len - 1) {
                sendMessage(" \n ------------------------------ \n");
            }
        }
        sendMessage(" \n ==============================\n");
        sendMessage("总 额：￥" + Arith.get2Decimal(receiBean.getTotalAmount()));
        sendMessage(" \n");
        sendMessage("折 扣：￥" + Arith.get2Decimal(receiBean.getDiscount()));
        sendMessage(" \n");
        sendMessage("应 付：￥" + Arith.get2Decimal(receiBean.getShouldPay()));
        sendMessage(" \n");
        sendMessage(" \n");
        sendMessage("欢迎下次光临 " + account);
        sendMessage(" \n");
        sendMessage(" \n");
        sendMessage(" \n");
        sendMessage(" \n");
    }

    /**
     * 打印
     *
     * @param message
     */
    private void sendMessage(String message) {
        if (message.length() > 0) {
            // Get the message bytes and tell the BluetoothService to write
            byte[] send;
            try {
                send = message.getBytes("GBK");
            } catch (UnsupportedEncodingException e) {
                send = message.getBytes();
            }
            bluetoos.getService(this).write(send);
        }
    }


    @Override
    public void onSelected(BluetoothDevice device) {
        bluetoos.getService(this).connect(device);
    }
}
