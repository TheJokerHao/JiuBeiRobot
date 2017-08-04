package com.fecmobile.jiubeirobot.ui.fragment.shop;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseApplication;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.base.BaseFragment;
import com.fecmobile.jiubeirobot.bean.StockBean;
import com.fecmobile.jiubeirobot.common.LinearItemDecoration;
import com.fecmobile.jiubeirobot.ui.activity.shop.LocalUserCheckOrderActivity;
import com.fecmobile.jiubeirobot.ui.activity.shop.UserCheckOrderActivity;
import com.fecmobile.jiubeirobot.ui.adapter.shop.ShopManifestListAdapter;
import com.fecmobile.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类描述    : 商品清单
 * 包名      : com.fecmobile.jiubeirobot.ui.fragment.shop
 * 类名称    : ShopManifestFragment
 * 创建人    : ghy
 * 创建时间  : 2017/3/3 9:39
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class ShopManifestFragment extends BaseFragment {
    @BindView(R.id.rv_shop_list)
    RecyclerView rvShopList;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    private ShopManifestListAdapter shopManifestListAdapter;
    private List<StockBean> list = new ArrayList<>();

    public void setList(List<StockBean> list) {
        this.list.clear();
        if (list != null) {
            this.list.addAll(list);
        }
        if (shopManifestListAdapter != null) {
            shopManifestListAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public int pageLayout() {
        return R.layout.fragment_shop_manifest;
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {
        rvShopList.addItemDecoration(new LinearItemDecoration(1));
        rvShopList.setLayoutManager(new LinearLayoutManager(getContext()));
        shopManifestListAdapter = new ShopManifestListAdapter(getContext(), list);
        rvShopList.setAdapter(shopManifestListAdapter);
    }

    @Override
    public void onSuccess(BaseBean bean, int flag) {

    }

    @OnClick({R.id.iv_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                if (getActivity() != null && BaseApplication.currentMode != BaseApplication.LOCAL_BUY) {
                    ((UserCheckOrderActivity) getActivity()).close();
                } else {
                    ((LocalUserCheckOrderActivity) getActivity()).close();
                }
                break;
        }
    }
}
