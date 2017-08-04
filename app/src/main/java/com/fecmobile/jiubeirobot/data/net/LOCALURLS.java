package com.fecmobile.jiubeirobot.data.net;

/**
 * 创建人：TheJoker丶豪 on
 * 创建时间：2017/6/2.
 */

public interface LOCALURLS {
    /**
     * 新增的功能点的接口数据拼接
     */
    //列表显示
    String LOCAL_URL_SHOP_LIST = "ci/p/p_001";//商品列表
    String LOCAL_URL_SHOP_DETAILS = "ci/p/p_002";//商品详情
    String LOCAL_URL_MEMBER_ORDER_SUBMIT = "ci/o/o_001";//订单提交
    String LOCAL_URL_CHECKUSER_INFO_ORDER = "ci/b/b_007";//验证客户信息
    String LOCAL_URL_QUERY_CUSTOMER_INFO = "ci/od/od_005";//查询客户地址信息.
    String LOCAL_URL_DELEATE_ADDRESS_INFO = "ci/od/od_004";//删除收货地址
    String LOCAL_URL_SETDEFAULT_ADDRESS = "ci/od/od_003";   //设置默认的收货地址
    String LOCAL_URL_CHANGE_ADDRESS = "ci/od/od_002";//修改收货地址
    String LOCAL_URL_NEW_ADDRESS = "ci/od/od_001";//新增收货地址
    String LOCAL_URL_GET_PAY_INFO = "ci/pay/pay_001";//获取订单支付方式


    //后台管理
    String LOCAL_URL_ORDER_NUM = "ci/o/o_007";//订单数量
    String LOCAL_URL_ORDER_LIST = "ci/o/o_002";//全部订单列表
    String LOCAL_URL_ORDER_DETAIL = "ci/o/o_003";//订单详情
    String LOCAL_URL_ORDER_RECEIABLE = "ci/o/o_004";//订单收款
    String LOCAL_URL_STORAGE_CELLAR = "ci/s/s_001";//酒品存储列表
    String LOCAL_URL_SURE_SEND = "ci/o/o_005";//确认发货
    String LOCAL_URL_END_ORDER = "ci/o/o_006";//订单完成
    String LOCAL_URL_STORAGE_CELLAR_TAB_DETAIL = "ci/s/s_002";//酒品存储列表详情
    String LOCAL_URL_GETOREDER_INFO = "ci/o/o_008"; // 获取订单商品信息   打印小票的时候用的
    String LOCAL_URL_SHOPID_BY_BARCODE = "ci/p/p_003";//获取商品条形码

}
