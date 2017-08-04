package com.fecmobile.jiubeirobot.utils;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.fecmobile.receiptsprint.BluetoothService;

/**
 * 类描述    :蓝牙工具类
 * 包名      : com.fecmobile.jiubeirobot.utils
 * 类名称    : BluetoothAdapterUtils
 * 创建人    : ghy
 * 创建时间  : 2017/3/24 9:41
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */

public class BluetoothAdapterUtils {
    private BluetoothAdapter mBluetoothAdapter;
    private static BluetoothService mService;
    public static final int REQUEST_ENABLE_BT = 2;
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    public static final String TOAST = "toast";
    public static final String DEVICE_NAME = "device_name";
    private String mConnectedDeviceName;

    public BluetoothService getService(Activity aty) {
        if (mService == null) {
            mService = new BluetoothService(aty, mHandler);
        }
        return mService;
    }

    /**
     * 判断支持蓝牙
     *
     * @return
     */
    public boolean isSupportBluetooth() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        L.i(mBluetoothAdapter + "");
        if (mBluetoothAdapter == null) {
            return false;
        }
        return true;
    }

    /**
     * 判断是否打开
     *
     * @return
     */
    public boolean isEnabled() {
        return mBluetoothAdapter.isEnabled();
    }

    /**
     * 打开蓝牙
     *
     * @param aty
     */
    public void openBluetooth(Activity aty) {
        if (!mBluetoothAdapter.isEnabled()) {
            // 打开蓝牙
            Intent enableIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            aty.startActivityForResult(enableIntent, REQUEST_ENABLE_BT);
        }
    }


    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BluetoothService.STATE_CONNECTED:// 已连接
                            L.i("STATE_CONNECTED");
                            T.showToCenter("设备连接成功");
                            break;
                        case BluetoothService.STATE_CONNECTING:// 正在连接...
                            L.i("STATE_CONNECTING");
                            T.showToCenter("设备连接中...");
                            break;
                        case BluetoothService.STATE_LISTEN:
                        case BluetoothService.STATE_NONE:// 无连接
                            T.showToCenter("无连接");
                            mService = null;
                            break;
                        case BluetoothService.MESSAGE_OFF:// 断开
                            T.showToCenter("连接断开");
                            mService = null;
                            break;
                    }
                    break;
                case MESSAGE_DEVICE_NAME:
                    mConnectedDeviceName = msg.getData().getString(DEVICE_NAME);
                    break;
            }
        }
    };
}