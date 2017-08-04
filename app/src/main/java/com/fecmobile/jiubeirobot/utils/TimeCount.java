package com.fecmobile.jiubeirobot.utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.widget.TextView;


public class TimeCount extends CountDownTimer {

    private TextView view;
    private Context context;

    /**
     * @param millisInFuture    总时长（毫秒数）
     * @param countDownInterval 计时的时间间隔（毫秒数）
     * @param view              需要显示文字的textview
     */
    public TimeCount(Context context, long millisInFuture,
                     long countDownInterval, TextView view) {
        super(millisInFuture, countDownInterval);
        this.view = view;
        this.context = context;
        this.view.setTextSize(14);
        this.view.setEnabled(false);
    }

    @Override
    public void onTick(long millisUntilFinished) {
        view.setText("(" + millisUntilFinished / 1000
                + "秒)后重新获取");
    }

    @Override
    public void onFinish() {
        view.setText("重新获取");
        this.view.setEnabled(true);
        view.setClickable(true);
    }
}
