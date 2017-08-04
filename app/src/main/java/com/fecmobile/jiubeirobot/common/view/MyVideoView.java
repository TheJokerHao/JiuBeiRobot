package com.fecmobile.jiubeirobot.common.view;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.common.Activitys;
import com.fecmobile.jiubeirobot.utils.L;
import com.fecmobile.jiubeirobot.utils.T;

import java.util.HashMap;

import butterknife.BindView;

/**
 * 类描述    :自定义视频播放器
 * 包名      : com.fecmobile.jiubeirobot.common.view
 * 类名称    : MyVideoView
 * 创建人    : ghy
 * 创建时间  : 2017/3/9 20:47
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class MyVideoView extends LinearLayout implements SeekBar.OnSeekBarChangeListener {
    SeekBar skbProgress;
    TextView tvCurrentTime;
    TextView tvTotalTime;
    private VideoView vvVideo;
    private ProgressBar pbProgress;
    private ImageView ivPlay;
    private LinearLayout llytPlayer;
    private boolean destroy = true;
    private boolean pause = true;
    private TextView tvSuspendCurrentProgress;
    private ImageView ivFull, iv_thumbnai_bitmap;
    private boolean allowFull = true;
    private onFullClick onFullClick;
    private String url;
    private static int curPos;
    private Bitmap thumbnai_bitmap;

    public void setOnFullClick(MyVideoView.onFullClick onFullClick) {
        this.onFullClick = onFullClick;
    }

    public void setAllowFull(boolean allowFull) {
        this.allowFull = allowFull;
    }

    public MyVideoView(Context context) {
        this(context, null);
    }

    public MyVideoView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final View view = inflate(context, R.layout.view_video_view, this);
        vvVideo = (VideoView) view.findViewById(R.id.vv_video);
        pbProgress = (ProgressBar) view.findViewById(R.id.pb_progress);
        ivPlay = (ImageView) view.findViewById(R.id.iv_play);
        llytPlayer = (LinearLayout) view.findViewById(R.id.llyt_player);
        skbProgress = (SeekBar) view.findViewById(R.id.skb_progress);
        tvCurrentTime = (TextView) view.findViewById(R.id.tv_current_time);
        tvTotalTime = (TextView) view.findViewById(R.id.tv_total_time);
        tvSuspendCurrentProgress = (TextView) view.findViewById(R.id.tv_suspend_current_progress);
        ivFull = (ImageView) view.findViewById(R.id.iv_full);
        iv_thumbnai_bitmap = (ImageView) view.findViewById(R.id.iv_thumbnai_bitmap);

        vvVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                mp.reset();
                mp.stop();
                mp.start();
            }
        });
        skbProgress.setEnabled(false);
        ivPlay.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vvVideo.isPlaying()) {
                    pauseVideo();
                } else {
                    startVideo();
                }
            }
        });

        llytPlayer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (vvVideo.isPlaying()) {
                    pauseVideo();
                } else {
                    startVideo();
                }
            }
        });
        ivFull.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onFullClick != null) {
                    onFullClick.onClick();
                }
                if (allowFull) {
                    pauseVideo();
                    Activitys.toFullVideo(getContext(), url, vvVideo.getCurrentPosition());
                }
            }
        });
        iv_thumbnai_bitmap.setImageBitmap(thumbnai_bitmap);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            pauseVideo();
        }
        return super.onKeyDown(keyCode, event);
    }

    public int getCurrentPostion() {
        return vvVideo.getCurrentPosition();
    }

    public void setCurrentPostion(int postion) {
        vvVideo.seekTo(postion);
    }

    public void startVideo() {
        pause = true;
        vvVideo.seekTo(curPos);
        vvVideo.start();
        ivPlay.setImageResource(R.mipmap.video_pause_icon);
        llytPlayer.setVisibility(GONE);
        iv_thumbnai_bitmap.setVisibility(GONE);

    }

    /**
     * 设置缩略图显示的方法
     */

    public void pauseVideo() {
        pause = false;
        ivPlay.setImageResource(R.mipmap.video_player_icon);
        tvSuspendCurrentProgress.setText(toWps(vvVideo.getCurrentPosition() / 1000));
        llytPlayer.setVisibility(VISIBLE);
        vvVideo.pause();
        curPos = skbProgress.getProgress();
    }

    public void setUrl(String url) {
        this.url = url;
        try {
            //  String url = "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
            if (url != null && url.length() > 0) {
                Uri uri = Uri.parse(url);
                L.i(uri);
                vvVideo.setVideoURI(uri);
                vvVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.start();
                        mp.setLooping(true);
                    }
                });
                vvVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {
                        pbProgress.setVisibility(View.GONE);
                        skbProgress.setOnSeekBarChangeListener(MyVideoView.this);
                        skbProgress.setEnabled(true);
                    }
                });
                vvVideo.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                    @Override
                    public boolean onError(MediaPlayer mp, int what, int extra) {
                        T.showToCenter("视频地址可能有误，暂无法播放此视频");
                        return true;
                    }
                });
                iv_thumbnai_bitmap.setImageBitmap(createVideoThumbnail(url, LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
            }
            handler.postDelayed(runable, 1000);
        } catch (Exception ex) {
            L.i(ex);
        }
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


    private MyReceiver receiver;

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        receiver = new MyReceiver();
        IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        getContext().registerReceiver(receiver, homeFilter);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        getContext().unregisterReceiver(receiver);
        if (vvVideo.isPlaying()) {
            vvVideo.stopPlayback();
        }
        destroy = false;
        handler.removeCallbacks(runable);
    }


    final Handler handler = new Handler();

    Runnable runable = new Runnable() {
        @Override
        public void run() {
            if (pause) {
                L.i("getCurrentPosition：" + vvVideo.getCurrentPosition());
                L.i("getDuration：" + vvVideo.getDuration());
                skbProgress.setProgress(vvVideo.getCurrentPosition());
                skbProgress.setMax(vvVideo.getDuration());
                if (tvCurrentTime != null && tvTotalTime != null) {
                    tvCurrentTime.setText(toWps(vvVideo.getCurrentPosition() / 1000));
                    tvTotalTime.setText(toWps(vvVideo.getDuration() / 1000));
                }
            }
            handler.postDelayed(runable, 1000);
        }
    };

    /**
     * 描述      : 转换 00:00:00
     * 方法名    : toWps
     * param    : int s秒
     * 返回类型  : void
     * 创建人    : ghy
     * 创建时间  : 2017/3/16 16:28
     * 修改人    :
     * 修改时间
     * 修改备注
     * throws
     */
    private String toWps(int s) {
        int points = s / 60;
        int when = points / 60;
        int second = s % 60;
        return String.format("%02d", when) + ":" + String.format("%02d", points) + ":" + String.format("%02d", second);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        pauseVideo();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        curPos = skbProgress.getProgress();
        startVideo();
    }

    public interface onFullClick {
        void onClick();
    }

    private class MyReceiver extends BroadcastReceiver {

        private final String SYSTEM_DIALOG_REASON_KEY = "reason";
        private final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
        private final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
                String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
                if (reason == null)
                    return;
                // 最近任务列表键
                if (reason.equals(SYSTEM_DIALOG_REASON_RECENT_APPS)) {
                    pauseVideo();
                }
                // Home键
                if (reason.equals(SYSTEM_DIALOG_REASON_HOME_KEY)) {
                    pauseVideo();
                }
            }
        }
    }
}
