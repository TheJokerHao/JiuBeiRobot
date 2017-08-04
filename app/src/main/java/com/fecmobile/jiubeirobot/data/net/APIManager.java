package com.fecmobile.jiubeirobot.data.net;

import android.content.Context;
import android.util.Log;

import com.fecmobile.jiubeirobot.base.BaseApplication;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.base.BaseData;
import com.fecmobile.jiubeirobot.bean.BrandBean;
import com.fecmobile.jiubeirobot.bean.CellarIDBean;
import com.fecmobile.jiubeirobot.bean.CellarInfoBean;
import com.fecmobile.jiubeirobot.bean.ChangeBean;
import com.fecmobile.jiubeirobot.bean.CheckCodeBean;
import com.fecmobile.jiubeirobot.bean.CheckCustomerInfoBean;
import com.fecmobile.jiubeirobot.bean.DefaultAddressBean;
import com.fecmobile.jiubeirobot.bean.DrinkingTexttureBean;
import com.fecmobile.jiubeirobot.bean.GrapeVarietyBean;
import com.fecmobile.jiubeirobot.bean.GrapevineBean;
import com.fecmobile.jiubeirobot.bean.IdentityCheckResultBean;
import com.fecmobile.jiubeirobot.bean.IsRegister;
import com.fecmobile.jiubeirobot.bean.ListBean;
import com.fecmobile.jiubeirobot.bean.LocalBean;
import com.fecmobile.jiubeirobot.bean.LocalPayInfoBean;
import com.fecmobile.jiubeirobot.bean.LocalQueryAddressBean;
import com.fecmobile.jiubeirobot.bean.LocalQueryAddressOtherBean;
import com.fecmobile.jiubeirobot.bean.LocalShopDetailsBean;
import com.fecmobile.jiubeirobot.bean.LocalStorageCellarTabDetailBean;
import com.fecmobile.jiubeirobot.bean.LocalSubmitOrderPramsBean;
import com.fecmobile.jiubeirobot.bean.LoginManagerBean;
import com.fecmobile.jiubeirobot.bean.ManagerOrderDetailBean;
import com.fecmobile.jiubeirobot.bean.ManagerOrderListBean;
import com.fecmobile.jiubeirobot.bean.MemberAddressBean;
import com.fecmobile.jiubeirobot.bean.MemberSubmitOrderPramsBean;
import com.fecmobile.jiubeirobot.bean.ObjBean;
import com.fecmobile.jiubeirobot.bean.OrderResultInfoBean;
import com.fecmobile.jiubeirobot.bean.OrderStateInfoBean;
import com.fecmobile.jiubeirobot.bean.PayInfoBean;
import com.fecmobile.jiubeirobot.bean.PendingOrderNumBean;
import com.fecmobile.jiubeirobot.bean.ProtocolsInfoBean;
import com.fecmobile.jiubeirobot.bean.ReceiptInfoBean;
import com.fecmobile.jiubeirobot.bean.ShopDetailsBean;
import com.fecmobile.jiubeirobot.bean.ShopFilterBean;
import com.fecmobile.jiubeirobot.bean.ShopIDByBarcode;
import com.fecmobile.jiubeirobot.bean.ShopListBean;
import com.fecmobile.jiubeirobot.bean.StorageCellBean;
import com.fecmobile.jiubeirobot.bean.StorageCellarTabDetailBean;
import com.fecmobile.jiubeirobot.bean.VersionCodeBean;
import com.fecmobile.jiubeirobot.common.Constants;
import com.fecmobile.jiubeirobot.ui.activity.shop.LocalUserCheckOrderActivity;
import com.fecmobile.jiubeirobot.utils.LP;
import com.fecmobile.jiubeirobot.utils.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import static com.fecmobile.jiubeirobot.data.net.URLS.URL_VERSION_CODE;

public class APIManager {
    private static APIManager apiManager;
    private INetLoad iNetLoad = new NetLoadImpl();
    private Gson gson = new Gson();

    public static APIManager getInstance() {
        if (apiManager == null) {
            synchronized (APIManager.class) {
                if (apiManager == null) {
                    apiManager = new APIManager();
                }
            }
        }
        return apiManager;
    }

