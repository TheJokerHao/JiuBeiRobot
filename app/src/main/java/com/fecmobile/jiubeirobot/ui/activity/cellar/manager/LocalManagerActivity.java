package com.fecmobile.jiubeirobot.ui.activity.cellar.manager;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseActivity;
import com.fecmobile.jiubeirobot.base.BaseApplication;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.bean.ObjBean;
import com.fecmobile.jiubeirobot.bean.PendingOrderNumBean;
import com.fecmobile.jiubeirobot.common.Activitys;
import com.fecmobile.jiubeirobot.common.view.CircularImageView;
import com.fecmobile.jiubeirobot.common.view.Hexagon;
import com.fecmobile.jiubeirobot.data.net.APIManager;
import com.fecmobile.jiubeirobot.data.net.FLags;
import com.fecmobile.jiubeirobot.ui.dialog.cellar.manage.SettingChangePasswordDetailDialog;
import com.fecmobile.jiubeirobot.ui.dialog.cellar.manage.SettingChangePasswordDialog;
import com.fecmobile.jiubeirobot.ui.dialog.cellar.manage.SystemManageSettingDialog;
import com.fecmobile.jiubeirobot.utils.GlideImageLoadImpl;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 本地管理的activity
 * 主要是针对 本地购买的商品的管理  跟系统平台上架的是有区别的
 */

public class LocalManagerActivity extends BaseActivity {
    //布局开始
    @BindView(R.id.local_civ_head_photo)
    CircularImageView mCivHeadPhoto;
    @BindView(R.id.local_tv_user_name)
    TextView mTvUserName;
    @BindView(R.id.local_tv_chateau)
    LinearLayout mTvChateau;

    @BindView(R.id.local_iv_setting)
    ImageView mIvSetting;
    @BindView(R.id.local_tv_user_account)
    TextView mTvUserAccount;
    @BindView(R.id.local_tv_cellar_name)
    TextView mTvCellarName;
    @BindView(R.id.local_iv_back)
    ImageView ivBack;

    @BindView(R.id.tv_localmanage_title)
    TextView tv_Localmanage_title;

    private SystemManageSettingDialog mSystemManageSettingDialog;
    private String mAid;
    private String mBid;
    private String mUid;
    private String mAccount;
    private String mBusiCompName;
    private String mBusiCompLogo_url;
    private SettingChangePasswordDetailDialog mSettingChangePasswordDetailDialog;


    //    最新修改的布局文件
    //未付款
    @BindView(R.id.hex_order_manage)
    Hexagon hex_order_manage;
    //未发货
    @BindView(R.id.hex_nopay)
    Hexagon HXnosen;
    //订单
    @BindView(R.id.hex_nosend)
    Hexagon HXOrederManage;
    //酒窖存储
    @BindView(R.id.hex_save_manage)
    Hexagon hex_save_manage;
    //客户管理
    @BindView(R.id.hex_custmer_manage)
    Hexagon hex_custmer_manage;
    //返回键
    @BindView(R.id.ll_back)
    LinearLayout llback;

    private SettingChangePasswordDialog mSettingChangePasswordDialog;
    private SettingChangePasswordDetailDialog mSettingChangePasswordDetailDialog1;
    private String mUserName;
    //布局结束

    @Override
    public int pageLayout() {
        return R.layout.activity_local_manager;
    }

    @Override
    public void initTitle() {
        //设置number为0的情况下  不显示红色圆点和数字
//        hex_save_manage.setNumber(0);
        HXOrederManage.setNumber(0);
        hex_custmer_manage.setNumber(0);

    }

    /**
     * 初始化 intent数据
     */
    @Override
    public void initView() {
        initIntent();
    }

    /**
     * 在这个获取本地购买的订单信息
     */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d("print", "LocalManagerActivity的onResume: ");
        APIManager.getInstance().getLocalOrderNum(this);
    }

    private void initIntent() {
        mAid = getIntent().getStringExtra("aid");
        mBid = getIntent().getStringExtra("bid");
        mUid = getIntent().getStringExtra("uid");
        mAccount = getIntent().getStringExtra("account");
        mBusiCompName = getIntent().getStringExtra("busiCompName");
        mBusiCompLogo_url = getIntent().getStringExtra("busiCompLogo_url");
        mUserName = getIntent().getStringExtra("userName");

        Log.d("print", "initIntent: 这是LocalManagerActivitty接收到的一系列的参数"
                + mAid + "   " + mAccount + "   " + mBid + "   " + mBusiCompLogo_url + "  "
                + mBusiCompName + "  " + mUid + "  " + mUserName);

        mTvUserAccount.setText(mAccount);
        mTvCellarName.setText(mBusiCompName);
        mTvUserName.setText(mUserName);

        mSettingChangePasswordDetailDialog = new SettingChangePasswordDetailDialog();
        //加载头像图片
        GlideImageLoadImpl.getInstance().load(this, mBusiCompLogo_url, mCivHeadPhoto);
    }

    @Override
    public void onSuccess(final BaseBean bean, int flag) {
        switch (flag) {
            case FLags.FLAG_ORDER_FINISH:
                //订单查询完成
                BaseBean<ObjBean<PendingOrderNumBean>> pOrderNum = bean;
                PendingOrderNumBean numBean = pOrderNum.getData().getObj();
                if (numBean.getNonPayment() > 0) {
                    hex_order_manage.setNumber(numBean.getNonPayment());
                } else {
                    hex_order_manage.setNumber(0);
                }
                if (numBean.getUnfilledOrders() > 0) {
                    HXnosen.setNumber(numBean.getUnfilledOrders());
                } else {
                    HXnosen.setNumber(0);
                }
                if (numBean.getOwnWineNum() > 0) {
                    hex_save_manage.setNumber(numBean.getOwnWineNum());
                } else {
                    hex_save_manage.setNumber(0);
                }

                break;
        }
        dismissHUD();
    }

    /**
     * 绑定相对应的监听
     * 在这里我修改了一些东西  修改的东西主要是取消了两个按钮  功能上面做了一些修改 少了代存储和自有酒品的分类
     * 这个管理的主要功能就是对本地购买下单的订单的管理
     *
     * @param view
     */
    @OnClick({R.id.ll_back, R.id.hex_nosend, R.id.hex_nopay, R.id.hex_order_manage, R.id.hex_custmer_manage, R.id.hex_save_manage, R.id.local_iv_setting})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.hex_order_manage:
                //未付款
                Activitys.toLocalOrderList(this, 1);
                break;
            case R.id.hex_nopay:
                //发货
                Activitys.toLocalOrderList(this, 2);
                break;
            case R.id.hex_nosend:
                //销售订单管理
                Activitys.toLocalOrderList(this, 0);
                break;
            case R.id.hex_save_manage:
                //酒窖存储管理
                Activitys.toStorageLocalCellarTab(this, "0");
                break;
            case R.id.local_iv_setting:
                //设置
                if (mSystemManageSettingDialog == null) {
                    mSystemManageSettingDialog = new SystemManageSettingDialog();
                }
                mSystemManageSettingDialog.show(getSupportFragmentManager(), "systemManageSettingDialog");
                Bundle bundle = new Bundle();
                bundle.putString("account", mAccount);
                bundle.putString("aid", mAid);
                mSystemManageSettingDialog.setArguments(bundle);
                break;
            case R.id.ll_back:
                finish();
                break;
            case R.id.hex_custmer_manage:
                Activitys.toCustomerManager(LocalManagerActivity.this);
                //点击客户管理
                break;
            default:
                break;
        }
    }
}