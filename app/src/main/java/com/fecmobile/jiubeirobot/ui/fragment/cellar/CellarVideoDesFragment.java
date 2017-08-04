package com.fecmobile.jiubeirobot.ui.fragment.cellar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.base.BaseFragment;
import com.fecmobile.jiubeirobot.bean.CellarInfoBean;
import com.fecmobile.jiubeirobot.bean.ObjBean;
import com.fecmobile.jiubeirobot.common.Constants;

import com.fecmobile.jiubeirobot.common.view.MyVideoView;
import com.fecmobile.jiubeirobot.data.net.APIManager;
import com.fecmobile.jiubeirobot.ui.activity.cellar.walk.WalkIntoCellarActivity;
import com.fecmobile.jiubeirobot.utils.L;

import java.util.HashMap;

import butterknife.BindView;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard;

/**
 * 类描述    :酒窖视频介绍
 * 包名      : com.fecmobile.jiubeirobot.ui.fragment.cellar
 * 类名称    : CellarVideoDesFragment
 * 创建人    : ghy
 * 创建时间  : 2017/3/1 16:13
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class CellarVideoDesFragment extends BaseFragment {
    @BindView(R.id.jcVideo)
    JCVideoPlayerStandard jcVideo;
    @BindView(R.id.tv_title)
    TextView tvTitle;

    @Override
    public int pageLayout() {
        return R.layout.fragment_cellar_video_des;
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("print", "onPause: CellarVideoDesFragment");
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void initView() {

        WalkIntoCellarActivity walkIntoCellar = (WalkIntoCellarActivity) getActivity();
        CellarInfoBean cellarInfo = walkIntoCellar.getBeanBaseBean().getData().getObj();

        String url = cellarInfo.getVideoUrl();
        String title = cellarInfo.getCellerIntroduct();

        if (!TextUtils.isEmpty(url)) {
            tvTitle.setText(title + "");
            jcVideo.setUp(url, JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL);
            jcVideo.thumbImageView.setImageBitmap(createVideoThumbnail(url, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        }
        L.i("url：" + url);
        L.i("title：" + title);

    }

    private Bitmap createVideoThumbnail(String url, int width, int height) {
        Bitmap bitmap = null;
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        int kind = MediaStore.Video.Thumbnails.MINI_KIND;
        try {
            if (Build.VERSION.SDK_INT >= 14) {
                retriever.setDataSource(url, new HashMap<String, String>());
            } else {
                retriever.setDataSource(url);
            }
            bitmap = retriever.getFrameAtTime();
        } catch (IllegalArgumentException ex) {
        } catch (RuntimeException ex) {
        } finally {
            try {
                retriever.release();
            } catch (RuntimeException ex) {
            }
        }
        if (kind == MediaStore.Images.Thumbnails.MICRO_KIND && bitmap != null) {
            bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                    ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        }
        return bitmap;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onSuccess(BaseBean bean, int flag) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