    /**
     * 获取机器人ID
     *
     * @param context
     */
    public void getCellarIDByRobot(Context context) {
        JSONObject jsonObject = new JSONObject();
        try {
            //这里就是post需要提交的参数
            jsonObject.put("robotSign", BaseData.getBaseData().getROBOT_SIGN(BaseApplication.getInstance()));
//            jsonObject.put("robotSign", "6c:fa:a7:62:a4:fa");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //这个地方是定义的请求数据的地方
        iNetLoad.loadData(
                FLags.FLAG_GET_CELLAR_ID_BY_ROBOT,
                URLS.URL_GET_CELLAR_ID_BY_ROBOT, jsonObject, context, (INetResult) context, new TypeToken<BaseBean<ObjBean<CellarIDBean>>>() {
                }.getType());
    }

    /**
     * 根据传入的酒窖ID获取酒窖信息
     *
     * @param context
     */
    public void getCellarInfo(Context context) {
        JSONObject jsonObject = new JSONObject();
        try {
            INetResult inet = (INetResult) context;
            if (BaseData.getBaseData().getCellarInfoBean() == null) {
                inet.onError("酒窖信息获取失败", FLags.FLAG_GET_CELLAR_INFO);
                return;
            } else {
                //传入酒窖ID   这个ID是需要加密的
                jsonObject.put("id", BaseData.getBaseData().getCellarInfoBean().getCellerId());
//                Log.d("print", "getCellarInfo: 加密后的机器人ID" + BaseData.getBaseData().getCellarInfoBean().getCellerId());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        iNetLoad.loadData(
                FLags.FLAG_GET_CELLAR_INFO,
                URLS.URL_GET_CELLAR_INFO, jsonObject, context, (INetResult) context, new TypeToken<BaseBean<ObjBean<CellarInfoBean>>>() {
                }.getType());
    }

    /**
     * 获取注册
     */
    public void register(Context context, INetResult iNetResult, String account, String password, String rePassword, String messageCode, String bid, String uuid) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("account", account);
            jsonObject.put("password", password);
            jsonObject.put("rePassword", rePassword);
            jsonObject.put("messageCode", messageCode);
            jsonObject.put("bid", bid);
            jsonObject.put("uuid", uuid);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        iNetLoad.loadData(
                FLags.FLAG_REGISTER,
                URLS.URL_REGISTER, jsonObject, context, iNetResult, new TypeToken<BaseBean<ObjBean<CellarInfoBean>>>() {
                }.getType());
    }

    /**
     * 获取验证码
     */
    public void getCheckCode(Context context, INetResult iNetResult, String mobile, String type) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("mobile", mobile);
            jsonObject.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        iNetLoad.loadData(
                FLags.FLAG_SEND_CHECK_CODE,
                URLS.URL_SEND_CHECK_CODE, jsonObject, context, iNetResult, new TypeToken<BaseBean<ObjBean<CheckCodeBean>>>() {
                }.getType());
    }

    /**
     * 请求logo、公众号二维码、注册条款
     * 你看这里就是直接get
     */
    public void getProtocolInfo(Context context) {
        JSONObject jsonObject = new JSONObject();
        iNetLoad.loadData(
                FLags.FLAG_PROTOCOLS_INFO,
                URLS.URL_PROTOCOLS_INFO, jsonObject, context, (INetResult) context, new TypeToken<BaseBean<ObjBean<ProtocolsInfoBean>>>() {
                }.getType());
    }


