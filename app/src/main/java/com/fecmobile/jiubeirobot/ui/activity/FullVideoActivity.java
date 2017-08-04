package com.fecmobile.jiubeirobot.ui.activity;

import android.view.WindowManager;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseActivity;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.base.BaseData;
import com.fecmobile.jiubeirobot.common.Constants;
import com.fecmobile.jiubeirobot.common.view.MyVideoView;

import butterknife.BindView;

/**
 * 类描述    :全屏视频播放器
 * 包名      : com.fecmobile.jiubeirobot.ui.activity
 * 类名称    : FullVideoActivity
 * 创建人    : ghy
 * 创建时间  : 2017/3/16 19:43
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class FullVideoActivity extends BaseActivity {
    private String url;
    private int currentPostion;

    @BindView(R.id.myvv_video)
    MyVideoView myvvVideo;

    @Override
    public int pageLayout() {
        // 隐藏状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        return R.layout.activity_full_video;
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {
        url = getIntent().getStringExtra(Constants.INTENT_VIDEO＿URL);
        currentPostion = getIntent().getIntExtra(Constants.INTENT_VIDEO_POSTION, 0);
        myvvVideo.setAllowFull(false);
        myvvVideo.setOnFullClick(new MyVideoView.onFullClick() {
            @Override
            public void onClick() {
                finish();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        myvvVideo.setUrl(url);
        myvvVideo.setCurrentPostion(currentPostion);
        myvvVideo.startVideo();
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        BaseData.videoIntroduce = myvvVideo.getCurrentPostion();
//        myvvVideo.onDestroy();
//    }

    @Override
    public void onSuccess(final BaseBean bean, int flag) {

    }

}


