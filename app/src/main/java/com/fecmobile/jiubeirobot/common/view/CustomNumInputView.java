package com.fecmobile.jiubeirobot.common.view;

import android.content.Context;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.base.BaseData;
import com.fecmobile.jiubeirobot.utils.L;

/**
 * 数字输入框
 * Created by gonghuiyuan on 2016/9/18.
 * contact:gonghuiyuan516@qq.com
 * 自定义View之左右为加减号  中间输入框的控件
 */
public class CustomNumInputView extends LinearLayout implements View.OnClickListener, TextWatcher {
    private ImageView ivPartivipateCrowdfundignminue;
    private ImageView ivPartivipateCrowdfundignAdd;
    private EditText etCrowdfundingMoq;
    private CustomNumInputTextChange customNumInputTextChange;
    private int minVal = 0;
    private int curentVal = 0;
    private int max = 0;

    public void setMax(int max) {
        this.max = max;
    }

    public int getMax() {
        return max;
    }

    public void setCurentVal(int curentVal) {
        this.curentVal = curentVal;
        etCrowdfundingMoq.setText(curentVal + "");
    }

    public int getCurentVal() {
        int c;
        try {
            c = Integer.parseInt(etCrowdfundingMoq.getText() + "");
        } catch (Exception e) {
            c = 0;
        }
        return this.curentVal = c;
    }

    public int getMinVal() {
        return minVal;
    }

    public void setMinVal(int minVal) {
        this.minVal = minVal;
    }

    public CustomNumInputView(Context context) {
        this(context, null);
    }

    public CustomNumInputView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomNumInputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inflate(context, R.layout.layout_custom_num_input, this);
        ivPartivipateCrowdfundignminue = (ImageView) findViewById(R.id.iv_partivipate_crowd_fundign_minue);
        etCrowdfundingMoq = (EditText) findViewById(R.id.et_crowd_funding_moq);
        ivPartivipateCrowdfundignAdd = (ImageView) findViewById(R.id.iv_partivipate_crowd_fundign_add);

        ivPartivipateCrowdfundignminue.setOnClickListener(this);
        ivPartivipateCrowdfundignAdd.setOnClickListener(this);
        etCrowdfundingMoq.addTextChangedListener(this);
    }

    public void setCustomNumInputTextChange(CustomNumInputTextChange customNumInputTextChange) {
        this.customNumInputTextChange = customNumInputTextChange;
    }

    public int getNum() {
        if (TextUtils.isEmpty(etCrowdfundingMoq.getText())) {
            return 0;
        }
        String num = etCrowdfundingMoq.getText().toString();
        return Integer.parseInt(num);
    }


    private void orderNum(int type) {
        int moqNum = 1;
        String moqStr = etCrowdfundingMoq.getText() + "";
        if (!TextUtils.isEmpty(moqStr)) {
            moqNum = Integer.parseInt(moqStr);
        }

        if (type == 1) {
            moqNum++;
            if (moqNum <= max) {
                L.i("----" + moqNum);
                if (customNumInputTextChange != null) {
                    customNumInputTextChange.textChange(this, moqNum);
                }
                etCrowdfundingMoq.setText(moqNum + "");
            }
        } else if (moqNum > minVal) {
            moqNum--;
            if (customNumInputTextChange != null) {
                customNumInputTextChange.textChange(this, moqNum);
            }
            etCrowdfundingMoq.setText(moqNum + "");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_partivipate_crowd_fundign_minue:
                orderNum(0);
                break;
            case R.id.iv_partivipate_crowd_fundign_add:
                orderNum(1);
                break;
        }
    }

    /**
     * 输入框文字改变之前
     *
     * @param s
     * @param start
     * @param count
     * @param after
     */

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    /**
     * 输入框文字改变的时候
     *
     * @param s
     * @param start
     * @param before
     * @param count
     */
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        int moqNum = 1;
        if (!TextUtils.isEmpty(etCrowdfundingMoq.getText())) {
            String moqStr = etCrowdfundingMoq.getText() + "";
            moqNum = Integer.parseInt(moqStr);
            if (moqNum < minVal) {
                moqStr = minVal + "";
                etCrowdfundingMoq.setText(moqStr);
                etCrowdfundingMoq.setSelection(1);
            } else if (moqNum > max) {
                moqStr = max + "";
                etCrowdfundingMoq.setText(moqStr);
                etCrowdfundingMoq.setSelection(moqStr.length());
            }
        }
        if (customNumInputTextChange != null)
            customNumInputTextChange.textChange(this, moqNum);
    }

    /**
     * 输入框文字改变之后
     *
     * @param s
     */
    @Override
    public void afterTextChanged(Editable s) {

    }

    /**
     * 接口回调的形式把当前View获取到的字符串 回传给需要的地方
     */
    public interface CustomNumInputTextChange {
        void textChange(View v, int num);
    }
}
