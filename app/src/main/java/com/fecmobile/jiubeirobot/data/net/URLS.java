package com.fecmobile.jiubeirobot.data.net;

/**
 * Created by Administrator on 2017/2/20.
 * 这是拼接访问服务器的URL
 */

public interface URLS {
//    String URL_SERVICE_COOKING_ORDER_DETAILS = "odt/odt_011";
//    String URL_SERVICE_COOKING_ORDER_LIST = "/b/b_003";

    String URL_GET_CELLAR_ID_BY_ROBOT = "b/b_003";//请求当前机器人所在酒窖id
    String URL_GET_CELLAR_INFO = "b/b_002";//机器人根据id获取酒窖信息
    String URL_REGISTER = "b/b_005";//注册
    String URL_SEND_CHECK_CODE = "b/b_004";//发送验证码  1 注册2 验证 3找回密码 4. 修改密码
    String URL_PROTOCOLS_INFO = "c/c_004";//请求logo、公众号二维码、注册条款
    String URL_ACCOUT_IS_REGSITER = "b/b_006";//验证账号是否已经注册
    String URL_SHOP_DETAILS = "p/p_002";//商品详情
    String URL_CHECK_IDNETITY = "b/b_007";//身份验证
    String URL_SHOP_LIST = "p/p_001";//商品列表
    String URL_SHOP_CLASS = "class/c_001";//商品分类
    String URL_GRAPE_VARIETY = "grape/g_001";//葡萄品种
    String URL_GRAPEVINE = "origin/w_001";//葡萄产地
    String URL_BRAND_TYPE = "brand/b_001";//品牌分类
    String URL_TASTE_AND_SUGGEST = "datacode/d_001";//口感和建议
    String URL_MEMBER_ADDRESS_LIST = "od/od_005";//会员地址列表
    String URL_MEMER_ADD_ADDRESS = "od/od_001";//新增会员地址
    String URL_DELETE_MEMBER_ADDRESS = "od/od_004";//删除会员地址
    String URL_UPDATE_MEMBER_ADDRESS = "od/od_002";//修改
    String URL_MEMBER_ORDER_SUBMIT = "o/o_001";//订单提交
    String URL_LOGIN_MANAGER = "b/b_001";//管理登录
    String URL_STORAGE_CELLAR = "s/s_001";//酒品存储
    String URL_STORAGE_CELLAR_TAB_DETAIL = "s/s_002";//酒品存储列表详情
    String URL_CHANGE_PASSWORD = "b/b_009";//修改密码
    String URL_FORGET_PASSWORD = "b/b_008";//找回密码
    String URL_ORDER_LIST = "o/o_002";//全部订单列表
    String URL_ORDER_DETAIL = "o/o_003";//订单详情
    String URL_ORDER_RECEIABLE = "o/o_004";//订单收款
    String URL_ORDER_SEND_OR_PAY = "o/o_005";//确认发货
    String URL_ORDER_FINISH = "o/o_006";//订单完成
    String URL_ORDER_NUM = "o/o_007";//订单数量
    String URL_GET_WECHAT_PAY_INFO = "pay/pay_002";//微信支付
    String URL_GET_AIL_PAY_INFO = "pay/pay_001";//支付宝支付
    String URL_GET_CASH_PAY_INFO = "pay/pay_003";//现金支付
    String URL_GET_ORDER_STATE = "od/od_006";//查询订单状态
    String URL_SHOPID_BY_BARCODE = "p/p_003";//根据条码获取商品ID
    String URL_RECEIPT_INFO = "o/o_008";//根据订单ID查询小票信息
    String URL_VERSION_CODE = "c/c_005";//获取apk最新版本，更新版本


}