package com.fecmobile.jiubeirobot.ui.activity.cellar.manager;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseActivity;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.bean.ObjBean;
import com.fecmobile.jiubeirobot.bean.PendingOrderNumBean;
import com.fecmobile.jiubeirobot.common.Activitys;
import com.fecmobile.jiubeirobot.common.view.CircularImageView;
import com.fecmobile.jiubeirobot.data.net.APIManager;
import com.fecmobile.jiubeirobot.data.net.FLags;
import com.fecmobile.jiubeirobot.ui.dialog.cellar.manage.SettingChangePasswordDetailDialog;
import com.fecmobile.jiubeirobot.ui.dialog.cellar.manage.SettingChangePasswordDialog;
import com.fecmobile.jiubeirobot.ui.dialog.cellar.manage.SystemManageSettingDialog;
import com.fecmobile.jiubeirobot.utils.GlideImageLoadImpl;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类描述    :系统管理首页
 * 包名      : com.fecmobile.jiubeirobot.ui.activity.cellar.manager
 * 类名称    : SysMainManagerActivity
 * 创建人    : ghy
 * 创建时间  : 2017/3/10 19:26
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class SysMainManagerActivity extends BaseActivity {
    @BindView(R.id.civ_head_photo)
    CircularImageView mCivHeadPhoto;
    @BindView(R.id.tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.tv_chateau)
    LinearLayout mTvChateau;
    @BindView(R.id.llyt_cellar_storage)
    LinearLayout mLlytCellarStorage;
    @BindView(R.id.iv_setting)
    ImageView mIvSetting;
    @BindView(R.id.tv_user_account)
    TextView mTvUserAccount;
    @BindView(R.id.tv_cellar_name)
    TextView mTvCellarName;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_non_payment)
    TextView tvNonPayment;
    @BindView(R.id.tv_unfilled_orders)
    TextView tvUnfilledOrders;
    @BindView(R.id.tv_own_wine_num)
    TextView tvOwnWineNum;
    @BindView(R.id.tv_generation_wine_num)
    TextView tvGenerationWineNum;
    private SystemManageSettingDialog mSystemManageSettingDialog;
    private String mAid;
    private String mBid;
    private String mUid;
    private String mAccount;
    private String mBusiCompName;
    private String mBusiCompLogo_url;
    private SettingChangePasswordDetailDialog mSettingChangePasswordDetailDialog;

    @BindView(R.id.lin_system_order_manager)
    LinearLayout linOrderManager;
    @BindView(R.id.lin_system_order_unpay)
    LinearLayout linOrderUnpay;
    @BindView(R.id.lin_system_order_unsend)
    LinearLayout linOrderUnsend;
    private SettingChangePasswordDialog mSettingChangePasswordDialog;
    private SettingChangePasswordDetailDialog mSettingChangePasswordDetailDialog1;
    private String mUserName;

    @Override
    public int pageLayout() {
        return R.layout.activity_sys_main_manager;
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {
        initIntent();

    }

    @Override
    protected void onResume() {
        super.onResume();
        APIManager.getInstance().getOrderNum(this);
    }

    private void initIntent() {
        mAid = getIntent().getStringExtra("aid");
        mBid = getIntent().getStringExtra("bid");
        mUid = getIntent().getStringExtra("uid");
        mAccount = getIntent().getStringExtra("account");
        mBusiCompName = getIntent().getStringExtra("busiCompName");
        mBusiCompLogo_url = getIntent().getStringExtra("busiCompLogo_url");
        mUserName = getIntent().getStringExtra("userName");

        Log.d("print", "initIntent: 这是SysManagerActivitty接收到的一系列的参数"
                + mAid + mAccount + mBid + mBusiCompLogo_url + mBusiCompName + mUid + mUserName);

        mTvUserAccount.setText(mAccount);
        mTvCellarName.setText(mBusiCompName);
        mTvUserName.setText(mUserName);

        mSettingChangePasswordDetailDialog = new SettingChangePasswordDetailDialog();


        GlideImageLoadImpl.getInstance().load(this, mBusiCompLogo_url, mCivHeadPhoto);
    }

    @Override
    public void onSuccess(final BaseBean bean, int flag) {
        switch (flag) {
            case FLags.FLAG_ORDER_FINISH:
                BaseBean<ObjBean<PendingOrderNumBean>> pOrderNum = bean;
                PendingOrderNumBean numBean = pOrderNum.getData().getObj();
                if (numBean.getGenerationWineNum() > 0) {
                    tvGenerationWineNum.setVisibility(View.VISIBLE);
                    tvGenerationWineNum.setText(numBean.getGenerationWineNum() + "");
                } else {
                    tvGenerationWineNum.setVisibility(View.GONE);
                }
                if (numBean.getNonPayment() > 0) {
                    tvNonPayment.setVisibility(View.VISIBLE);
                    tvNonPayment.setText(numBean.getNonPayment() + "");
                } else {
                    tvNonPayment.setVisibility(View.GONE);
                }
                if (numBean.getOwnWineNum() > 0) {
                    tvOwnWineNum.setVisibility(View.VISIBLE);
                    tvOwnWineNum.setText(numBean.getOwnWineNum() + "");
                } else {
                    tvOwnWineNum.setVisibility(View.GONE);
                }
                if (numBean.getUnfilledOrders() > 0) {
                    tvUnfilledOrders.setVisibility(View.VISIBLE);
                    tvUnfilledOrders.setText(numBean.getUnfilledOrders() + "");
                } else {
                    tvUnfilledOrders.setVisibility(View.GONE);
                }
                break;
        }
        dismissHUD();
    }

    /**
     * 使用butterkniffer 来绑定监听点击事件
     *
     * @param view
     */
    @OnClick({R.id.llyt_my_cellar, R.id.llyt_agent_cellar, R.id.lin_system_order_manager, R.id.lin_system_order_unpay, R.id.lin_system_order_unsend, R.id.llyt_cellar_storage, R.id.iv_setting, R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.lin_system_order_manager:
                Activitys.toOrderList(this, 0);
                break;
            case R.id.lin_system_order_unpay:
                Activitys.toOrderList(this, 1);
                break;
            case R.id.lin_system_order_unsend:
                Activitys.toOrderList(this, 2);
                break;
            case R.id.llyt_cellar_storage:
                Activitys.toStorageCellarTab(this, "0");
                break;
            case R.id.iv_setting:
                if (mSystemManageSettingDialog == null) {
                    mSystemManageSettingDialog = new SystemManageSettingDialog();
                }
                mSystemManageSettingDialog.show(getSupportFragmentManager(), "systemManageSettingDialog");
                Bundle bundle = new Bundle();
                bundle.putString("account", mAccount);
                bundle.putString("aid", mAid);
                mSystemManageSettingDialog.setArguments(bundle);
                break;
            case R.id.llyt_my_cellar:
                Activitys.toStorageCellarTab(this, "1");
                break;
            case R.id.llyt_agent_cellar:
                Activitys.toStorageCellarTab(this, "2");
                break;
            default:
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}