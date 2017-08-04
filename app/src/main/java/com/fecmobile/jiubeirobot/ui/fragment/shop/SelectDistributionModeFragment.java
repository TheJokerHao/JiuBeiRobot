package com.fecmobile.jiubeirobot.ui.fragment.shop;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.base.BaseFragment;
import com.fecmobile.jiubeirobot.common.view.BoderButtton;
import com.fecmobile.jiubeirobot.ui.activity.shop.UserCheckOrderActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类描述    : 选择配送方式
 * 包名      : com.fecmobile.jiubeirobot.ui.fragment.shop
 * 类名称    : SelectDistributionModeFragment
 * 创建人    : ghy
 * 创建时间  : 2017/3/2 15:34
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class SelectDistributionModeFragment extends BaseFragment {
    @BindView(R.id.rb_no_need)
    BoderButtton rbNoNeed;
    @BindView(R.id.rb_need)
    BoderButtton rbNeed;
    @BindView(R.id.btn_submit_order)
    Button btnSubmitOrder;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    private UserCheckOrderActivity userCheckOrderActivity;

    @Override
    public int pageLayout() {
        return R.layout.fragment_select_distribution_mode;
    }

    @Override
    public void initTitle() {
        userCheckOrderActivity = (UserCheckOrderActivity) getActivity();
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
                if (userCheckOrderActivity != null) {
                    if (rbNeed.isChecked()) {
                        userCheckOrderActivity.need();
                    } else {
                        userCheckOrderActivity.noNeed();
                    }
                    userCheckOrderActivity.close();
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
