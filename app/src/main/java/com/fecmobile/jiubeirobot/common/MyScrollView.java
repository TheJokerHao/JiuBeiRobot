package com.fecmobile.jiubeirobot.common;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

import com.fecmobile.jiubeirobot.utils.L;

/**
 * 类描述    :垂直滚动冲突
 * 包名      : com.fecmobile.jiubeirobot.common
 * 类名称    : MyScrollView
 * 创建人    : ghy
 * 创建时间  : 2017/3/8 20:56
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class MyScrollView extends ScrollView {
    AddressSelectedView llytAddress;

    public void setLlytAddress(AddressSelectedView llytAddress) {
        this.llytAddress = llytAddress;
    }

    public MyScrollView(Context context) {
        super(context);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        boolean e = super.onInterceptTouchEvent(ev);
        if (ev.getY() > 240 && ev.getY() < 410) {
            return false;
        }
        return e;
    }
}
