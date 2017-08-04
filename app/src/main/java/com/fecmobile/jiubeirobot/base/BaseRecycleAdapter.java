package com.fecmobile.jiubeirobot.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/2/21.
 * 自定义的base适配器
 */

public abstract class BaseRecycleAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    protected Context context;
    protected List<T> list;
    private int layoutId;

    public BaseRecycleAdapter(Context context, List<T> list, int layoutId) {
        this.context = context;
        this.list = list;
        this.layoutId = layoutId;
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        BaseViewHolder baseViewHolder = BaseViewHolder.get(context, parent, layoutId);

        return baseViewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.setPostion(position);
        convert(holder, list.get(position));
    }

    public abstract void convert(BaseViewHolder holder, T t);

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }
}
