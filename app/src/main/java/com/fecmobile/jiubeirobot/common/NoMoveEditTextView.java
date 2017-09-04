package com.fecmobile.jiubeirobot.common;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;

import com.fecmobile.jiubeirobot.utils.L;

/**
 * 类描述    :不处理Move事件EditText
 * 包名      : com.fecmobile.jiubeirobot.common
 * 类名称    : NoMoveEditTextView
 * 创建人    : ghy
 * 创建时间  : 2017/3/23 11:18
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */

public class NoMoveEditTextView extends android.support.v7.widget.AppCompatEditText {
    private ScrollView scrollView;

    public void setScrollView(ScrollView scrollView) {
        this.scrollView = scrollView;
    }

    public NoMoveEditTextView(Context context) {
        super(context);
    }

    public NoMoveEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public NoMoveEditTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean result = super.onTouchEvent(event);
        if (scrollView != null) {
            scrollView.scrollBy((int) scrollView.getX(), (int) event.getY());
        }
        return result;
    }
}
