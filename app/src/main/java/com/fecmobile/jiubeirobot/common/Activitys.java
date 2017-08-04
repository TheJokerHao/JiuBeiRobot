package com.fecmobile.jiubeirobot.common;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.fecmobile.jiubeirobot.ui.activity.*;
import com.fecmobile.jiubeirobot.ui.activity.shop.*;
import com.fecmobile.jiubeirobot.ui.activity.cellar.walk.*;
import com.fecmobile.jiubeirobot.ui.activity.cellar.manager.*;

import java.io.Serializable;

/**
 * 类描述    :Activity跳转路由类
 * 包名      : com.fecmobile.jiubeirobot.common
 * 类名称    : Activitys
 * 创建人    : ghy
 * 创建时间  : 2017/2/22 11:20
 * 修改人    :
 * 修改时间  :
 * 修改备注  : 定义的activity的跳转类
 */
public class Activitys {
    /**
     * 跳转主页
     */
    public static void toMain(Activity aty) {
        Intent intent = new Intent(aty, MainActivity.class);
        aty.startActivity(intent);
    }

    /**
     * 跳转商品列表
     */
    public static void toShopList(Activity aty) {
        Intent intent = new Intent(aty, ShopListActivity.class);
        intent.putExtra(Constants.INTENT_SHOP_LIST_TYPE, "");
        aty.startActivity(intent);
    }

    /**
     * 跳转到本地购买商品的展示
     */
    public static void toLocalBuyList(Activity aty) {
        Intent intent = new Intent(aty, LocalBuyActivity.class);
        intent.putExtra(Constants.INTENT_LOCAL_SHOP_LIST_TYPE, "");
        aty.startActivity(intent);
    }

    /**
     * 跳转商品列表的商品详情
     */
    public static void toShopListByType(Context aty, String id) {
        Intent intent = new Intent(aty, ShopListActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constants.INTENT_SHOP_LIST_TYPE, Constants.INTENT_SHOP_DETAILS);
        intent.putExtra(Constants.INTENT_SHOP_BARCODE, id);
        aty.startActivity(intent);
    }


