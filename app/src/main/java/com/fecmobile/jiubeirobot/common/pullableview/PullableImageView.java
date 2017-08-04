package com.fecmobile.jiubeirobot.common.pullableview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

@Deprecated
public class PullableImageView extends ImageView implements Pullable {

    private boolean canPullDown = true;
    private boolean canPullUp = true;

    public PullableImageView(Context context) {
        super(context);
    }

    public PullableImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canPullDown() {

        if (!canPullDown) {
            return false;
        }

        return true;
    }

    @Override
    public boolean canPullUp() {

        if (!canPullUp) {
            return false;
        }
        return true;
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
