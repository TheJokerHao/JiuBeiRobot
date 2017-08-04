package com.fecmobile.jiubeirobot.ui.fragment.cellar;

import android.os.Bundle;
import android.util.Log;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.base.BaseFragment;
import com.fecmobile.jiubeirobot.bean.CellarInfoBean;
import com.fecmobile.jiubeirobot.bean.ObjBean;
import com.fecmobile.jiubeirobot.common.Constants;
import com.fecmobile.jiubeirobot.common.MyWebView;
import com.fecmobile.jiubeirobot.data.net.APIManager;
import com.fecmobile.jiubeirobot.data.net.FLags;
import com.fecmobile.jiubeirobot.ui.activity.cellar.walk.WalkIntoCellarActivity;
import com.fecmobile.jiubeirobot.utils.HtmlRegexpUtil;
import com.fecmobile.jiubeirobot.utils.L;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;

import butterknife.BindView;

/**
 * 类描述    :WevView页面
 * 包名      : com.fecmobile.jiubeirobot.ui.fragment.cellar
 * 类名称    : WebViewFragment
 * 创建人    : ghy
 * 创建时间  : 2017/3/1 16:12
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class WebViewFragment extends BaseFragment {

    @BindView(R.id.wv_content)
    MyWebView wvContent;

    SpeechSynthesizer mTts;

    String cellarDesc;

    public String getCellarDesc() {
        return cellarDesc;
    }

    @Override
    public int pageLayout() {
        return R.layout.fragment_web_view;
    }

    @Override
    public void initTitle() {
    }

    @Override
    public void initView() {

        try {
            WalkIntoCellarActivity walkIntoCellar = (WalkIntoCellarActivity) getActivity();
            String cellerDescprition = walkIntoCellar.getBeanBaseBean().getData().getObj().getCellerDescprition();
            wvContent.setHtml(walkIntoCellar.getBeanBaseBean().getData().getObj().getCellerDescprition());
            cellarDesc = HtmlRegexpUtil.getTextHTMLToCellarDesc(walkIntoCellar.getBeanBaseBean().getData().getObj().getCellerDescprition());
            wvContent.setHtml(cellerDescprition);
        } catch (Exception ex) {
            L.i(ex);
        }

    }


    public void speakDetails(String cellarDesc) {
        setParam();
        mTts.startSpeaking(cellarDesc, mSynListener);
    }

    public void mtsPapuse() {
        Log.d("print", "mtsPapuse: " + mTts.isSpeaking());

        if (mTts.isSpeaking()) {
            mTts.pauseSpeaking();
            Log.d("print", "mtsPapuse: 1");
        }
    }

    //合成监听器
    public SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {
            mTts.setParameter(SpeechConstant.PARAMS, null);
        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {

        }

        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        }

        //开始播放
        public void onSpeakBegin() {
        }

        //暂停播放
        public void onSpeakPaused() {
        }

        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        //恢复播放回调接口
        public void onSpeakResumed() {
        }

    };


    private void setParam() {
        // 1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
        mTts = SpeechSynthesizer.createSynthesizer(getContext(), null);
        Log.d("print", "Speak: " + mTts);
        // 2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoyan");// 设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");// 设置语速
        mTts.setParameter(SpeechConstant.VOLUME, "80");// 设置音量，范围0~100
        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); // 设置云端
        // 设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
        // 保存在SD卡需要在AndroidManifest.xml添加写SD卡权限
        // 如果不需要保存合成音频，注释该行代码
        // mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");
        // 3.开始合成
    }

    @Override
    public void onSuccess(BaseBean bean, int flag) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mTts) {
            mTts.stopSpeaking();
            // 退出时释放连接
            mTts.destroy();
        }
    }
}