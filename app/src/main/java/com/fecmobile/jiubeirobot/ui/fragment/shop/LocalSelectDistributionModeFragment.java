package com.fecmobile.jiubeirobot.ui.fragment.shop;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.base.BaseFragment;
import com.fecmobile.jiubeirobot.common.view.BoderButtton;
import com.fecmobile.jiubeirobot.common.view.EditTextWithDel;
import com.fecmobile.jiubeirobot.data.net.APIManager;
import com.fecmobile.jiubeirobot.ui.activity.shop.LocalUserCheckOrderActivity;
import com.fecmobile.jiubeirobot.utils.RegexUtils;
import com.fecmobile.jiubeirobot.utils.SharedPreferencesUtils;
import com.fecmobile.jiubeirobot.utils.T;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 创建人：TheJoker丶豪 on
 * 创建时间：2017/6/6.
 * 本地购买 选择配送方式
 */

public class LocalSelectDistributionModeFragment extends BaseFragment {
    @BindView(R.id.rb_no_need)
    BoderButtton rbNoNeed;
    @BindView(R.id.rb_need)
    BoderButtton rbNeed;
    @BindView(R.id.btn_submit_order)
    Button btnSubmitOrder;
    @BindView(R.id.iv_close)
    ImageView ivClose;

    @BindView(R.id.et_Telnumber)
    EditTextWithDel etTelNumber;

    private String etnumber;

    private LocalUserCheckOrderActivity userCheckOrderActivity;

    @Override
    public int pageLayout() {
        return R.layout.fragment_local_select_distribution_mode;
    }

    @Override
    public void initTitle() {
        userCheckOrderActivity = (LocalUserCheckOrderActivity) getActivity();

    }

    @Override
    public void initView() {
        rbNoNeed.setText(getString(R.string.shopping_check_no_need_delivery));
        rbNeed.setText(getString(R.string.shopping_check_need_delivery));

        rbNeed.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbNeed.setChecked(true);
                rbNoNeed.setChecked(false);
            }
        });
        rbNoNeed.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rbNoNeed.setChecked(true);
                rbNeed.setChecked(false);
            }
        });

        rbNoNeed.setChecked(true);

    }

    @Override
    public void onSuccess(BaseBean bean, int flag) {
    }

    @OnClick({R.id.btn_submit_order, R.id.iv_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit_order:
                //这个地方需要判断一下手机号码的输入框是否为空以及是否满足手机号码的基本格式
                etnumber = etTelNumber.getText().toString().trim();
                SharedPreferencesUtils.putString(getContext(), "etphonenumber", etnumber);

                if (rbNoNeed.isChecked()) {
                    if (etnumber != null && !etnumber.equals("")) {
                        if (RegexUtils.checkMobile(etnumber)) {
                            userCheckOrderActivity.noNeed();
                            userCheckOrderActivity.close();
                            APIManager.getInstance().checkLocalCustomerInfo(etnumber, getContext());
                        } else {
                            T.showToCenter("请输入正确的手机号码");
                        }
                    } else if (etnumber.equals("")) {
                        userCheckOrderActivity.noNeed();
                        userCheckOrderActivity.close();
                    }
                } else if (rbNeed.isChecked()) {
                    if (userCheckOrderActivity != null) {
                        //TODO 判断当前的情况是 需不需要配送  以及返回的ID是否为空   不为空则显示默认的地址和手机号在配送地址  为空则显示一个手机号
                        if (RegexUtils.checkMobile(etnumber)) {
                            APIManager.getInstance().checkLocalCustomerInfo(etnumber, getContext());
                            userCheckOrderActivity.need();
                            userCheckOrderActivity.close();
                        } else {
                            T.showToCenter("请输入正确的手机号码");
                        }
                    }
                }
                break;
            case R.id.iv_close:
                if (userCheckOrderActivity != null) {
                    userCheckOrderActivity.close();
                }
                break;
        }
    }
}