    /**
     * 跳转商品列表的商品详情
     */
    public static void toLocalListByType(Context aty, String id) {
        Intent intent = new Intent(aty, LocalBuyActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(Constants.INTENT_LOCAL_SHOP_LIST_TYPE, Constants.INTENT_LOCAL_SHOP_DETAILS);
        intent.putExtra(Constants.INTENT_LOCAL_SHOP_BARCODE, id);
        aty.startActivity(intent);
    }

    /**
     * 跳转购物车
     */
    public static void toShoppingCard(Activity aty) {
        Intent intent = new Intent(aty, ShoppingCartActivity.class);
        aty.startActivity(intent);
    }


    /**
     * 跳转走进酒窖
     */
    public static void toWalkIntoCellar(Activity aty) {
        Intent intent = new Intent(aty, WalkIntoCellarActivity.class);
        aty.startActivity(intent);
    }

    /**
     * 跳转核对订单
     */
    public static void toUserCheckOrder(Activity aty, String type, Serializable shop, String bid) {
        Intent intent = new Intent(aty, UserCheckOrderActivity.class);
        intent.putExtra(Constants.INTENT_NOW_BUY, type);
        intent.putExtra(Constants.INTENT_STOCK_BEAN, shop);
        intent.putExtra(Constants.INTENT_MEMBER_ID, bid);
        aty.startActivity(intent);
    }

    /**
     * 跳转到提交酒窖信息的页面
     */
    public static void toUPInfoActivity(Activity aty) {
        Intent intent = new Intent(aty, UpRobotInfoActivity.class);
        aty.startActivity(intent);
    }

    /**
     * 跳转本地购买的核对订单
     */
    public static void toLocalUserCheckOrder(Activity aty, String type, Serializable shop, String bid) {
        Intent intent = new Intent(aty, LocalUserCheckOrderActivity.class);
        intent.putExtra(Constants.INTENT_NOW_BUY, type);
        intent.putExtra(Constants.INTENT_STOCK_BEAN, shop);
        intent.putExtra(Constants.INTENT_MEMBER_ID, bid);
        aty.startActivity(intent);
    }


    /**
     * 跳转选择支付方式
     */
    public static void toSelectOrderPayType(Activity aty, String oid, String bid, String code, String money) {
        Intent intent = new Intent(aty, SelectOrderPayTypeActivity.class);
        intent.putExtra(Constants.INTENT_OID, oid);
        intent.putExtra(Constants.INTENT_ORDER_CODE, code);
        intent.putExtra(Constants.INTENT_PAY_MONEY, money);
        intent.putExtra(Constants.INTENT_MEMBER_ID, bid);
        aty.startActivity(intent);
    }


    /**
     * 本地购买 需要跳转选择支付方式
     * TODO  这个地方需要冲新设计一下 实行什么样一个逻辑   然后手动调用微信  和支付宝的 返回的二维码来结账
     */

    public static void toLocalSelectOrderPayType(Activity aty, String oid, String paymoney, String code, String money) {
        Intent intent = new Intent(aty, LocalSelectOrderPayTypeActivity.class);
        intent.putExtra(Constants.INTENT_OID, oid);
        intent.putExtra(Constants.INTENT_ORDER_CODE, code);
        intent.putExtra(Constants.INTENT_PAY_MONEY, money);
        intent.putExtra(Constants.INTENT_LOCAL_PAY_MONEY, paymoney);
        aty.startActivity(intent);
    }

    /**
     * 跳转二维码页面
     */
    public static void toQRPay(Activity aty, int type, String oid, String bid) {
        Intent intent = new Intent(aty, QRPayActivity.class);
        intent.putExtra(Constants.INTENT_PAY_TYPE, type);
        intent.putExtra(Constants.INTENT_OID, oid);
        intent.putExtra(Constants.INTENT_MEMBER_ID, bid);
        aty.startActivity(intent);
    }

    /**
     * 本地跳转二维码页面
     */
    public static void toLocalQRPay(Activity aty, int type, String paymoney) {
        Intent intent = new Intent(aty, LocalQRPayActivity.class);
        intent.putExtra(Constants.INTENT_PAY_TYPE, type);
        intent.putExtra(Constants.INTENT_LOCAL_PAY_MONEY, paymoney);
        aty.startActivity(intent);
    }

    /**
     * 跳转支付结果
     */
    public static void toOrderPayResult(Activity aty, int type, String code, String money) {
        Intent intent = new Intent(aty, OrderPayResultActivity.class);
        intent.putExtra(Constants.INTENT_ORDER_CODE, code);
        intent.putExtra(Constants.INTENT_PAY_MONEY, money);
        intent.putExtra(Constants.INTENT_PAY_TYPE, type);
        aty.startActivity(intent);
    }


    /**
     * 跳转本地支付结果
     */
    public static void toLocalOrderPayResult(Activity aty, int type, String code, String money) {
        Intent intent = new Intent(aty, LocalOrderPayResultActivity.class);
        intent.putExtra(Constants.INTENT_ORDER_CODE, code);
        intent.putExtra(Constants.INTENT_PAY_MONEY, money);
        intent.putExtra(Constants.INTENT_PAY_TYPE, type);
        aty.startActivity(intent);
    }


    /**
     * 跳转搜索商品
     */
    public static void toSearchShop(Activity aty, boolean isShoList) {
        Intent intent;
        if (!isShoList) {
            intent = new Intent(aty, ShopListActivity.class);
            aty.startActivity(intent);
        }
        intent = new Intent(aty, SearchShopActivity.class);
        aty.startActivityForResult(intent, 0);
        //aty.overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }


    /**
     * 跳转本地搜索商品
     */
    public static void toLocalSearchShop(Activity aty, boolean isShoList) {
        Intent intent;
        if (!isShoList) {
            intent = new Intent(aty, LocalBuyActivity.class);
            aty.startActivity(intent);
        }
        intent = new Intent(aty, LocalSearchShopActivity.class);
        aty.startActivityForResult(intent, 0);
        //aty.overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }


    /**
     * 跳转WebView
     */
    public static void toWebView(Activity aty, String title, String htmlContent) {
        Intent intent = new Intent(aty, WebViewActivity.class);
        intent.putExtra(Constants.INTENT_WEBVIEW_TITLE, title);
        intent.putExtra(Constants.INTENT_WEBVIEW_HTML_CONTENT, htmlContent);
        aty.startActivity(intent);
    }

    /**
     * 跳转Login
     */
    public static void toLoginManage(Activity aty) {
        Intent intent = new Intent(aty, LoginManageActivity.class);
        aty.startActivity(intent);
    }

    /**
     * 跳转系统管理首页
     */
    public static void toSysMainManager(Activity aty, String aid, String bid, String uid, String account, String busiCompName, String busiCompLogo_url, String userName) {
        Intent intent = new Intent(aty, SysMainManagerActivity.class);
        intent.putExtra("uid", uid);
        intent.putExtra("account", account);
        intent.putExtra("aid", aid);
        intent.putExtra("busiCompName", busiCompName);
        intent.putExtra("bid", bid);
        intent.putExtra("busiCompLogo_url", busiCompLogo_url);
        intent.putExtra("userName", userName);
        aty.startActivity(intent);
    }


    /**
     * 跳转到本地管理首页
     */

    public static void toLocalMainManager(Activity aty, String aid, String bid, String uid, String account, String busiCompName, String busiCompLogo_url, String userName) {
        Intent intent = new Intent(aty, LocalManagerActivity.class);
        intent.putExtra("uid", uid);
        intent.putExtra("account", account);
        intent.putExtra("aid", aid);
        intent.putExtra("busiCompName", busiCompName);
        intent.putExtra("bid", bid);
        intent.putExtra("busiCompLogo_url", busiCompLogo_url);
        intent.putExtra("userName", userName);
        aty.startActivity(intent);
    }

    /**
     * 跳转系统管理第一个二级页面
     */
    public static void toSysManagerGroup(Activity aty, String aid, String bid, String uid, String account, String busiCompName, String busiCompLogo_url, String userName) {
        Intent intent = new Intent(aty, ManagerGroupActivity.class);
        intent.putExtra("uid", uid);
        intent.putExtra("account", account);
        intent.putExtra("aid", aid);
        intent.putExtra("busiCompName", busiCompName);
        intent.putExtra("bid", bid);
        intent.putExtra("busiCompLogo_url", busiCompLogo_url);
        intent.putExtra("userName", userName);
        aty.startActivity(intent);
    }

    /**
     * 跳转酒窖库存
     */
    public static void toStorageCellarTab(Activity aty, String selectType) {
        Intent intent = new Intent(aty, StorageCellarTabActivity.class);
        intent.putExtra("selectType", selectType);
        aty.startActivity(intent);
    }


    /**
     * 跳转本地管理酒窖库存
     */
    public static void toStorageLocalCellarTab(Activity aty, String selectType) {
        Intent intent = new Intent(aty, LocalStorageCellarTabActivity.class);
        intent.putExtra("selectType", selectType);
        aty.startActivity(intent);
    }

    /**
     * 跳转到本地订单量列表
     */
    public static void toLocalOrderList(Activity aty, int filterStatus) {
        Intent intent = new Intent(aty, LocalOrderListActivity.class);
        intent.putExtra(Constants.ORDER_LIST_STATUS, filterStatus);
        aty.startActivity(intent);
    }

    /**
     * 跳转到客户管理页面
     */
    public static void toCustomerManager(Activity aty) {
        Intent intent = new Intent(aty, CustomerManagerActivity.class);
        aty.startActivity(intent);

    }


    /**
     * 跳转订单量列表
     */
    public static void toOrderList(Activity aty, int filterStatus) {
        Intent intent = new Intent(aty, OrderListActivity.class);
        intent.putExtra(Constants.ORDER_LIST_STATUS, filterStatus);
        aty.startActivity(intent);
    }

    /***
     * 跳转到订单搜索
     */
    public static void toOrderSearch(Activity aty, String search) {
        Intent intent = new Intent(aty, OrderSearchActivity.class);
        intent.putExtra(Constants.ORDER_LIST_SEARCH, search);
        aty.startActivityForResult(intent, Constants.ORDER_SEARCH);
    }

    /***
     * 视频全屏
     */
    public static void toFullVideo(Context aty, String url, int postion) {
        Intent intent = new Intent(aty, FullVideoActivity.class);
        intent.putExtra(Constants.INTENT_VIDEO＿URL, url);
        intent.putExtra(Constants.INTENT_VIDEO_POSTION, postion);
        aty.startActivity(intent);
    }

    /***
     * 忘记密码
     */
    public static void toSystemForgetPassWordActivity(Activity aty) {
        Intent intent = new Intent(aty, SystemForgetPassWordActivity.class);
        aty.startActivity(intent);
    }

    /***
     * 找回密码
     */
    public static void toSystemForgetPassWordFoundActivity(Activity aty, String account, String type) {
        Intent intent = new Intent(aty, SystemForgetPassWordFoundActivity.class);
        intent.putExtra("account", account);
//        intent.putExtra("uuid", uuid);
        intent.putExtra("type", type);
        aty.startActivityForResult(intent, Constants.FORGET_PASSWORD_RESULT);
    }


    /**
     * 扫码结果
     */
    public static void toScanResult(Context context, String code) {
        Intent intent = new Intent(context, ScanResultActivity.class);
        intent.putExtra(Constants.INTENT_SHOP_BARCODE, code);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}