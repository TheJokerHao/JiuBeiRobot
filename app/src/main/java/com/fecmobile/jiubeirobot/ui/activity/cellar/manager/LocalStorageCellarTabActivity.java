package com.fecmobile.jiubeirobot.ui.activity.cellar.manager;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.GridLayoutManager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseActivity;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.bean.ListBean;
import com.fecmobile.jiubeirobot.bean.LocalStorageCellarTabDetailBean;
import com.fecmobile.jiubeirobot.bean.ObjBean;
import com.fecmobile.jiubeirobot.bean.StorageCellBean;
import com.fecmobile.jiubeirobot.bean.StorageCellarTabDetailBean;
import com.fecmobile.jiubeirobot.common.view.CommAdapter;
import com.fecmobile.jiubeirobot.data.net.APIManager;
import com.fecmobile.jiubeirobot.data.net.FLags;
import com.fecmobile.jiubeirobot.listener.ItemClick;
import com.fecmobile.jiubeirobot.ui.adapter.cellar.manage.StorageCellarAdapter;
import com.fecmobile.jiubeirobot.ui.fragment.cellar.manage.LocalStorageCellarDetailFragment;
import com.fecmobile.jiubeirobot.ui.fragment.cellar.manage.StorageCellarDetailFragment;
import com.fecmobile.jiubeirobot.ui.popupwindow.ListPopupWindow;
import com.fecmobile.jiubeirobot.utils.L;
import com.fecmobile.jiubeirobot.utils.PageUtils;
import com.fecmobile.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author TheJoker丶豪
 *         本地管理的酒窖库存管理
 * @date 创建时间:2017/6/1
 */
public class LocalStorageCellarTabActivity extends BaseActivity implements DrawerLayout.DrawerListener, ItemClick, XRecyclerView.LoadingListener {

    @BindView(R.id.llyt_back)
    LinearLayout mLlytBack;
    @BindView(R.id.llyt_search_type)
    LinearLayout mLlytSearchType;
    @BindView(R.id.et_search_input)
    EditText mEtSearchInput;
    @BindView(R.id.title_type_search_title)
    LinearLayout mTitleTypeSearchTitle;
    @BindView(R.id.recycler_cellar_message)
    XRecyclerView mRecyclerCellarMessage;
    @BindView(R.id.activity_storage_cellar_tab)
    DrawerLayout mActivityStorageCellarTab;
    @BindView(R.id.tv_search_type)
    TextView mTvSearchType;
    @BindView(R.id.fl_shop_details)
    FrameLayout mFlShopDetails;
    private List<StorageCellBean> mList;
    private StorageCellarAdapter mStorageCellarAdapter;
    //    private String[] cellarTab = {"全部酒品", "自有酒品", "代存酒品"};
    //本地管理应该是分为全部酒品和自有酒品   并没有代存酒品
    private String[] cellarTab = {"全部酒品", "自有酒品"};

    private PopupWindow mPopupWindow;
    private List<Map<String, Object>> mList_pop;
    private CommAdapter mAdapter;
    private ListView mLlyt_pop_cellar_storage;
    private TextView mTv_item_cellar_name;
    private LocalStorageCellarDetailFragment mStorageCellarDetailFragment;
    //0全部1自有2代存
    private String flag = "0";
    private String mSelectType;
    private BaseBean<ListBean<StorageCellBean>> mStoragecellbean;
    private StorageCellBean mStorageCellBean;
    private boolean isFrist;
    private LocalStorageCellarTabDetailBean mObj;
    private PageUtils pageUtils = new PageUtils();

    private ListPopupWindow listPopupWindow;
    private String searchkey;

    public String getFlag() {
        return flag;
    }

    @Override
    public int pageLayout() {
        return R.layout.activity_local_storage_cellar_tab;
    }

