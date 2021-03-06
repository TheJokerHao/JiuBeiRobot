package com.fecmobile.jiubeirobot.ui.fragment.cellar.manage;

import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseApplication;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.base.BaseFragment;
import com.fecmobile.jiubeirobot.bean.StorageCellarTabDetailBean;
import com.fecmobile.jiubeirobot.common.pullableview.PullableRecyclerView;
import com.fecmobile.jiubeirobot.common.view.BannerLayout;
import com.fecmobile.jiubeirobot.data.net.APIManager;
import com.fecmobile.jiubeirobot.ui.activity.cellar.manager.LocalStorageCellarTabActivity;
import com.fecmobile.jiubeirobot.ui.activity.cellar.manager.StorageCellarTabActivity;
import com.fecmobile.jiubeirobot.ui.adapter.cellar.manage.StorageCellarMessageAdapter;
import com.fecmobile.jiubeirobot.utils.BasicTool;
import com.fecmobile.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 类描述    : 存储酒品详情的fragment
 * 包名      : com.fecmobile.jiubeirobot.ui.fragment.cellar.manage
 * 类名称    : StorageCellarDetailFragment
 * 创建人    : wangxing
 * 创建时间  :  2017/3/13   19:56
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */


public class StorageCellarDetailFragment extends BaseFragment {
    @BindView(R.id.blyt_photo_message)
    BannerLayout mBlytPhotoMessage;
    @BindView(R.id.tv_cellar_detail_name)
    TextView mTvCellarDetailName;
    @BindView(R.id.tv_cellar_detail_type)
    TextView mTvCellarDetailType;
    @BindView(R.id.tv_cellar_detail_breed)
    TextView mTvCellarDetailBreed;
    @BindView(R.id.textView)
    TextView mTextView;
    @BindView(R.id.tv_cellar_detail_brand)
    TextView mTvCellarDetailBrand;
    @BindView(R.id.tv_cellar_detail_production)
    TextView mTvCellarDetailProduction;
    @BindView(R.id.tv_cellar_detail_growing)
    LinearLayout mTvCellarDetailGrowing;
    @BindView(R.id.tv_cellar_detail_time)
    TextView mTvCellarDetailTime;
    @BindView(R.id.tv_cellar_detail_stock)
    TextView mTvCellarDetailStock;
    @BindView(R.id.recycler_cellar)
    RecyclerView mRecyclerCellar;

    private String mPid;
    private List<String> mViewRes = new ArrayList<>();
    private List<StorageCellarTabDetailBean.StockListBean> mStockList = new ArrayList<>();
    private StorageCellarMessageAdapter mStorageCellarMessageAdapter;


    @Override
    public int pageLayout() {
        return R.layout.item_storagecellar_detail;
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {
        mStorageCellarMessageAdapter = new StorageCellarMessageAdapter(getActivity(), mStockList);
        mRecyclerCellar.setLayoutManager(new GridLayoutManager(getActivity(), 1));
        mRecyclerCellar.setAdapter(mStorageCellarMessageAdapter);

//        Drawable dividerDrawable = ContextCompat.getDrawable(getContext(), com.fecmobile.xrecyclerview.R.drawable.divider_sample);
//        mRecyclerCellar.addItemDecoration(mRecyclerCellar.new DividerItemDecoration(dividerDrawable));

        mBlytPhotoMessage.setFragment(this);
    }


    @Override
    public void onResume() {
        super.onResume();
        StorageCellarTabActivity storageCellarTabActivity = (StorageCellarTabActivity) getActivity();
        if (getActivity() != null) {
            APIManager.getInstance().getStorageCellarTabDetail(getActivity(), mPid, storageCellarTabActivity.getFlag());
        }
    }


    @Override
    public void onSuccess(BaseBean bean, int flag) {


    }

    public void setId(String id) {
        mPid = id;
    }

    public void setParms(StorageCellarTabDetailBean objBeanBaseBean) {
        String listPic1_url = objBeanBaseBean.getListPic1_url();
        String listPic2_url = objBeanBaseBean.getListPic2_url();
        String listPic3_url = objBeanBaseBean.getListPic3_url();
        String listPic4_url = objBeanBaseBean.getListPic4_url();
        String listPic5_url = objBeanBaseBean.getListPic5_url();
        mViewRes.clear();
        if (BasicTool.isNotEmpty(listPic1_url)) {
            mViewRes.add(listPic1_url);
        }
        if (BasicTool.isNotEmpty(listPic2_url)) {
            mViewRes.add(listPic2_url);
        }
        if (BasicTool.isNotEmpty(listPic3_url)) {
            mViewRes.add(listPic3_url);
        }
        if (BasicTool.isNotEmpty(listPic4_url)) {
            mViewRes.add(listPic4_url);
        }
        if (BasicTool.isNotEmpty(listPic5_url)) {
            mViewRes.add(listPic5_url);
        }
        mBlytPhotoMessage.setViewUrls(mViewRes, null);
        mTvCellarDetailName.setText(objBeanBaseBean.getProductName());
        mTvCellarDetailType.setText(objBeanBaseBean.getClassName());
        mTvCellarDetailBreed.setText(objBeanBaseBean.getGrapeVarietiesNames());
        mTvCellarDetailBrand.setText(objBeanBaseBean.getBrandName());
        mTvCellarDetailProduction.setText(objBeanBaseBean.getChateauName());
        mTvCellarDetailTime.setText(objBeanBaseBean.getYears() + "年");
        mTvCellarDetailStock.setText(objBeanBaseBean.getStockCount() + "瓶");

        mStockList.clear();
        mStockList.addAll(objBeanBaseBean.getStockList());
        mStorageCellarMessageAdapter.notifyDataSetChanged();
//        mTvCellarDetailGrowing.setTag(objBeanBaseBean.getRegionPname());
        String regionPname = objBeanBaseBean.getRegionPname();
        mTvCellarDetailGrowing.removeAllViews();
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_storage_detail_growing_message, mTvCellarDetailGrowing);
        TextView tv_growing = (TextView) view.findViewById(R.id.tv_growing);
        tv_growing.setText(regionPname);
    }
}
