package com.fecmobile.jiubeirobot.ui.fragment.shop;

import android.text.TextUtils;
import android.util.Log;
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
import com.fecmobile.jiubeirobot.bean.LocalSubmitOrderPramsBean;
import com.fecmobile.jiubeirobot.bean.MemberSubmitOrderPramsBean;
import com.fecmobile.jiubeirobot.common.AddressSelectedView;
import com.fecmobile.jiubeirobot.common.MyScrollView;
import com.fecmobile.jiubeirobot.common.NoMoveEditTextView;
import com.fecmobile.jiubeirobot.common.view.BoderButtton;
import com.fecmobile.jiubeirobot.ui.activity.shop.LocalUserCheckOrderActivity;
import com.fecmobile.jiubeirobot.utils.T;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人：TheJoker丶豪 on
 * 创建时间：2017/6/6.
 * 本地购买  开具发票的fragment
 */

public class LocalSelectInvoiceTypeFragment extends BaseFragment {

    @BindView(R.id.bbtn_no_invoice)
    BoderButtton bbtnNoInvoice;
    @BindView(R.id.bbtn_general_invoice)
    BoderButtton bbtnGeneralInvoice;
    @BindView(R.id.bbtn_add_val_invoice)
    BoderButtton bbtnAddValInvoice;
    @BindView(R.id.llyt_invoice_content)
    LinearLayout llytInvoiceContent;
    @BindView(R.id.ll_new)
    LinearLayout llytNomal;

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
    @BindView(R.id.rb_geren)
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
    @BindView(R.id.rb_danwei)
    RadioButton rbDanwei;

    @BindView(R.id.rg_fapiao)
    RadioGroup rg_fapiao;

    //    普通发票
    @BindView(R.id.et_nomal_code)//纳税号码
            EditText etNomalcode;
    @BindView(R.id.et_nomal_tel)//电话
            EditText et_nomal_tel;
    @BindView(R.id.et_nomal_bank)//开户行
            EditText et_nomal_bank;
    @BindView(R.id.et_nomal_account)//银行账号
            EditText et_nomal_account;
    @BindView(R.id.et_nomal_name)//公司名
            EditText et_nomal_name;


    private LocalUserCheckOrderActivity userCheckOrderActivity;

    private int type;//发票类型
    private String province, city, district;


    @Override
    public int pageLayout() {
        userCheckOrderActivity = (LocalUserCheckOrderActivity) getActivity();
        return R.layout.fragment_local_select_invoice_type;
    }

    @Override
    public void initTitle() {
        userCheckOrderActivity = (LocalUserCheckOrderActivity) getActivity();
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
                LocalSelectInvoiceTypeFragment.this.city = city;
                LocalSelectInvoiceTypeFragment.this.province = province;
                LocalSelectInvoiceTypeFragment.this.district = district;
            }
        });

        etCompanyRegAddress.setScrollView(msvScroll);

        msvScroll.setLlytAddress(llytAddress);
    }

    @Override
    public void onSuccess(BaseBean bean, int flag) {

    }

    //1

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

                rg_fapiao.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId) {
                            case R.id.rb_danwei:
                                etGeneralName.setVisibility(View.GONE);
                                llytNomal.setVisibility(View.VISIBLE);
                                break;
                            case R.id.rb_geren:
                                etGeneralName.setVisibility(View.VISIBLE);
                                llytNomal.setVisibility(View.GONE);
                                break;
                        }
                    }
                });

//                llytAppreciation.setVisibility(View.VISIBLE);
//                llytInvoiceContent.setVisibility(View.GONE);

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
                        LocalSubmitOrderPramsBean pramsBean = new LocalSubmitOrderPramsBean();
                        pramsBean.setIsHaveInvoice(type == 1 ? "1" : "0"); //是否需要发票
                        pramsBean.setInvoiceType(type + "");
                        if (type == 2) {
                            pramsBean.setInvoiceTitle(rbPerson.isChecked() ? "0" : "1");
                            pramsBean.setCompanyName(etGeneralName.getText() + "");
                            pramsBean.setInvoiceContent(rbInvoiceContentPerson.isChecked() ? getString(R.string.common_personage) : getString(R.string.shopping_check_invoice_catering));
                            pramsBean.setCompanyName(et_nomal_name.getText() + "");//公司名
                            pramsBean.setRatepayerCode(etNomalcode.getText() + "");//纳税号码
                            pramsBean.setInvoiceTel(et_nomal_tel.getText() + "");//电话号码
                            pramsBean.setDepositBank(et_nomal_bank.getText() + "");//开户银行
                            pramsBean.setDepositAccount(et_nomal_account.getText() + "");//银行账号

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
                        Log.d("print", "onClick: " + pramsBean.toString());
                    }
                    userCheckOrderActivity.close();
                }
        }
    }


    private boolean checkPage() {
        if (type == 2) {

            if (rbPerson.isChecked()) {
                if (TextUtils.isEmpty(etGeneralName.getText())) {
                    T.showToCenter(getString(R.string.shopping_check_invoice_person_hint));
                    return false;
                }
            }


            if (rbDanwei.isChecked()) {
                if (TextUtils.isEmpty(et_nomal_name.getText())) {
                    T.showToCenter(getString(R.string.shopping_check_unit_name_hint));
                    return false;
                }
                if (TextUtils.isEmpty(etNomalcode.getText())) {
                    T.showToCenter(getString(R.string.shopping_check_pay_taxes));
                    return false;
                }
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
