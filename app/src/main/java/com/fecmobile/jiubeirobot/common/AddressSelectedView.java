package com.fecmobile.jiubeirobot.common;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.fecmobile.jiubeirobot.R;
import com.fecmobile.jiubeirobot.utils.BasicTool;
import com.fecmobile.pickerview.lib.WheelView;
import com.fecmobile.pickerview.view.BasePickerView2;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 类描述    :地址选择器
 * 包名      : com.fecmobile.jiubeirobot.common
 * 类名称    : AddressSelectedView
 * 创建人    : ghy
 * 创建时间  : 2017/3/8 20:23
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class AddressSelectedView extends LinearLayout {

    private ArrayList<String> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private BasePickerView2 bpvAddress;

    public AddressSelectedView(Context context) {
        super(context);
    }

    public AddressSelectedView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AddressSelectedView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    public void setCancelClick(OnClickListener cancelClick) {
        bpvAddress.setCancelOnclick(cancelClick);
    }

    public OptionsSelectListener optionsSelectListener;

    public void setOptionsSelectListener(OptionsSelectListener optionsSelectListener) {
        this.optionsSelectListener = optionsSelectListener;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initData();

        bpvAddress = new BasePickerView2.Builder(getContext(), new BasePickerView2.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                if (optionsSelectListener != null) {
                    optionsSelectListener.onOptionsSelect(options1Items.get(options1), options2Items.get(options1).get(option2), options3Items.get(options1).get(option2).get(options3), v);
                }
            }
        }).setDividerType(WheelView.DividerType.FILL)
                .setSelectOptions(17, 12, 6)//默认选中广东 深圳 福田
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(18)//设置滚轮文字大小
                .setCancelColor(getResources().getColorStateList(R.color.black))
                .setSubmitColor(getResources().getColorStateList(R.color.color_main))
                .build(this);

        bpvAddress.setPicker(options1Items, options2Items, options3Items);//三级选择器
    }


    public interface OptionsSelectListener {
        void onOptionsSelect(String province, String city, String district, View v);
    }

    private void initData() {
        //初始化选项选择器
        AssetManager assets = getContext().getAssets();
        try {
            InputStream is = assets.open("address.txt");
            int size = is.available();

            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();

            String text = new String(buffer, "GB2312");
            JSONObject mJsonObject = new JSONObject(text);

            JSONObject data = mJsonObject.getJSONObject("data");
            JSONArray list = data.getJSONArray("list");

            List<Map<String, Object>> list4 = BasicTool.jsonArrToList(list);

            for (int i = 0; i < list4.size(); i++) {
                String sheng = String.valueOf(list4.get(i).get("name"));
                options1Items.add(sheng);//获取省名
                JSONArray jsonArray = (JSONArray) list4.get(i).get("content");//这个是市的array

                List<Map<String, Object>> list5 = BasicTool.jsonArrToList(jsonArray);
                ArrayList<String> mShi_list = new ArrayList<>();
                ArrayList<ArrayList<String>> mList_are_ii = new ArrayList<>();
                for (int j = 0; j < list5.size(); j++) {
                    String shi = String.valueOf(list5.get(j).get("name"));//获取市名
                    mShi_list.add(shi);
                    JSONArray jsonArray1 = (JSONArray) list5.get(j).get("content");//获取市所对应的区
                    ArrayList<String> mList_are_i = new ArrayList<>();
                    for (int k = 0; k < jsonArray1.length(); k++) {
                        mList_are_i.add(String.valueOf(jsonArray1.get(k)));
                    }
                    mList_are_ii.add(mList_are_i);
                }
                options2Items.add(mShi_list);
                options3Items.add(mList_are_ii);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
