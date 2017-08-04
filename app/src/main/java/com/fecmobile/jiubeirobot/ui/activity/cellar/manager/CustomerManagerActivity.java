package com.fecmobile.jiubeirobot.ui.activity.cellar.manager;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseActivity;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.ui.adapter.cellar.manage.CustomerManagerAdapter;
import com.fecmobile.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author TheJoker丶豪
 *         这是客户管理的页面   针对获取到的客户信息来显示在这个系统里面购买过商品的客户信息
 * @date 创建时间:2017/6/15
 */
public class CustomerManagerActivity extends BaseActivity {

    /**
     * 顶部布局初始化
     */
    @BindView(R.id.llyt_back)
    LinearLayout llback;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_back_txt)
    TextView tvBack;
    @BindView(R.id.iv_search_icon)
    ImageView IvSearch;
    //    列表布局
    @BindView(R.id.rv_customer_manager)
    XRecyclerView rvlist;
    //    适配器
    CustomerManagerAdapter customerManagerAdapter;

    @Override
    public int pageLayout() {
        return R.layout.activity_customer_manager;
    }

    @Override
    public void initTitle() {
        IvSearch.setVisibility(View.GONE);
        tvTitle.setText(getString(R.string.title_customer_manager));
        tvBack.setVisibility(View.VISIBLE);

    }

    @Override
    public void initView() {
        //初始化适配器和适配数据
    }

    @Override
    public void onSuccess(BaseBean bean, int flag) {
        //数据判断
    }

    @OnClick({R.id.llyt_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llyt_back:
                finish();
                break;
        }
    }

}
