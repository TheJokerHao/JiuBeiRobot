package com.fecmobile.jiubeirobot.ui.activity.cellar.manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseActivity;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.common.Constants;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * 类描述    : 订单搜索
 * 包名      : com.fecmobile.jiubeirobot.ui.activity.cellar.manager
 * 类名称    : OrderSearchActivity
 * 创建人    : lc
 * 创建时间  :  2017-03-14
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class OrderSearchActivity extends BaseActivity {

    @BindView(R.id.llyt_back)
    LinearLayout llytBack;
    @BindView(R.id.et_search_input)
    EditText etSearchInput;
    @BindView(R.id.tv_shop_number)
    TextView tvShopNumber;
    @BindView(R.id.rl_card)
    RelativeLayout rlCard;

    @Override
    public int pageLayout() {
        return R.layout.activity_order_search;
    }

    @Override
    public void initTitle() {
        etSearchInput.setHint(R.string.order_search_hint);
        rlCard.setVisibility(View.GONE);
    }

    private String getSearchContent() {
        return getIntent().getStringExtra(Constants.ORDER_LIST_SEARCH);
    }

    @Override
    public void initView() {
        if (getSearchContent() != null) {
            etSearchInput.setText(getSearchContent());
            etSearchInput.setSelection(getSearchContent().length());
        }

        etSearchInput.setOnEditorActionListener(new EditText.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //监听按下和搜索键事件
                    String searchContent = etSearchInput.getText().toString().trim();
                    Intent intent = new Intent();
                    intent.putExtra(Constants.ORDER_LIST_SEARCH, searchContent);
                    setResult(RESULT_OK, intent);
                    finish();
                }
                return true;
            }
        });
    }

    @Override
    public void onSuccess(BaseBean bean, int flag) {

    }


    @OnClick(R.id.llyt_back)
    public void onClick() {
        finish();
    }
}
