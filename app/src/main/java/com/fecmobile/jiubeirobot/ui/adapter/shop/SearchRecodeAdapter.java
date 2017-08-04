package com.fecmobile.jiubeirobot.ui.adapter.shop;

import android.content.Context;
import android.view.View;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseRecycleAdapter;
import com.fecmobile.jiubeirobot.base.BaseViewHolder;
import com.fecmobile.jiubeirobot.listener.ItemClick;

import java.util.List;

/**
 * 类描述    :热门推荐、搜索记录适配器
 * 包名      : com.fecmobile.jiubeirobot.ui.adapter.shop
 * 类名称    : SearchRecodeAdapter
 * 创建人    : ghy
 * 创建时间  : 2017/3/4 16:37
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class SearchRecodeAdapter extends BaseRecycleAdapter<String> {
    private ItemClick itemClick;

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public SearchRecodeAdapter(Context context, List<String> list) {
        super(context, list, R.layout.item_search_recode_item);
    }

    @Override
    public void convert(final BaseViewHolder holder, String s) {
        holder.setText(R.id.tv_text, s);
        holder.getView(R.id.llyt_bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClick != null) {
                    itemClick.onItemClick(holder.getPostion());
                }
            }
        });
    }
}