    @Override
    public void initTitle() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        mRecyclerCellarMessage.refresh();
    }

    @Override
    public void initView() {
        mRecyclerCellarMessage.setPullRefreshEnabled(true);
        mRecyclerCellarMessage.setLoadingMoreEnabled(false);
        mRecyclerCellarMessage.setLoadingListener(this);
        pageUtils.setPageSize(8);

        initIntent();
        mActivityStorageCellarTab.addDrawerListener(this);
        mActivityStorageCellarTab.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        initData();
        initPopWindow();
        mStorageCellarAdapter = new StorageCellarAdapter(this, mList, R.layout.item_storage_cellar_tab);
        mStorageCellarAdapter.setOnIntemClick(this);

        mRecyclerCellarMessage.setLayoutManager(new GridLayoutManager(this, 2));
        mRecyclerCellarMessage.setAdapter(mStorageCellarAdapter);
        initListener();
    }

    private void initIntent() {
        mSelectType = getIntent().getStringExtra("selectType");
        flag = mSelectType;
        if ("0".equals(flag)) {
            mTvSearchType.setText(cellarTab[0]);
        } else if ("1".equals(flag)) {
            mTvSearchType.setText(cellarTab[1]);
        }
    }

    private void initListener() {
        //监听edittext事件
        mEtSearchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE) {
                    pageUtils.setPageIndex(0);
                    setLoading();
                }
                return true;
            }
        });
    }

    private void initPopWindow() {

    }

    private void initData() {
        //popWindow的数据
        mList_pop = new ArrayList<>();
        for (int i = 0; i < cellarTab.length; i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("name", cellarTab[i]);
            map.put("checked", false);
            mList_pop.add(map);
        }
        mList = new ArrayList<StorageCellBean>();

    }

    @Override
    public void onSuccess(BaseBean bean, int flag) {
        switch (flag) {
            case FLags.FLAG_LOCAL_STORAGE_CELLAR:
                mStoragecellbean = bean;
                ListBean<StorageCellBean> data = mStoragecellbean.getData();
                initListData(data);
                break;
            case FLags.FLAG_LOCAL_STORAGE_CELLAR_TAB_DETAIL:
                BaseBean<ObjBean<LocalStorageCellarTabDetailBean>> beanDetail = bean;
                mObj = beanDetail.getData().getObj();
                mStorageCellarDetailFragment.setParms(mObj);
                break;
        }
        dismissHUD();
    }

    @Override
    public void onError(String error, int flag) {
        switch (flag) {
            case FLags.FLAG_STORAGE_CELLAR:
                if (pageUtils.isCurentLoadMode()) {
                    mRecyclerCellarMessage.loadMoreComplete();
                } else {
                    mRecyclerCellarMessage.refreshComplete();
                }
                break;
        }
        super.onError(error, flag);
    }

    private void initListData(ListBean<StorageCellBean> obj) {
        pageUtils.setTotalNum(obj.getRowCount());
        mRecyclerCellarMessage.setLoadingMoreEnabled(pageUtils.isMore());
        if (pageUtils.isCurentLoadMode()) {
            mRecyclerCellarMessage.loadMoreComplete();
        } else {
            mList.clear();
            mRecyclerCellarMessage.refreshComplete();
        }
        mList.addAll(obj.getList());
        mStorageCellarAdapter.notifyDataSetChanged();
    }


    @Override
    public void onItemClick(int postion) {
        addShopDetailsFragment(postion);
        mActivityStorageCellarTab.openDrawer(Gravity.RIGHT);
    }


    @OnClick({R.id.llyt_back, R.id.llyt_search_type})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llyt_back:
                this.finish();
                break;
            case R.id.llyt_search_type:
                initPopWindow(view);
                break;
        }
    }

    /**
     * 描述      :  酒品存储列表中的popWindow
     * 方法名    :  initPopWindow
     * param    :
     * 返回类型  :
     * 创建人    :wangxing
     * 创建时间  :
     * 修改人    :
     * 修改时间
     * 修改备注
     * throws
     */
    private void initPopWindow(View view) {

        if (listPopupWindow == null) {
            listPopupWindow = new ListPopupWindow(this, mList_pop);
            listPopupWindow.setItemClick(new ItemClick() {
                @Override
                public void onItemClick(int postion) {
                    mTvSearchType.setText(String.valueOf(mList_pop.get(postion).get("name")));
                    pageUtils.setPageIndex(0);
                    if (0 == postion) {
                        flag = "0";
                    } else if (1 == postion) {
                        flag = "1";
                    } else if (2 == postion) {
                        flag = "2";
                    }
                    setLoading();
                }
            });
        }
        listPopupWindow.show(view);

    }


    @Override
    public void onDrawerOpened(View drawerView) {

        mActivityStorageCellarTab.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    @Override
    public void onDrawerClosed(View drawerView) {
        mActivityStorageCellarTab.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    @Override
    public void onDrawerSlide(View drawerView, float slideOffset) {

    }

    @Override
    public void onDrawerStateChanged(int newState) {

    }

    private void addShopDetailsFragment(int position) {
        mStorageCellBean = mList.get(position);
        if (mStorageCellarDetailFragment == null) {
            mStorageCellarDetailFragment = new LocalStorageCellarDetailFragment();
            FragmentTransaction ftrans = getSupportFragmentManager().beginTransaction();
            ftrans.replace(R.id.fl_shop_details, mStorageCellarDetailFragment);
            ftrans.commit();
        }

        L.i("mStorageCellBean.getId()：" + mStorageCellBean.getId());

        mStorageCellarDetailFragment.setId(mStorageCellBean.getId());
        mStorageCellarDetailFragment.onResume();
    }

    @Override
    public void onRefresh() {
        pageUtils.setCurentLoadMode(false);
        setLoading();
    }

    @Override
    public void onLoadMore() {
        pageUtils.setCurentLoadMode(true);
        setLoading();
    }

    /**
     * 发送联网请求
     */
    private void setLoading() {
        searchkey = mEtSearchInput.getText().toString().trim();
        APIManager.getInstance().getLocalStorageCellarTab(this, flag, searchkey, pageUtils.getPageIndex(), pageUtils.getPageSize());
    }
}
