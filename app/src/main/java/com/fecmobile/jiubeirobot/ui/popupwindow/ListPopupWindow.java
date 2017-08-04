package com.fecmobile.jiubeirobot.ui.popupwindow;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.common.view.CommAdapter;
import com.fecmobile.jiubeirobot.common.view.CommViewHolder;
import com.fecmobile.jiubeirobot.listener.ItemClick;

import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

/**
 * 类描述    : 列表popupwindow
 * 包名      : com.fecmobile.jiubeirobot.ui.popupwindow
 * 类名称    : ListPopupWindow
 * 创建人    : lc
 * 创建时间  :  2017-03-16
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class ListPopupWindow {

    private PopupWindow popupwindow;
    private List<Map<String, Object>> mData;
    private WeakReference<Context> mContext;
    private CommAdapter mAdapter;
    private ItemClick itemClick;

    public ListPopupWindow(Context context, List mData) {
        this.mData = mData;
        mContext = new WeakReference<Context>(context);
        initView();
    }

    private void initView() {
        if (mData == null || mData.size() == 0) {
            return;
        }
        popupwindow = new PopupWindow(mContext.get());
        popupwindow.setWidth(ActionBar.LayoutParams.WRAP_CONTENT);
        popupwindow.setHeight(ActionBar.LayoutParams.WRAP_CONTENT);

        View inflate = LayoutInflater.from(mContext.get()).inflate(R.layout.pop_cellar_storage, null);
        ListView mLlyt_pop_cellar_storage = (ListView) inflate.findViewById(R.id.llyt_pop_cellar_storage);
        mAdapter = new CommAdapter<Map<String, Object>>(mContext.get(), mData, R.layout.pop_item_cellar_storage) {
            @Override
            public void convert(CommViewHolder holder, Map item) {
                holder.setText(R.id.tv_item_cellar_name, String.valueOf(item.get("name")));
                if ((boolean) (item.get("checked"))) {
                    ((TextView) holder.getView(R.id.tv_item_cellar_name)).setTextColor(mContext.getResources().getColor(R.color.color_cellar_red));
                } else {
                    ((TextView) holder.getView(R.id.tv_item_cellar_name)).setTextColor(mContext.getResources().getColor(R.color.color_33));
                }
            }
        };
        mLlyt_pop_cellar_storage.setAdapter(mAdapter);
        mLlyt_pop_cellar_storage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                for (int i = 0; i < mData.size(); i++) {
                    TextView mTv_item_cellar_name = (TextView) adapterView.getChildAt(i).findViewById(R.id.tv_item_cellar_name);
                    mTv_item_cellar_name.setTextColor(mContext.get().getResources().getColor(R.color.color_33));
                    mData.get(i).put("checked", false);
                }
                ((TextView) adapterView.getChildAt(position).findViewById(R.id.tv_item_cellar_name)).setTextColor(mContext.get().getResources().getColor(R.color.color_cellar_red));

                mData.get(position).put("checked", true);
//                mTvSearchType.setText(String.valueOf(mList_pop.get(position).get("name")));
                if (itemClick != null) {
                    itemClick.onItemClick(position);
                }
                popupwindow.dismiss();
            }
        });

        popupwindow.setContentView(inflate);
        popupwindow.setOutsideTouchable(true);
        popupwindow.setFocusable(true);
        ColorDrawable dw = new ColorDrawable(0X00000000);
        popupwindow.setBackgroundDrawable(dw);

    }

    /***
     * 替换原始数据
     */
    public void setData(List mData) {
        if (mData == null) {
            this.mData = mData;
        } else {
            this.mData.clear();
            this.mData.addAll(mData);
        }
    }


    public void show(View view) {
        if (popupwindow == null) {
            return;
        }
        if (popupwindow.isShowing()) {
            popupwindow.dismiss();
        } else {
            popupwindow.showAsDropDown(view, 0, 10);
        }
//        if (mData!=null&&mData.size()>postion){
//            for (int i = 0; i < mData.size(); i++) {
//                mData.get(i).put("checked",false);
//            }
//            mData.get(postion).put("checked",true);
//        }
    }


    public void setItemClick(ItemClick itemClick) {
        this.itemClick = itemClick;
    }


    public void dismiss() {
        setPopDismiss(false);
    }

    public void dismiss(boolean isDestroy) {
        setPopDismiss(true);
    }

    private void setPopDismiss(boolean isDestroy) {
        if (isDestroy) {
            //销毁数据源
            mData = null;
            popupwindow = null;
        } else {
            popupwindow.dismiss();
        }
    }

}
