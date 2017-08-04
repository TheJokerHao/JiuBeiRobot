package com.fecmobile.jiubeirobot.ui.adapter.shop;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseRecycleAdapter;
import com.fecmobile.jiubeirobot.base.BaseViewHolder;
import com.fecmobile.jiubeirobot.bean.StockBean;
import com.fecmobile.jiubeirobot.common.view.CustomNumInputView;
import com.fecmobile.jiubeirobot.utils.GlideImageLoadImpl;
import com.fecmobile.jiubeirobot.utils.L;

import java.util.List;

/**
 * 类描述    :商品购物车
 * 包名      : com.fecmobile.jiubeirobot.ui.adapter.shop
 * 类名称    : ShopCardAdapter
 * 创建人    : ghy
 * 创建时间  : 2017/2/24 15:41
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */

public class ShopCardAdapter extends BaseRecycleAdapter<StockBean> {
    private OnCustomNumInputTextChange onCustomNumInputTextChange;

    public void setOnCustomNumInputTextChange(OnCustomNumInputTextChange onCustomNumInputTextChange) {
        this.onCustomNumInputTextChange = onCustomNumInputTextChange;
    }

    public ShopCardAdapter(Context context, List<StockBean> list) {
        super(context, list, R.layout.item_shopping_card_item);
    }

    @Override
    public void convert(final BaseViewHolder holder, final StockBean bean) {
        holder.setText(R.id.tv_shop_name, bean.getDrinkingName());
        holder.setText(R.id.tv_brand_name_en, bean.getDrinkingNameEn());
        holder.setText(R.id.tv_lsStock, "库存：" + bean.getProductStock() + "瓶");
        final CustomNumInputView cnivNum = holder.getView(R.id.cniv_num);

        //取消当前item的显示的订单
        holder.getView(R.id.iv_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCustomNumInputTextChange != null) {
                    onCustomNumInputTextChange.delete(v, bean, holder.getPostion());
                }
            }
        });

        cnivNum.setCustomNumInputTextChange(new CustomNumInputView.CustomNumInputTextChange() {
            @Override
            public void textChange(View v, int num) {
                if ((onCustomNumInputTextChange != null)) {
                    L.i("textChange" + num);
                    onCustomNumInputTextChange.textChange(v, num, bean);
                }
            }
        });
        cnivNum.setMinVal(1);
        cnivNum.setMax(Integer.parseInt(bean.getProductStock()));
        cnivNum.setCurentVal(bean.getNum());
        holder.setText(R.id.tv_price, "￥" + bean.getLsPrice());
        GlideImageLoadImpl.getInstance().load(((Activity) context), bean.getMainPicUrl(), (ImageView) holder.getView(R.id.iv_shop_img));
    }

    public interface OnCustomNumInputTextChange {
        void textChange(View v, int num, StockBean bean);

        void delete(View v, StockBean bean, int postion);
    }
}
