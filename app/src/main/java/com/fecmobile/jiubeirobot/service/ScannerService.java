package com.fecmobile.jiubeirobot.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.serialport.scannerLib;
import android.support.annotation.Nullable;
import android.util.Log;

import com.fecmobile.jiubeirobot.receivers.ScannerReceivers;

/**
 * 类描述    :监控扫码枪数据
 * 包名      : com.fecmobile.jiubeirobot.service
 * 类名称    : ScannerService
 * 创建人    : ghy
 * 创建时间  : 2017/3/24 14:30
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */


public class ScannerService extends Service {
    private scannerLib scanner;
    private ScannerReceivers scan;

    @Override
    public void onCreate() {
        super.onCreate();
        scan = new ScannerReceivers();
        //配置扫码器
        scanner = new scannerLib();
        scanner.initScanner();
        scanner.scannerHandler = new Handler() {
            public void handleMessage(Message msg) {
                RefreshDisplay(msg);
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(ScannerReceivers.ACTION);
        registerReceiver(scan, filter);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (scan != null) {
            unregisterReceiver(scan);
        }
    }

    /**
     * 刷新界面
     */
    private void RefreshDisplay(Message msg) {
        switch (msg.what) {
            case 0:
                String val = new String(scanner.scannerRx_Buffer);
                Log.d("print", "RefreshDisplay: " + val);
                Intent intent = new Intent();
                intent.setAction(ScannerReceivers.ACTION);
                intent.putExtra(ScannerReceivers.DATA, val);
                sendBroadcast(intent);
                //msg.arg1 可以获取接收字符长度
                //Arrays.fill(scanner.scannerRx_Buffer, (byte) 0);
                break;
        }
    }
}
