package com.fecmobile.jiubeirobot.ui.dialog;

import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseDialogFragment;
import com.fecmobile.jiubeirobot.utils.DetectTool;
import com.fecmobile.jiubeirobot.utils.GlideImageLoadImpl;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 类描述    :预览图片Dialog
 * 包名      : com.fecmobile.jiubeirobot.ui.dialog
 * 类名称    : PreviewImgDialog
 * 创建人    : ghy
 * 创建时间  : 2017/3/1 17:03
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class PreviewImgDialog extends BaseDialogFragment {
    @BindView(R.id.iv_img)
    ImageView ivImg;
    @BindView(R.id.iv_close)
    ImageView ivClose;
    private String imgUrl;

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    @Override
    protected int layout() {
        return R.layout.dialog_preview_img;
    }

    @Override
    protected ViewGroup.LayoutParams setLayoutParams() {
        return new FrameLayout.LayoutParams((int) (DetectTool.getResolutionX(getContext()) * 0.62), (int) (DetectTool.getResolutionY(getContext()) * 0.52));
    }

    @Override
    protected void initView() {
        GlideImageLoadImpl.getInstance().load(this, imgUrl, ivImg);
    }

    @OnClick({R.id.iv_img, R.id.iv_close})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                dismiss();
                break;
        }
    }
}
