package com.fecmobile.jiubeirobot.ui.adapter.shop;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseRecycleAdapter;
import com.fecmobile.jiubeirobot.base.BaseViewHolder;
import com.fecmobile.jiubeirobot.bean.ShopListBean;
import com.fecmobile.jiubeirobot.bean.StockBean;
import com.fecmobile.jiubeirobot.listener.ItemClick;
import com.fecmobile.jiubeirobot.utils.L;
import com.fecmobile.jiubeirobot.utils.T;

import java.util.List;

/**
 * 类描述    :商品列表适配器
 * 包名      : com.fecmobile.jiubeirobot.ui.adapter.shop
 * 类名称    : ShopListAdapter
 * 创建人    : ghy
 * 创建时间  : 2017/2/22 17:56
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class ShopListAdapter extends BaseRecycleAdapter<ShopListBean> {
    private ClickCell itemClick;

    public void setClickCell(ClickCell itemClick) {
        this.itemClick = itemClick;
    }

    public ShopListAdapter(Context context, List<ShopListBean> list) {
        super(context, list, R.layout.item_shop_list);
    }

    @Override
    public void convert(final BaseViewHolder holder, final ShopListBean bean) {
        holder.getView(R.id.llyt_item_bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClick != null) {
                    itemClick.onItemClick(holder.getPostion());
                }
            }
        });
        //点击加入购物车
        holder.getView(R.id.tv_add_to_card).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClick != null) {
                    if (bean.getProductStock().equals("0")) {
                        T.show("库存不足", 2);
                    } else {
                        int[] startLocation = new int[2];// 一个整型数组，用来存储按钮的在屏幕的X、Y坐标
                        v.getLocationInWindow(startLocation);// 这是获取购买按钮的在屏幕的X、Y坐标（这也是动画开始的坐标）
                        ImageView ball = new ImageView(context);
                        ball.setImageResource(R.mipmap.card_anim_icon);
                        L.i("startLocation：" + startLocation[0] + "    " + startLocation[1]);

                        //这是本地购物车的实体类  通过这个实体类来存储相应的参数
                        StockBean sbean = new StockBean();
                        sbean.setId(bean.getId());
                        sbean.setMainPicUrl(bean.getMainPicUrl());
                        sbean.setDrinkingName(bean.getDrinkingName());
                        sbean.setDrinkingNameEn(bean.getDrinkingNameEn());
                        sbean.setLsPrice(bean.getLsPrice());
                        sbean.setOriginName(bean.getOriginName());
                        sbean.setChateauName(bean.getChateauName());
                        sbean.setProductStock(bean.getProductStock());
                        itemClick.addToCard(ball, startLocation, sbean, 1);
                    }
                }
            }
        });

        holder.setImg(R.id.iv_shop_img, bean.getMainPicUrl());
        holder.setImg(R.id.tv_national_floag, bean.getOriginLogo());
        holder.setText(R.id.tv_shop_name, bean.getDrinkingName());
        holder.setText(R.id.tv_shop_name_en, bean.getDrinkingNameEn());
        holder.setText(R.id.tv_chateau, bean.getChateauName());
        holder.setText(R.id.tv_repertory, "库存：" + bean.getProductStock());
        holder.setText(R.id.tv_price, "￥" + bean.getLsPrice());
        holder.setText(R.id.tv_national, bean.getOriginName());
    }

    /**
     * item的点击监听回调
     */
    public interface ClickCell extends ItemClick {
        void addToCard(View v, int[] startLocation, StockBean stockBean, int shopNum);
    }
}