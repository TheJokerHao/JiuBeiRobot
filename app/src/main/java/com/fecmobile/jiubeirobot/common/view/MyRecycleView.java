package com.fecmobile.jiubeirobot.common.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 类描述    :MyRecycleView 根据内容设置高度，不回收ConverView
 * 包名      : com.fecmobile.jiubeirobot.common.view
 * 类名称    : MyRecycleView
 * 创建人    : ghy
 * 创建时间  : 2017/2/22 16:30
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */

public class MyRecycleView extends RecyclerView {
    public MyRecycleView(Context context) {
        super(context);
    }

    public MyRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
