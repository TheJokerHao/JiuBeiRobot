package com.fecmobile.jiubeirobot.ui.fragment.shop;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.base.BaseFragment;
import com.fecmobile.jiubeirobot.bean.ListBean;
import com.fecmobile.jiubeirobot.bean.MemberAddressBean;
import com.fecmobile.jiubeirobot.common.LinearItemDecoration;
import com.fecmobile.jiubeirobot.data.net.APIManager;
import com.fecmobile.jiubeirobot.data.net.FLags;
import com.fecmobile.jiubeirobot.listener.ItemClick;
import com.fecmobile.jiubeirobot.ui.activity.shop.UserCheckOrderActivity;
import com.fecmobile.jiubeirobot.ui.adapter.shop.ShippingAddressAdapter;
import com.fecmobile.jiubeirobot.ui.dialog.AlertDialog;
import com.fecmobile.jiubeirobot.ui.dialog.ConfirmDropPointDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类描述    :收货地址
 * 包名      : com.fecmobile.jiubeirobot.ui.fragment.shop
 * 类名称    : ShippingAddressFragment
 * 创建人    : ghy
 * 创建时间  : 2017/3/3 11:41
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class ShippingAddressFragment extends BaseFragment implements ShippingAddressAdapter.ClickLinsener, ItemClick {

    @BindView(R.id.rv_address_list)
    RecyclerView rvAddressList;
    @BindView(R.id.btn_new_add)
    Button btnNewAdd;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    private ShippingAddressAdapter shippingAddressAdapter;
    private List<MemberAddressBean> list = new ArrayList<>();

    private ConfirmDropPointDialog confirmDropPointDialog = new ConfirmDropPointDialog();

    @Override
    public int pageLayout() {
        return R.layout.fragment_shipping_address;
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {
        rvAddressList.addItemDecoration(new LinearItemDecoration(1));

        rvAddressList.setLayoutManager(new LinearLayoutManager(getContext()));
        shippingAddressAdapter = new ShippingAddressAdapter(getContext(), list);
        shippingAddressAdapter.setClickLinsener(this);
        rvAddressList.setAdapter(shippingAddressAdapter);
        onRefresh();
    }

    public void onRefresh() {
        APIManager.getInstance().getMemberAddressList(getContext(), this);
    }

    @Override
    public void onSuccess(BaseBean bean, int flag) {
        switch (flag) {
            case FLags.FLAG_MEMBER_ADDRESS_LIST:
                BaseBean<ListBean<MemberAddressBean>> addressBean = bean;
                list.clear();
                list.addAll(addressBean.getData().getList());
                shippingAddressAdapter.notifyDataSetChanged();
                break;
            case FLags.FLAG_DELETE_MEMBER_ADDRESS:
                onRefresh();
                break;
        }
        dismissHUD();
    }

    @OnClick({R.id.btn_new_add, R.id.iv_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_new_add:
                if (getActivity() != null) {
                    ((UserCheckOrderActivity) getActivity()).AddShippingAddress();
                }
                break;
            case R.id.iv_close:
                if (getActivity() != null) {
                    ((UserCheckOrderActivity) getActivity()).close();
                }
                break;
        }
    }


    @Override
    public void update(int postion) {
        if (getActivity() != null) {
            ((UserCheckOrderActivity) getActivity()).toEditAddress(list.get(postion));
        }
    }

    @Override
    public void delete(final int postion) {
        confirmDropPointDialog.show(getContext(), getString(R.string.common_comfirm_delete), getString(R.string.common_comfirm), getString(R.string.common_cancel));
        confirmDropPointDialog.setClickLisener(new ConfirmDropPointDialog.ClickLisener() {
            @Override
            public void onLeftClick(AlertDialog dialog) {
                dialog.dismiss();
            }

            @Override
            public void onRightClick(AlertDialog dialog) {
                APIManager.getInstance().dropMemberAddress(getContext(), ShippingAddressFragment.this, list.get(postion).getId());
            }
        });
    }

    @Override
    public void onItemClick(int postion) {
        if (getActivity() != null) {
            ((UserCheckOrderActivity) getActivity()).setAddrss(list.get(postion));
            ((UserCheckOrderActivity) getActivity()).close();
        }
    }
}
