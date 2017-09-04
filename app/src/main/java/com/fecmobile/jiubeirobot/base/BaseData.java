package com.fecmobile.jiubeirobot.base;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.fecmobile.jiubeirobot.bean.CellarIDBean;
import com.fecmobile.jiubeirobot.bean.IdentityCheckResultBean;
import com.fecmobile.jiubeirobot.bean.LoginManagerBean;
import com.fecmobile.jiubeirobot.bean.ProtocolsInfoBean;
import com.fecmobile.jiubeirobot.bean.StockBean;
import com.fecmobile.jiubeirobot.common.Constants;
import com.fecmobile.jiubeirobot.utils.Arith;
import com.fecmobile.jiubeirobot.utils.DetectTool;
import com.fecmobile.jiubeirobot.utils.L;
import com.fecmobile.jiubeirobot.utils.SPUtil;
import com.fecmobile.jiubeirobot.BuildConfig;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BaseData {
    private static BaseData baseData = new BaseData();
    private BluetoothDevice device; //蓝牙设备

    public static BaseData getBaseData() {
        return baseData;
    }

    private final String UID = "123";
    private String ROBOT_SIGN;//机器人UUID
    private CellarIDBean cellarInfoBean = new CellarIDBean();//机器人信息
    private ProtocolsInfoBean protocolsInfoBean = new ProtocolsInfoBean();//logo 协议等信息
    private IdentityCheckResultBean identityCheckResultBean = new IdentityCheckResultBean();//身份校验结果
    private LoginManagerBean mLoginManagerBean = new LoginManagerBean();//服务员登录信息

    //走进酒窖视频介绍，视频进度
    public static int videoIntroduce;

    //购物车
    private List<StockBean> cardShops = new ArrayList<>();

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public void setLoginManagerBean(LoginManagerBean loginManagerBean) {
        mLoginManagerBean = loginManagerBean;
    }

    public LoginManagerBean getLoginManagerBean() {
        return mLoginManagerBean;
    }

    private int cardNums;
    private double totalMoney;

    public IdentityCheckResultBean getIdentityCheckResultBean() {
        return identityCheckResultBean;
    }

    /**
     * 设置身份校验的结果
     *
     * @param identityCheckResultBean
     */
    public void setIdentityCheckResultBean(IdentityCheckResultBean identityCheckResultBean) {
        this.identityCheckResultBean = identityCheckResultBean;
    }

    public String getTotalMoney() {
        return Arith.get2Decimal(totalMoney + "");
    }

    public int getCardNums() {
        return cardNums;
    }

    public void clearCartShops() {
        cardShops.clear();
        cardNums = 0;
    }

    public int getCardItemNum() {
        return cardShops.size();
    }

    public int queryShopNum(String sid) {
        Iterator<StockBean> iterator = cardShops.iterator();
        while (iterator.hasNext()) {
            StockBean nextBean = iterator.next();
            if (nextBean.getId().equals(sid)) {
                return nextBean.getNum();
            }
        }
        return 0;
    }

    public void addShop(StockBean bean, int num) {
        L.i("addShop：" + bean.toString());
        totalMoney = 0;
        cardNums = 0;
        boolean isAdd = true;
        Iterator<StockBean> iterator = cardShops.iterator();
        while (iterator.hasNext()) {
            StockBean nextBean = iterator.next();
            if (nextBean.getId().equals(bean.getId())) {
                nextBean.setNum(nextBean.getNum() + num);
                isAdd = false;
            }
            cardNums += nextBean.getNum();
            totalMoney += nextBean.getNum() * Double.parseDouble(nextBean.getLsPrice());
        }
        if (isAdd) {
            bean.setNum(num);
            cardNums += bean.getNum();
            totalMoney += num * Double.parseDouble(bean.getLsPrice());
            cardShops.add(bean);
        }
    }

    public void setShopNum(StockBean bean, int num) {
        L.i("setShopNum：" + bean.toString());
//        if (cardShops.contains(bean)) {
//            bean.setNum(bean.getNum() + num);
//            return;
//        }
        totalMoney = 0;
        cardNums = 0;
        boolean isAdd = true;
        Iterator<StockBean> iterator = cardShops.iterator();
        //迭代便利
        while (iterator.hasNext()) {
            StockBean nextBean = iterator.next();
            if (nextBean.getId().equals(bean.getId())) {
                nextBean.setNum(num);
                isAdd = false;
            }
            cardNums += nextBean.getNum();
            totalMoney += nextBean.getNum() * Double.parseDouble(nextBean.getLsPrice());
        }
        if (isAdd) {
            cardNums += bean.getNum();
            totalMoney += num * Double.parseDouble(bean.getLsPrice());
            bean.setNum(num);
            cardShops.add(bean);
        }
    }

    public void minusShop(StockBean bean, int num) {
        L.i("minusShop：" + bean.toString());
//        if (cardShops.contains(bean)) {
//            bean.setNum(bean.getNum() - num);
//        }
        cardNums = 0;
        Iterator<StockBean> iterator = cardShops.iterator();
        while (iterator.hasNext()) {
            StockBean nextBean = iterator.next();
            if (nextBean.getId().equals(bean.getId())) {
                nextBean.setNum(nextBean.getNum() - num);
                totalMoney -= nextBean.getNum() * Double.parseDouble(nextBean.getLsPrice());
            }
            cardNums += num;
        }
    }

    public void removeShop(StockBean bean) {
        L.i("removeShop：" + bean.toString());
        for (int i = 0; i < cardShops.size(); i++) {
            StockBean nextBean = cardShops.get(i);
            if (nextBean.getId().equals(bean.getId())) {
                totalMoney -= nextBean.getNum() * Double.parseDouble(nextBean.getLsPrice());
                cardNums -= nextBean.getNum();
                cardShops.remove(i);
                L.i("移除");
                return;
            }
        }
    }

    public List<StockBean> getCardShops() {
        L.i("数据源：" + cardShops);
        return cardShops;
    }

    public void setProtocolsInfoBean(ProtocolsInfoBean protocolsInfoBean) {
        this.protocolsInfoBean = protocolsInfoBean;
    }

    public ProtocolsInfoBean getProtocolsInfoBean() {
        return protocolsInfoBean;
    }

    public String getUID() {
        return UID;
    }

    public CellarIDBean getCellarInfoBean() {
        return cellarInfoBean;
    }

    public void setCellarInfoBean(CellarIDBean cellarInfoBean) {
        this.cellarInfoBean = cellarInfoBean;
    }

    public String getROBOT_SIGN(BaseApplication application) {//取Mac地址
//        if (BuildConfig.DEBUG) {
//            //DEBUG状态下回提示这个字符串
//            return "6c:fa:a7:62:a5:9c".toLowerCase();
////            return "00:e0:70:35:0e:24".toLowerCase();
//        } else {
        ROBOT_SIGN = (String) SPUtil.get(application, Constants.ROBOT_SIGN_UUID_KEY, "");
        if (ROBOT_SIGN == null || ROBOT_SIGN.length() == 0) {
            WifiManager wm = (WifiManager) application.getSystemService(Context.WIFI_SERVICE);
            WifiInfo wi = wm.getConnectionInfo();
            String macAddress = wi.getMacAddress();
            Log.d("print", "getROBOT_SIGN: 初始" + macAddress);
            ROBOT_SIGN = macAddress;

            if (ROBOT_SIGN == null || ROBOT_SIGN.length() == 0) {//从SP获取失败？
                ROBOT_SIGN = DetectTool.getUUID();
            }
            SPUtil.put(application, Constants.ROBOT_SIGN_UUID_KEY, ROBOT_SIGN);
        }
        Log.d("print", "getROBOT_SIGN: " + ROBOT_SIGN.toLowerCase());
        return ROBOT_SIGN.toLowerCase();
    }
//    }
}