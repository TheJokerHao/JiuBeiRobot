package com.fecmobile.jiubeirobot.base;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/2/21.
 */

public abstract class BaseMultiRecycleAdapter<T> extends BaseRecycleAdapter<T> {
    private IMultiItemType<T> iMultiItemType;

    public BaseMultiRecycleAdapter(Context context, List<T> list, IMultiItemType<T> itemType) {
        super(context, list, 0);
        this.iMultiItemType = itemType;
    }

    @Override
    public int getItemViewType(int position) {
        return iMultiItemType.getItemViewType(position, list.get(position));
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder viewHolder = BaseViewHolder.get(context, parent, iMultiItemType.getLayoutId(viewType));
        return viewHolder;
    }
}
