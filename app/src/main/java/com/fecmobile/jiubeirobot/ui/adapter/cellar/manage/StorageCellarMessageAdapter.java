package com.fecmobile.jiubeirobot.ui.adapter.cellar.manage;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseRecycleAdapter;
import com.fecmobile.jiubeirobot.base.BaseViewHolder;
import com.fecmobile.jiubeirobot.bean.StorageCellBean;
import com.fecmobile.jiubeirobot.bean.StorageCellMessageBean;
import com.fecmobile.jiubeirobot.bean.StorageCellarTabDetailBean;
import com.fecmobile.jiubeirobot.utils.BasicTool;

import java.util.List;

/**
 * 类描述    : 存储酒品详情的adapter
 * 包名      : com.fecmobile.jiubeirobot.ui.adapter.cellar.manage
 * 类名称    : StorageCellarMessageAdapter
 * 创建人    : wangxing
 * 创建时间  :  2017/3/14   11:26
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */

public class StorageCellarMessageAdapter extends BaseRecycleAdapter<StorageCellarTabDetailBean.StockListBean> {

    public StorageCellarMessageAdapter(Context context, List<StorageCellarTabDetailBean.StockListBean> list) {
        super(context, list, R.layout.item_storage_cellar_message);
    }


    @Override
    public void convert(BaseViewHolder holder, StorageCellarTabDetailBean.StockListBean stockListBean) {

        holder.setText(R.id.tv_storage_name, stockListBean.getAccountName());
        holder.setText(R.id.tv_storage_start_time, stockListBean.getStorageTime());
        TextView tv_storage_start_time = holder.getView(R.id.tv_storage_start_time);
        BasicTool.setYMDTime(stockListBean.getStorageTime(), tv_storage_start_time);
        holder.setText(R.id.tv_storage_age, stockListBean.getStorageYears() + "年");
        holder.setText(R.id.tv_storage_all_number, stockListBean.getStock() + "瓶");

        if (BasicTool.isNotEmpty(stockListBean.getSaleStock())) {
            holder.setText(R.id.tv_storage_number, stockListBean.getSaleStock() + "瓶");
        } else {
            holder.setText(R.id.tv_storage_number, "0" + "瓶");
        }

    }
}
