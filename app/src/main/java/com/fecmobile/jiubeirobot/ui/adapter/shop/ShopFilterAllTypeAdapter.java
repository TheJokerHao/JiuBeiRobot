package com.fecmobile.jiubeirobot.ui.adapter.shop;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseRecycleAdapter;
import com.fecmobile.jiubeirobot.base.BaseViewHolder;
import com.fecmobile.jiubeirobot.bean.IShopFilterInteface;
import com.fecmobile.jiubeirobot.bean.ShopFilterBean;
import com.fecmobile.jiubeirobot.listener.ItemClick;

import java.util.List;

/**
 * 类描述    : 筛选全部分类
 * 包名      : com.fecmobile.jiubeirobot.ui.adapter.shop
 * 类名称    : ShopFilterAllTypeAdapter
 * 创建人    : ghy
 * 创建时间  : 2017/2/23 14:32
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class ShopFilterAllTypeAdapter extends BaseRecycleAdapter<IShopFilterInteface> {
    private ItemClick itemClick;
    private int classLevel = -1;
    private int current = -1;

    public void setClassLevel(int classLevel) {
        this.classLevel = classLevel;
    }

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public ShopFilterAllTypeAdapter(Context context, List<IShopFilterInteface> list) {
        super(context, list, R.layout.item_shop_filter_all_type_item);
    }

    @Override
    public void convert(final BaseViewHolder holder, IShopFilterInteface bean) {
        holder.getView(R.id.rl_item).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClick != null) {
                    itemClick.onItemClick(holder.getPostion());
                    if (classLevel != -1) {
                        current = holder.getPostion();
                        notifyDataSetChanged();
                    }
                }
            }
        });
        ImageView ivMore = holder.getView(R.id.iv_more);
        TextView tvName = holder.getView(R.id.tv_name);
        TextView tvDes = holder.getView(R.id.tv_des);
        if (classLevel == -1) {
            tvDes.setVisibility(View.VISIBLE);
            tvDes.setText("全部");
        } else {
            tvDes.setVisibility(View.GONE);
            if (current == holder.getPostion()) {
                ivMore.setImageResource(R.mipmap.select_img);
                ivMore.setVisibility(View.VISIBLE);
            } else {
                ivMore.setVisibility(View.GONE);
            }
        }
        tvName.setText(bean.getClassName());
    }

}
