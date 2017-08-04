package com.fecmobile.jiubeirobot.receivers;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.view.View;

import com.fecmobile.jiubeirobot.ui.dialog.AlertDialog;
import com.fecmobile.jiubeirobot.utils.L;
import com.fecmobile.jiubeirobot.utils.NetUtils;

/**
 * 网络广播  一个dialog来显示网络是否连接成功
 */

public class NetReceivers extends BroadcastReceiver {
    private AlertDialog alertDialog;

    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
            boolean isConnected = NetUtils.isNetworkConnected(context);

            L.i("网络状态：" + isConnected);
            L.i("wifi状态：" + NetUtils.isWifiConnected(context));
            L.i("移动网络状态：" + NetUtils.isMobileConnected(context));
            L.i("网络连接类型：" + NetUtils.getConnectedType(context));

            if (isConnected) {
            } else {
                if (alertDialog != null && alertDialog.isShowing()) {
                    alertDialog.dismiss();
                }
                alertDialog = new AlertDialog(context).builder().setMsg("检测到无网络连接")
                        .setPositiveButton("设置", new AlertDialog.OnClickListenerAlertDialog() {
                            @Override
                            public void onClick(View v, Dialog dialog) {
                                // 判断手机系统的版本 即API大于10 就是3.0或以上版本
                                Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
                                context.startActivity(intent);
                                dialog.dismiss();
                            }
                        }).setNegativeButton("取消", null);
                alertDialog.show();
            }
        }
    }

}
