package com.fecmobile.jiubeirobot.ui.activity.shop;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseActivity;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.base.BaseData;
import com.fecmobile.jiubeirobot.bean.ListBean;
import com.fecmobile.jiubeirobot.bean.ShopListBean;
import com.fecmobile.jiubeirobot.common.Constants;
import com.fecmobile.jiubeirobot.data.net.APIManager;
import com.fecmobile.jiubeirobot.data.net.FLags;
import com.fecmobile.jiubeirobot.listener.ItemClick;
import com.fecmobile.jiubeirobot.ui.adapter.shop.SearchRecodeAdapter;
import com.fecmobile.jiubeirobot.ui.dialog.AlertDialog;
import com.fecmobile.jiubeirobot.ui.dialog.ConfirmDropPointDialog;
import com.fecmobile.jiubeirobot.utils.L;
import com.fecmobile.jiubeirobot.utils.SPUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 类描述    :搜索框
 * 包名      : com.fecmobile.jiubeirobot.ui.activity.shop
 * 类名称    : SearchShopActivity
 * 创建人    : ghy
 * 创建时间  : 2017/3/4 15:11
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class SearchShopActivity extends BaseActivity {
    @BindView(R.id.rl_card)
    RelativeLayout rlCard;
    @BindView(R.id.llyt_back)
    LinearLayout llytBack;
    @BindView(R.id.rv_hot_list)
    RecyclerView rvHotList;
    @BindView(R.id.rv_recent_search_list)
    RecyclerView rvRecentSearchList;
    @BindView(R.id.et_search_input)
    EditText etSearchInput;
    @BindView(R.id.llyt_point)
    LinearLayout llytPoint;
    @BindView(R.id.llyt_history)
    LinearLayout llytHistory;
    @BindView(R.id.iv_clear_search_recode)
    ImageView ivClearSearchRecode;
    private SearchRecodeAdapter hotListAdapter;
    private SearchRecodeAdapter recentSearchAdapter;
    private ConfirmDropPointDialog confirmDropPointDialog = new ConfirmDropPointDialog();
    private List<String> histroys = new ArrayList<>();
    private List<String> hotlist = new ArrayList<>();
    private Gson gson = new Gson();
    private int pageSize = 8;

    @Override
    public int pageLayout() {
        return R.layout.activity_search_shop;
    }

    @Override
    public void initTitle() {
        rlCard.setVisibility(View.GONE);
    }

    @Override
    public void initView() {
        rvHotList.setLayoutManager(new GridLayoutManager(this, 2));
        rvRecentSearchList.setLayoutManager(new LinearLayoutManager(this));
        String hotStr = BaseData.getBaseData().getProtocolsInfoBean().getSite_search_hot_words();
        if (hotStr != null) {
            String[] hotWords = hotStr.split("，");
            if (hotStr != null) {
                hotlist.addAll(Arrays.asList(hotWords));
            }
        }

        hotListAdapter = new SearchRecodeAdapter(this, hotlist);
        recentSearchAdapter = new SearchRecodeAdapter(this, histroys);
        rvHotList.setAdapter(hotListAdapter);
        rvRecentSearchList.setAdapter(recentSearchAdapter);

        etSearchInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    APIManager.getInstance().shopList(SearchShopActivity.this, etSearchInput.getText() + "", "", "", "", "", "", "", "", "0", "", 0, pageSize, "");
                    histroys.add(0, etSearchInput.getText() + "");
                    while (histroys.size() > 3) {
                        histroys.remove(histroys.size() - 1);
                    }
                    L.i("--------------" + histroys.size());
                    recentSearchAdapter.notifyDataSetChanged();
                    return true;
                }
                return false;
            }
        });

        confirmDropPointDialog.setClickLisener(new ConfirmDropPointDialog.ClickLisener() {
            @Override
            public void onLeftClick(AlertDialog dialog) {

            }

            @Override
            public void onRightClick(AlertDialog dialog) {
                SPUtil.put(SearchShopActivity.this, Constants.SEARCH_HISTORY_RECORD, "");
                histroys.clear();
                recentSearchAdapter.notifyDataSetChanged();
            }
        });

        final String histroy = SPUtil.get(this, Constants.SEARCH_HISTORY_RECORD, "") + "";
        L.i(histroy);
        Type type = new TypeToken<List<String>>() {
        }.getType();
        List<String> strs = gson.fromJson(histroy, type);

        L.i("////////////////////////////////" + strs);

        if (strs != null) {
            histroys.addAll(strs);
            recentSearchAdapter.notifyDataSetChanged();
        }
        hotListAdapter.setItemClick(new ItemClick() {
            @Override
            public void onItemClick(int postion) {
                etSearchInput.setText(hotlist.get(postion));
                APIManager.getInstance().shopList(SearchShopActivity.this, hotlist.get(postion) + "", "", "", "", "", "", "", "", "0", "", 0, pageSize, "");
            }
        });
        recentSearchAdapter.setItemClick(new ItemClick() {
            @Override
            public void onItemClick(int postion) {
                etSearchInput.setText(histroys.get(postion));
                APIManager.getInstance().shopList(SearchShopActivity.this, histroys.get(postion) + "", "", "", "", "", "", "", "", "0", "", 0, pageSize, "");
            }
        });
    }

    @Override
    public void onSuccess(final BaseBean bean, int flag) {
        switch (flag) {
            case FLags.FLAG_SHOP_LIST:
                BaseBean<ListBean<ShopListBean>> listBeanBaseBean = bean;
                int rowCount = listBeanBaseBean.getData().getRowCount();
                if (rowCount == 0) {
                    llytPoint.setVisibility(View.VISIBLE);
                    llytHistory.setVisibility(View.GONE);
                } else {
                    Intent intent = new Intent();
                    intent.putExtra(Constants.INTENT_SHOP_SEARCH_RESULT, gson.toJson(listBeanBaseBean.getData().getList()));
                    setResult(Activity.RESULT_OK, intent);
                    finish();
                }
                break;
        }
        dismissHUD();
    }


    @OnClick({R.id.llyt_back, R.id.iv_clear_search_recode})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llyt_back:
                finish();
                break;
            case R.id.iv_clear_search_recode:
                confirmDropPointDialog.show(this, getString(R.string.shopping_list_delete_search_recode), getString(R.string.common_comfirm), getString(R.string.common_cancel));
                break;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        SPUtil.put(this, Constants.SEARCH_HISTORY_RECORD, gson.toJson(histroys));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        confirmDropPointDialog.dismiss();
    }
}


