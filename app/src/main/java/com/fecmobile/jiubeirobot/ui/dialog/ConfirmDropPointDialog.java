package com.fecmobile.jiubeirobot.ui.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;

/**
 * 类描述    :删除确认框
 * 包名      : com.fecmobile.jiubeirobot.ui.dialog
 * 类名称    : ConfirmDropPointDialog
 * 创建人    : ghy
 * 创建时间  : 2017/3/13 20:11
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class ConfirmDropPointDialog {
    private AlertDialog alertDialog;
    private ClickLisener clickLisener;

    public void setClickLisener(ClickLisener clickLisener) {
        this.clickLisener = clickLisener;
    }

    public void show(final Context context, String msg, String leftBtn, String rightBtn) {
        alertDialog = new AlertDialog(context).builder().setMsg(msg)
                .setPositiveButton(leftBtn, new AlertDialog.OnClickListenerAlertDialog() {
                    @Override
                    public void onClick(View v, Dialog dialog) {
                        if (clickLisener != null) {
                            clickLisener.onRightClick(alertDialog);
                        }
                    }
                })
                .setNegativeButton(rightBtn, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (clickLisener != null) {
                            clickLisener.onLeftClick(alertDialog);
                        }
                    }
                });
        alertDialog.show();
    }

    public void dismiss() {
        if (alertDialog != null && alertDialog.isShowing()) {
            alertDialog.dismiss();
        }
    }

    public interface ClickLisener {
        void onLeftClick(AlertDialog dialog);

        void onRightClick(AlertDialog dialog);
    }

}
