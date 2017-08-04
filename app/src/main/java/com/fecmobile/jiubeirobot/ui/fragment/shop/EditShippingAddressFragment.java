package com.fecmobile.jiubeirobot.ui.fragment.shop;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseApplication;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.base.BaseFragment;
import com.fecmobile.jiubeirobot.bean.MemberAddressBean;
import com.fecmobile.jiubeirobot.common.AddressSelectedView;
import com.fecmobile.jiubeirobot.data.net.APIManager;
import com.fecmobile.jiubeirobot.data.net.FLags;
import com.fecmobile.jiubeirobot.ui.activity.shop.UserCheckOrderActivity;
import com.fecmobile.jiubeirobot.utils.L;
import com.fecmobile.jiubeirobot.utils.RegexUtils;
import com.fecmobile.jiubeirobot.utils.T;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类描述    :编辑收货地址
 * 包名      : com.fecmobile.jiubeirobot.ui.fragment.shop
 * 类名称    : EditShippingAddressFragment
 * 创建人    : ghy
 * 创建时间  : 2017/3/3 14:24
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class EditShippingAddressFragment extends BaseFragment {
    @BindView(R.id.llyt_address)
    AddressSelectedView llytAddress;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.llyt_address_item)
    LinearLayout llytAddressItem;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.btn_save)
    Button btnSave;
    @BindView(R.id.et_consignee)
    EditText etConsignee;
    @BindView(R.id.et_consignee_phone)
    EditText etConsigneePhone;
    @BindView(R.id.et_consignee_details_address)
    EditText etConsigneeDetailsAddress;
    private String province;
    private String city;
    private String district;
    private UserCheckOrderActivity userCheckOrderActivity;
    private MemberAddressBean memberAddressBean;

    @Override
    public int pageLayout() {
        userCheckOrderActivity = (UserCheckOrderActivity) getActivity();
        return R.layout.fragment_edit_shipping_address;
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {
        llytAddress.setCancelClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llytAddress.setVisibility(View.GONE);
            }
        });
        llytAddress.setOptionsSelectListener(new AddressSelectedView.OptionsSelectListener() {
            @Override
            public void onOptionsSelect(String province, String city, String district, View v) {
                tvAddress.setText(province + " " + city + " " + district);
                EditShippingAddressFragment.this.province = province;
                EditShippingAddressFragment.this.city = city;
                EditShippingAddressFragment.this.district = district;
                llytAddress.setVisibility(View.GONE);
            }
        });
    }

    public void getContent(MemberAddressBean m) {
        this.memberAddressBean = m;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (memberAddressBean != null) {
            etConsignee.setText(memberAddressBean.getDeliveryName());
            etConsigneePhone.setText(memberAddressBean.getDeliveryPhone());
            if (memberAddressBean.getDeliveryProv() == null) {
                tvAddress.setText(getString(R.string.shopping_check_shipping_address));
            } else {
                province = memberAddressBean.getDeliveryProv();
                city = memberAddressBean.getDeliveryCity();
                district = memberAddressBean.getDeliveryArea();
                tvAddress.setText(memberAddressBean.getDeliveryProv() + "  " + memberAddressBean.getDeliveryCity() + "  " + memberAddressBean.getDeliveryArea());
            }
            etConsigneeDetailsAddress.setText(memberAddressBean.getDeliveryAddr());
        }
    }

    @Override
    public void onSuccess(BaseBean bean, int flag) {
        switch (flag) {
            case FLags.FLAG_MEMER_ADD_ADDRESS:
                if (userCheckOrderActivity != null) {
                    userCheckOrderActivity.closeRefreshAddress();
                    if (isAdded()) {
                        T.showToCenter(getString(R.string.common_success));
                    }
                }
                break;
            case FLags.FLAG_UPDATE_MEMBER_ADDRESS:
                if (getActivity() != null) {
                    userCheckOrderActivity.closeRefreshAddress();
                    if (isAdded()) {
                        T.showToCenter(getString(R.string.common_success));
                    }
                }
                break;
        }
    }


    @OnClick({R.id.llyt_address, R.id.llyt_address_item, R.id.iv_close, R.id.btn_save})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llyt_address_item:
                if (llytAddress.getVisibility() == View.VISIBLE) {
                    llytAddress.setVisibility(View.GONE);
                } else {
                    llytAddress.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.iv_close:
                if (getActivity() != null) {
                    getActivity().onBackPressed();
                }
                break;
            case R.id.btn_save:
                if (!checkPageData()) {
                    return;
                }
                L.i("***********************checkPageData");
                MemberAddressBean m = new MemberAddressBean();
                m.setDeliveryName(etConsignee.getText() + "");
                m.setDeliveryProv(province);
                m.setDeliveryCity(city);
                m.setDeliveryArea(district);
                m.setDeliveryAddr(etConsigneeDetailsAddress.getText() + "");
                m.setDeliveryPhone(etConsigneePhone.getText() + "");
                if (BaseApplication.currentMode == BaseApplication.MEMBER_BUY) {
                    //会员购买
                    if (memberAddressBean != null && memberAddressBean.getId() != null) {
                        m.setCid(memberAddressBean.getCid());
                        m.setId(memberAddressBean.getId());
                        APIManager.getInstance().updateMemberAddress(getContext(), this, m);
                    } else {
                        APIManager.getInstance().addMemberAddress(getContext(), this, m);
                    }
                } else if (BaseApplication.currentMode == BaseApplication.FAST_BUY) {
                    //快速购买
                    if (getActivity() != null) {
                        L.i("***********************getActivity");
                        userCheckOrderActivity = (UserCheckOrderActivity) getActivity();
                        userCheckOrderActivity.setAddrss(m);
                        userCheckOrderActivity.close();
                    }
                } else if (BaseApplication.currentMode == BaseApplication.LOCAL_BUY) {
                    //TODO  在编辑配送地址的时候添加了本地购买的配送地址
                    if (getActivity() != null) {
                        L.i("***********************getActivity");
                        userCheckOrderActivity = (UserCheckOrderActivity) getActivity();
                        userCheckOrderActivity.setAddrss(m);
                        userCheckOrderActivity.close();
                    }
                }
                break;
        }
    }

    private boolean checkPageData() {
        if (TextUtils.isEmpty(etConsignee.getText())) {
            T.showToCenter(getString(R.string.shopping_check_consignee_hint));
            return false;
        }
        if (TextUtils.isEmpty(etConsigneePhone.getText())) {
            T.showToCenter(getString(R.string.shopping_check_consignee_phone_hint));
            return false;
        }
        if (!RegexUtils.checkPhone(etConsigneePhone.getText() + "")) {
            T.showToCenter(getString(R.string.common_point_phone_format_error));
            return false;
        }
        if (TextUtils.isEmpty(tvAddress.getText()) || getString(R.string.shopping_check_shipping_address).equals(tvAddress.getText())) {
            T.showToCenter(getString(R.string.shopping_check_input_shipping_address));
            return false;
        }
        if (TextUtils.isEmpty(etConsigneeDetailsAddress.getText())) {
            T.showToCenter(getString(R.string.shopping_check_details_hint));
            return false;
        }
        return true;
    }
}
