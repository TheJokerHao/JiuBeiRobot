package com.fecmobile.jiubeirobot.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.utils.L;

/**
 * 类描述    :筛选 区域筛选
 * 包名      : com.fecmobile.jiubeirobot.common.view
 * 类名称    : ConstionView
 * 创建人    : ghy
 * 创建时间  : 2017/2/23 13:59
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class ConstionView extends LinearLayout {
    private TextView titleTxt;
    private EditText etLow;
    private EditText etHigt;
    private SelectedView svFilter1;
    private SelectedView svFilter2;
    private SelectedView svFilter3;
    private View vLine;
    private String type = "0";//0 价格   1年份
    private TextWatcher textWatcher;
    private String[] prices = new String[2];

    public String getStartPrice() {
        return etLow.getText() + "";
    }

    public String getEndPrice() {
        return etHigt.getText() + "";
    }

    public String getYear() {
        return etLow.getText() + "";
    }

    public void setType(String type) {
        this.type = type;
    }

    public ConstionView(Context context) {
        this(context, null);
    }

    public ConstionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ConstionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_filter_item_costion, this);
        svFilter1 = (SelectedView) findViewById(R.id.sv_filter1);
        svFilter2 = (SelectedView) findViewById(R.id.sv_filter2);
        svFilter3 = (SelectedView) findViewById(R.id.sv_filter3);
        titleTxt = (TextView) findViewById(R.id.tv_title);
        etLow = (EditText) findViewById(R.id.et_low);
        etHigt = (EditText) findViewById(R.id.et_higt);


        textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String val = etLow + "-" + etHigt;
                L.i(val);
                if (!(val.equals(svFilter1.getText()) || val.equals(svFilter2.getText()) || val.equals(svFilter3.getText()))) {
                    svFilter1.setChecked(false);
                    svFilter2.setChecked(false);
                    svFilter3.setChecked(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };


        vLine = findViewById(R.id.v_line);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.IntervalSearch, 0, 0);
            etLow.setHint(a.getString(R.styleable.IntervalSearch_lowTxt));
            etHigt.setHint(a.getString(R.styleable.IntervalSearch_higtTxt));
            svFilter1.setText(a.getString(R.styleable.IntervalSearch_svFilter1));
            svFilter2.setText(a.getString(R.styleable.IntervalSearch_svFilter2));
            svFilter3.setText(a.getString(R.styleable.IntervalSearch_svFilter3));
            titleTxt.setText(a.getString(R.styleable.IntervalSearch_titleText));

            if (a.getBoolean(R.styleable.IntervalSearch_higtHide, true)) {
                etHigt.setVisibility(VISIBLE);
            } else {
                etHigt.setVisibility(GONE);
                vLine.setVisibility(GONE);
            }
            a.recycle();//回收内存
        }

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        svFilter1.getLlytItemFilterBg().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                etLow.removeTextChangedListener(textWatcher);
                etHigt.removeTextChangedListener(textWatcher);

                svFilter1.setChecked(!svFilter1.isChecked());
                svFilter2.setChecked(false);
                svFilter3.setChecked(false);
                if ("0".equals(type)) {
                    String[] prices = svFilter1.getText().split("-");
                    etLow.setText(prices[0]);
                    etHigt.setText(prices[1]);
                } else {
                    String year = svFilter1.getText();
                    year = year.substring(0, year.length() - 1);
                    etLow.setText(year);
                }

                etLow.addTextChangedListener(textWatcher);
                etHigt.addTextChangedListener(textWatcher);
            }
        });
        svFilter2.getLlytItemFilterBg().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                etLow.removeTextChangedListener(textWatcher);
                etHigt.removeTextChangedListener(textWatcher);

                svFilter1.setChecked(false);
                svFilter2.setChecked(!svFilter2.isChecked());
                svFilter3.setChecked(false);
                if ("0".equals(type)) {
                    String[] prices = svFilter2.getText().split("-");
                    etLow.setText(prices[0]);
                    etHigt.setText(prices[1]);
                } else {
                    String year = svFilter2.getText();
                    year = year.substring(0, year.length() - 1);
                    etLow.setText(year);
                }

                etLow.addTextChangedListener(textWatcher);
                etHigt.addTextChangedListener(textWatcher);
            }
        });
        svFilter3.getLlytItemFilterBg().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                etLow.removeTextChangedListener(textWatcher);
                etHigt.removeTextChangedListener(textWatcher);

                svFilter1.setChecked(false);
                svFilter2.setChecked(false);
                svFilter3.setChecked(!svFilter3.isChecked());
                if ("0".equals(type)) {
                    String[] prices = svFilter3.getText().split("-");
                    etLow.setText(prices[0]);
                    etHigt.setText(prices[1]);
                } else {
                    String year = svFilter3.getText();
                    year = year.substring(0, year.length() - 1);
                    etLow.setText(year);
                }

                etLow.addTextChangedListener(textWatcher);
                etHigt.addTextChangedListener(textWatcher);
            }
        });
    }

    public String[] getSelectItem() {
        prices[0] = etLow.getText().toString().trim();
        prices[1] = etHigt.getText().toString().trim();
        return prices;
    }


    public void onReset() {
        etLow.setText("");
        etHigt.setText("");
        svFilter1.setChecked(false);
        svFilter2.setChecked(false);
        svFilter3.setChecked(false);
    }
}
