package com.fecmobile.jiubeirobot.ui.activity.cellar.walk;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseActivity;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.bean.CellarInfoBean;
import com.fecmobile.jiubeirobot.bean.ObjBean;
import com.fecmobile.jiubeirobot.data.net.APIManager;
import com.fecmobile.jiubeirobot.ui.fragment.cellar.CellarIndoorFragment;
import com.fecmobile.jiubeirobot.ui.fragment.cellar.CellarVideoDesFragment;
import com.fecmobile.jiubeirobot.ui.fragment.cellar.WebViewFragment;
import com.fecmobile.jiubeirobot.utils.L;

import butterknife.BindView;
import butterknife.OnClick;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;


/**
 * 类描述    : 走进酒窖
 * 包名      : com.fecmobile.jiubeirobot.ui.activity.cellar.walk
 * 类名称    : WalkIntoCellarActivity
 * 创建人    : ghy
 * 创建时间  : 2017/3/1 14:53
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class WalkIntoCellarActivity extends BaseActivity {
    @BindView(R.id.llyt_back)
    LinearLayout llytBack;
    @BindView(R.id.iv_search_icon)
    ImageView ivSearchIcon;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.rl_graphic_details)
    RelativeLayout rlGraphicDetails;
    @BindView(R.id.rl_video_des)
    RelativeLayout rlVideoDes;
    @BindView(R.id.rl_cellar_interior)
    RelativeLayout rlCellarInterior;

    @BindView(R.id.rb_graphic_details)
    RadioButton tvGraphicDetails;
    @BindView(R.id.rb_cellar_video_des)
    RadioButton tvCellarVideoDes;
    @BindView(R.id.rb_cellar_interior)
    RadioButton tvCellarInterior;

    @BindView(R.id.v_graphic_details)
    View vGraphicDetails;
    @BindView(R.id.v_cellar_video_des)
    View vCellarVideoDes;
    @BindView(R.id.v_cellar_interior)
    View vCellarInterior;
    private WebViewFragment webViewFragment;
    private CellarVideoDesFragment cellarVideoDesFragment;
    private CellarIndoorFragment cellarIndoorFragment;
    private BaseBean<ObjBean<CellarInfoBean>> beanBaseBean;

    public boolean isPlay = false;//判断是否在读详情  在读为TRUE
    @BindView(R.id.iv_play_detail)
    ImageView ivplay;

    public BaseBean<ObjBean<CellarInfoBean>> getBeanBaseBean() {
        return beanBaseBean;
    }

    @Override
    public int pageLayout() {
        return R.layout.activity_walk_into_cellar;
    }

    @Override
    public void initTitle() {
        ivSearchIcon.setVisibility(View.GONE);
        tvTitle.setText(getString(R.string.cellar_wallk_into));
    }

    @Override
    public void initView() {
        APIManager.getInstance().getCellarInfo(this);
    }

    @Override
    public void onSuccess(final BaseBean bean, int flag) {
        beanBaseBean = bean;
        changeTab(0);
        dismissHUD();
    }

    @Override
    public void onBackPressed() {
        if (JCVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }

    @OnClick({R.id.rl_graphic_details, R.id.rl_video_des, R.id.rl_cellar_interior, R.id.llyt_back, R.id.iv_play_detail})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_graphic_details:
                changeTab(0);
                break;
            case R.id.rl_video_des:
                changeTab(1);
                break;
            case R.id.rl_cellar_interior:
                changeTab(2);
                break;
            case R.id.llyt_back:
                onBackPressed();
                break;
            case R.id.iv_play_detail:
                Log.d("print", "onClick: " + isPlay);
                if (!isPlay) {
                    webViewFragment.speakDetails(webViewFragment.getCellarDesc());
                    isPlay = true;
                } else {
                    webViewFragment.mtsPapuse();
                    isPlay = false;
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        //super.onSaveInstanceState(outState, outPersistentState);
    }

    private void changeTab(int index) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (index == 0) {
            tvGraphicDetails.setChecked(true);
            tvCellarInterior.setChecked(false);
            tvCellarVideoDes.setChecked(false);
            vGraphicDetails.setVisibility(View.VISIBLE);
            vCellarVideoDes.setVisibility(View.INVISIBLE);
            vCellarInterior.setVisibility(View.INVISIBLE);

            if (webViewFragment == null) {
                webViewFragment = new WebViewFragment();
                transaction.add(R.id.rl_content, webViewFragment);
            } else {
                transaction.show(webViewFragment);
            }

            if (cellarVideoDesFragment != null) {
                cellarVideoDesFragment.onPause();
                transaction.hide(cellarVideoDesFragment);
            }

            if (cellarIndoorFragment != null) {
                transaction.hide(cellarIndoorFragment);
            }
            transaction.commitAllowingStateLoss();

        } else if (index == 1) {
            L.i("index：" + index);
            tvGraphicDetails.setChecked(false);
            tvCellarVideoDes.setChecked(true);
            tvCellarInterior.setChecked(false);
            vGraphicDetails.setVisibility(View.INVISIBLE);
            vCellarVideoDes.setVisibility(View.VISIBLE);
            vCellarInterior.setVisibility(View.INVISIBLE);

            if (cellarVideoDesFragment == null) {
                cellarVideoDesFragment = new CellarVideoDesFragment();
                transaction.add(R.id.rl_content, cellarVideoDesFragment);
            } else {
                transaction.show(cellarVideoDesFragment);
            }
            if (webViewFragment != null) {
                transaction.hide(webViewFragment);
            }
            if (cellarIndoorFragment != null) {
                transaction.hide(cellarIndoorFragment);
            }
            transaction.commitAllowingStateLoss();

        } else if (index == 2) {
            tvGraphicDetails.setChecked(false);
            tvCellarVideoDes.setChecked(false);
            tvCellarInterior.setChecked(true);
            vGraphicDetails.setVisibility(View.INVISIBLE);
            vCellarVideoDes.setVisibility(View.INVISIBLE);
            vCellarInterior.setVisibility(View.VISIBLE);

            if (cellarIndoorFragment == null) {
                cellarIndoorFragment = new CellarIndoorFragment();
                transaction.add(R.id.rl_content, cellarIndoorFragment);
            } else {
                transaction.show(cellarIndoorFragment);
            }
            if (webViewFragment != null) {
                transaction.hide(webViewFragment);
            }
            if (cellarVideoDesFragment != null) {
                cellarVideoDesFragment.onPause();
                transaction.hide(cellarVideoDesFragment);
            }
            transaction.commitAllowingStateLoss();
        }
    }
}


