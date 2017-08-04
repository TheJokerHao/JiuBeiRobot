package com.fecmobile.jiubeirobot.ui.adapter.shop;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseRecycleAdapter;
import com.fecmobile.jiubeirobot.base.BaseViewHolder;
import com.fecmobile.jiubeirobot.bean.LocalQueryAddressBean;
import com.fecmobile.jiubeirobot.bean.LocalQueryAddressOtherBean;
import com.fecmobile.jiubeirobot.listener.ItemClick;
import com.fecmobile.jiubeirobot.utils.DesensitizationUtil;

import java.util.List;

/**
 * 创建人：TheJoker丶豪 on
 * 创建时间：2017/6/8.
 */

public class LocalShippingAddressAdapter extends BaseRecycleAdapter<LocalQueryAddressOtherBean> {
    private LocalShippingAddressAdapter.ClickLinsener clickLinsener;


    public void setClickLinsener(LocalShippingAddressAdapter.ClickLinsener clickLinsener) {
        this.clickLinsener = clickLinsener;
    }

    public LocalShippingAddressAdapter(Context context, List<LocalQueryAddressOtherBean> list) {
        super(context, list, R.layout.item_shiing_local_address_item);
    }

    @Override
    public void convert(final BaseViewHolder holder, LocalQueryAddressOtherBean s) {
        ImageView ivDefaultIcon = holder.getView(R.id.iv_default_icon);
        holder.getView(R.id.llyt_update).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickLinsener != null) {
                    clickLinsener.update(holder.getPostion());
                }
            }
        });

        holder.getView(R.id.llyt_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickLinsener != null) {
                    clickLinsener.delete(holder.getPostion());
                }
            }
        });
        holder.setText(R.id.tv_name, s.getDeliveryName() + "        " + DesensitizationUtil.phoneDesensitization(s.getDeliveryPhone()));
        holder.setText(R.id.tv_address, s.getDeliveryProv() + "  " + s.getDeliveryCity() + "  " + s.getDeliveryArea() + " " + s.getDeliveryAddr());

        ivDefaultIcon.setVisibility(View.VISIBLE);

        holder.getView(R.id.rl_bg).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickLinsener != null) {
                    clickLinsener.onItemClick(holder.getPostion());
                }
            }
        });
        if ("1".equals(s.getIsDefault())) {
            ivDefaultIcon.setVisibility(View.VISIBLE);
        } else {
            ivDefaultIcon.setVisibility(View.INVISIBLE);
        }
    }

    public interface ClickLinsener extends ItemClick {
        void update(int postion);

        void delete(int postion);
    }
}