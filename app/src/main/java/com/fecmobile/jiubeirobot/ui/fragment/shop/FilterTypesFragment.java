package com.fecmobile.jiubeirobot.ui.fragment.shop;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.base.BaseFragment;
import com.fecmobile.jiubeirobot.bean.DrinkingTexttureBean;
import com.fecmobile.jiubeirobot.bean.IShopFilterInteface;
import com.fecmobile.jiubeirobot.bean.ListBean;
import com.fecmobile.jiubeirobot.bean.ObjBean;
import com.fecmobile.jiubeirobot.bean.ShopFilterBean;
import com.fecmobile.jiubeirobot.common.Constants;
import com.fecmobile.jiubeirobot.common.LinearItemDecoration;
import com.fecmobile.jiubeirobot.data.net.APIManager;
import com.fecmobile.jiubeirobot.data.net.FLags;
import com.fecmobile.jiubeirobot.listener.ItemClick;
import com.fecmobile.jiubeirobot.ui.activity.shop.LocalBuyActivity;
import com.fecmobile.jiubeirobot.ui.activity.shop.ShopListActivity;
import com.fecmobile.jiubeirobot.ui.adapter.shop.ShopFilterAllTypeAdapter;
import com.fecmobile.jiubeirobot.utils.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类描述    :筛选分类
 * 包名      : com.fecmobile.jiubeirobot.ui.fragment.shop
 * 类名称    : FilterTypesFragment
 * 创建人    : ghy
 * 创建时间  : 2017/2/23 14:17
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class FilterTypesFragment extends BaseFragment implements ItemClick {
    @BindView(R.id.rv_all_type_list)
    RecyclerView rvAllTypeList;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private ShopFilterAllTypeAdapter shopFilterAllTypeAdapter;
    private List<IShopFilterInteface> lists = new ArrayList<>();
    private ShopFilterBean shopFilterBean;
    private String type;

    public static final String ALL_TYPE = "ALL_TYPE";//全部分类
    public static final String SUGGESTING_TYPE = "SUGGESTING_TYPE";//配餐建议
    public static final String SECOND_LEVEL = "SECOND_LEVEL";//二级分类

    @Override
    public int pageLayout() {
        return R.layout.fragment_filter_types;
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void initView() {
        shopFilterAllTypeAdapter = new ShopFilterAllTypeAdapter(getContext(), lists);
        shopFilterBean = (ShopFilterBean) getArguments().getSerializable(Constants.INTENT_SHOP_FILTER_BEAN);
        type = getArguments().getString(Constants.INTENT_FILTER_TYPE, FilterTypesFragment.ALL_TYPE);

        rvAllTypeList.addItemDecoration(new LinearItemDecoration(1));

        if (FilterTypesFragment.ALL_TYPE.equals(type)) {
            shopFilterAllTypeAdapter.setClassLevel(-1);
        } else {
            shopFilterAllTypeAdapter.setClassLevel(1);
        }

        rvAllTypeList.setLayoutManager(new LinearLayoutManager(getContext()));
        rvAllTypeList.setAdapter(shopFilterAllTypeAdapter);
        shopFilterAllTypeAdapter.setItemClick(this);

        if (FilterTypesFragment.ALL_TYPE.equals(type)) {
            APIManager.getInstance().getShopClass(getContext(), this, "-1");
        } else if (FilterTypesFragment.SECOND_LEVEL.equals(type)) {
            APIManager.getInstance().getShopClass(getContext(), this, shopFilterBean.getClassId());
        } else if (FilterTypesFragment.SUGGESTING_TYPE.equals(type)) {
            APIManager.getInstance().getTasteAndSuggest(getContext(), this, 1);
        }

    }

    @Override
    public void onSuccess(BaseBean bean, int flag) {
        switch (flag) {
            case FLags.FLAG_SHOP_CLASS:
                BaseBean<ListBean<ShopFilterBean>> beanBaseBean = bean;
                lists.clear();
                lists.addAll(beanBaseBean.getData().getList());
                shopFilterAllTypeAdapter.notifyDataSetChanged();
                if (FilterTypesFragment.ALL_TYPE.equals(type)) {
                    tvTitle.setText(getString(R.string.shopping_list_flilter_all_type));
                } else if (shopFilterBean != null) {
                    tvTitle.setText(shopFilterBean.getClassName());
                }
                break;
            case FLags.FLAG_TASTE_AND_SUGGEST:
                tvTitle.setText(getString(R.string.shopping_list_flilter_suggest));
                BaseBean<ObjBean<DrinkingTexttureBean>> dbean = bean;
                lists.clear();
                lists.addAll(dbean.getData().getObj().getDrinking_cateringAdvise());
                shopFilterAllTypeAdapter.notifyDataSetChanged();
                break;
        }
        dismissHUD();
    }


    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                if (getActivity() != null && getActivity() instanceof ShopListActivity) {
                    ((ShopListActivity) getActivity()).backStack();
                }
                if (getActivity() != null && getActivity() instanceof LocalBuyActivity) {
                    ((LocalBuyActivity) getActivity()).backStack();
                }

                break;
        }
    }

    @Override
    public void onItemClick(int postion) {
        L.i("onItemClick");
        //TODO  修改的筛选里面的activity的判定
        if (getActivity() != null && getActivity() instanceof ShopListActivity) {
            L.i(getActivity().getLocalClassName());
            if (FilterTypesFragment.ALL_TYPE.equals(type)) {
                ((ShopListActivity) getActivity()).setShopFilterBean(new ShopFilterBean(lists.get(postion).getClassId(), lists.get(postion).getClassName()));
                ((ShopListActivity) getActivity()).addSubFilterTypesFragment();
            } else if (FilterTypesFragment.SECOND_LEVEL.equals(type)) {
                ((ShopListActivity) getActivity()).onAllSelect(new ShopFilterBean(lists.get(postion).getClassId(), lists.get(postion).getClassName()));
                ((ShopListActivity) getActivity()).backStack();
                ((ShopListActivity) getActivity()).backStack();
            } else if (FilterTypesFragment.SUGGESTING_TYPE.equals(type)) {
                L.i(lists.get(postion).getClassName());
                ((ShopListActivity) getActivity()).setCateringAdvise(new DrinkingTexttureBean.DrinkingTextture(lists.get(postion).getClassName(), lists.get(postion).getClassId()));
                ((ShopListActivity) getActivity()).backStack();
            }
        }

        if (getActivity() != null && getActivity() instanceof LocalBuyActivity) {
            L.i(getActivity().getLocalClassName());
            if (FilterTypesFragment.ALL_TYPE.equals(type)) {
                ((LocalBuyActivity) getActivity()).setShopFilterBean(new ShopFilterBean(lists.get(postion).getClassId(), lists.get(postion).getClassName()));
                ((LocalBuyActivity) getActivity()).addSubFilterTypesFragment();
            } else if (FilterTypesFragment.SECOND_LEVEL.equals(type)) {
                ((LocalBuyActivity) getActivity()).onAllSelect(new ShopFilterBean(lists.get(postion).getClassId(), lists.get(postion).getClassName()));
                ((LocalBuyActivity) getActivity()).backStack();
                ((LocalBuyActivity) getActivity()).backStack();
            } else if (FilterTypesFragment.SUGGESTING_TYPE.equals(type)) {
                L.i(lists.get(postion).getClassName());
                ((LocalBuyActivity) getActivity()).setCateringAdvise(new DrinkingTexttureBean.DrinkingTextture(lists.get(postion).getClassName(), lists.get(postion).getClassId()));
                ((LocalBuyActivity) getActivity()).backStack();
            }
        }
    }
}

