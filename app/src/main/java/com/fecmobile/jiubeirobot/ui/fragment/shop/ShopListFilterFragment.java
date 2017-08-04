package com.fecmobile.jiubeirobot.ui.fragment.shop;

import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseApplication;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.base.BaseFragment;
import com.fecmobile.jiubeirobot.bean.DrinkingTexttureBean;
import com.fecmobile.jiubeirobot.bean.FilterBean;
import com.fecmobile.jiubeirobot.bean.ListBean;
import com.fecmobile.jiubeirobot.bean.ObjBean;
import com.fecmobile.jiubeirobot.bean.ShopFilterBean;
import com.fecmobile.jiubeirobot.common.view.ConstionView;
import com.fecmobile.jiubeirobot.common.view.FilterItemView;
import com.fecmobile.jiubeirobot.data.net.APIManager;
import com.fecmobile.jiubeirobot.data.net.FLags;
import com.fecmobile.jiubeirobot.ui.activity.shop.LocalBuyActivity;
import com.fecmobile.jiubeirobot.ui.activity.shop.ShopListActivity;
import com.fecmobile.jiubeirobot.utils.L;
import com.fecmobile.jiubeirobot.utils.T;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类描述    :会员商城列表筛选（一级分类）
 * 包名      : com.fecmobile.jiubeirobot.ui.fragment
 * 类名称    : ShopListFilterFragment
 * 创建人    : ghy
 * 创建时间  : 2017/2/22 14:56
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class ShopListFilterFragment extends BaseFragment {
    @BindView(R.id.rl_all_type)
    RelativeLayout rlAllType;
    @BindView(R.id.rl_flilter_suggest)
    RelativeLayout rlFlilterSuggest;
    @BindView(R.id.tv_all_class_selete)
    TextView tvAllClassSelete;
    @BindView(R.id.btn_reset)
    Button btnReset;
    @BindView(R.id.btn_submit)
    Button btnSubmit;
    @BindView(R.id.fiv_grapevine)
    FilterItemView fivGrapevine;
    @BindView(R.id.fiv_grape_variety)
    FilterItemView fivGrapeVariety;
    @BindView(R.id.fiv_brand)
    FilterItemView fivBrand;
    @BindView(R.id.fiv_flavor)
    FilterItemView fivFlavor;
    @BindView(R.id.tv_suggest)
    TextView tvSuggest;
    @BindView(R.id.cv_price_filter)
    ConstionView cvPriceFilter;
    @BindView(R.id.cv_year_filter)
    ConstionView cvYearFilter;

    @Override
    public int pageLayout() {
        return R.layout.fragment_shop_list_filter;
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {
        APIManager.getInstance().getGrapevine(getContext(), this);
        APIManager.getInstance().getGrapeVariety(getContext(), this);
        APIManager.getInstance().getBrandType(getContext(), this);
        APIManager.getInstance().getTasteAndSuggest(getContext(), this, 0);

        cvPriceFilter.setType("0");
        cvYearFilter.setType("1");
    }

    @Override
    public void onSuccess(BaseBean bean, int flag) {
        BaseBean<ListBean<FilterBean>> gbean;
        switch (flag) {
            case FLags.FLAG_GRAPEVINE:
                gbean = bean;
                fivGrapevine.setList(gbean.getData().getList());
                fivGrapevine.setFilterItemClick(new FilterItemView.FilterItemClick() {
                    @Override
                    public void itemClick(FilterBean bean) {
                        if (getActivity() != null && getActivity() instanceof ShopListActivity) {
                            ((ShopListActivity) getActivity()).setProducing(bean);
                        }

                        if (getActivity() != null && getActivity() instanceof LocalBuyActivity) {
                            ((LocalBuyActivity) getActivity()).setProducing(bean);
                        }

                    }
                });
                break;
            case FLags.FLAG_GRAPE_VARIETY:
                gbean = bean;
                fivGrapeVariety.setList(gbean.getData().getList());
                fivGrapeVariety.setFilterItemClick(new FilterItemView.FilterItemClick() {
                    @Override
                    public void itemClick(FilterBean bean) {
                        if (getActivity() != null && getActivity() instanceof ShopListActivity) {
                            ((ShopListActivity) getActivity()).setGrapeVariety(bean);
                        }

                        if (getActivity() != null && getActivity() instanceof LocalBuyActivity) {
                            ((LocalBuyActivity) getActivity()).setGrapeVariety(bean);
                        }
                    }
                });
                break;
            case FLags.FLAG_BRAND_TYPE:
                gbean = bean;
                fivBrand.setList(gbean.getData().getList());
                fivBrand.setFilterItemClick(new FilterItemView.FilterItemClick() {
                    @Override
                    public void itemClick(FilterBean bean) {
                        if (getActivity() != null && getActivity() instanceof ShopListActivity) {
                            ((ShopListActivity) getActivity()).setBrandType(bean);
                        }

                        if (getActivity() != null && getActivity() instanceof LocalBuyActivity) {
                            ((LocalBuyActivity) getActivity()).setBrandType(bean);
                        }

                    }
                });
                break;
            case FLags.FLAG_TASTE_AND_SUGGEST:
                BaseBean<ObjBean<DrinkingTexttureBean>> dkTextBean = bean;
                fivFlavor.setList(dkTextBean.getData().getObj().getDrinking_textture());
                fivFlavor.setFilterItemClick(new FilterItemView.FilterItemClick() {
                    @Override
                    public void itemClick(FilterBean bean) {
                        //TODO  添加了一个判断  判断口感添加的时候的activity的类型
                        if (BaseApplication.currentMode != BaseApplication.LOCAL_BUY) {
                            ((ShopListActivity) getActivity()).setTaste(bean);
                        } else {
                            ((LocalBuyActivity) getActivity()).setTaste(bean);
                        }
                    }
                });
                break;
        }
        dismissHUD();
    }

    public void setOnSelect(ShopFilterBean filterBean) {
        tvAllClassSelete.setText(filterBean.getClassName());
    }

    public void setCateringAdvise(DrinkingTexttureBean.DrinkingTextture filterBean) {
        L.i(filterBean.getClassName());
        tvSuggest.setText(filterBean.getClassName());
    }


    private void reset() {
        tvAllClassSelete.setText(getString(R.string.common_all));
        tvSuggest.setText(getString(R.string.common_all));
        fivGrapevine.onReset();
        fivGrapeVariety.onReset();
        fivBrand.onReset();
        fivFlavor.onReset();
        cvPriceFilter.onReset();
        cvYearFilter.onReset();
    }

    @OnClick({R.id.btn_reset, R.id.btn_submit, R.id.rl_all_type, R.id.rl_flilter_suggest})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_reset:
                if (getActivity() != null && getActivity() instanceof ShopListActivity) {
                    reset();
                    ((ShopListActivity) getActivity()).reset();
                    //   ((ShopListActivity) getActivity()).closeFilter();
                }
                //TODO 添加了判断是什么activity
                if (getActivity() != null && getActivity() instanceof LocalBuyActivity) {
                    reset();
                    ((LocalBuyActivity) getActivity()).reset();
                    //   ((ShopListActivity) getActivity()).closeFilter();
                }


                break;
            case R.id.btn_submit:
                if (getActivity() != null && getActivity() instanceof ShopListActivity) {
                    ShopListActivity shopListAty = ((ShopListActivity) getActivity());
                    try {
                        Double startPrice = Double.parseDouble(cvPriceFilter.getStartPrice());
                        Double endPrice = Double.parseDouble(cvPriceFilter.getEndPrice());
                        if (startPrice > endPrice) {
                            T.showToCenter(getString(R.string.shopping_list_price_filter_point));
                            return;
                        }
                    } catch (Exception ex) {
                        L.i(ex);
                    }
                    shopListAty.setPriceBegin(cvPriceFilter.getStartPrice());
                    shopListAty.setPriceEnd(cvPriceFilter.getEndPrice());
                    shopListAty.setYears(cvYearFilter.getYear());
                    shopListAty.submit();
                    shopListAty.closeFilter();
                }
                //添加一个判断

                if (getActivity() != null && getActivity() instanceof LocalBuyActivity) {
                    LocalBuyActivity localBuyActivity = ((LocalBuyActivity) getActivity());
                    try {
                        Double startPrice = Double.parseDouble(cvPriceFilter.getStartPrice());
                        Double endPrice = Double.parseDouble(cvPriceFilter.getEndPrice());
                        if (startPrice > endPrice) {
                            T.showToCenter(getString(R.string.shopping_list_price_filter_point));
                            return;
                        }
                    } catch (Exception ex) {
                        L.i(ex);
                    }
                    localBuyActivity.setPriceBegin(cvPriceFilter.getStartPrice());
                    localBuyActivity.setPriceEnd(cvPriceFilter.getEndPrice());
                    localBuyActivity.setYears(cvYearFilter.getYear());
                    localBuyActivity.submit();
                    localBuyActivity.closeFilter();
                }
                break;
            case R.id.rl_all_type:
                if (getActivity() != null && getActivity() instanceof ShopListActivity) {
                    ((ShopListActivity) getActivity()).addFilterTypesFragment();
                }
                if (getActivity() != null && getActivity() instanceof LocalBuyActivity) {
                    ((LocalBuyActivity) getActivity()).addFilterTypesFragment();
                }

                break;
            case R.id.rl_flilter_suggest:
                //TODO  这个fragment里面添加的判断全部是基于两个activity来考虑的  需要改的时候很方便
                if (getActivity() != null && getActivity() instanceof ShopListActivity) {
                    ((ShopListActivity) getActivity()).addCateringAdviseFilterTypesFragment();
                } else if (getActivity() != null && getActivity() instanceof LocalBuyActivity) {
                    ((LocalBuyActivity) getActivity()).addCateringAdviseFilterTypesFragment();
                }
                break;
        }
    }

}
