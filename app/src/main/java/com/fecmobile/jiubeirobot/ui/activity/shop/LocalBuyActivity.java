package com.fecmobile.jiubeirobot.ui.activity.shop;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseActivity;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.base.BaseData;
import com.fecmobile.jiubeirobot.bean.DrinkingTexttureBean;
import com.fecmobile.jiubeirobot.bean.FilterBean;
import com.fecmobile.jiubeirobot.bean.ListBean;
import com.fecmobile.jiubeirobot.bean.LocalShopDetailsBean;
import com.fecmobile.jiubeirobot.bean.ShopFilterBean;
import com.fecmobile.jiubeirobot.bean.ShopListBean;
import com.fecmobile.jiubeirobot.bean.StockBean;
import com.fecmobile.jiubeirobot.common.Activitys;
import com.fecmobile.jiubeirobot.common.Constants;
import com.fecmobile.jiubeirobot.data.net.APIManager;
import com.fecmobile.jiubeirobot.data.net.FLags;
import com.fecmobile.jiubeirobot.ui.adapter.shop.LocalBuyShopListAdapter;
import com.fecmobile.jiubeirobot.ui.fragment.shop.FilterTypesFragment;
import com.fecmobile.jiubeirobot.ui.fragment.shop.LocalShopDetailsFragment;
import com.fecmobile.jiubeirobot.ui.fragment.shop.ShopDetailsFragment;
import com.fecmobile.jiubeirobot.ui.fragment.shop.ShopListFilterFragment;
import com.fecmobile.jiubeirobot.utils.L;
import com.fecmobile.jiubeirobot.utils.PageUtils;
import com.fecmobile.jiubeirobot.utils.T;
import com.fecmobile.xrecyclerview.XRecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 这是新增的本地购买需要显示的activity
 * 本地购买   下单的时候需要区分 这样在管理页面才能明确的区分出来
 */

public class LocalBuyActivity extends BaseActivity implements XRecyclerView.LoadingListener, DrawerLayout.DrawerListener, LocalBuyShopListAdapter.LocalClickCell {
    //布局开始
    @BindView(R.id.llyt_back)
    LinearLayout llytBack;
    @BindView(R.id.local_dlt_filter_content)
    DrawerLayout dltFilterContent;
    @BindView(R.id.local_frame_content)
    FrameLayout frameContent;
    @BindView(R.id.local_rv_view)
    XRecyclerView rvView;
    @BindView(R.id.local_dl_shop_conetn_and_details)
    DrawerLayout dlShopConetnAndDetails;
    @BindView(R.id.rl_card)
    RelativeLayout rlCard;
    @BindView(R.id.et_search_input)
    EditText etSearchInput;
    @BindView(R.id.tv_shop_number)
    TextView tvShopNumber;

    @BindView(R.id.local_btn_default)
    TextView btnDefault;
    @BindView(R.id.local_btn_sales)
    TextView btnSales;
    @BindView(R.id.local_btn_price)
    TextView btnPrice;
    @BindView(R.id.local_iv_price)
    ImageView ivPrice;
    @BindView(R.id.local_llyt_price)
    LinearLayout llytPrice;
    @BindView(R.id.local_btn_filter)
    TextView btnFilter;
    @BindView(R.id.local_llyt_shop_list_filter_filter)
    LinearLayout llytShopListFilterFilter;
    //布局结束

    private ShopListFilterFragment shopListFilterFragment;//TODO  添加了一个判断
    private FilterTypesFragment filterTypesFragment;
    private FilterTypesFragment subFilterTypesFragment;
    private LocalShopDetailsFragment shopDetailsFragment;
    private FilterTypesFragment cateringAdviseFilterTypesFragment;

    //    private ShopListAdapter shopListAdaptet;//RecyclerView的适配器
    private LocalBuyShopListAdapter shopListAdaptet;


    private List<ShopListBean> dataLst = new ArrayList<>();//数据集合
    private ViewGroup anim_mask_layout;//动画层
    private int priceType;
    private boolean isFrist;
    private ShopFilterBean shopFilterBean;//商品分类筛选
    private DrinkingTexttureBean.DrinkingTextture cateringAdvise;//配餐建议
    private String sort = "0";//排序  0 默认   1  销量降序  2 销量升序  3 价格降序  4 价格升序
    //各个过滤的选择的信息
    private FilterBean producing;//产地
    private FilterBean grapeVariety;//品种
    private FilterBean brandType;//品牌分类
    private FilterBean taste;//口感
    private String priceBegin;//价格开始
    private String priceEnd;//价格结束
    private String years;//年份
    private PageUtils pageUtils = new PageUtils();


