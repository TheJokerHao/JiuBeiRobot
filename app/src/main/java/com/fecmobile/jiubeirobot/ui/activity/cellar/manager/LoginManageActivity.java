package com.fecmobile.jiubeirobot.ui.activity.cellar.manager;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.BuildConfig;
import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseActivity;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.base.BaseData;
import com.fecmobile.jiubeirobot.bean.LoginManagerBean;
import com.fecmobile.jiubeirobot.bean.ObjBean;
import com.fecmobile.jiubeirobot.common.Activitys;
import com.fecmobile.jiubeirobot.data.net.APIManager;
import com.fecmobile.jiubeirobot.data.net.FLags;
import com.fecmobile.jiubeirobot.ui.dialog.cellar.manage.SettingChangePasswordDialog;
import com.fecmobile.jiubeirobot.utils.BasicTool;
import com.fecmobile.jiubeirobot.utils.RegexUtils;
import com.fecmobile.jiubeirobot.utils.T;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类描述    : 登录系统管理
 * 包名      : com.fecmobile.jiubeirobot.ui.activity.cellar.manager
 * 类名称    : LoginManageActivity
 * 创建人    : wangxing
 * 创建时间  :  2017/3/10   16:16
 * 修改人    :
 * 修改时间  :
 * 修改备注  : 在这个登录页面修改了  修改的操作为 原来的登录是直接登录到系统管理  对商品信息进行查看和存储以及订单的管理
 * 修改为登录成功后跳转到第二个层级 这个层级主要显示的是系统管理和本地管理的分层 以及当前酒窖的一些信息 通过这个层级可以直接分配下一步
 * 要执行什么操作  这个操作可以是进入系统管理 也可以是进入本地管理 也可以实时的查看当前酒窖的详细信息
 */

public class LoginManageActivity extends BaseActivity {
    @BindView(R.id.llyt_back)
    LinearLayout mLlytBack;
    @BindView(R.id.et_manage_account)
    EditText mEtManageAccount;
    @BindView(R.id.et_manage_password)
    EditText mEtManagePassword;
    @BindView(R.id.bt_manage_login)
    Button mBtManageLogin;
    @BindView(R.id.tv_manage_forget)
    TextView mTvManageForget;
    @BindView(R.id.activity_login_manage)
    RelativeLayout mActivityLoginManage;
    @BindView(R.id.btn_toUP)
    Button btntoUP;

//    private SettingChangePasswordDialog mSettingChangePasswordDialog;

    @Override
    public int pageLayout() {
        return R.layout.activity_login_manage;
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {
        //Debug 模式下显示固定的电话号码和密码
        if (BuildConfig.DEBUG) {
            mEtManageAccount.setText("18877914676");
            mEtManagePassword.setText("123456");
        }
    }

    @Override
    public void onSuccess(BaseBean bean, int flag) {
        switch (flag) {
            case FLags.FLAG_LOGIN_MANAGER:
                BaseBean<ObjBean<LoginManagerBean>> loginBean = bean;
                String account = loginBean.getData().getObj().getAccount();
                String busiCompLogo_url = loginBean.getData().getObj().getBusiCompLogo_url();
                String busiCompName = loginBean.getData().getObj().getBusiCompName();
                String aid = loginBean.getData().getObj().getAid();
                String bid = loginBean.getData().getObj().getBid();
                String uid = loginBean.getData().getObj().getUid();
                String userName = loginBean.getData().getObj().getUserName();
                Log.d("print", "onSuccess: 登录成功的数据返回" + aid + bid + uid + userName);
                //TODO  这是修改的地方  这个地方主要是进入分层管理
//                Activitys.toSysMainManager(this, aid, bid, uid, account, busiCompName, busiCompLogo_url, userName);
                Activitys.toSysManagerGroup(this, aid, bid, uid, account, busiCompName, busiCompLogo_url, userName);
                mEtManagePassword.setText("");
                BaseData.getBaseData().setLoginManagerBean(loginBean.getData().getObj());
                finish();
                break;
        }
        dismissHUD();

    }

    /***
     * 设置点击监听
     *
     * @param view
     */
    @OnClick({R.id.llyt_back, R.id.bt_manage_login, R.id.tv_manage_forget, R.id.btn_toUP})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llyt_back:
                this.finish();
                break;
            case R.id.bt_manage_login:
                String et_account = mEtManageAccount.getText().toString().trim();
                String et_password = mEtManagePassword.getText().toString().trim();
                if (checklogin(et_account, et_password)) {
                    APIManager.getInstance().loginMange(this, et_account, et_password);
                }
                break;
            case R.id.tv_manage_forget:
                Activitys.toSystemForgetPassWordActivity(this);
                break;

            case R.id.btn_toUP:
                Activitys.toUPInfoActivity(LoginManageActivity.this);
                finish();
                break;

        }
    }

    /**
     * 描述      :  检验登录的格式
     * 方法名    :  checklogin
     * param    :
     * 返回类型  :
     * 创建人    :wangxing
     * 创建时间  :
     * 修改人    :
     * 修改时间
     * 修改备注
     * throws
     */
    private boolean checklogin(String et_account, String et_password) {
        //在对输入的登录账号和密码的验证需要从 账号和密码的验证入手

        if (!BasicTool.isNotEmpty(et_account)) {
            T.showToCenter(getString(R.string.cellar_manage_login_account_not_null));
            return false;
        }
        if (!RegexUtils.checkMobile(et_account)) {
            T.showToCenter(getString(R.string.cellar_manage_login_account_fail));
            return false;
        }
        if (!BasicTool.isNotEmpty(et_password)) {
            T.showToCenter(getString(R.string.cellar_manage_login_password_not_null));
            return false;
        }
        return true;
    }
}
