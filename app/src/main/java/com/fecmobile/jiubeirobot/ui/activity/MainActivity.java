package com.fecmobile.jiubeirobot.ui.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.fecmobile.jiubeirobot.BuildConfig;
import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseActivity;
import com.fecmobile.jiubeirobot.base.BaseApplication;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.base.BaseData;
import com.fecmobile.jiubeirobot.bean.CellarIDBean;
import com.fecmobile.jiubeirobot.bean.IdentityCheckResultBean;
import com.fecmobile.jiubeirobot.bean.ObjBean;
import com.fecmobile.jiubeirobot.bean.ProtocolsInfoBean;
import com.fecmobile.jiubeirobot.bean.VersionCodeBean;
import com.fecmobile.jiubeirobot.common.Activitys;
import com.fecmobile.jiubeirobot.data.net.APIManager;
import com.fecmobile.jiubeirobot.data.net.FLags;
import com.fecmobile.jiubeirobot.ui.dialog.AlertDialog;
import com.fecmobile.jiubeirobot.ui.dialog.user.RegisterUserDialog;
import com.fecmobile.jiubeirobot.utils.OkHttpHelper;
import com.fecmobile.jiubeirobot.utils.SharedPreferencesUtils;
import com.fecmobile.jiubeirobot.utils.T;
import com.lzy.okhttputils.callback.FileCallback;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Response;

