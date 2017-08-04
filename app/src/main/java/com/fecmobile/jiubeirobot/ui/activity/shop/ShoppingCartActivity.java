package com.fecmobile.jiubeirobot.ui.activity.shop;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseActivity;
import com.fecmobile.jiubeirobot.base.BaseApplication;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.base.BaseData;
import com.fecmobile.jiubeirobot.bean.IdentityCheckResultBean;
import com.fecmobile.jiubeirobot.bean.StockBean;
import com.fecmobile.jiubeirobot.common.Activitys;
import com.fecmobile.jiubeirobot.ui.adapter.shop.ShopCardAdapter;
import com.fecmobile.jiubeirobot.ui.dialog.user.IdentityCheckDialog;
import com.fecmobile.jiubeirobot.utils.L;
import com.fecmobile.xrecyclerview.XRecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类描述    : 购物车
 * 包名      : com.fecmobile.jiubeirobot.ui.activity.shop
 * 类名称    : ShoppingCartActivity
 * 创建人    : ghy
 * 创建时间  : 2017/2/23 16:24
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class ShoppingCartActivity extends BaseActivity implements ShopCardAdapter.OnCustomNumInputTextChange {
    @BindView(R.id.llyt_back)
    LinearLayout llytBack;
    @BindView(R.id.rv_shop_item)
    XRecyclerView rvShopItem;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.btn_settlement)
    Button btnSettlement;
    @BindView(R.id.tv_back_txt)
    TextView tvBackTxt;
    @BindView(R.id.iv_search_icon)
    ImageView ivSearchIcon;
    @BindView(R.id.tv_shop_total_money)
    TextView tvShopTotalMoney;
    @BindView(R.id.tv_total_num)
    TextView tvTotalNum;
    @BindView(R.id.tv_be_paid_money)
    TextView tvBePaidMoney;
    private ShopCardAdapter shopCardAdapter;
    private IdentityCheckDialog identityCheckDialog;
    private List<StockBean> cartShops = BaseData.getBaseData().getCardShops();

    @Override
    public int pageLayout() {
        return R.layout.activity_shopping_card;
    }

    @Override
    public void initTitle() {
        tvBackTxt.setVisibility(View.GONE);
        ivSearchIcon.setVisibility(View.INVISIBLE);
        tvTitle.setText(getString(R.string.common_buy_card));
    }

    @Override
    public void initView() {
        rvShopItem.setLayoutManager(new LinearLayoutManager(this));
        Drawable dividerDrawable = ContextCompat.getDrawable(this, com.fecmobile.xrecyclerview.R.drawable.divider_sample);
        rvShopItem.addItemDecoration(rvShopItem.new DividerItemDecoration(dividerDrawable));
        shopCardAdapter = new ShopCardAdapter(this, cartShops);
        rvShopItem.setAdapter(shopCardAdapter);
        shopCardAdapter.setOnCustomNumInputTextChange(this);
        setPrice();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (BaseData.getBaseData().getCardItemNum() == 0) {
            finish();
        } else {
            shopCardAdapter.notifyDataSetChanged();
        }
    }

    private void setPrice() {
        tvShopTotalMoney.setText("￥" + BaseData.getBaseData().getTotalMoney());
        tvTotalNum.setText(BaseData.getBaseData().getCardItemNum() + "件商品");
        tvBePaidMoney.setText("￥" + BaseData.getBaseData().getTotalMoney());
    }

    @Override
    public void onSuccess(final BaseBean bean, int flag) {

    }

    @OnClick({R.id.llyt_back, R.id.btn_settlement, R.id.iv_search_icon})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llyt_back:
                finish();
                break;
            case R.id.btn_settlement:
                //结算按钮的点击监听
                if (BaseData.getBaseData().getCardItemNum() == 0) {
                    return;
                }
                //TODO  点击结算按钮的时候需要判断是什么模式的选择  一共有三个模式   一个是本地购买  一个是快速购买  一个是会员购买
                if (BaseApplication.currentMode == BaseApplication.FAST_BUY) {
                    Activitys.toUserCheckOrder(this, UserCheckOrderActivity.GENERAL_BUY, null, "");
                } else if (BaseApplication.currentMode == BaseApplication.LOCAL_BUY) {
                    //选择的是本地购买  这样的购买不需要登录   直接调用跟快速购买一样的就OK‘
                    Activitys.toLocalUserCheckOrder(this, LocalUserCheckOrderActivity.GENERAL_BUY, null, "");
                } else {
                    //会员购买
                    if (identityCheckDialog == null) {
                        identityCheckDialog = new IdentityCheckDialog();
                        identityCheckDialog.setCheckResult(new IdentityCheckDialog.CheckResult() {
                            @Override
                            public void result(IdentityCheckResultBean bean) {
                                Activitys.toUserCheckOrder(ShoppingCartActivity.this, UserCheckOrderActivity.GENERAL_BUY, null, bean.getBid());
                            }
                        });
                    }
                    identityCheckDialog.show(getSupportFragmentManager(), "identityCheckDialog");
                }
                break;
            case R.id.iv_search_icon:
                Activitys.toSearchShop(this, false);
                break;
        }
    }

    @Override
    public void textChange(View v, int num, StockBean bean) {
        BaseData.getBaseData().setShopNum(bean, num);
        setPrice();
    }

    @Override
    public void delete(View v, StockBean bean, int postion) {
        L.i("--------" + postion);
        BaseData.getBaseData().removeShop(bean);
        if (BaseData.getBaseData().getCardItemNum() == 0) {
            finish();
        } else {
            setPrice();
            //shopCardAdapter.notifyItemRemoved(postion);
            shopCardAdapter.notifyDataSetChanged();
        }
    }
}