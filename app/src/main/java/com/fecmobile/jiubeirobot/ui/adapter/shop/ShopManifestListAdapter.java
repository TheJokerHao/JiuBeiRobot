package com.fecmobile.jiubeirobot.ui.adapter.shop;

import android.content.Context;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseData;
import com.fecmobile.jiubeirobot.base.BaseRecycleAdapter;
import com.fecmobile.jiubeirobot.base.BaseViewHolder;
import com.fecmobile.jiubeirobot.bean.StockBean;

import java.util.List;

/**
 * 类描述    :商品清单列表
 * 包名      : com.fecmobile.jiubeirobot.ui.adapter.shop
 * 类名称    : ShopManifestListAdapter
 * 创建人    : ghy
 * 创建时间  : 2017/3/3 10:03
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */

public class ShopManifestListAdapter extends BaseRecycleAdapter<StockBean> {

    public ShopManifestListAdapter(Context context, List<StockBean> list) {
        super(context, list, R.layout.item_shop_manifest_item);
    }

    @Override
    public void convert(BaseViewHolder holder, StockBean s) {
        holder.setImg(R.id.iv_shop_img, s.getMainPicUrl());
        holder.setText(R.id.iv_shop_name, s.getDrinkingName());
        holder.setText(R.id.iv_shop_name_en, s.getDrinkingNameEn());
        holder.setText(R.id.iv_shop_price, "￥" + s.getLsPrice());
        holder.setText(R.id.iv_shop_num, "X" + s.getNum());
    }
}
