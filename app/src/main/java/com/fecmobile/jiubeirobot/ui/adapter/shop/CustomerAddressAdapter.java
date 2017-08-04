package com.fecmobile.jiubeirobot.ui.adapter.shop;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseRecycleAdapter;
import com.fecmobile.jiubeirobot.base.BaseViewHolder;
import com.fecmobile.jiubeirobot.bean.LocalQueryAddressBean;

import java.util.List;

/**
 * 创建人：TheJoker丶豪 on
 * 创建时间：2017/6/7.
 * 查询到的客户已经存在的地址信息的适配器
 */

public class CustomerAddressAdapter extends BaseRecycleAdapter<LocalQueryAddressBean> {

    public CustomerAddressAdapter(Context context, List<LocalQueryAddressBean> list) {
        super(context, list, R.layout.item_customer_address);
    }

    @Override
    public void convert(BaseViewHolder holder, LocalQueryAddressBean localQueryAddressBean) {
        holder.setText(R.id.tv_customer_name, localQueryAddressBean.getDeliveryName());//收货人
        holder.setText(R.id.tv_customer_telnum, localQueryAddressBean.getDeliveryTel());//电话号码
        holder.setText(R.id.tv_customer_address, localQueryAddressBean.getDeliveryProv() + localQueryAddressBean.getDeliveryCity() + localQueryAddressBean.getDeliveryArea() + localQueryAddressBean.getDeliveryAddr());//收货地址
        holder.setText(R.id.tv_customer_email, localQueryAddressBean.getDeliveryEmail());//邮箱
        holder.setText(R.id.tv_customer_ecode, localQueryAddressBean.getDeliveryPost());//邮编
//        holder.getView(R.id.rb_isDefault).setOnCheckedChangeListene{
//
//        }
        holder.getView(R.id.tv_deleateaddress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("print", "onClick: 点击了");
            }
        });

        holder.getView(R.id.tv_changeaddress).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("print", "onClick: 点击了");
            }
        });

    }
}
