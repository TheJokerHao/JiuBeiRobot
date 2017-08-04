package com.fecmobile.jiubeirobot.ui.fragment.shop;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.base.BaseFragment;
import com.fecmobile.jiubeirobot.bean.MemberSubmitOrderPramsBean;
import com.fecmobile.jiubeirobot.common.AddressSelectedView;
import com.fecmobile.jiubeirobot.common.MyScrollView;
import com.fecmobile.jiubeirobot.common.NoMoveEditTextView;
import com.fecmobile.jiubeirobot.common.view.BoderButtton;
import com.fecmobile.jiubeirobot.ui.activity.shop.UserCheckOrderActivity;
import com.fecmobile.jiubeirobot.utils.T;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类描述    :选择发票类型
 * 包名      : com.fecmobile.jiubeirobot.ui.fragment.shop
 * 类名称    : SelectInvoiceTypeFragment
 * 创建人    : ghy
 * 创建时间  : 2017/3/3 10:37
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class SelectInvoiceTypeFragment extends BaseFragment {
    @BindView(R.id.bbtn_no_invoice)
    BoderButtton bbtnNoInvoice;
    @BindView(R.id.bbtn_general_invoice)
    BoderButtton bbtnGeneralInvoice;
    @BindView(R.id.bbtn_add_val_invoice)
    BoderButtton bbtnAddValInvoice;
    @BindView(R.id.llyt_invoice_content)
    LinearLayout llytInvoiceContent;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.llyt_address)
    AddressSelectedView llytAddress;
    @BindView(R.id.tv_addres)
    TextView tvAddres;
    @BindView(R.id.llyt_appreciation)
    LinearLayout llytAppreciation;
    @BindView(R.id.btn_submit_order)
    Button btnSubmitOrder;
    @BindView(R.id.rb_person)
    RadioButton rbPerson;
    @BindView(R.id.et_general_name)
    EditText etGeneralName;
    @BindView(R.id.rb_invoiceContent_person)
    RadioButton rbInvoiceContentPerson;
    @BindView(R.id.et_appreciation_name)
    EditText etAppreciationName;
    @BindView(R.id.rg_invoiceContent)
    RadioGroup rgInvoiceContent;
    @BindView(R.id.et_ratepayer_code)
    EditText etRatepayerCode;
    @BindView(R.id.et_company_reg_address)
    NoMoveEditTextView etCompanyRegAddress;
    @BindView(R.id.et_deposit_bank)
    EditText etDepositBank;
    @BindView(R.id.et_deposit_account)
    EditText etDepositAccount;
    @BindView(R.id.et_invoice_name)
    EditText etInvoiceName;
    @BindView(R.id.et_invoice_address)
    EditText etInvoiceAddress;
    @BindView(R.id.et_invoice_tel)
    EditText etInvoiceTel;
    @BindView(R.id.et_company_tel)
    EditText etCompanyTel;
    @BindView(R.id.msv_scroll)
    MyScrollView msvScroll;

    private UserCheckOrderActivity userCheckOrderActivity;

    private int type;//发票类型
    private String province, city, district;

    @Override
    public int pageLayout() {
//        userCheckOrderActivity = (UserCheckOrderActivity) getActivity();
        return R.layout.fragment_select_invoice_type;
    }

    @Override
    public void initTitle() {
        userCheckOrderActivity = (UserCheckOrderActivity) getActivity();
    }

    @Override
    public void initView() {
        bbtnNoInvoice.setText(getString(R.string.shopping_check_no_invoice));
        bbtnGeneralInvoice.setText(getString(R.string.shopping_check_general_invoice));
        bbtnAddValInvoice.setText(getString(R.string.shopping_check_add_val_invoice));
        bbtnNoInvoice.setChecked(true);

        bbtnNoInvoice.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectInvoiceType(0);
            }
        });
        bbtnGeneralInvoice.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectInvoiceType(1);
            }
        });
        bbtnAddValInvoice.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectInvoiceType(2);
            }
        });

        llytAddress.setCancelClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llytAddress.setVisibility(View.GONE);
            }
        });
        llytAddress.setOptionsSelectListener(new AddressSelectedView.OptionsSelectListener() {
            @Override
            public void onOptionsSelect(String province, String city, String district, View v) {
                tvAddres.setText(province + " " + city + " " + district);
                llytAddress.setVisibility(View.GONE);
                SelectInvoiceTypeFragment.this.city = city;
                SelectInvoiceTypeFragment.this.province = province;
                SelectInvoiceTypeFragment.this.district = district;
            }
        });

        etCompanyRegAddress.setScrollView(msvScroll);

        msvScroll.setLlytAddress(llytAddress);
    }

    @Override
    public void onSuccess(BaseBean bean, int flag) {

    }


    private void selectInvoiceType(int type) {
        switch (type) {
            case 0:
                this.type = 1;
                bbtnNoInvoice.setChecked(true);
                bbtnGeneralInvoice.setChecked(false);
                bbtnAddValInvoice.setChecked(false);
                llytInvoiceContent.setVisibility(View.GONE);
                llytAppreciation.setVisibility(View.GONE);
                break;
            case 1:
                this.type = 2;
                bbtnNoInvoice.setChecked(false);
                bbtnGeneralInvoice.setChecked(true);
                bbtnAddValInvoice.setChecked(false);
                llytInvoiceContent.setVisibility(View.VISIBLE);
                llytAppreciation.setVisibility(View.GONE);
                break;
            case 2:
                this.type = 3;
                bbtnNoInvoice.setChecked(false);
                bbtnGeneralInvoice.setChecked(false);
                bbtnAddValInvoice.setChecked(true);
                llytAppreciation.setVisibility(View.VISIBLE);
                llytInvoiceContent.setVisibility(View.GONE);
                break;
        }
    }


    @OnClick({R.id.iv_close, R.id.tv_addres, R.id.btn_submit_order})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                if (userCheckOrderActivity != null) {
                    userCheckOrderActivity.close();
                }

                break;
            case R.id.tv_addres:
                if (llytAddress.getVisibility() == View.VISIBLE) {
                    llytAddress.setVisibility(View.GONE);
                } else {
                    llytAddress.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.btn_submit_order:
                if (!checkPage()) {
                    return;
                }
                if (userCheckOrderActivity != null) {
                    if (userCheckOrderActivity != null) {
                        MemberSubmitOrderPramsBean pramsBean = new MemberSubmitOrderPramsBean();
                        pramsBean.setIsHaveInvoice(type == 1 ? "1" : "0"); //是否需要发票
                        pramsBean.setInvoiceType(type + "");
                        if (type == 2) {
                            pramsBean.setInvoiceTitle(rbPerson.isChecked() ? "0" : "1");
                            pramsBean.setCompanyName(etGeneralName.getText() + "");
                            pramsBean.setInvoiceContent(rbInvoiceContentPerson.isChecked() ? getString(R.string.common_personage) : getString(R.string.shopping_check_invoice_catering));
                        } else if (type == 3) {
                            pramsBean.setCompanyName(etAppreciationName.getText() + "");
                            pramsBean.setRatepayerCode(etRatepayerCode.getText() + "");
                            pramsBean.setCompanyRegAddress(etCompanyRegAddress.getText() + "");
                            pramsBean.setCompanyTel(etCompanyTel.getText() + "");
                            pramsBean.setDepositBank(etDepositBank.getText() + "");
                            pramsBean.setDepositAccount(etDepositAccount.getText() + "");
                            pramsBean.setInvoiceName(etInvoiceName.getText() + "");
                            pramsBean.setInvoiceTel(etInvoiceTel.getText() + "");
                            pramsBean.setInvoicePro(province);
                            pramsBean.setInvoiceCity(city);
                            pramsBean.setInvoiceArea(district);
                            pramsBean.setInvoiceAddress(etInvoiceAddress.getText() + "");
                        }
                        userCheckOrderActivity.setInvoiceType(pramsBean);
                    }
                    userCheckOrderActivity.close();
                }
        }
    }

    private boolean checkPage() {
        if (type == 2) {
            if (TextUtils.isEmpty(etGeneralName.getText())) {
                T.showToCenter(getString(R.string.shopping_check_invoice_person_hint));
                return false;
            }
        } else if (type == 3) {
            if (TextUtils.isEmpty(etAppreciationName.getText())) {
                T.showToCenter(getString(R.string.shopping_check_unit_name_hint));
                return false;
            }
            if (TextUtils.isEmpty(etRatepayerCode.getText())) {
                T.showToCenter(getString(R.string.shopping_check_pay_taxes));
                return false;
            }
            if (TextUtils.isEmpty(etCompanyRegAddress.getText())) {
                T.showToCenter(getString(R.string.shopping_check_register_address));
                return false;
            }
            if (TextUtils.isEmpty(etCompanyTel.getText())) {
                T.showToCenter(getString(R.string.shopping_check_tiket_phone));
                return false;
            }
            if (TextUtils.isEmpty(etDepositBank.getText())) {
                T.showToCenter(getString(R.string.shopping_check_account_bank));
                return false;
            }
            if (TextUtils.isEmpty(etDepositAccount.getText())) {
                T.showToCenter(getString(R.string.shopping_check_account_bank));
                return false;
            }
            if (TextUtils.isEmpty(etInvoiceName.getText())) {
                T.showToCenter(getString(R.string.shopping_check_tiket_name));
                return false;
            }
            if (TextUtils.isEmpty(tvAddres.getText())) {
                T.showToCenter(getString(R.string.shopping_check_tiket_address));
                return false;
            }
            if (TextUtils.isEmpty(etInvoiceAddress.getText())) {
                T.showToCenter(getString(R.string.shopping_check_tiket_details_address));
                return false;
            }
            if (TextUtils.isEmpty(etInvoiceTel.getText())) {
                T.showToCenter(getString(R.string.shopping_check_tiket_phone));
                return false;
            }
        }
        return true;
    }

}
