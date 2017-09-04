package com.fecmobile.jiubeirobot.ui.adapter.cellar.manage;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.View;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseRecycleAdapter;
import com.fecmobile.jiubeirobot.base.BaseViewHolder;
import com.fecmobile.jiubeirobot.listener.ItemClick;

import java.util.List;

/**
 * 类描述    : 已配对蓝牙
 * 包名      : com.fecmobile.jiubeirobot.ui.adapter.cellar.manage
 * 类名称    : SelectBluetoothAdapter
 * 创建人    : ghy
 * 创建时间  : 2017/3/24 11:28
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class SelectBluetoothAdapter extends BaseRecycleAdapter<BluetoothDevice> {
    private ItemClick itemClick;

    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public SelectBluetoothAdapter(Context context, List<BluetoothDevice> list) {
        super(context, list, R.layout.item_pari_bluetooth_item);
    }

    @Override
    public void convert(final BaseViewHolder holder, BluetoothDevice o) {
        holder.setText(R.id.tv_name, o.getName() + "  " + o.getAddress());
        holder.getView(R.id.tv_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClick.onItemClick(holder.getPostion());
            }
        });
    }


}
