package com.fecmobile.jiubeirobot.ui.activity;

import android.util.Log;

import com.fecmobile.jiubeirobot.base.BaseActivity;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.bean.LocalShopIDByBarcode;
import com.fecmobile.jiubeirobot.bean.ObjBean;
import com.fecmobile.jiubeirobot.bean.ShopIDByBarcode;
import com.fecmobile.jiubeirobot.common.Activitys;
import com.fecmobile.jiubeirobot.common.Constants;
import com.fecmobile.jiubeirobot.data.net.APIManager;
import com.fecmobile.jiubeirobot.utils.L;

import static com.fecmobile.jiubeirobot.data.net.FLags.FLAG_LOCAL_SHOPID_BY_BARCODE;
import static com.fecmobile.jiubeirobot.data.net.FLags.FLAG_SHOPID_BY_BARCODE;

/**
 * 类描述    :扫码结果   这是扫码枪扫描回来的数据显示的activity
 * 包名      : com.fecmobile.jiubeirobot.ui.activity
 * 类名称    : ScanResultActivity
 * 创建人    : ghy
 * 创建时间  : 2017/4/18 10:27
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class ScanResultActivity extends BaseActivity {
    String localbarcode;
    String barcode;

    @Override
    public int pageLayout() {
        return -1;
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {
        barcode = getIntent().getStringExtra(Constants.INTENT_SHOP_BARCODE);
        localbarcode = getIntent().getStringExtra(Constants.INTENT_SHOP_BARCODE);
        getline(barcode);
        getlocal(localbarcode);

    }

    private void getlocal(String barcode) {
        if (barcode != null) {
            APIManager.getInstance().getLocalShopIDByBarcode(this, barcode);
        }
    }

    private void getline(String barcode) {
        if (barcode != null) {
            APIManager.getInstance().getShopIDByBarcode(this, barcode);
        }
    }


    @Override
    public void onSuccess(final BaseBean bean, int flag) {
        switch (flag) {
            case FLAG_SHOPID_BY_BARCODE:
                //线上
                BaseBean<ObjBean<ShopIDByBarcode>> mShopIDByBarcode = bean;
                String id = mShopIDByBarcode.getData().getObj().getId();
                Log.d("print", "onSuccess: id" + id);
                if (id != null) {
                    Activitys.toShopListByType(this, id);
                }
                break;
            case FLAG_LOCAL_SHOPID_BY_BARCODE:
                //本地
                BaseBean<ObjBean<ShopIDByBarcode>> mShopIDByBarcodelocal = bean;
                String localid = mShopIDByBarcodelocal.getData().getObj().getId();
                if (localid != null) {
                    Activitys.toLocalListByType(this, localid);
                }
                break;
            default:
                break;
        }
//        if (bean != null) {
//            BaseBean<ObjBean<ShopIDByBarcode>> mShopIDByBarcode = bean;
//            String id = mShopIDByBarcode.getData().getObj().getId();
//            Activitys.toShopListByType(this, id);
//        }
        dismissHUD();
        finish();
    }

    @Override
    public void onError(String error, int flag) {
        super.onError(error, flag);
        finish();
    }
}


