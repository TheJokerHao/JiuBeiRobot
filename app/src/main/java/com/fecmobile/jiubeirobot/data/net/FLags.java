package com.fecmobile.jiubeirobot.data.net;

/**
 * Created by Administrator on 2017/2/20.
 * 这是定义的常量  这些常量用来对请求数据的判定
 */

public interface FLags {
    int FLAG_GET_CELLAR_ID_BY_ROBOT = 20001;//获取机器人ID
    int FLAG_GET_CELLAR_INFO = 20002;//获取酒窖信息
    int FLAG_REGISTER = 20003;//获取注册
    int FLAG_SEND_CHECK_CODE = 20004;//获取验证码
    int FLAG_PROTOCOLS_INFO = 20005;//请求公众号等信息
    int FLAG_ACCOUT_IS_REGSITER = 20006;//账号是否注册的验证
    int FLAG_SHOP_DETAILS = 20007;//商品详情
    int FLAG_CHECK_IDNETITY = 20008;//验证身份
    int FLAG_SHOP_LIST = 20009;//商品列表
    int FLAG_SHOP_CLASS = 20010;//商品分类接口
    int FLAG_GRAPE_VARIETY = 20011;//葡萄品种
    int FLAG_GRAPEVINE = 20012;//葡萄产地
    int FLAG_BRAND_TYPE = 20013;//葡萄品牌分类
    int FLAG_TASTE_AND_SUGGEST = 20014;//口感 配餐建议
    int FLAG_MEMBER_ADDRESS_LIST = 20015;//获取会员地址列表
    int FLAG_MEMER_ADD_ADDRESS = 20016;//会员添加地址
    int FLAG_DELETE_MEMBER_ADDRESS = 20017;//删除地址
    int FLAG_UPDATE_MEMBER_ADDRESS = 20018;//修改地址
    int FLAG_MEMBER_ORDER_SUBMIT = 20019;//订单提交
    int FLAG_WECHAT_PAY = 20020;//微信支付
    int FLAG_ALI_PAY = 20021;//支付宝
    int FLAG_GET_CASH_PAY_INFO = 20022;//获取资金支付
    int FLAG_GET_ORDER_STATE = 20023;//获取订单状态
    int FLAG_SHOPID_BY_BARCODE = 20024;//根据条形码 获取商品ID
    int FLAG_RECEIPT_INFO = 20010;//打印小票

    int FLAG_LOGIN_MANAGER = 30001;//系统管理的登录
    int FLAG_STORAGE_CELLAR = 30002;//酒品存储列表
    int FLAG_CHANGE_PASSWORD = 30003;//修改密码
    int FLAG_FORGET_PASSWORD = 40001;//找回密码
    int FLAG_STORAGE_CELLAR_TAB_DETAIL = 40002;//存储酒品详情

    int FLAG_ORDER_LIST = 30004;//订单列表
    int FLAG_ORDER_DETAIL = 30005;//订单详情
    int FLAG_ORDER_RECEIABLE = 30006;//订单收款
    int FLAG_ORDER_SEND = 30007;//订单发货
    int FLAG_ORDER_FINISH = 30008;//订单完成
    int FLAG_ORDER_NUM = 30009;

    int FLAG_VERSION_CODE = 40001;//版本更新


    //本地购买功能添加的FLAG  这些都是针对本地购买的添加
    int FLAG_LOCAL_SHOP_LIST = 50001;//商品列表
    int FLAG_LOCAL_ORDER_LIST = 50004;//订单列表
    int FLAG_LOCAL_SHOP_DETAILS = 50007;//商品详情
    int FLAG_LOCAL_ORDER_DETAIL = 50005;//订单详情
    int FLAG_LOCAL_STORAGE_CELLAR = 50002;//酒品存储列表
    int FLAG_LOCAL_STORAGE_CELLAR_TAB_DETAIL = 50008;//本地存储酒品详情
    int FLAG_LOCAL_MEMBER_ORDER_SUBMIT = 50019;//订单提交
    int FLAG_LOCAL_CHECK_CUSTOMER_INFO = 50020;//验证客户信息

    int FLAG_LOCAL_QUERY_CUSTOMER_INFO = 50021;//查询客户地址信息

    int FLAG_LOCAL_DELETE_ADDRESS = 50017;//删除地址
    int FLAG_LOCAL_UPDATE_ADDRESS = 50018;//修改地址
    int FLAG_LOCAL_ADD_ADDRESS = 50016;//本地添加地址

    int FLAG_LOCAL_DEFAULT_ADDRESS = 50003;//本地添加地址
    int FLAG_LOCAL_PAY_INFO = 50022;//本地订单的支付信息
    int FLAG_LOCAL_ORDER_FINISH = 50006;//本地订单完成
    int FLAG_LOCAL_ORDER_RECEIABLE = 50009;//订单收款
    int FLAG_LOCAL_ORDER_SEND = 50012;//本地订单发货
    int FLAG_LOCAL_RECEIPT_INFO = 50011;//打印小票
    int FLAG_LOCAL_SHOPID_BY_BARCODE = 50024;//本地根据条形码 获取商品ID

}