    public void setPriceBegin(String priceBegin) {
        this.priceBegin = priceBegin;
    }

    public void setPriceEnd(String priceEnd) {
        this.priceEnd = priceEnd;
    }

    public void setYears(String years) {
        this.years = years;
    }

    public void setTaste(FilterBean taste) {
        this.taste = taste;
    }

    public void setBrandType(FilterBean brandType) {
        this.brandType = brandType;
    }

    public void setProducing(FilterBean producing) {
        this.producing = producing;
    }

    public void setGrapeVariety(FilterBean grapeVariety) {
        this.grapeVariety = grapeVariety;
    }

    public void setShopFilterBean(ShopFilterBean shopFilterBean) {
        this.shopFilterBean = shopFilterBean;
    }


    @Override
    public int pageLayout() {
        return R.layout.activity_local_buy;
    }

    @Override
    public void initTitle() {

    }

    /**
     * 初始化控件
     */
    @Override
    public void initView() {
        rvView.setPullRefreshEnabled(true);//设置下拉刷新为TRUE
        rvView.setLoadingMoreEnabled(false);//设置加载更多
        rvView.setLoadingListener(this);//设置加载监听
        pageUtils.setPageSize(10);//设置每页的大小

        dltFilterContent.addDrawerListener(this);
        dlShopConetnAndDetails.addDrawerListener(this);

        dlShopConetnAndDetails.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        rvView.setLayoutManager(new GridLayoutManager(this, 2));//设置RecyclerView显示的格式   网格布局


        shopListAdaptet = new LocalBuyShopListAdapter(this, dataLst);

        //设置item的点击监听
        shopListAdaptet.setLocalClickCell(this);

        rvView.setAdapter(shopListAdaptet);//设置适配器

        etSearchInput.setKeyListener(null);
        etSearchInput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activitys.toLocalSearchShop(LocalBuyActivity.this, true);
            }
        });

        tvShopNumber.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (tvShopNumber.getText().length() == 0) {
                    tvShopNumber.setVisibility(View.INVISIBLE);
                } else {
                    tvShopNumber.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        if (!isFrist) {
            addShoplistFilterFragment();
            isFrist = true;
        }
        getBarcode();
        rvView.refresh();
        L.i("initView");
    }


    private void getBarcode() {
        String type = getIntent().getStringExtra(Constants.INTENT_LOCAL_SHOP_LIST_TYPE);
        if (Constants.INTENT_LOCAL_SHOP_DETAILS.equals(type)) {
            String shopId = getIntent().getStringExtra(Constants.INTENT_LOCAL_SHOP_BARCODE);
            L.i("----shopId：" + shopId);
            addShopDetailsFragment(shopId);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        getBarcode();
        L.i("onNewIntent");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<ShopListBean>>() {
            }.getType();
            String shopStrs = data.getStringExtra(Constants.INTENT_SHOP_SEARCH_RESULT);
            List<ShopListBean> shopList = gson.fromJson(shopStrs, type);
            if (shopList != null) {
                dataLst.clear();
                dataLst.addAll(shopList);
                shopListAdaptet.notifyDataSetChanged();
            } else {
                submit();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void onSuccess(final BaseBean bean, int flag) {
        switch (flag) {
            case FLags.FLAG_LOCAL_SHOP_LIST:
                BaseBean<ListBean<ShopListBean>> listBeanBaseBean = bean;
                pageUtils.setTotalNum(listBeanBaseBean.getData().getRowCount());
                if (pageUtils.isCurentLoadMode()) {
                    rvView.loadMoreComplete();
                } else {
                    dataLst.clear();
                    rvView.refreshComplete();
                }
                rvView.setLoadingMoreEnabled(pageUtils.isMore());
                dataLst.addAll(listBeanBaseBean.getData().getList());
                shopListAdaptet.notifyDataSetChanged();
                break;
        }
        dismissHUD();
    }

    @Override
    protected void onResume() {
        super.onResume();
        InputMethodManager inputManager = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(etSearchInput.getWindowToken(), 0);
        if (shopDetailsFragment != null) {
            shopDetailsFragment.onResume();
        }
        setPrice();
    }

    @OnClick({R.id.llyt_back, R.id.local_llyt_shop_list_filter_filter, R.id.rl_card, R.id.local_btn_default, R.id.local_btn_sales, R.id.local_llyt_price})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llyt_back:
                finish();
//                Intent intent = new Intent();
//                intent.setAction(ScannerReceivers.ACTION);
//                intent.putExtra(ScannerReceivers.DATA,"dfdfsdsfdsfsfs");
//                sendBroadcast(intent);
                break;
            case R.id.local_llyt_shop_list_filter_filter:
                //点击筛选
                if (dltFilterContent.isDrawerOpen(Gravity.RIGHT)) {
                    dltFilterContent.closeDrawer(Gravity.RIGHT);
                } else {
                    dltFilterContent.openDrawer(Gravity.RIGHT);
                }
                changeState(3);
                break;
            case R.id.rl_card:
                //直接点击购物车 判断购物车；里面是否含有相应的物品
                if (BaseData.getBaseData().getCardShops().size() > 0) {
                    Activitys.toShoppingCard(this);
                } else {
                    T.showToCenter(getString(R.string.common_cart_is_null));
                }
                break;
            //默认按钮的点击事件监听
            case R.id.local_btn_default:
                changeState(0);
                pageUtils.setCurentLoadMode(false);//刷新页面
                submit();
                break;
            //销量按钮
            case R.id.local_btn_sales:
                changeState(1);
                pageUtils.setCurentLoadMode(false);
                submit();
                break;
            case R.id.local_llyt_price:
                changeState(2);
                pageUtils.setCurentLoadMode(false);
                submit();
                break;
        }
    }

    public void onAllSelect(ShopFilterBean shopFilterBean) {
        this.shopFilterBean = shopFilterBean;
        if (shopListFilterFragment != null && shopFilterBean != null) {
            shopListFilterFragment.setOnSelect(shopFilterBean);
        }
    }

    public void setCateringAdvise(DrinkingTexttureBean.DrinkingTextture cateringAdvise) {
        this.cateringAdvise = cateringAdvise;
        if (shopListFilterFragment != null && cateringAdvise != null) {
            shopListFilterFragment.setCateringAdvise(cateringAdvise);
        }
    }

    private void changeState(int index) {
        btnDefault.setTextColor(getResources().getColorStateList(R.color.color_33));
        btnSales.setTextColor(getResources().getColorStateList(R.color.color_33));
        btnPrice.setTextColor(getResources().getColorStateList(R.color.color_33));
        btnFilter.setTextColor(getResources().getColorStateList(R.color.color_33));
        ivPrice.setImageResource(R.mipmap.price_default);
        if (index == 0) {
            //默认按钮点击
            sort = "0";
            priceType = 0;
            btnDefault.setTextColor(getResources().getColorStateList(R.color.color_main));
        } else if (index == 1) {
            //销量按钮
            sort = "1";
            priceType = 0;
            btnSales.setTextColor(getResources().getColorStateList(R.color.color_main));
        } else if (index == 2) {
            //价格
            btnPrice.setTextColor(getResources().getColorStateList(R.color.color_main));
            priceType++;
            sort = "3".equals(sort) ? "4" : "3";
            if (priceType > 2) {
                priceType = 1;
            }
        } else if (index == 3) {
            priceType = 0;
            btnFilter.setTextColor(getResources().getColorStateList(R.color.color_main));
        }

        if (priceType == 0) {
            ivPrice.setImageResource(R.mipmap.price_default);
        } else if (priceType == 1) {
            ivPrice.setImageResource(R.mipmap.price_down);
        } else {
            ivPrice.setImageResource(R.mipmap.price_up);
        }
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerOpened(View drawerView) {
        switch (drawerView.getId()) {
            case R.id.local_frame_content:
                addShoplistFilterFragment();
                break;
            case R.id.local_fl_shop_details:
                dlShopConetnAndDetails.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
                break;
        }
    }

    /**
     * 描述      :添加商品详情
     * 方法名    :  addShopDetailsFragment
     * param    :无
     * 返回类型  :void
     * 创建人    : ghy
     * 创建时间  : 2017/2/23 16:21
     * 修改人    :
     * 修改时间
     * 修改备注
     * throws
     */
    private void addShopDetailsFragment(String id) {
        if (shopDetailsFragment == null) {
            shopDetailsFragment = new LocalShopDetailsFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constants.INTENT_SHOP_ID, id);
            shopDetailsFragment.setArguments(bundle);
        } else {
            shopDetailsFragment.setId(id);
            shopDetailsFragment.onResume();
        }
        FragmentTransaction ftrans = getSupportFragmentManager().beginTransaction();
        ftrans.replace(R.id.local_fl_shop_details, shopDetailsFragment);
        ftrans.commitAllowingStateLoss();
        dlShopConetnAndDetails.openDrawer(Gravity.RIGHT);
    }


    /**
     * 描述      :添加商品筛选到商品列表
     * 方法名    :  addShoplistFilterFragment
     * param    :无
     * 返回类型  :void
     * 创建人    : ghy
     * 创建时间  : 2017/2/23 14:41
     * 修改人    :
     * 修改时间
     * 修改备注
     * throws
     */
    public void addShoplistFilterFragment() {
        if (shopListFilterFragment == null) {
            shopListFilterFragment = new ShopListFilterFragment();
            FragmentTransaction ftrans = getSupportFragmentManager().beginTransaction();
            ftrans.add(R.id.local_frame_content, shopListFilterFragment);
            ftrans.commitAllowingStateLoss();
        }
    }

    /**
     * 描述      :添加全部类型到侧滑筛选
     * 方法名    :  addFilterTypesFragment
     * param    :   无
     * 返回类型  : void
     * 创建人    : ghy
     * 创建时间  : 2017/2/23 14:41
     * 修改人    :
     * 修改时间
     * 修改备注
     * throws
     */
    public void addFilterTypesFragment() {
        if (filterTypesFragment == null) {
            filterTypesFragment = new FilterTypesFragment();
        }
        FragmentTransaction ftrans = getSupportFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.INTENT_SHOP_FILTER_BEAN, null);
        bundle.putString(Constants.INTENT_FILTER_TYPE, FilterTypesFragment.ALL_TYPE);
        filterTypesFragment.setArguments(bundle);
        ftrans.add(R.id.local_frame_content, filterTypesFragment);
        ftrans.addToBackStack(null);
        ftrans.commit();
    }

    /**
     * 描述      : 添加子分类型到侧滑筛选
     * 方法名    :  addFilterTypesFragment
     * param    :   无
     * 返回类型  : void
     * 创建人    : ghy
     * 创建时间  : 2017/2/23 14:41
     * 修改人    :
     * 修改时间
     * 修改备注
     * throws
     */
    public void addSubFilterTypesFragment() {
        if (subFilterTypesFragment == null) {
            subFilterTypesFragment = new FilterTypesFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants.INTENT_SHOP_FILTER_BEAN, shopFilterBean);
        bundle.putString(Constants.INTENT_FILTER_TYPE, FilterTypesFragment.SECOND_LEVEL);
        subFilterTypesFragment.setArguments(bundle);
        FragmentTransaction ftrans = getSupportFragmentManager().beginTransaction();
        ftrans.add(R.id.local_frame_content, subFilterTypesFragment);
        ftrans.addToBackStack(null);
        ftrans.commit();
    }

    /**
     * 描述      : 配餐建议
     * 方法名    :  addCateringAdviseFilterTypesFragment
     * param    : 无
     * 返回类型  : void
     * 创建人    : ghy
     * 创建时间  : 2017/3/13 11:12
     * 修改人    :
     * 修改时间
     * 修改备注
     * throws
     */
    public void addCateringAdviseFilterTypesFragment() {
        if (cateringAdviseFilterTypesFragment == null) {
            cateringAdviseFilterTypesFragment = new FilterTypesFragment();
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.INTENT_FILTER_TYPE, FilterTypesFragment.SUGGESTING_TYPE);
        cateringAdviseFilterTypesFragment.setArguments(bundle);
        FragmentTransaction ftrans = getSupportFragmentManager().beginTransaction();
        ftrans.add(R.id.local_frame_content, cateringAdviseFilterTypesFragment);
        ftrans.addToBackStack(null);
        ftrans.commit();
    }


    /**
     * 描述      :返回上一个Fragment
     * 方法名    : backStack
     * param    :   无
     * 返回类型  : Void
     * 创建人    : ghy
     * 创建时间  : 2017/2/23 14:42
     * 修改人    :
     * 修改时间
     * 修改备注
     * throws
     */
    public void backStack() {
        getSupportFragmentManager().popBackStack();
    }


    @Override
    public void onDrawerClosed(View drawerView) {
        switch (drawerView.getId()) {
            case R.id.local_fl_shop_details:
                shopDetailsFragment.onDestroy();
                dlShopConetnAndDetails.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
                break;
        }
    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    /**
     * item点击事件监听
     *
     * @param postion
     */
    @Override
    public void onItemClick(int postion) {
        ShopListBean bean = dataLst.get(postion);
        Log.d("print", "LocalBuyActivity的onItemClick: " + bean);
        addShopDetailsFragment(bean.getId());
    }

    /**
     * 描述      :关闭商品详情
     * 方法名    :  closeShopDetails
     * param    :   无
     * 返回类型  : void
     * 创建人    : ghy
     * 创建时间  : 2017/3/9 11:22
     * 修改人    :
     * 修改时间
     * 修改备注
     * throws
     */
    public void closeShopDetails() {
        dlShopConetnAndDetails.closeDrawer(Gravity.RIGHT);
    }

    /**
     * 描述      : 关闭筛选
     * 方法名    :  closeFilter
     * param    : 无
     * 返回类型  :void
     * 创建人    : ghy
     * 创建时间  : 2017/3/15 10:28
     * 修改人    :
     * 修改时间
     * 修改备注
     * throws
     */
    public void closeFilter() {
        dltFilterContent.closeDrawer(Gravity.RIGHT);
    }

    /**
     * 描述      : 提交
     * 方法名    :  submit
     * param    : 无
     * 返回类型  :void
     * 创建人    : ghy
     * 创建时间  : 2017/3/15 10:28
     * 修改人    :
     * 修改时间
     * 修改备注
     * throws
     */
    public void submit() {
        String brandId;
        String classId;
        String advise;
        String origin;
        String grape;
        String texture;
        if (shopFilterBean == null) {
            shopFilterBean = new ShopFilterBean();
        }
        if (cateringAdvise == null) {
            cateringAdvise = new DrinkingTexttureBean.DrinkingTextture();
        }
        if (brandType == null) {
            brandType = new FilterBean();
        }
        if (producing == null) {
            producing = new FilterBean();
        }
        if (grapeVariety == null) {
            grapeVariety = new FilterBean();
        }
        if (taste == null) {
            taste = new FilterBean();
        }

        texture = taste.getId();
        classId = shopFilterBean.getClassId();
        advise = cateringAdvise.getClassId();
        brandId = brandType.getId();
        origin = producing.getId();
        grape = grapeVariety.getId();
        //重新调用请求商品列表的方法来请求数据  按照固定的格式来显示
//        APIManager.getInstance().shopList(this, "", brandId, classId, advise, origin, grape, priceBegin, priceEnd, sort, years, pageUtils.getPageIndex(), pageUtils.getPageSize(), texture);
        APIManager.getInstance().locashopList(this, "", brandId, classId, advise, origin, grape, priceBegin, priceEnd, sort, years, pageUtils.getPageIndex(), pageUtils.getPageSize(), texture);
    }

    /**
     * 描述      : 重置
     * 方法名    :  submit
     * param    : 无
     * 返回类型  :void
     * 创建人    : ghy
     * 创建时间  : 2017/3/15 10:28
     * 修改人    :
     * 修改时间
     * 修改备注
     * throws
     */
    public void reset() {
        shopFilterBean = null;
        cateringAdvise = null;
        brandType = null;
        producing = null;
        grapeVariety = null;
        taste = null;
        priceBegin = null;
        priceEnd = null;
        years = null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        shopListFilterFragment = null;
        filterTypesFragment = null;
        subFilterTypesFragment = null;
        shopDetailsFragment = null;
    }


    /**
     * 添加动画层
     *
     * @return
     */
    private ViewGroup createAnimLayout() {
        ViewGroup rootView = (ViewGroup) this.getWindow().getDecorView();
        LinearLayout animLayout = new LinearLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        animLayout.setLayoutParams(lp);
        animLayout.setId(Integer.MAX_VALUE - 1);
        animLayout.setBackgroundResource(android.R.color.transparent);
        rootView.addView(animLayout);
        return animLayout;
    }

    /**
     * 将View添加动画图层
     *
     * @param parent
     * @param view
     * @param location
     * @return
     */
    private View addViewToAnimLayout(final ViewGroup parent, final View view, int[] location) {
        int x = location[0];
        int y = location[1];
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = x;
        lp.topMargin = y;
        view.setLayoutParams(lp);
        return view;
    }

    /**
     * 开始动画
     * 点击加入购物车显示的动画效果
     *
     * @param v
     * @param startLocation
     */
    public void setAnim(final View v, int[] startLocation) {
        anim_mask_layout = null;
        anim_mask_layout = createAnimLayout();
        anim_mask_layout.addView(v);//把动画小球添加到动画层
        final View view = addViewToAnimLayout(anim_mask_layout, v, startLocation);
        int[] endLocation = new int[2];// 存储动画结束位置的X、Y坐标
        tvShopNumber.getLocationInWindow(endLocation);//在窗口中计算视图的坐标。这个参数必须是两个整数的数组。方法返回后，数组包含该顺序中的x和y位置

        L.i("endLocation：" + endLocation[0] + "  " + endLocation[1]);


        // 计算位移
        int endX = endLocation[0] - startLocation[0];// 动画位移的X坐标
        int endY = endLocation[1] - startLocation[1];// 动画位移的y坐标

        TranslateAnimation translateAnimationX = new TranslateAnimation(0, endX, 0, 0);
        translateAnimationX.setInterpolator(new LinearInterpolator());

        TranslateAnimation translateAnimationY = new TranslateAnimation(0, 0, 0, endY);
        translateAnimationY.setInterpolator(new AccelerateInterpolator());

        AnimationSet set = new AnimationSet(false);
        set.setFillAfter(false);
        set.addAnimation(translateAnimationY);
        set.addAnimation(translateAnimationX);
        set.setDuration(800);// 动画的执行时间
        view.startAnimation(set);
        // 动画监听事件
        set.setAnimationListener(new Animation.AnimationListener() {
            // 动画的开始
            @Override
            public void onAnimationStart(Animation animation) {
                v.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            // 动画的结束
            @Override
            public void onAnimationEnd(Animation animation) {
                v.setVisibility(View.GONE);
                setPrice();
            }
        });
    }

    private void setPrice() {
        int size = BaseData.getBaseData().getCardNums();
        if (size == 0) {
            tvShopNumber.setVisibility(View.INVISIBLE);
        } else if (size > 99) {
            tvShopNumber.setVisibility(View.VISIBLE);
            tvShopNumber.setText("99");
        } else {
            tvShopNumber.setVisibility(View.VISIBLE);
            tvShopNumber.setText(size + "");
        }
        if (shopDetailsFragment != null) {
            shopDetailsFragment.setCard();
        }
    }

    /**
     * 这是监听当前的item的点击事件的回调
     *
     * @param v             当前控件  根据控件显示回调动画
     * @param startLocation 数组定义起点   终点
     * @param stockBean     购物车对象
     * @param shopNum
     */
    @Override
    public void addToCard(View v, int[] startLocation, StockBean stockBean, int shopNum) {
        if (stockBean == null) return;

        int stock = Integer.parseInt(stockBean.getProductStock());
        if (stock == 0) {
            T.showToCenter(getString(R.string.common_inventory));
            return;
        }
        int repertory = Integer.parseInt(stockBean.getProductStock());
        int purchaseNum = BaseData.getBaseData().queryShopNum(stockBean.getId());
        if (purchaseNum >= repertory) {
            T.showToCenter(getString(R.string.common_inventory));
            return;
        }
        if (stockBean != null) {
            BaseData.getBaseData().addShop(stockBean, shopNum);
        }
        L.i(BaseData.getBaseData().getCardShops());
        L.i(BaseData.getBaseData().getCardNums());

        setAnim(v, startLocation);
    }


    @Override
    public void onError(String error, int flag) {
        switch (flag) {
            case FLags.FLAG_LOCAL_SHOP_LIST:
                if (pageUtils.isCurentLoadMode()) {
                    rvView.loadMoreComplete();
                } else {
                    rvView.refreshComplete();
                }
                break;
        }
        super.onError(error, flag);
    }

    @Override
    public void onRefresh() {
        pageUtils.setCurentLoadMode(false);
        submit();
    }

    @Override
    public void onLoadMore() {
        pageUtils.setCurentLoadMode(true);
        submit();
    }
}