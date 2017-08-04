package com.fecmobile.jiubeirobot.common.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.support.v7.widget.GridLayoutManager;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.bean.FilterBean;
import com.fecmobile.jiubeirobot.bean.GrapevineBean;
import com.fecmobile.jiubeirobot.bean.ListBean;
import com.fecmobile.jiubeirobot.listener.ItemClick;
import com.fecmobile.jiubeirobot.utils.L;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 类描述    :筛选项
 * 包名      : com.fecmobile.jiubeirobot.common.view
 * 类名称    : FilterItemView
 * 创建人    : ghy
 * 创建时间  : 2017/2/22 16:01
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class FilterItemView extends LinearLayout implements ToggleButton.OnCheckedChangeListener {
    MyGridView myrvDataList;
    ToggleButton tbExpand;
    private Set<Integer> selects = new HashSet<>();
    private List<FilterBean> list = new ArrayList<>();
    FilterItemAdapter filterItemAdapter;
    private int count = 3;
    private TextView tvTitle;
    private FilterItemClick itemClick;

    public void setFilterItemClick(FilterItemClick itemClick) {
        this.itemClick = itemClick;
    }

    public FilterItemView(Context context) {
        this(context, null);
    }

    public FilterItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FilterItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_filter_item, this);
        myrvDataList = (MyGridView) findViewById(R.id.myrv_data_list);
        tbExpand = (ToggleButton) findViewById(R.id.tb_expand);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tbExpand.setOnCheckedChangeListener(this);

        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.Filter, 0, 0);
            String title = a.getString(R.styleable.Filter_titleTxt);
            if (title != null && tvTitle != null) {
                tvTitle.setText(title);
            }
            a.recycle();//回收内存
        }
        tbExpand.setText(getContext().getString(R.string.common_all));
        tbExpand.setTextOn(getContext().getString(R.string.common_all));
        tbExpand.setTextOff(getContext().getString(R.string.common_all));
    }

    public void onReset() {
        filterItemAdapter.setCurrent(-1);
        filterItemAdapter.notifyDataSetChanged();
        tbExpand.setTextColor(getResources().getColorStateList(R.color.color_33));
        tbExpand.setText(getContext().getString(R.string.common_all));
        tbExpand.setTextOn(getContext().getString(R.string.common_all));
        tbExpand.setTextOff(getContext().getString(R.string.common_all));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        GridLayoutManager gm = new GridLayoutManager(getContext(), 3);
        gm.setAutoMeasureEnabled(true);

        filterItemAdapter = new FilterItemAdapter(getContext(), list);
        myrvDataList.setAdapter(filterItemAdapter);
    }

    public void setList(List<? extends FilterBean> list) {
        if (list == null) {
            this.list.clear();
        } else {
            this.list.addAll(list);
        }
        filterItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.tb_expand) {
            if (!isChecked) {
                count = 3;
            } else {
                count = list.size();
            }
            filterItemAdapter.notifyDataSetChanged();

            if (filterItemAdapter.getCurrent() != -1) {
                String name = list.get(filterItemAdapter.getCurrent()).getName();
                L.i(name);
                tbExpand.setTextColor(getResources().getColorStateList(R.color.color_main));
                tbExpand.setText(name);
                tbExpand.setTextOn(name);
                tbExpand.setTextOff(name);
            }
        }
    }

    class FilterItemAdapter extends CommAdapter<FilterBean> {
        private int current = -1;

        public void setCurrent(int current) {
            this.current = current;
        }

        public int getCurrent() {
            return current;
        }

        public FilterItemAdapter(Context mContext, List<FilterBean> mData) {
            super(mContext, mData, R.layout.item_filter_item);
        }

        @Override
        public int getCount() {
            if (list.size() >= count) {
                return count;
            }
            return list.size();
        }

        @Override
        public void convert(final CommViewHolder holder, final FilterBean bean) {
            final SelectedView llytItemFilterBg = holder.getView(R.id.sv_btn);

            llytItemFilterBg.getLlytItemFilterBg().setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    llytItemFilterBg.setChecked(!llytItemFilterBg.getChecked());
                    if (llytItemFilterBg.getChecked()) {
                        selects.add(holder.getPosition());
                    } else {
                        selects.remove(holder.getPosition());
                    }
                    if (current == holder.getPosition()) {
                        current = -1;
                    } else {
                        current = holder.getPosition();
                    }
                    String name = "";
                    if (current == -1) {
                        tbExpand.setTextColor(getResources().getColorStateList(R.color.color_33));
                        name = getContext().getString(R.string.common_all);
                        if (itemClick != null) {
                            itemClick.itemClick(new FilterBean());
                        }
                    } else {
                        tbExpand.setTextColor(getResources().getColorStateList(R.color.color_main));
                        name = bean.getName();
                        if (itemClick != null) {
                            itemClick.itemClick(bean);
                        }
                    }
                    tbExpand.setText(name);
                    tbExpand.setTextOn(name);
                    tbExpand.setTextOff(name);

                    notifyDataSetChanged();
                }
            });

//            llytItemFilterBg.setChecked(selects.contains(holder.getPosition()));


            if (current == holder.getPosition()) {
                // llytItemFilterBg.setChecked(selects.contains(holder.getPosition()));
                llytItemFilterBg.setChecked(true);
            } else {
                llytItemFilterBg.setChecked(false);
            }
            String name = bean.getName();
            if (name.length() > 5) {
                name = name.substring(0, 5);
            }
            llytItemFilterBg.setText(name);
        }
    }

    public interface FilterItemClick {
        void itemClick(FilterBean bean);
    }
}
