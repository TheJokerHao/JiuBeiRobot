package com.fecmobile.jiubeirobot.common.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ToggleButton;

import com.fecmobile.jiubeirobot.R;

/**
 * 类描述    :筛选项按钮，选中时显示勾
 * 包名      : com.fecmobile.jiubeirobot.common.view
 * 类名称    : SelectedView
 * 创建人    : ghy
 * 创建时间  : 2017/2/23 12:43
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class SelectedView extends LinearLayout {
    private boolean checked;
    private View llytItemFilterBg;
    private ImageView ivSelected;
    private ToggleButton tbItem;

    public View getLlytItemFilterBg() {
        return llytItemFilterBg;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
        if (checked) {
            tbItem.setChecked(true);
            llytItemFilterBg.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_filter_item_red_bg));
            tbItem.setTextColor(getResources().getColorStateList(R.color.color_title_bg));
            ivSelected.setVisibility(VISIBLE);
        } else {
            tbItem.setChecked(false);
            ivSelected.setVisibility(GONE);
            llytItemFilterBg.setBackgroundDrawable(getResources().getDrawable(R.drawable.shape_filter_item_default_bg));
            tbItem.setTextColor(getResources().getColorStateList(R.color.color_default_font_color));
        }
    }

    public boolean getChecked() {
        return this.checked;
    }

    public SelectedView(Context context) {
        this(context, null);
    }

    public SelectedView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SelectedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_item_filter_item, this);
        llytItemFilterBg = findViewById(R.id.llyt_item_filter_bg);
        ivSelected = (ImageView) findViewById(R.id.iv_selected);
        tbItem = (ToggleButton) findViewById(R.id.tb_item);
    }

    public String getText() {
        return tbItem.getText() + "";
    }

    public void setText(String text) {
        tbItem.setText(text);
        tbItem.setTextOff(text);
        tbItem.setTextOn(text);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setChecked(false);
    }
}
