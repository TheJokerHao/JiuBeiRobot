package com.fecmobile.jiubeirobot.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;

public class BoderButtton extends LinearLayout {
    View view;
    private LinearLayout llytBg;
    private boolean checked;
    private TextView tvText;
    private ImageView ivIcon;

    public boolean isChecked() {
        return checked;
    }

    public void setListener(OnClickListener listener) {
        llytBg.setOnClickListener(listener);
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        if (checked) {
            ivIcon.setVisibility(VISIBLE);
            tvText.setTextColor(getResources().getColorStateList(R.color.color_main));
            llytBg.setBackground(getResources().getDrawable(R.drawable.shape_red_boder_ischeck_bg));
        } else {
            tvText.setTextColor(getResources().getColorStateList(R.color.color_33));
            ivIcon.setVisibility(GONE);
            llytBg.setBackground(getResources().getDrawable(R.drawable.shape_red_boder_nocheck_bg));
        }
    }

    public void setText(String txt) {
        tvText.setText(txt);
    }

    public BoderButtton(Context context) {
        this(context, null);
    }

    public BoderButtton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BoderButtton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = inflate(context, R.layout.layout_boder_btn, this);
        llytBg = (LinearLayout) view.findViewById(R.id.llyt_bg);
        tvText = (TextView) view.findViewById(R.id.tv_text);
        ivIcon = (ImageView) view.findViewById(R.id.iv_icon);

    }

}
