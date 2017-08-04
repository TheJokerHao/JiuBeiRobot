package com.fecmobile.jiubeirobot.receivers
        ;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.fecmobile.jiubeirobot.common.Activitys;
import com.fecmobile.jiubeirobot.utils.L;

/**
 * 类描述    :
 * 包名      : com.fecmobile.jiubeirobot.receivers
 * 类名称    : ScannerReceivers
 * 创建人    : ghy
 * 创建时间  : 2017/3/24 14:55
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class ScannerReceivers extends BroadcastReceiver {
    public static final String ACTION = "com.fecmobile.jiubeirobot.receivers.ScannerReceivers";
    public static final String DATA = "data";

    @Override
    public void onReceive(Context context, Intent intent) {
        String reuslt = intent.getStringExtra(DATA);
        L.i("------------------" + reuslt);
        Activitys.toScanResult(context, reuslt);
    }
}