    /**
     * 验证账号是否已经注册
     */
    public void checkAccountIsRegister(Context context, INetResult iNetResult, String account) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("account", account);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        iNetLoad.loadData(
                FLags.FLAG_ACCOUT_IS_REGSITER,
                URLS.URL_ACCOUT_IS_REGSITER, jsonObject, context, iNetResult, new TypeToken<BaseBean<ObjBean<IsRegister>>>() {
                }.getType());
    }

    /**
     * 验证身份
     */
    public void checkIdentity(Context context, INetResult iNetResult, String account, String uuid, String messageCode) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("account", account);
            jsonObject.put("uuid", uuid);
            jsonObject.put("messageCode", messageCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        iNetLoad.loadData(
                FLags.FLAG_CHECK_IDNETITY,
                URLS.URL_CHECK_IDNETITY, jsonObject, context, iNetResult, new TypeToken<BaseBean<ObjBean<IdentityCheckResultBean>>>() {
                }.getType());


    }

    /**
     * 商品详情
     */
    public void shopDetails(Context context, INetResult iNetResult, String shopId) {
        JSONObject jsonObject = new JSONObject();
        try {
            CellarIDBean bean = BaseData.getBaseData().getCellarInfoBean();
            jsonObject.put("id", shopId);
            jsonObject.put("bid", bean.getBid());
            jsonObject.put("cellerId", bean.getCellerId());

            Log.d("print", "shopDetails: 获取商品详情的入参" + shopId + "      " + bean.getBid() + "         " + bean.getCellerId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        iNetLoad.loadData(
                FLags.FLAG_SHOP_DETAILS,
                URLS.URL_SHOP_DETAILS, jsonObject, context, iNetResult, new TypeToken<BaseBean<ObjBean<ShopDetailsBean>>>() {
                }.getType());
    }


    /**
     * 本地购买的商品详情
     */
    public void localshopDetails(Context context, INetResult iNetResult, String shopId) {
        JSONObject jsonObject = new JSONObject();
        try {
            CellarIDBean bean = BaseData.getBaseData().getCellarInfoBean();
            jsonObject.put("id", shopId);
            jsonObject.put("bid", bean.getBid());
            jsonObject.put("cellarId", bean.getCellerId());

            Log.d("print", "shopDetails: 获取商品详情的入参" + shopId + "      " + bean.getBid() + "         " + bean.getCellerId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        iNetLoad.loadData2(
                FLags.FLAG_LOCAL_SHOP_DETAILS,
                LOCALURLS.LOCAL_URL_SHOP_DETAILS, jsonObject, context, iNetResult, new TypeToken<BaseBean<ObjBean<LocalShopDetailsBean>>>() {
                }.getType());
    }


    /**
     * 商品列表
     */
    public void shopList(Context context,
                         String productName,
                         String brandId,
                         String classId,
                         String advise,
                         String origin,
                         String grape,
                         String priceBegin,
                         String priceEnd,
                         String sort,
                         String years,
                         int pageIndex,
                         int pageSize,
                         String texture) {
        JSONObject jsonObject = new JSONObject();
        try {
            CellarIDBean bean = BaseData.getBaseData().getCellarInfoBean();
            jsonObject.put("bid", bean.getBid());//会员ID
            jsonObject.put("cellerId", bean.getCellerId());//存储酒窖ID
            jsonObject.put("productName", productName);//商品名称
            jsonObject.put("brandId", brandId);//品牌
            jsonObject.put("classId", classId);
            jsonObject.put("advise", advise);
            jsonObject.put("origin", origin);
            jsonObject.put("grape", grape);
            jsonObject.put("priceBegin", priceBegin);
            jsonObject.put("priceEnd", priceEnd);
            jsonObject.put("sort", sort);
            jsonObject.put("years", years);
            jsonObject.put("pageIndex", pageIndex);
            jsonObject.put("pageSize", pageSize);
            jsonObject.put("texture", texture);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("print", "shopList商品列表的展示的参数的拼接: " + jsonObject);


        iNetLoad.loadData(
                FLags.FLAG_SHOP_LIST, URLS.URL_SHOP_LIST, jsonObject, context, (INetResult) context, new TypeToken<BaseBean<ListBean<ShopListBean>>>() {
                }.getType());
    }


    /**
     * 本地页面商品列表
     */
    public void locashopList(Context context,
                             String productName,
                             String brandId,
                             String classId,
                             String advise,
                             String origin,
                             String grape,
                             String priceBegin,
                             String priceEnd,
                             String sort,
                             String years,
                             int pageIndex,
                             int pageSize,
                             String texture) {
        JSONObject jsonObject = new JSONObject();
        try {
            CellarIDBean bean = BaseData.getBaseData().getCellarInfoBean();
            jsonObject.put("bid", bean.getBid());//会员ID
            jsonObject.put("cellarId", bean.getCellerId());//存储酒窖ID  修改过的
            jsonObject.put("productName", productName);//商品名称
            jsonObject.put("brandId", brandId);//品牌
            jsonObject.put("classId", classId);
            jsonObject.put("advise", advise);
            jsonObject.put("origin", origin);
            jsonObject.put("grape", grape);
            jsonObject.put("priceBegin", priceBegin);
            jsonObject.put("priceEnd", priceEnd);
            jsonObject.put("sort", sort);
            jsonObject.put("years", years);
            jsonObject.put("pageIndex", pageIndex);
            jsonObject.put("pageSize", pageSize);
            jsonObject.put("texture", texture);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Log.d("print", "本地购买shopList商品列表的展示的参数的拼接: " + jsonObject);

        iNetLoad.loadData2(
                FLags.FLAG_LOCAL_SHOP_LIST, LOCALURLS.LOCAL_URL_SHOP_LIST, jsonObject, context, (INetResult) context, new TypeToken<BaseBean<ListBean<ShopListBean>>>() {
                }.getType());
    }


    //商品分类接口
    public void getShopClass(Context context, INetResult iNetResult, String pid) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("rowCount", "1");
            jsonObject.put("pid", pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        iNetLoad.loadData(
                FLags.FLAG_SHOP_CLASS,
                URLS.URL_SHOP_CLASS, jsonObject, context, iNetResult, new TypeToken<BaseBean<ListBean<ShopFilterBean>>>() {
                }.getType());
    }


    //管理登录
    public void loginMange(Context context, String account, String password) {
        JSONObject jsonObject = new JSONObject();
        try {
            CellarIDBean bean = BaseData.getBaseData().getCellarInfoBean();
            jsonObject.put("account", account);
            jsonObject.put("password", password);
            jsonObject.put("bid", bean.getBid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        iNetLoad.loadData(
                FLags.FLAG_LOGIN_MANAGER,
                URLS.URL_LOGIN_MANAGER, jsonObject, context, (INetResult) context, new TypeToken<BaseBean<ObjBean<LoginManagerBean>>>() {
                }.getType());
    }

    //葡萄产地
    public void getGrapevine(Context context, INetResult iNetResult) {
        JSONObject jsonObject = new JSONObject();
        iNetLoad.loadData(
                FLags.FLAG_GRAPEVINE,
                URLS.URL_GRAPEVINE, jsonObject, context, iNetResult, new TypeToken<BaseBean<ListBean<GrapevineBean>>>() {
                }.getType());
    }

    //葡萄品种
    public void getGrapeVariety(Context context, INetResult iNetResult) {
        JSONObject jsonObject = new JSONObject();
        iNetLoad.loadData(
                FLags.FLAG_GRAPE_VARIETY,
                URLS.URL_GRAPE_VARIETY, jsonObject, context, iNetResult, new TypeToken<BaseBean<ListBean<GrapeVarietyBean>>>() {
                }.getType());
    }

    //葡萄品牌分类
    public void getBrandType(Context context, INetResult iNetResult) {
        JSONObject jsonObject = new JSONObject();
        iNetLoad.loadData(
                FLags.FLAG_BRAND_TYPE,
                URLS.URL_BRAND_TYPE, jsonObject, context, iNetResult, new TypeToken<BaseBean<ListBean<BrandBean>>>() {
                }.getType());
    }

    //口感、配餐建议
    public void getTasteAndSuggest(Context context, INetResult iNetResult, int type) {
        JSONObject jsonObject = new JSONObject();
        try {
            if (type == 0) {
                jsonObject.put("dataCode", "drinking_textture");
            } else {
                jsonObject.put("dataCode", "drinking_cateringAdvise");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        iNetLoad.loadData(
                FLags.FLAG_TASTE_AND_SUGGEST,
                URLS.URL_TASTE_AND_SUGGEST, jsonObject, context, iNetResult, new TypeToken<BaseBean<ObjBean<DrinkingTexttureBean>>>() {
                }.getType());
    }

    //获取会员地址列表
    public void getMemberAddressList(Context context, INetResult iNetResult) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("cid", BaseData.getBaseData().getIdentityCheckResultBean().getBid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        iNetLoad.loadData(
                FLags.FLAG_MEMBER_ADDRESS_LIST,
                URLS.URL_MEMBER_ADDRESS_LIST, jsonObject, context, iNetResult, new TypeToken<BaseBean<ListBean<MemberAddressBean>>>() {
                }.getType());
    }

    /**
     * 本地购买查询客户地址信息
     * 这里是拼接的地方
     */

    public void getLocalAddressList(Context context, INetResult iNetResult) {
        JSONObject jsonObject = new JSONObject();
        try {
            CellarIDBean bean = BaseData.getBaseData().getCellarInfoBean();
            jsonObject.put("customerId", SharedPreferencesUtils.getString(context, "customerId"));
            jsonObject.put("bid", bean.getBid());
        } catch (Exception e) {
            e.printStackTrace();
        }
        iNetLoad.loadData2(
                FLags.FLAG_LOCAL_QUERY_CUSTOMER_INFO,
                LOCALURLS.LOCAL_URL_QUERY_CUSTOMER_INFO, jsonObject, context, iNetResult, new TypeToken<BaseBean<ListBean<LocalQueryAddressOtherBean>>>() {
                }.getType());
    }

    //会员添加地址
    public void addMemberAddress(Context context, INetResult iNetResult, MemberAddressBean addressMemberBean) {
        JSONObject jsonObject = null;
        try {
            addressMemberBean.setCid(BaseData.getBaseData().getIdentityCheckResultBean().getBid());
            Log.d("print", "addMemberAddress: " + addressMemberBean);
            jsonObject = new JSONObject(gson.toJson(addressMemberBean));
        } catch (Exception e) {
            e.printStackTrace();
        }
        iNetLoad.loadData(
                FLags.FLAG_MEMER_ADD_ADDRESS,
                URLS.URL_MEMER_ADD_ADDRESS, jsonObject, context, iNetResult, new TypeToken<BaseBean<ObjBean<DrinkingTexttureBean>>>() {
                }.getType());
    }


    //本地添加地址
    public void addLocalAddress(String customerId, Context context, INetResult iNetResult, LocalQueryAddressBean addressLocalBean) {
        JSONObject jsonObject = null;
        try {
            addressLocalBean.setCustomerId(customerId);
            jsonObject = new JSONObject(gson.toJson(addressLocalBean));
        } catch (Exception e) {
            e.printStackTrace();
        }
        iNetLoad.loadData2(
                FLags.FLAG_LOCAL_ADD_ADDRESS,
                LOCALURLS.LOCAL_URL_NEW_ADDRESS, jsonObject, context, iNetResult, new TypeToken<BaseBean<ObjBean<DrinkingTexttureBean>>>() {
                }.getType());

    }

    //删除地址
    public void dropMemberAddress(Context context, INetResult iNetResult, String id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        iNetLoad.loadData(
                FLags.FLAG_DELETE_MEMBER_ADDRESS,
                URLS.URL_DELETE_MEMBER_ADDRESS, jsonObject, context, iNetResult, new TypeToken<BaseBean<ObjBean<MemberAddressBean>>>() {
                }.getType());
    }

    //删除本地地址
    public void dropLocalAddress(Context context, INetResult iNetResult, String id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        iNetLoad.loadData2(
                FLags.FLAG_LOCAL_DELETE_ADDRESS,
                LOCALURLS.LOCAL_URL_DELEATE_ADDRESS_INFO, jsonObject, context, iNetResult, new TypeToken<BaseBean<ObjBean<LocalQueryAddressBean>>>() {
                }.getType());
    }


    //本地购买设置默认的收货地址
    public void setDefaultAddress(Context context, INetResult iNetResult, String id) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", id);
            jsonObject.put("customerId", SharedPreferencesUtils.getString(context, "customerId"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("print", "setDefaultAddress: " + jsonObject);

        iNetLoad.loadData2(
                FLags.FLAG_LOCAL_DEFAULT_ADDRESS,
                LOCALURLS.LOCAL_URL_SETDEFAULT_ADDRESS, jsonObject, context, iNetResult, new TypeToken<BaseBean<ObjBean<DefaultAddressBean>>>() {
                }.getType());
    }


    //修改地址
    public void updateMemberAddress(Context context, INetResult iNetResult, MemberAddressBean memberAddressBean) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(gson.toJson(memberAddressBean));
        } catch (Exception e) {
            e.printStackTrace();
        }
        iNetLoad.loadData(
                FLags.FLAG_UPDATE_MEMBER_ADDRESS,
                URLS.URL_UPDATE_MEMBER_ADDRESS, jsonObject, context, iNetResult, new TypeToken<BaseBean<ObjBean<MemberAddressBean>>>() {
                }.getType());
    }

    //本地修改地址
    public void updateLocalAddress(Context context, INetResult iNetResult, LocalQueryAddressBean memberAddressBean) {
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(gson.toJson(memberAddressBean));
            Log.d("print", "updateLocalAddress: " + memberAddressBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        iNetLoad.loadData2(
                FLags.FLAG_LOCAL_UPDATE_ADDRESS,
                LOCALURLS.LOCAL_URL_CHANGE_ADDRESS, jsonObject, context, iNetResult, new TypeToken<BaseBean<ObjBean<LocalQueryAddressBean>>>() {
                }.getType());
    }

    //订单提交
    public void orderSubmit(Context context, INetResult iNetResult, MemberSubmitOrderPramsBean pramsBean) {
        JSONObject jsonObject = null;
        try {
            pramsBean.setBuyerBid(BaseData.getBaseData().getIdentityCheckResultBean().getBid());
            pramsBean.setSellerBid(BaseData.getBaseData().getCellarInfoBean().getBid());
            pramsBean.setDeliveryCellerId(BaseData.getBaseData().getCellarInfoBean().getCellerId());
            Log.d("print", "orderSubmit: pramsBean" + pramsBean);

            jsonObject = new JSONObject(gson.toJson(pramsBean));

            Log.d("print", "orderSubmit: " + jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }

        iNetLoad.loadData(
                FLags.FLAG_MEMBER_ORDER_SUBMIT,
                URLS.URL_MEMBER_ORDER_SUBMIT, jsonObject, context, iNetResult, new TypeToken<BaseBean<ObjBean<OrderResultInfoBean>>>() {
                }.getType());

        Log.d("print", "orderSubmit: gettype " + new TypeToken<BaseBean<ObjBean<OrderResultInfoBean>>>() {
        }.getType());

    }


    //本地订单提交
    public void localOrderSubmit(Context context, INetResult iNetResult, LocalSubmitOrderPramsBean pramsBean) {
        JSONObject jsonObject = null;
        try {
            pramsBean.setBuyerBid(BaseData.getBaseData().getIdentityCheckResultBean().getBid());
            pramsBean.setSellerBid(BaseData.getBaseData().getCellarInfoBean().getBid());
            pramsBean.setDeliveryCellerId(BaseData.getBaseData().getCellarInfoBean().getCellerId());
            Log.d("print", "orderSubmit: pramsBean" + pramsBean);
            jsonObject = new JSONObject(gson.toJson(pramsBean));
            Log.d("print", "orderSubmit: " + jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
        iNetLoad.loadData2(
                FLags.FLAG_LOCAL_MEMBER_ORDER_SUBMIT,
                LOCALURLS.LOCAL_URL_MEMBER_ORDER_SUBMIT, jsonObject, context, iNetResult, new TypeToken<BaseBean<ObjBean<OrderResultInfoBean>>>() {
                }.getType());

        Log.d("print", "orderSubmit: gettype " + new TypeToken<BaseBean<ObjBean<OrderResultInfoBean>>>() {
        }.getType());

    }


    /**
     * 本地购买的时候  验证客户信息
     */

    public void checkLocalCustomerInfo(String phonenum, Context context) {
        JSONObject object = new JSONObject();
        try {
            CellarIDBean bean = BaseData.getBaseData().getCellarInfoBean();
            object.put("phone", phonenum);
            object.put("bid", bean.getBid());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        iNetLoad.loadData2(FLags.FLAG_LOCAL_CHECK_CUSTOMER_INFO,
                LOCALURLS.LOCAL_URL_CHECKUSER_INFO_ORDER, object, context, (INetResult) context,
                new TypeToken<BaseBean<ObjBean<CheckCustomerInfoBean>>>() {
                }.getType());

    }


    //订单列表
    public void getOrderList(Context context,
                             int filterStatus,
                             String timeSection,
                             int pageIndex,
                             int pageSize,
                             String searchContent) {
        JSONObject object = new JSONObject();
        try {

            object.put("filterStatus", filterStatus);
            object.put("searchKey", searchContent);
            object.put("timeSection", timeSection);
            object.put("pageIndex", pageIndex);
            object.put("pageSize", pageSize);
            CellarIDBean bean = BaseData.getBaseData().getCellarInfoBean();
            object.put("bid", bean.getBid());
            object.put("rowCount", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        iNetLoad.loadData(FLags.FLAG_ORDER_LIST,
                URLS.URL_ORDER_LIST, object, context, (INetResult) context,
                new TypeToken<BaseBean<ObjBean<ManagerOrderListBean>>>() {
                }.getType());
    }

    //本地购买的订单列表
    public void getLocalOrderList(Context context,
                                  int filterStatus,
                                  String timeSection,
                                  int pageIndex,
                                  int pageSize,
                                  String searchContent) {
        JSONObject object = new JSONObject();
        try {
            object.put("filterStatus", filterStatus);
            object.put("searchKey", searchContent);
            object.put("timeSection", timeSection);
            object.put("pageIndex", pageIndex);
            object.put("pageSize", pageSize);
            CellarIDBean bean = BaseData.getBaseData().getCellarInfoBean();
            object.put("bid", bean.getBid());
            object.put("rowCount", "1");
            Log.d("print", "getLocalOrderList: " + object);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        iNetLoad.loadData2(FLags.FLAG_LOCAL_ORDER_LIST,
                LOCALURLS.LOCAL_URL_ORDER_LIST, object, context, (INetResult) context,
                new TypeToken<BaseBean<ObjBean<ManagerOrderListBean>>>() {
                }.getType());
    }


    /**
     * 请求订单详情
     */
    public void getOrderDetail(Context context, INetResult iNetResult, String orderId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", orderId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        iNetLoad.loadData(FLags.FLAG_ORDER_DETAIL,
                URLS.URL_ORDER_DETAIL, jsonObject, context, iNetResult,
                new TypeToken<BaseBean<ObjBean<ManagerOrderDetailBean>>>() {
                }.getType());
    }


    /**
     * 请求本地订单详情
     */
    public void getLocaOrderDetail(Context context, INetResult iNetResult, String orderId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", orderId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("print", "getLocaOrderDetail: " + orderId);
        iNetLoad.loadData2(FLags.FLAG_LOCAL_ORDER_DETAIL,
                LOCALURLS.LOCAL_URL_ORDER_DETAIL, jsonObject, context, iNetResult,
                new TypeToken<BaseBean<ObjBean<ManagerOrderDetailBean>>>() {
                }.getType());
    }

    //获取酒品存储列表
    public void getStorageCellarTab(Context context, String selectType, String searchkey, int pageIndex, int pageSize) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("searchKey", searchkey);
            // jsonObject.put("rowCount", "0");
            jsonObject.put("pageIndex", pageIndex);
            jsonObject.put("pageSize", pageSize);
            jsonObject.put("selectType", selectType);
            jsonObject.put("cellerId", BaseData.getBaseData().getCellarInfoBean().getCellerId());
            jsonObject.put("bid", BaseData.getBaseData().getCellarInfoBean().getBid());

        } catch (Exception e) {
            e.printStackTrace();
        }
        iNetLoad.loadData(
                FLags.FLAG_STORAGE_CELLAR,
                URLS.URL_STORAGE_CELLAR, jsonObject, context, (INetResult) context, new TypeToken<BaseBean<ListBean<StorageCellBean>>>() {
                }.getType());
    }

    //获取本地酒品存储列表
    public void getLocalStorageCellarTab(Context context, String selectType, String searchkey, int pageIndex, int pageSize) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("searchKey", searchkey);
            jsonObject.put("pageIndex", pageIndex);
            jsonObject.put("pageSize", pageSize);
//            jsonObject.put("selectType", selectType);
            jsonObject.put("cellerId", BaseData.getBaseData().getCellarInfoBean().getCellerId());
            jsonObject.put("bid", BaseData.getBaseData().getCellarInfoBean().getBid());

        } catch (Exception e) {
            e.printStackTrace();
        }
        iNetLoad.loadData2(
                FLags.FLAG_LOCAL_STORAGE_CELLAR,
                LOCALURLS.LOCAL_URL_STORAGE_CELLAR, jsonObject, context, (INetResult) context, new TypeToken<BaseBean<ListBean<StorageCellBean>>>() {
                }.getType());
    }


    //获取酒品存储详情
    public void getStorageCellarTabDetail(Context context, String pid, String selectType) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("selectType", selectType);
            jsonObject.put("cellerId", BaseData.getBaseData().getCellarInfoBean().getCellerId());
            jsonObject.put("bid", BaseData.getBaseData().getCellarInfoBean().getBid());
            jsonObject.put("pid", pid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        iNetLoad.loadData(
                FLags.FLAG_STORAGE_CELLAR_TAB_DETAIL,
                URLS.URL_STORAGE_CELLAR_TAB_DETAIL, jsonObject, context, (INetResult) context, new TypeToken<BaseBean<ObjBean<StorageCellarTabDetailBean>>>() {
                }.getType());
    }


    //获取本地酒品存储详情
    public void getLocalStorageCellarTabDetail(Context context, String pid, String selectType) {
        JSONObject jsonObject = new JSONObject();
        try {
//            jsonObject.put("selectType", selectType);
            jsonObject.put("cellerId", BaseData.getBaseData().getCellarInfoBean().getCellerId());
            jsonObject.put("bid", BaseData.getBaseData().getCellarInfoBean().getBid());
            jsonObject.put("pid", pid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        iNetLoad.loadData2(
                FLags.FLAG_LOCAL_STORAGE_CELLAR_TAB_DETAIL,
                LOCALURLS.LOCAL_URL_STORAGE_CELLAR_TAB_DETAIL, jsonObject, context, (INetResult) context, new TypeToken<BaseBean<ObjBean<LocalStorageCellarTabDetailBean>>>() {
                }.getType());
    }


    //修改密码
    public void getChangePassword(Context context, String aid, String password, String rePassword, String sms_code, String mUuid, INetResult iNetResult) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("aid", aid);
            jsonObject.put("password", password);
            jsonObject.put("rePassword", rePassword);
            jsonObject.put("messageCode", sms_code);
            jsonObject.put("uuid", mUuid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        iNetLoad.loadData(
                FLags.FLAG_CHANGE_PASSWORD,
                URLS.URL_CHANGE_PASSWORD, jsonObject, context, iNetResult, new TypeToken<BaseBean<ObjBean<ChangeBean>>>() {
                }.getType());
    }

    //找回密码
    public void getforgetPassword(Context context, String account, String password, String rePassword, String messageCode, String uuid) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("account", account);
            jsonObject.put("password", password);
            jsonObject.put("rePassword", rePassword);
            jsonObject.put("messageCode", messageCode);
            jsonObject.put("uuid", uuid);
        } catch (Exception e) {
            e.printStackTrace();
        }

        iNetLoad.loadData(
                FLags.FLAG_FORGET_PASSWORD,
                URLS.URL_FORGET_PASSWORD, jsonObject, context, (INetResult) context, new TypeToken<BaseBean<ObjBean<ChangeBean>>>() {
                }.getType());
    }

    /**
     * 订单收款
     */
    public void getOrderReceivable(Context context, INetResult iNetResult, String orderId, String type) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", orderId);
            jsonObject.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        iNetLoad.loadData(FLags.FLAG_ORDER_RECEIABLE,
                URLS.URL_ORDER_RECEIABLE, jsonObject, context, iNetResult,
                new TypeToken<BaseBean<ObjBean<Object>>>() {
                }.getType());
    }


    /**
     * 本地订单收款
     */
    public void getLocalOrderReceivable(Context context, INetResult iNetResult, String orderId, String type) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", orderId);
            jsonObject.put("type", type);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        iNetLoad.loadData2(FLags.FLAG_LOCAL_ORDER_RECEIABLE,
                LOCALURLS.LOCAL_URL_ORDER_RECEIABLE, jsonObject, context, iNetResult,
                new TypeToken<BaseBean<ObjBean<Object>>>() {
                }.getType());
    }


    /**
     * 订单发货
     */
    public void getOrderSend(Context context
            , INetResult iNetResult
            , String orderId
            , String orderCode
            , String deliveryType
            , String deliveryName
            , String deliveryMobile
            , String logiticsCompanyName
            , String logiticsCode) {
        JSONObject object = new JSONObject();
        try {
            object.put("orderId", orderId);
            object.put("orderCode", orderCode);
            object.put("deliveryType", deliveryType);
            if (deliveryType.equals("2")) {
                //配送
                object.put("deliveryName", deliveryName);
                object.put("deliveryMobile", deliveryMobile);
                object.put("logisticsCompanyName", logiticsCompanyName);
                object.put("logisticsCode", logiticsCode);
            }
            object.put("cellerId", BaseData.getBaseData().getCellarInfoBean().getCellerId());
            object.put("bid", BaseData.getBaseData().getCellarInfoBean().getBid());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        iNetLoad.loadData(FLags.FLAG_ORDER_SEND
                , URLS.URL_ORDER_SEND_OR_PAY, object, context, iNetResult
                , new TypeToken<BaseBean<ObjBean<Object>>>() {
                }.getType());
    }


    /**
     * 本地订单发货
     */
    public void getLocalOrderSend(Context context
            , INetResult iNetResult
            , String orderId
            , String orderCode
            , String deliveryType
            , String deliveryName
            , String deliveryMobile
            , String logiticsCompanyName
            , String logiticsCode) {
        JSONObject object = new JSONObject();
        try {
            object.put("orderId", orderId);
            object.put("orderCode", orderCode);
            object.put("deliveryType", deliveryType);
            if (deliveryType.equals("2")) {
                //配送
                object.put("deliveryName", deliveryName);
                object.put("deliveryMobile", deliveryMobile);
                object.put("logisticsCompanyName", logiticsCompanyName);
                object.put("logisticsCode", logiticsCode);
            }
            object.put("cellerId", BaseData.getBaseData().getCellarInfoBean().getCellerId());
            object.put("bid", BaseData.getBaseData().getCellarInfoBean().getBid());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("print", "getLocalOrderSend: " + object);

        iNetLoad.loadData2(FLags.FLAG_LOCAL_ORDER_SEND
                , LOCALURLS.LOCAL_URL_SURE_SEND, object, context, iNetResult
                , new TypeToken<BaseBean<ObjBean<Object>>>() {
                }.getType());
    }


    /***
     * 订单完成
     */
    public void setOrderFinish(Context context, INetResult iNetResult, String orderId) {
        JSONObject object = new JSONObject();
        try {
            object.put("id", orderId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        iNetLoad.loadData(FLags.FLAG_ORDER_FINISH,
                URLS.URL_ORDER_FINISH, object, context, iNetResult,
                new TypeToken<BaseBean<ObjBean<Object>>>() {
                }.getType());
    }

    /***
     * 本地订单完成
     */
    public void setLocalOrderFinish(Context context, INetResult iNetResult, String orderId) {
        JSONObject object = new JSONObject();
        try {
            object.put("id", orderId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        iNetLoad.loadData2(FLags.FLAG_LOCAL_ORDER_FINISH,
                LOCALURLS.LOCAL_URL_END_ORDER, object, context, iNetResult,
                new TypeToken<BaseBean<ObjBean<Object>>>() {
                }.getType());
    }


    /***
     * 订单数量信息
     */
    public void getOrderNum(Context context) {
        JSONObject object = new JSONObject();
        try {
            CellarIDBean bean = BaseData.getBaseData().getCellarInfoBean();
            object.put("bid", bean.getBid());
            object.put("cellerId", bean.getCellerId());
            LP.d("getOrderNum: " + bean.getBid() + "            " + bean.getCellerId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        iNetLoad.loadData(FLags.FLAG_ORDER_FINISH,
                URLS.URL_ORDER_NUM, object, context, (INetResult) context,
                new TypeToken<BaseBean<ObjBean<PendingOrderNumBean>>>() {
                }.getType());
    }

    /***
     * 订单数量信息
     */
    public void getLocalOrderNum(Context context) {
        JSONObject object = new JSONObject();
        try {
            CellarIDBean bean = BaseData.getBaseData().getCellarInfoBean();
            object.put("bid", bean.getBid());
            object.put("cellerId", bean.getCellerId());

            LP.d("getOrderNum: " + bean.getBid() + "            " + bean.getCellerId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        iNetLoad.loadData2(FLags.FLAG_ORDER_FINISH,
                LOCALURLS.LOCAL_URL_ORDER_NUM, object, context, (INetResult) context,
                new TypeToken<BaseBean<ObjBean<PendingOrderNumBean>>>() {
                }.getType());
    }

    /***
     * 获取微信支付信息
     */
    public void getWechatPayInfo(Context context, String oids, String bid) {
        JSONObject object = new JSONObject();
        try {
            object.put("oids", oids);//多订单用逗号隔开   必填
            object.put("bid", bid);//会员ID   快速购买不填  会员购买必填
            object.put("type", "1");// 1  代表支付   2代表充值
            object.put("payId", "10013");//支付ID
            object.put("rechargeAmout", "");// 充值金额  如果类型type 是充值则必填
        } catch (JSONException e) {
            e.printStackTrace();
        }
        iNetLoad.loadData(FLags.FLAG_WECHAT_PAY,
                URLS.URL_GET_WECHAT_PAY_INFO, object, context, (INetResult) context,
                new TypeToken<BaseBean<ObjBean<PayInfoBean>>>() {
                }.getType());
    }

    /***
     * 获取支付宝支付信息
     */
    public void getAliPayInfo(Context context, String oids, String bid) {
        JSONObject object = new JSONObject();
        try {
            object.put("oids", oids);
            object.put("bid", bid);
            object.put("type", "1");
            object.put("payId", "10010");
            object.put("rechargeAmout", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        iNetLoad.loadData(FLags.FLAG_ALI_PAY,
                URLS.URL_GET_AIL_PAY_INFO, object, context, (INetResult) context,
                new TypeToken<BaseBean<ObjBean<PayInfoBean>>>() {
                }.getType());
    }

    /**
     * 获取本地订单的支付信息
     */
    public void getLocalPayInfo(Context context) {
        JSONObject object = new JSONObject();
        try {
            CellarIDBean bean = BaseData.getBaseData().getCellarInfoBean();
            object.put("bid", bean.getBid());
            object.put("cellarId", bean.getCellerId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d("print", "getLocalPayInfo: " + object);
        iNetLoad.loadData2(FLags.FLAG_LOCAL_PAY_INFO,
                LOCALURLS.LOCAL_URL_GET_PAY_INFO, object, context, (INetResult) context,
                new TypeToken<BaseBean<ObjBean<LocalPayInfoBean>>>() {
                }.getType());
    }


    /***
     * 获取资金支付
     */
    @Deprecated
    public void cashPay(Context context, String oids, String bid) {
        JSONObject object = new JSONObject();
        try {
            object.put("oids", oids);
            object.put("bid", bid);
            object.put("type", "1");
            object.put("payId", "10010");
            object.put("rechargeAmout", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        iNetLoad.loadData(FLags.FLAG_GET_CASH_PAY_INFO,
                URLS.URL_GET_CASH_PAY_INFO, object, context, (INetResult) context,
                new TypeToken<BaseBean<ObjBean<PayInfoBean>>>() {
                }.getType());
    }

    /***
     * 获取订单状态
     */
    public void getOrderState(Context context, String oids) {
        JSONObject object = new JSONObject();
        try {
            object.put("id", oids);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        iNetLoad.loadData(FLags.FLAG_GET_ORDER_STATE,
                URLS.URL_GET_ORDER_STATE, object, context, (INetResult) context,
                new TypeToken<BaseBean<ObjBean<OrderStateInfoBean>>>() {
                }.getType());
    }

    /***
     * 根据条码获取商品ID
     */
    public void getShopIDByBarcode(Context context, String barcode) {
        JSONObject object = new JSONObject();
        try {
            object.put("barCode", barcode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("print", "getShopIDByBarcode: " + barcode);
        iNetLoad.loadData(FLags.FLAG_SHOPID_BY_BARCODE,
                URLS.URL_SHOPID_BY_BARCODE, object, context, (INetResult) context,
                new TypeToken<BaseBean<ObjBean<ShopIDByBarcode>>>() {
                }.getType());
    }


    /***
     * 本地根据条码获取商品ID
     */
    public void getLocalShopIDByBarcode(Context context, String barcode) {
        JSONObject object = new JSONObject();
        try {
            object.put("barCode", barcode);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("print", "getShopIDByBarcode: " + barcode);
        iNetLoad.loadData2(FLags.FLAG_LOCAL_SHOPID_BY_BARCODE,
                LOCALURLS.LOCAL_URL_SHOPID_BY_BARCODE, object, context, (INetResult) context,
                new TypeToken<BaseBean<ObjBean<ShopIDByBarcode>>>() {
                }.getType());
    }


    /**
     * 根据订单ID查询小票信息
     */
    public void getReceiptByOrderID(Context context, String orderId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("orderId", orderId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        iNetLoad.loadData(
                FLags.FLAG_RECEIPT_INFO,
                URLS.URL_RECEIPT_INFO, jsonObject, context, (INetResult) context, new TypeToken<BaseBean<ObjBean<ReceiptInfoBean>>>() {
                }.getType());
    }

    /**
     * 本地购买根据订单ID查询小票信息
     */
    public void getLocalReceiptByOrderID(Context context, String orderId) {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("orderId", orderId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        iNetLoad.loadData2(
                FLags.FLAG_LOCAL_RECEIPT_INFO,
                LOCALURLS.LOCAL_URL_GETOREDER_INFO, jsonObject, context, (INetResult) context, new TypeToken<BaseBean<ObjBean<ReceiptInfoBean>>>() {
                }.getType());
    }

    /**
     * 获取版本号，更新版本
     */
    public void getVersionCode(Context context) {
        JSONObject jsonObject = new JSONObject();
        iNetLoad.loadData(
                FLags.FLAG_VERSION_CODE,
                URL_VERSION_CODE, jsonObject, context, (INetResult) context, new TypeToken<BaseBean<ObjBean<VersionCodeBean>>>() {
                }.getType());
    }
}
