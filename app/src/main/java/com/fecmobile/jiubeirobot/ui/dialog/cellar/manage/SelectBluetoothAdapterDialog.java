package com.fecmobile.jiubeirobot.ui.dialog.cellar.manage;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseDialogFragment;
import com.fecmobile.jiubeirobot.common.view.MyRecycleView;
import com.fecmobile.jiubeirobot.listener.ItemClick;
import com.fecmobile.jiubeirobot.ui.adapter.cellar.manage.SelectBluetoothAdapter;
import com.fecmobile.jiubeirobot.utils.DetectTool;
import com.fecmobile.jiubeirobot.utils.L;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类描述    :选择蓝牙打印机
 * 包名      : com.fecmobile.jiubeirobot.ui.dialog.cellar.manage
 * 类名称    : SelectBluetoothAdapterDialog
 * 创建人    : ghy
 * 创建时间  : 2017/3/24 10:30
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */

public class SelectBluetoothAdapterDialog extends BaseDialogFragment implements ItemClick {
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.lLayout_bg)
    RelativeLayout lLayoutBg;
    @BindView(R.id.mrv_driver_list)
    MyRecycleView mrvDriverList;
    private BluetoothAdapter bluetoothAdapter;
    private SelectBluetoothAdapter selectBluetoothAdapter;
    private List<BluetoothDevice> datas = new ArrayList<>();
    private SelectDriverLinsener selectDriverLinsener;

    public void setSelectDriverLinsener(SelectDriverLinsener selectDriverLinsener) {
        this.selectDriverLinsener = selectDriverLinsener;
    }

    @Override
    protected int layout() {
        return R.layout.dialog_select_bluetooth_adapter;
    }

    @Override
    protected ViewGroup.LayoutParams setLayoutParams() {
        return new FrameLayout.LayoutParams((int) (DetectTool.getResolutionX(getContext()) * 0.459), LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected void initView() {
        mrvDriverList.setLayoutManager(new LinearLayoutManager(getContext()));

        selectBluetoothAdapter = new SelectBluetoothAdapter(getContext(), datas);
        mrvDriverList.setAdapter(selectBluetoothAdapter);
        selectBluetoothAdapter.setItemClick(this);
    }


    @OnClick({R.id.iv_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
        }
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        super.show(manager, tag);
        datas.clear();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter != null) {
            Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
            if (pairedDevices != null) {
                datas.addAll(new ArrayList<BluetoothDevice>(pairedDevices));
                if (selectBluetoothAdapter != null) {
                    selectBluetoothAdapter.notifyDataSetChanged();
                }
                L.i("----------------------------" + datas);
            }
        }
    }

    @Override
    public void onItemClick(int postion) {
        if (selectDriverLinsener != null) {
            selectDriverLinsener.onSelected(datas.get(postion));
            dismiss();
        }
    }

    public interface SelectDriverLinsener {
        void onSelected(BluetoothDevice device);
    }
}
