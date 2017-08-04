package com.fecmobile.jiubeirobot.base;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.utils.GlideImageLoadImpl;

/**
 * Created by Administrator on 2017/2/21.
 * 为RecyclerView提供的ViewHolder
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> sparseArray;
    private View converView;
    private int postion;
    private Context context;

    public void setPostion(int postion) {
        this.postion = postion;
    }

    public int getPostion() {
        return postion;
    }

    public BaseViewHolder(View itemView, Context context) {
        super(itemView);
        this.context = context;
        this.sparseArray = new SparseArray<>();
        this.converView = itemView;
    }

    public static BaseViewHolder get(Context context, ViewGroup viewGroup, int layoutID) {
        View view = LayoutInflater.from(context).inflate(layoutID, viewGroup, false);
        return new BaseViewHolder(view, context);
    }

    public <T extends View> T getView(int viewId) {
        View view = sparseArray.get(viewId);
        if (view == null) {
            view = converView.findViewById(viewId);
            sparseArray.put(viewId, view);
        }
        return (T) view;
    }

    public void setText(int viewId, String txt) {
        TextView tv = getView(viewId);
        tv.setText(txt);
    }

    public void setImg(int viewId, String url) {
        ImageView img = getView(viewId);
        GlideImageLoadImpl.getInstance().load((Activity) context, url, img);
    }
}
