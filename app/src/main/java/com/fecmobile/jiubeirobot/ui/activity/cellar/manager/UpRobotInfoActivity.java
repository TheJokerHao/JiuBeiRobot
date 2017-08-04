package com.fecmobile.jiubeirobot.ui.activity.cellar.manager;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseActivity;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.utils.NetUtils;

import butterknife.BindView;

/**
 * @author TheJoker丶豪
 *         这是定义的首次使用久碑系统中机器人的时候需要上传机器人相关信息的activity
 *         需要上传一些列的参数比如Mac地址
 * @date 创建时间:2017/7/21
 */
public class UpRobotInfoActivity extends BaseActivity {

    @BindView(R.id.tv_robot_mac)
    TextView tvMac;

    @Override
    public int pageLayout() {
        return R.layout.activity_up_robot_info;

    }

    @Override
    public void initTitle() {
        if (NetUtils.isNetworkConnected(this)) {
            String mac = getLocalMacAddressFromWifiInfo(this);
            Log.d("print", "initTitle: Mac地址为——————》" + mac);
            tvMac.setText(mac);

        }

    }

    @Override
    public void initView() {

    }

    @Override
    public void onSuccess(BaseBean bean, int flag) {

    }

    //根据Wifi信息获取本地Mac
    public static String getLocalMacAddressFromWifiInfo(Context context) {
        WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo info = wifi.getConnectionInfo();
        return info.getMacAddress();
    }

}
