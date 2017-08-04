package com.fecmobile.jiubeirobot.common.pullableview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

@Deprecated
public class PullableExpandableListView extends ExpandableListView implements Pullable {

    private boolean canPullDown = true;
    private boolean canPullUp = true;

    public PullableExpandableListView(Context context) {
        super(context);
    }

    public PullableExpandableListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PullableExpandableListView(Context context, AttributeSet attrs,
                                      int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canPullDown() {
        if (!canPullDown) {
            return false;
        }

        if (getCount() == 0) {
            // 没有item的时候也可以下拉刷新
            return true;
        } else if (getFirstVisiblePosition() == 0 && getChildAt(0).getTop() >= 0) {
            // 滑到顶部了
            return true;
        } else
            return false;
    }

    @Override
    public boolean canPullUp() {
        if (!canPullUp) {
            return false;
        }


        if (getCount() == 0) {
            // 没有item的时候也可以上拉加载
            return true;
        } else if (getLastVisiblePosition() == (getCount() - 1)) {
            // 滑到底部了
            if (getChildAt(getLastVisiblePosition() - getFirstVisiblePosition()) != null
                    && getChildAt(
                    getLastVisiblePosition()
                            - getFirstVisiblePosition()).getBottom() <= getMeasuredHeight())
                return true;
        }
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
