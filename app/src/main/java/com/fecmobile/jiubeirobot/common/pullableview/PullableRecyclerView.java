package com.fecmobile.jiubeirobot.common.pullableview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by Administrator on 2017/2/20.
 */
@Deprecated
public class PullableRecyclerView extends RecyclerView implements Pullable {
    private boolean canPullDown = true;
    private boolean canPullUp = true;

    public PullableRecyclerView(Context context) {
        this(context, null);
    }

    public PullableRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullableRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean canPullDown() {
        if (!canPullDown) {
            return false;
        }
        return isFirstItemVisible();
    }

    private boolean isFirstItemVisible() {
        final Adapter<?> adapter = this.getAdapter();
        if (null == adapter || adapter.getItemCount() == 0) {
            return true;

        } else {
            // 第一个条目完全展示,可以刷新
            if (getFirstVisiblePosition() == 0) {
                return this.getChildAt(0).getTop() >= this.getTop();
            }
        }

        return false;
    }

    private int getFirstVisiblePosition() {
        View firstVisibleChild = this.getChildAt(0);
        return firstVisibleChild != null ? this
                .getChildAdapterPosition(firstVisibleChild) : -1;
    }


    @Override
    public boolean canPullUp() {
        if (!canPullUp) {
            return false;
        }
        final Adapter<?> adapter = this.getAdapter();

        // 如果未设置Adapter或者Adapter没有数据可以上拉刷新
        if (null == adapter || adapter.getItemCount() == 0) {

            return true;

        } else {
            // 最后一个条目View完全展示,可以刷新
            int lastVisiblePosition = getLastVisiblePosition();
            if (lastVisiblePosition >= this.getAdapter().getItemCount() - 1) {
                return this.getChildAt(
                        this.getChildCount() - 1).getBottom() <= this
                        .getBottom();
            }
        }
        return false;
    }

    private int getLastVisiblePosition() {
        View lastVisibleChild = this.getChildAt(this
                .getChildCount() - 1);
        return lastVisibleChild != null ? this
                .getChildAdapterPosition(lastVisibleChild) : -1;
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
