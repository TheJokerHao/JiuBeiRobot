package com.fecmobile.jiubeirobot.common.pullableview;

import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

@Deprecated
public class PullableWebView extends WebView implements Pullable {

    private boolean canPullDown = true;
    private boolean canPullUp = true;

    public PullableWebView(Context context) {
        super(context);
    }

    public PullableWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canPullDown() {
        if (!canPullDown) {
            return false;
        }

        if (getScrollY() == 0)
            return true;
        else
            return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean canPullUp() {

        if (!canPullUp) {
            return false;
        }

        if (getScrollY() >= getContentHeight() * getScale()
                - getMeasuredHeight())
            return true;
        else
            return false;
    }

    @Override
    public void setPullUp(boolean canPullUp) {
        this.canPullUp = canPullUp;

    }

    @Override
    public void setPullDown(boolean canPullDown) {
        this.canPullDown = canPullDown;

    }
}
