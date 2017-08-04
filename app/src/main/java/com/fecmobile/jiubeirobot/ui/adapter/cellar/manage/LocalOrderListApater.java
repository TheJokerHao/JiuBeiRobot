package com.fecmobile.jiubeirobot.ui.adapter.cellar.manage;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseRecycleAdapter;
import com.fecmobile.jiubeirobot.base.BaseViewHolder;
import com.fecmobile.jiubeirobot.bean.ManagerOrderListBean;
import com.fecmobile.jiubeirobot.listener.ItemButtonClick;
import com.fecmobile.jiubeirobot.ui.dialog.AlertDialog;
import com.fecmobile.jiubeirobot.ui.dialog.ConfirmDropPointDialog;
import com.fecmobile.jiubeirobot.utils.Arith;
import com.fecmobile.jiubeirobot.utils.GlideImageLoadImpl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建人：TheJoker丶豪 on
 * 创建时间：2017/6/14.
 */

public class LocalOrderListApater extends BaseRecycleAdapter<ManagerOrderListBean.OrderListBean> {
    private ItemButtonClick itemClick;
    private Context mContext;
    private LayoutInflater inflater;
    private ConfirmDropPointDialog confirmDialog;

    private Map<String, String> states = new HashMap<String, String>() {
        {
//            put("0","已提交");
//            put("1","已支付");
//            put("2","已财务审核");
//            put("3","已发货");
//            put("4","完成");
//            put("5","打印小票");
//            put("6","已取消");
            put("0", "收款");
            put("1", "财务审核");
            put("2", "发货");
            put("3", "完成");
            put("4", "完成");
            put("5", "打印小票");
            put("6", "已取消");
        }
    };

    public LocalOrderListApater(Context context, List<ManagerOrderListBean.OrderListBean> list) {
        super(context, list, R.layout.item_order_list);
        this.mContext = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void convert(final BaseViewHolder holder, final ManagerOrderListBean.OrderListBean orderListBean) {
        FrameLayout linProduct = holder.getView(R.id.item_order_list_product_fl);
        linProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClick != null) {
                    itemClick.onItemButtonClick(holder.getPostion(), "item");
                }
            }
        });

        TextView tvOperator = holder.getView(R.id.item_order_list_operator);
        ImageView ivFinish = holder.getView(R.id.item_order_list_finish);
        holder.setText(R.id.item_order_list_code, mContext.getString(R.string.order_list_code_title) + orderListBean.getOrderCode());
        String orderAccount = orderListBean.getOrderAccount();
        if (orderAccount == null) {
            orderAccount = context.getString(R.string.common_tourist);
        }
        holder.setText(R.id.item_order_list_member_name, mContext.getString(R.string.order_list_member_title) + orderAccount);

        holder.setText(R.id.item_order_list_product_price, "￥" + Arith.get2Decimal(orderListBean.getSumAmout()));

        holder.setText(R.id.item_order_list_product_number, String.format(mContext.getString(R.string.order_list_item_total)
                , orderListBean.getOrderLineCount()));


        LinearLayout linProductList = holder.getView(R.id.item_order_list_product_list);
        linProductList.removeAllViews();
        List<ManagerOrderListBean.OrderListBean.PicListBean> picList = orderListBean.getPicList();
        for (int i = 0; i < picList.size(); i++) {
            ManagerOrderListBean.OrderListBean.PicListBean picListBean = picList.get(i);
            View inflate = inflater.inflate(R.layout.item_order_list_img, linProductList, false);
            ImageView imageView = (ImageView) inflate.findViewById(R.id.imageView);
            GlideImageLoadImpl.getInstance().load((Activity) mContext, picListBean.getPic(), imageView);
            linProductList.addView(inflate);
        }
        final String orderStatus = orderListBean.getOrderStatus();
        if (orderStatus.equals("0") || orderStatus.equals("2") || orderStatus.equals("3") || orderStatus.equals("4") || orderStatus.equals("5")) {
            tvOperator.setVisibility(View.VISIBLE);
        } else {
            tvOperator.setVisibility(View.GONE);
        }

        ivFinish.setVisibility(View.GONE);

        tvOperator.setText(states.get(orderStatus));
        //tvOperator.setVisibility(View.GONE);

        final int postion = holder.getPostion();
        tvOperator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderStatus.equals("0")) {
                    //收款
                    itemClick.onItemButtonClick(postion, "receive");
                } else if (orderStatus.equals("2")) {
                    //发货
                    itemClick.onItemButtonClick(postion, "send");
                } else if (orderStatus.equals("3") || orderStatus.equals("4")) {
                    //完成
                    showConfirmDialog(postion);
                } else if ("5".equals(orderStatus)) {//已完成  打印小票
                    itemClick.onItemButtonClick(postion, "print");
                }
            }
        });
    }

    /**
     * 展示完成提示对话框
     */
    private void showConfirmDialog(final int postion) {
        if (confirmDialog == null) {
            confirmDialog = new ConfirmDropPointDialog();
        }
        confirmDialog.setClickLisener(new ConfirmDropPointDialog.ClickLisener() {
            @Override
            public void onLeftClick(AlertDialog dialog) {
                //取消
                itemClick.onItemButtonClick(postion, "finish");
                confirmDialog.dismiss();
            }

            @Override
            public void onRightClick(AlertDialog dialog) {
                //确定
                confirmDialog.dismiss();
            }
        });
        confirmDialog.show(mContext, mContext.getString(R.string.order_finish_title)
                , mContext.getString(R.string.common_cancel)
                , mContext.getString(R.string.common_comfirm));
    }

    public void setItemClick(ItemButtonClick itemClick) {
        this.itemClick = itemClick;
    }
}