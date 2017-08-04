package com.fecmobile.jiubeirobot.ui.fragment.shop;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.base.BaseFragment;
import com.fecmobile.jiubeirobot.bean.ListBean;
import com.fecmobile.jiubeirobot.bean.LocalQueryAddressOtherBean;
import com.fecmobile.jiubeirobot.common.LinearItemDecoration;
import com.fecmobile.jiubeirobot.data.net.APIManager;
import com.fecmobile.jiubeirobot.data.net.FLags;
import com.fecmobile.jiubeirobot.listener.ItemClick;
import com.fecmobile.jiubeirobot.ui.activity.shop.LocalUserCheckOrderActivity;
import com.fecmobile.jiubeirobot.ui.adapter.shop.LocalShippingAddressAdapter;
import com.fecmobile.jiubeirobot.ui.dialog.AlertDialog;
import com.fecmobile.jiubeirobot.ui.dialog.ConfirmDropPointDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人：TheJoker丶豪 on
 * 创建时间：2017/6/6.
 */

public class LocalShippingAddressFragment extends BaseFragment implements ItemClick, LocalShippingAddressAdapter.ClickLinsener {

    @BindView(R.id.rv_local_address_list)
    RecyclerView rvAddressList;
    @BindView(R.id.btn_local_new_add)
    Button btnNewAdd;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    private LocalShippingAddressAdapter shippingAddressAdapter;
    private List<LocalQueryAddressOtherBean> list = new ArrayList<>();

    private ConfirmDropPointDialog confirmDropPointDialog = new ConfirmDropPointDialog();

    @Override
    public int pageLayout() {
        return R.layout.fragment_local_shipping_address;
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {
        rvAddressList.addItemDecoration(new LinearItemDecoration(1));

        rvAddressList.setLayoutManager(new LinearLayoutManager(getContext()));
        shippingAddressAdapter = new LocalShippingAddressAdapter(getContext(), list);
        shippingAddressAdapter.setClickLinsener(this);
        rvAddressList.setAdapter(shippingAddressAdapter);
        onRefresh();
    }

    public void onRefresh() {
        //这是请求数据的地方
        APIManager.getInstance().getLocalAddressList(getContext(), this);
    }

    /**
     * 数据请求成功的回调
     *
     * @param bean
     * @param flag
     */
    @Override
    public void onSuccess(BaseBean bean, int flag) {
        switch (flag) {
            case FLags.FLAG_LOCAL_QUERY_CUSTOMER_INFO:
                BaseBean<ListBean<LocalQueryAddressOtherBean>> addressBean = bean;
                list.clear();
                list.addAll(addressBean.getData().getList());
                shippingAddressAdapter.notifyDataSetChanged();
                break;
            case FLags.FLAG_LOCAL_DELETE_ADDRESS:
                onRefresh();
                break;
        }
        dismissHUD();
    }

    @OnClick({R.id.btn_local_new_add, R.id.iv_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_local_new_add:
                if (getActivity() != null) {
                    ((LocalUserCheckOrderActivity) getActivity()).AddShippingAddress();
                }
                break;
            case R.id.iv_close:
                if (getActivity() != null) {
                    ((LocalUserCheckOrderActivity) getActivity()).close();
                }
                break;
        }
    }


    @Override
    public void update(int postion) {
        if (getActivity() != null) {
            ((LocalUserCheckOrderActivity) getActivity()).toEditAddress(list.get(postion));
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
                APIManager.getInstance().dropLocalAddress(getContext(), LocalShippingAddressFragment.this, list.get(postion).getId());
            }
        });
    }

//    /**
//     * 设置默认收货地址的方法
//     * @param position
//     */
//    @Override
//    public void setDefault(int position) {
//        APIManager.getInstance().setDefaultAddress(getContext(),this, String.valueOf(position));
//    }

    @Override
    public void onItemClick(int postion) {
        if (getActivity() != null) {
            ((LocalUserCheckOrderActivity) getActivity()).setAddrss(list.get(postion));
            ((LocalUserCheckOrderActivity) getActivity()).close();
        }
    }

}
