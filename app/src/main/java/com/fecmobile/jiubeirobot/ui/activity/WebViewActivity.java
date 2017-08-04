package com.fecmobile.jiubeirobot.ui.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseActivity;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.common.Constants;
import com.fecmobile.jiubeirobot.common.MyWebView;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类描述    :网页
 * 包名      : com.fecmobile.jiubeirobot.ui.activity
 * 类名称    : WebViewActivity
 * 创建人    : ghy
 * 创建时间  : 2017/3/13 21:41
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class WebViewActivity extends BaseActivity {
    @BindView(R.id.tv_back_txt)
    TextView tvBackTxt;
    @BindView(R.id.llyt_back)
    LinearLayout llytBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_search_icon)
    ImageView ivSearchIcon;
    @BindView(R.id.mwv_content)
    MyWebView mwvContent;

    @Override
    public int pageLayout() {
        return R.layout.activity_web_view;
    }

    @Override
    public void initTitle() {
        ivSearchIcon.setVisibility(View.INVISIBLE);
        tvTitle.setText(getIntent().getStringExtra(Constants.INTENT_WEBVIEW_TITLE));
        getIntent().getStringExtra(Constants.INTENT_WEBVIEW_HTML_CONTENT);
    }

    @Override
    public void initView() {
        String content = getIntent().getStringExtra(Constants.INTENT_WEBVIEW_HTML_CONTENT);
        mwvContent.setHtml(content);
    }

    @Override
    public void onSuccess(final BaseBean bean, int flag) {

    }

    @OnClick({R.id.tv_back_txt, R.id.llyt_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llyt_back:
                finish();
                break;
        }
    }
}