/**
 * 类描述    : 首页
 * 包名      : com.fecmobile.jiubeirobot.ui.activity
 * 类名称    : MainActivity
 * 创建人    : ghy
 * 创建时间  : 2017/2/21 19:53
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class MainActivity extends BaseActivity {
    @BindView(R.id.llyt_main_member_block)
    LinearLayout llytMainMemberBlock;
    @BindView(R.id.llyt_join)
    LinearLayout llytJoin;
    @BindView(R.id.llyt_cellar_des)
    LinearLayout llytCellarDes;
    @BindView(R.id.llyt_fast_buy)
    LinearLayout llytFastBuy;
    @BindView(R.id.llyt_system_manage)
    LinearLayout mLlytSystemManage;
    //新增本地购买
    @BindView(R.id.llyt_local_buy)
    LinearLayout llytLocalBuy;

    private RegisterUserDialog registerUserDialog;//注册的对话框的dialog
    private int currentState = 1;//机器人信息当前数据状态（是否请求成功）1、加载中  2、加载完成 3、加载失败
    private int reqCount;//定义的加载网络数据的类型    初始值为0  在请求你数据返回成功的时候为2

    @Override
    public int pageLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initTitle() {
    }

    @Override
    public void initView() {
        registerUserDialog = new RegisterUserDialog();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //桌面应用每次切换到主页，更新酒窖基本信息
        reqCount = 0;
        req();
    }

    /**
     * 刷新酒窖信息
     */
    private void req() {
        APIManager.getInstance().getCellarIDByRobot(this);
        APIManager.getInstance().getProtocolInfo(this);
        APIManager.getInstance().getVersionCode(this);
    }

    /**
     * 网络请求成功回调
     *
     * @param bean 回调的数据
     * @param flag 返回值
     */

    @Override
    public void onSuccess(BaseBean bean, int flag) {
        switch (flag) {
            case FLags.FLAG_GET_CELLAR_ID_BY_ROBOT:
                //获取机器人所在的酒窖ID
                BaseBean<ObjBean<CellarIDBean>> baseBean = bean;
                BaseData.getBaseData().setCellarInfoBean(baseBean.getData().getObj());
                String CellarName = BaseData.getBaseData().getCellarInfoBean().getCellarName();
                Log.d("print", "onSuccess: CellarName" + CellarName);
                if (!TextUtils.isEmpty(CellarName)) {
                    //不为空  使用sp存储
                    SharedPreferencesUtils.putString(MainActivity.this, "CellarName", CellarName);
                }
                reqCount++;
                break;
            case FLags.FLAG_PROTOCOLS_INFO:
                BaseBean<ObjBean<ProtocolsInfoBean>> pbean = bean;
                BaseData.getBaseData().setProtocolsInfoBean(pbean.getData().getObj());
                reqCount++;
                break;
            case FLags.FLAG_VERSION_CODE:
                BaseBean<ObjBean<VersionCodeBean>> vCode = bean;
                VersionCodeBean codeNum = vCode.getData().getObj();
                getCodeThan(codeNum);
            default:
                break;
        }

        dismissHUD();
    }

    /**
     * 获取当前的版本号 判断是否需要更新版本
     *
     * @param codeNum
     */
    private void getCodeThan(VersionCodeBean codeNum) {
        String XTcode = codeNum.getVersionCode();
        String appUrl = codeNum.getVersionUrl();
        int code = 0;
        Log.d("print", "getCodeThan: " + XTcode);

        if (!XTcode.equals("")) {
            code = Integer.parseInt(XTcode);
        }
        if (code > getCurrentVersionName()) {
            showDownloadDialog(appUrl, XTcode);
        }
    }

    /**
     * 提示更新版本的dialog
     *
     * @param appUrl 更新的版本所在的地址
     * @param intr   版本号
     */

    private void showDownloadDialog(final String appUrl, String intr) {
        AlertDialog dialog = new AlertDialog(this);
        AlertDialog.OnClickListenerAlertDialog listenerAlertDialog = new AlertDialog.OnClickListenerAlertDialog() {
            @Override
            public void onClick(View v, Dialog dialog) {
                downLoadApk(appUrl);
            }
        };
        dialog.builder().setTitle("版本更新").setMsg("版本：" + intr).setCancelable(false).setPositiveButton("下载", listenerAlertDialog)
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
    }

    // 下载apk文件
    private void downLoadApk(String appUrl) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pd.setCancelable(false);
        pd.setMessage("正在下载更新");
        pd.show();
        OkHttpHelper.getInstance().downloadFile(this, appUrl, new FileCallback(getString(R.string.app_name) + ".apk") {
            @Override
            public void onSuccess(File file, okhttp3.Call call, Response response) {
                installApk(file);
                pd.dismiss();
            }

            @Override
            public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
                pd.setProgress((int) (progress * 100));
            }

            @Override
            public void onError(okhttp3.Call call, Response response, Exception e) {
                super.onError(call, response, e);
                T.show("下载失败", 2);
                pd.dismiss();
            }
        });
    }

    //安装apk
    protected void installApk(File file) {
        Intent intent = new Intent();
        //执行动作
        intent.setAction(Intent.ACTION_VIEW);
        //执行的数据类型
        intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        startActivity(intent);
        finish();
    }

    /**
     * 请求失败回调
     *
     * @param error 错误信息
     * @param flag  错误值
     */

    @Override
    public void onError(String error, int flag) {
        super.onError(error, flag);
        Log.d("print", "MainActivity的onError: " + error + flag);
        currentState = 3;//加载失败
    }

    /**
     * 监听回退键
     */
    @Override
    public void onBackPressed() {
    }

    /**
     * TODO  新增需求的时候   点击跳转另一个activity的时候可以在这里去添加  直接监听点击事件
     *
     * @param view
     */
    @OnClick({R.id.llyt_main_member_block, R.id.llyt_join, R.id.llyt_cellar_des, R.id.llyt_fast_buy, R.id.llyt_system_manage, R.id.llyt_local_buy})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llyt_main_member_block:
                //入口一   会员入口
                BaseApplication.currentMode = BaseApplication.MEMBER_BUY;
                if (clickPage())
                    //跳转到商品详情页 this 表示当前activity
                    Activitys.toShopList(this);
                break;
            case R.id.llyt_join:
                // 入口三   注册入口
                registerUserDialog.show(getSupportFragmentManager(), "reg");
                break;
            case R.id.llyt_cellar_des:
                // 入口四   酒窖文化入口
                if (clickPage()) {
                    Activitys.toWalkIntoCellar(this);
                }
                break;
            case R.id.llyt_fast_buy:
                //入口二   快速购买的入口
                BaseData.getBaseData().setIdentityCheckResultBean(new IdentityCheckResultBean());
                BaseApplication.currentMode = BaseApplication.FAST_BUY;
                if (clickPage())
                    Activitys.toShopList(this);
                break;
            case R.id.llyt_system_manage:
                // 入口五   系统管理入口
                if (clickPage())
                    Activitys.toLoginManage(this);
                break;
            //新增入口  本地购买  进入本地购买页面是不需要身份校验的  直接进入 定义个模式来供结账的时候判定  本地购买的值为2
            case R.id.llyt_local_buy:
//                Log.d("print", "本地: 进入");
                //定义入口的模式 设置为本地购买
//                DetectTool.getAPPVersionCodeFromAPP(this);
                BaseApplication.currentMode = BaseApplication.LOCAL_BUY;
                if (clickPage()) {
                    Activitys.toLocalBuyList(this);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 判断是否初始化成功
     *
     * @return
     */
    public boolean clickPage() {
        if (reqCount == 2) {
            currentState = reqCount;//加载完成
        } else if (currentState == 1) {
            //加载中
            T.showToCenter(getString(R.string.common_is_init));
//            req();//新添加的
//            currentState = reqCount;
        } else if (currentState == 3) {
            //加载失败
            currentState = 1;
            reqCount = 0;
            req();
            T.showToCenter(getString(R.string.common_is_defeated));
        }
        return currentState == 2; //加载完成
    }

    /**
     * @return 当前应用版本号
     */
    public int getCurrentVersionName() {
        return BuildConfig.VERSION_CODE;
    }
}