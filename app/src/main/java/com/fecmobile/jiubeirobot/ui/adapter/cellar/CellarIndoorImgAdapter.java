package com.fecmobile.jiubeirobot.ui.adapter.cellar;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseFragment;
import com.fecmobile.jiubeirobot.base.BaseRecycleAdapter;
import com.fecmobile.jiubeirobot.base.BaseViewHolder;
import com.fecmobile.jiubeirobot.common.view.CommAdapter;
import com.fecmobile.jiubeirobot.common.view.CommViewHolder;
import com.fecmobile.jiubeirobot.listener.ItemClick;
import com.fecmobile.jiubeirobot.utils.GlideImageLoadImpl;
import com.fecmobile.jiubeirobot.utils.L;

import java.util.List;

/**
 * 类描述    : 图片适配器
 * 包名      : com.fecmobile.jiubeirobot.ui.adapter.cellar
 * 类名称    : CellarIndoorImgAdapter
 * 创建人    : ghy
 * 创建时间  : 2017/3/1 16:39
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class CellarIndoorImgAdapter extends BaseRecycleAdapter<String> {
    private ItemClick itemClick;
    private BaseFragment fragment;

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    /**
     * @param mContext 上下文对象
     * @param mData    数据源
     */
    public CellarIndoorImgAdapter(Context mContext, BaseFragment fragment, List<String> mData) {
        super(mContext, mData, R.layout.item_img_item);
        this.fragment = fragment;
    }


    @Override
    public void convert(final BaseViewHolder holder, String s) {
        if (itemClick != null) {
            holder.getView(R.id.llyt_bg).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClick.onItemClick(holder.getPosition());
                }
            });
        }
        L.i("URL：" + s);
        GlideImageLoadImpl.getInstance().load(fragment, s, (ImageView) holder.getView(R.id.iv_img));
    }
}
