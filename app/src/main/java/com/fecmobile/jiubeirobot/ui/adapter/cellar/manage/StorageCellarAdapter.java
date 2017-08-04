package com.fecmobile.jiubeirobot.ui.adapter.cellar.manage;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseRecycleAdapter;
import com.fecmobile.jiubeirobot.base.BaseViewHolder;
import com.fecmobile.jiubeirobot.bean.StorageCellBean;
import com.fecmobile.jiubeirobot.listener.ItemClick;
import com.fecmobile.jiubeirobot.utils.GlideImageLoadImpl;
import com.fecmobile.jiubeirobot.utils.L;

import java.util.List;

/**
 * 类描述    : 酒窖库存adapter
 * 包名      : com.fecmobile.jiubeirobot.ui.adapter.cellar.manage
 * 类名称    : StorageCellarAdapter
 * 创建人    : wangxing
 * 创建时间  :  2017/3/13   11:54
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */

public class StorageCellarAdapter extends BaseRecycleAdapter<StorageCellBean> {
    private ItemClick mItemClick;
    private StorageCellBean mStorageCellBean;
    private int id;
    private Context mContext;

    public void setOnIntemClick(ItemClick clickMessage) {
        this.mItemClick = clickMessage;
    }

    public StorageCellarAdapter(Context context, List<StorageCellBean> list, int layoutId) {

        super(context, list, R.layout.item_storage_cellar_tab);
        id = layoutId;
        mContext = context;
    }

    @Override
    public void convert(final BaseViewHolder holder, final StorageCellBean storageCellBean) {
//        L.d("酒窖存储的数据:" + storageCellBean.toString());
        mStorageCellBean = storageCellBean;
        holder.setText(R.id.iv_cellar_name, mStorageCellBean.getProductName());
        holder.setText(R.id.iv_cellar_type, mStorageCellBean.getClassName());
        holder.setText(R.id.iv_cellar_all_storage, mStorageCellBean.getTotalStock());
        holder.setText(R.id.iv_cellar_cellar, mStorageCellBean.getChateauName());
        ImageView iv_cellar_icon = holder.getView(R.id.iv_cellar_icon);
        String mainPic = mStorageCellBean.getMainPic();
        GlideImageLoadImpl.getInstance().load(mContext, mainPic, iv_cellar_icon);

        holder.getView(R.id.llyt_item_storage_cellar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClick != null) {
                    mItemClick.onItemClick(holder.getPostion());
                }
            }
        });

    }

}
