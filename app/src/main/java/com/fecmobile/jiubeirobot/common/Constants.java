package com.fecmobile.jiubeirobot.common;

/**
 * Created by Administrator on 2017/2/18.、
 * 常量类接口
 */

public interface Constants {
    //===========================KEY======================
    int ALIPAY_TYPE = 0;//支付宝支付
    int WECHATPAY_TYPE = 1;//微信支付
    int CASH_TYPE = 2;//现金支付

    //===========================PREFERENCE=====================
    String ROBOT_SIGN_UUID_KEY = "ROBOT_SIGN_UUID_KEY";//机器人识别码
    String CELLAR_INFO_BEAN_KEY = "CELLAR_INFO_BEAN_KEY";//酒窖信息
    String SEARCH_HISTORY_RECORD = "SEARCH_HISTORY_RECORD";//历史搜索记录

    //===========================INTENT=========================
    String INTENT_MEMBER_ADDRESS_BEAN = "memberAddressBean";//会员地址对象
    String INTENT_NOW_BUY = "INTENT_NOW_BUY";//立即购买
    String INTENT_STOCK_BEAN = "INTENT_STOCK_BEAN";//本地商品实例
    String INTENT_SHOP_SEARCH_RESULT = "INTENT_SHOP_SEARCH_RESULT";//商品搜索结果
    String INTENT_WEBVIEW_TITLE = "INTENT_WEBVIEW_TITLE";//Webview标题
    String INTENT_WEBVIEW_HTML_CONTENT = "INTENT_WEBVIEW_HTML_CONTENT";//Webview内容
    String INTENT_PAY_TYPE = "INTENT_PAY_TYPE";//支付类型
    String INTENT_SHOP_FILTER_BEAN = "INTENT_SHOP_FILTER_BEAN";//筛选字段
    String INTENT_FILTER_TYPE = "INTENT_FILTER_TYPE";//筛选类型 一级 二级
    String INTENT_VIDEO＿URL = "INTENT_VIDEO＿URL";//视频播放地址
    String INTENT_VIDEO_POSTION = "INTENT_VIDEO_POSTION";//视频播放进度
    String INTENT_OID = "INTENT_OID";//订单ID
    String INTENT_MEMBER_ID = "INTENT_MEMBER_ID";//会员ID
    String INTENT_LOCAL_PAY_MONEY = "INTENT_LOCAL_PAY_MONEY";//本地金额
    String INTENT_SHOP_ID = "INTENT_SHOP_ID";//商品ID
    String INTENT_ORDER_CODE = "INTENT_ORDER_CODE";//订单号码
    String INTENT_PAY_MONEY = "INTENT_PAY_MONEY";//订单金额
    String INTENT_SHOP_DETAILS = "INTENT_SHOP_DETAILS";//商品详情
    String INTENT_SHOP_LIST_TYPE = "INTENT_SHOP_LIST_TYPE";//跳转到商品列表，根据这个类型显示具体内容
    String INTENT_SHOP_BARCODE = "INTENT_SHOP_BARCODE";//商品条码
    String INTENT_LOCAL_SHOP_BARCODE = "INTENT_SHOP_LOCAL_BARCODE";//本地商品条码
    String INTENT_LOCAL_SHOP_LIST_TYPE = "LOCAL_INTENT_SHOP_LIST_TYPE";//跳转到本地商品列表，根据这个类型显示具体内容
    String INTENT_LOCAL_SHOP_DETAILS = "LOCAL_INTENT_SHOP_DETAILS";//本地商品详情


    //===========================ORDER===========================
    String ORDER_LIST_STATUS = "ORDERSTATUS";//订单状态
    String ORDER_LIST_SEARCH = "ORDERSEARCH";//订单搜索
    int ORDER_SEARCH = 5;//订单搜索码

    /*-----------------------------------*/
    int FORGET_PASSWORD_RESULT = 0x0001;//找回密码
    int FORGET_PASSWORD_DETAIL_RESULT = 0x0002;

}

