package com.fecmobile.jiubeirobot.ui.fragment.cellar;

import android.support.v7.widget.GridLayoutManager;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseBean;
import com.fecmobile.jiubeirobot.base.BaseFragment;
import com.fecmobile.jiubeirobot.bean.CellarInfoBean;
import com.fecmobile.jiubeirobot.bean.ObjBean;
import com.fecmobile.jiubeirobot.common.Constants;
import com.fecmobile.jiubeirobot.listener.ItemClick;
import com.fecmobile.jiubeirobot.ui.activity.cellar.walk.WalkIntoCellarActivity;
import com.fecmobile.jiubeirobot.ui.adapter.cellar.CellarIndoorImgAdapter;
import com.fecmobile.jiubeirobot.ui.dialog.PreviewImgDialog;
import com.fecmobile.jiubeirobot.utils.BasicTool;
import com.fecmobile.jiubeirobot.utils.L;
import com.fecmobile.xrecyclerview.XRecyclerView;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 类描述    :酒窖内景
 * 包名      : com.fecmobile.jiubeirobot.ui.fragment.cellar
 * 类名称    : CellarIndoorFragment
 * 创建人    : ghy
 * 创建时间  : 2017/3/1 16:15
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class CellarIndoorFragment extends BaseFragment implements ItemClick {
    @BindView(R.id.rv_view)
    XRecyclerView rvView;
    private CellarIndoorImgAdapter cellarIndoorImgAdapter;
    private PreviewImgDialog previewImgDialog;
    private List<String> list;

    @Override
    public int pageLayout() {
        return R.layout.fragment_cellar_indoor;
    }

    @Override
    public void initTitle() {

    }

    @Override
    public void initView() {
        rvView.setLoadingMoreEnabled(false);
        rvView.setPullRefreshEnabled(false);

        rvView.setLayoutManager(new GridLayoutManager(getContext(), 3));
        list = new ArrayList<>();
        try {
            WalkIntoCellarActivity walkIntoCellar = (WalkIntoCellarActivity) getActivity();
            CellarInfoBean obj = walkIntoCellar.getBeanBaseBean().getData().getObj();

            for (int i = 1; i < 10; i++) {
                Class clz = CellarInfoBean.class;
                Method method = clz.getDeclaredMethod("getPic" + i + "_url", new Class[0]);
                String str = method.invoke(obj) + "";
                if (BasicTool.isNotEmpty(str)) {
                    list.add(str);
                }
            }
        } catch (Exception e) {
            L.i(e);
        }
        cellarIndoorImgAdapter = new CellarIndoorImgAdapter(getContext(), this, list);
        cellarIndoorImgAdapter.setItemClick(this);
        rvView.setAdapter(cellarIndoorImgAdapter);
    }

    @Override
    public void onSuccess(BaseBean bean, int flag) {

    }

    @Override
    public void onItemClick(int postion) {
        if (previewImgDialog == null) {
            previewImgDialog = new PreviewImgDialog();
        }
        previewImgDialog.setImgUrl(list.get(postion - 1));
        previewImgDialog.show(getFragmentManager(), "pid");
    }

}
