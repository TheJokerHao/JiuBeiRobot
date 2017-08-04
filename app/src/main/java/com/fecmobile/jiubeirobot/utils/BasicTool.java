package com.fecmobile.jiubeirobot.utils;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.text.format.Time;
import android.util.Base64;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author liubp
 */
public class BasicTool {

    /**
     * 检测字符串是否为空，
     *
     * @param str
     * @return 是空 返回 <code>false</code> 否则返回 <code>true</code>
     */
    public static boolean isNotEmpty(String str) {
        if (null == str)
            return false;
        if (str.equalsIgnoreCase("null"))
            return false;
        return !"".equals(str.trim());
    }

    public static boolean isEmptyForCart(JSONObject keyObj) throws JSONException {
        if (null == keyObj || "{}".equals(keyObj.toString()) || !BasicTool.isNotEmpty(keyObj.getString("value")))
            return false;
        else
            return true;
    }

    /**
     * @param str
     * @return
     * @function 手机号的验证, 验证通过则返回ture，否则为false
     */
    public static boolean isCellphone(String str) {
        Pattern pattern = Pattern.compile("((13[0-9])|(15[0-9])|(14[0-9])|(18[0-9])|(17[0-9]))\\d{8}$");
        Matcher matcher = pattern.matcher(str);

        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 获取时间戳
     *
     * @return
     */
    public static String getCurString() {
        long msg = System.currentTimeMillis();

        return Long.toString(msg);

    }

    /**
     * 将单个list转成json字符串
     *
     * @param list
     * @return
     * @throws Exception
     */
    public static String listToJsonString(List<Map<String, Object>> list) throws Exception {
        String jsonL = "";
        StringBuffer temp = new StringBuffer();
        temp.append("[");
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> m = list.get(i);
            if (i == list.size() - 1) {
                temp.append(mapToJsonObj(m));
            } else {
                temp.append(mapToJsonObj(m) + ",");
            }
        }
        if (temp.length() > 1) {
            temp = new StringBuffer(temp.substring(0, temp.length()));
        }
        temp.append("]");
        jsonL = temp.toString();
        return jsonL;
    }

    /**
     * 将map数据解析出来，并拼接成json字符串
     *
     * @param map
     * @return
     * @throws Exception
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static JSONObject mapToJsonObj(Map map) throws Exception {
        JSONObject json = null;
        StringBuffer temp = new StringBuffer();
        if (!map.isEmpty()) {
            temp.append("{");
            // 遍历map
            Set set = map.entrySet();
            Iterator i = set.iterator();
            while (i.hasNext()) {
                Map.Entry entry = (Map.Entry) i.next();
                String key = (String) entry.getKey();

                Object value = entry.getValue();

                temp.append("\"" + key + "\":");

                if (null == value || "".equals(value)) {
                    temp.append("\"\"" + ", ");
                } else if (value instanceof Map<?, ?>) {
                    temp.append(mapToJsonObj((Map<String, Object>) value) + ",");
                } else if (value instanceof List<?>) {
                    temp.append(listToJsonString((List<Map<String, Object>>) value) + ",");
                } else if (value instanceof String) {
                    temp.append("\"" + String.valueOf(value) + "\",");
                } else {
                    temp.append(String.valueOf(value) + ",");
                }

            }
            if (temp.length() > 1) {
                String mString = temp.toString();
                mString = mString.trim();

                temp = new StringBuffer(mString.substring(0, mString.length() - 1));
            }

            temp.append("}");

            json = new JSONObject(temp.toString());
        }
        return json;
    }

    /**
     * 将json 对象转换为Map 对象
     *
     * @param jsonString
     * @return
     */
    public static Map<String, Object> jsonToMap(String jsonString) {
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            Iterator<String> keyIter = jsonObject.keys();
            String key;
            Object value;
            Map<String, Object> valueMap = new HashMap<String, Object>();
            while (keyIter.hasNext()) {
                key = (String) keyIter.next();
                value = jsonObject.get(key);
                valueMap.put(key, value);
            }
            return valueMap;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 把json 转换为 ArrayList 形式
     *
     * @return
     */
    public static List<Map<String, Object>> jsonArrToList(String jsonString) {
        List<Map<String, Object>> list = null;

        try {
            JSONArray jsonArray = new JSONArray(jsonString);

            JSONObject jsonObject;

            list = new ArrayList<Map<String, Object>>();

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                list.add(jsonToMap(jsonObject.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 把json 转换为 ArrayList 形式
     *
     * @return
     */
    public static List<Map<String, Object>> jsonArrToList(JSONArray jsonArray) {
        List<Map<String, Object>> list = null;

        try {
            JSONObject jsonObject;
            list = new ArrayList<Map<String, Object>>();

            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.getJSONObject(i);
                list.add(jsonToMap(jsonObject.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * @param gap 获取时间的间隔，要获取之前的日期则为负，如获取前一周的时间，则为-7；反之当前日期之后的则为正
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getDateBefore(int gap) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar now = Calendar.getInstance();

        now.setTime(new Date());
        now.set(Calendar.DATE, now.get(Calendar.DATE) + gap);

        return sdf.format(now.getTime());
    }

    /**
     * 获取当前日期，格式：yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static String getCruDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        return sdf.format(new Date());
    }

    @SuppressLint("SimpleDateFormat")
    public static String getOrderTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");

        return sdf.format(new Date());
    }

    /**
     * 日期时间字符串转换为日期字符串
     */
    @SuppressLint("SimpleDateFormat")
    public static String dateTimeToDate(String datatime) {
        SimpleDateFormat dataTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date;
        try {
            date = dataTime.parse(datatime);
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
            return sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final double EARTH_RADIUS = 6378137;

    private static double rad(double d) {
        return d * Math.PI / 180.0;
    }

    /**
     * 根据两点间经纬度坐标（double值），计算两点间距离，单位为米
     *
     * @param lng1
     * @param lat1
     * @param lng2
     * @param lat2
     * @return
     */
    public static double getDistance(double lng1, double lat1, double lng2, double lat2) {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

    /**
     * 缩放Bitmap图片
     **/
    public static Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {

        int w = bitmap.getWidth();

        int h = bitmap.getHeight();

        Matrix matrix = new Matrix();

        float scaleWidth = ((float) width / w);

        float scaleHeight = ((float) height / h);

        matrix.postScale(scaleWidth, scaleHeight);// 利用矩阵进行缩放不会造成内存溢出

        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);

        return newbmp;

    }

    /**
     * 把Bitmap用Base64编码并返回生成的字符串
     */
    public static String bitmaptoString(Bitmap bitmap) {
        String string = null;

        ByteArrayOutputStream bStream = new ByteArrayOutputStream();

        bitmap.compress(CompressFormat.JPEG, 100, bStream);

        byte[] bytes = bStream.toByteArray();

        string = Base64.encodeToString(bytes, Base64.DEFAULT);

        return string;
    }

    /**
     * 把Bitmap转换并返回生成的字符串
     */
    public static String bitmaptoString2(Bitmap bitmap) {
        String string = null;

        ByteArrayOutputStream bStream = new ByteArrayOutputStream();

        bitmap.compress(CompressFormat.JPEG, 100, bStream);

        byte[] bytes = bStream.toByteArray();

        string = String.valueOf(bytes);

        return string;
    }


    /**
     * 根据Object obj以及key获取对应的value值
     *
     * @param obj 目标对象
     * @param key 待取值的key
     * @return
     */
    public static String getValue(JSONObject obj, String key) {
        String value = "";

        if (obj.has(key)) {
            try {
                value = obj.getString(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return value;
    }

    public static Drawable getDrawable(String urlpath) {
        Drawable d = null;
        try {
            URL url = new URL(urlpath);
            URLConnection conn = url.openConnection();
            conn.connect();
            InputStream in;
            in = conn.getInputStream();
            d = Drawable.createFromStream(in, "abc.jpg");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return d;
    }


    /**
     * 日期时间比较
     *
     * @param DATE 输入的日期、时间
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static boolean compare_date(String DATE, boolean hasHour) {
        Time time = new Time();
        time.setToNow();
        String DATE2;
        SimpleDateFormat df;
        if (hasHour) {
            DATE2 = time.year + "-" + (time.month + 1) + "-" + time.monthDay + " " + time.hour + ":" + time.minute;
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        } else {
            DATE2 = time.year + "-" + (time.month + 1) + "-" + time.monthDay;
            df = new SimpleDateFormat("yyyy-MM-dd");
        }
        L.e("DATE2当前系统时间", DATE2);
        L.e("DATE", DATE);

        try {
            Date dt1 = df.parse(DATE);
            Date dt2 = df.parse(DATE2);

            if (hasHour) {
                if (dt1.getTime() >= dt2.getTime()) {
                    return true;
                } else if (dt1.getTime() < dt2.getTime()) {
                    return false;
                }

            } else {
                if (dt1.getTime() > dt2.getTime()) {
                    return true;
                } else if (dt1.getTime() <= dt2.getTime()) {
                    return false;
                }
            }

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return true;
    }

    /**
     * 日期时间比较
     * DATE 与 DATE2做对比
     *
     * @param
     * @return
     */
    @SuppressLint("SimpleDateFormat")
    public static long compareDate(String DATE, String DATE2, boolean hasHour) {
        SimpleDateFormat df;
        if (hasHour) {
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else {
            df = new SimpleDateFormat("yyyy-MM-dd");
        }
        L.e("DATE2", DATE2);
        L.e("DATE", DATE);

        try {
            Date dt1 = df.parse(DATE);//输入时间
            Date dt2 = df.parse(DATE2);//输入时间
            return dt1.getTime() - dt2.getTime();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return -1;
    }


    public static String getEncryptionMobile(String realmobile) {
        String str = realmobile.substring(0, 3) + "****" + realmobile.substring(7, realmobile.length());
        return str;
    }


    /**
     * 校验银行卡卡号
     *
     * @param cardId
     * @return
     */
    public static boolean checkBankCard(String cardId) {
        if (cardId.length() == 15) {
            return true;
        }
        char bit = getBankCardCheckCode(cardId.substring(0, cardId.length() - 1));
        if (bit == 'N') {
            return false;
        }
        return cardId.charAt(cardId.length() - 1) == bit;
    }

    /**
     * 从不含校验位的银行卡卡号采用 Luhm 校验算法获得校验位
     *
     * @param nonCheckCodeCardId
     * @return
     */
    public static char getBankCardCheckCode(String nonCheckCodeCardId) {
        if (nonCheckCodeCardId == null || nonCheckCodeCardId.trim().length() == 0 || !nonCheckCodeCardId.matches("\\d+")) {
            //如果传的不是数据返回N
            return 'N';
        }
        char[] chs = nonCheckCodeCardId.trim().toCharArray();
        int luhmSum = 0;
        for (int i = chs.length - 1, j = 0; i >= 0; i--, j++) {
            int k = chs[i] - '0';
            if (j % 2 == 0) {
                k *= 2;
                k = k / 10 + k % 10;
            }
            luhmSum += k;
        }
        return (luhmSum % 10 == 0) ? '0' : (char) ((10 - luhmSum % 10) + '0');


    }

    /**
     * 验证银行卡长度
     */
    public static boolean getBankLength(String cartNumber) {
        if (cartNumber == null) {
            return false;
        }
        return cartNumber.matches("^(\\\\d{15}||\\\\d{16}||\\\\d{17}||\\\\d{18}||\\\\d{19})$");
    }

    /**
     * 时间格式的转换:
     * 2016-12-02 10:48:17   ------   12月02日  10:48
     */

    public static void getCommentTime(String time, TextView textView) {
        if (BasicTool.isNotEmpty(time)) {
            String[] split = time.split(" ");
            String[] split1 = split[1].split(":");
            String[] split0 = split[0].split("-");
            textView.setText(split0[1] + "月" + split0[2] + "日" + "　" + split1[0] + "：" + split1[1]);
        }

    }

    /**
     * 朋友圈的时间设置
     *
     * @param time
     * @param textView
     */
    public static void setFriendPlace(String time, TextView textView) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //分析时间
            if (BasicTool.isNotEmpty(time)) {
                //item的时间
                Date parse = simpleDateFormat.parse(time);
                Calendar itemTime = Calendar.getInstance();
                itemTime.setTime(parse);
                //今天的时间

                String currentTime = android.text.format.DateFormat.format("yyyy-MM-dd HH:mm:ss", new Date()).toString();
                Calendar current = Calendar.getInstance();
                Calendar yesterday = Calendar.getInstance();

                yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
                yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
                yesterday.add(Calendar.DAY_OF_MONTH, -1);

                //昨天的时间
                String yesterTime = simpleDateFormat.format(yesterday.getTime());
                String yes_mon = String.valueOf(yesterday.get(Calendar.MONTH));
                String yes_year = String.valueOf(yesterday.get(Calendar.YEAR));
                String yes_day = String.valueOf(yesterday.get(Calendar.DAY_OF_MONTH));

                //今天的时间
                String current_mon = String.valueOf(current.get(Calendar.MONTH));//月
                String current_mon_last = String.valueOf(current.get(Calendar.MONTH) - 1);//上个月
                String current_year = String.valueOf(current.get(Calendar.YEAR));//年
                String current_day = String.valueOf(current.get(Calendar.DAY_OF_MONTH));//日

                //item的时间
                String item_year = String.valueOf(itemTime.get(Calendar.YEAR));//年
                String item_mon = String.valueOf(itemTime.get(Calendar.MONTH));//月
                String item_day = String.valueOf(itemTime.get(Calendar.DAY_OF_MONTH));//日
                String m = "";
                if (item_year.equals(current_year)) {

                    //今天:分为:刚刚-几分钟前-几小时前
                    if (item_day.equals(String.valueOf(current.get(Calendar.DAY_OF_MONTH))) && item_mon.equals(current_mon)) {
                        //时与分相同
                        long mTimeInMillis_item = itemTime.getTimeInMillis();//item的时间
                        long mTimeInMillis_current = current.getTimeInMillis();//现在的时间
                        long hour = (mTimeInMillis_current - mTimeInMillis_item) / (1000 * 60 * 60);//间隔的时
                        long min = (mTimeInMillis_current - mTimeInMillis_item) / (1000 * 60);//间隔的分钟
                        long second = (mTimeInMillis_current - mTimeInMillis_item) / (1000);//间隔的秒钟
                        if (!(Integer.valueOf(String.valueOf(second)) > 60)) {
                            time = "刚刚";
                        } else if (!(Integer.valueOf(String.valueOf(min)) > 60)) {
                            time = min + "分钟前";
                        } else {
                            time = hour + "小时前";
                        }
                    }
                    //昨天 时:分
                    else if (item_day.equals(yes_day) && item_mon.equals(yes_mon) && item_year.equals(yes_year)) {
                        if (itemTime.get(Calendar.MINUTE) < 10) {
                            m = "0" + String.valueOf(itemTime.get(Calendar.MINUTE));
                        } else {
                            m = String.valueOf(itemTime.get(Calendar.MINUTE));
                        }
                        time = "昨天　" + String.valueOf(itemTime.get(Calendar.HOUR_OF_DAY) + "：" + m);
                    }
                    //今年的其他   月-日:时-分
                    else {

                        if (itemTime.get(Calendar.MINUTE) < 10) {
                            m = "0" + String.valueOf(itemTime.get(Calendar.MINUTE));
                        } else {
                            m = String.valueOf(itemTime.get(Calendar.MINUTE));
                        }
                        time = Arith.get0Decimal(Arith.add(item_mon, "1")) + "月" + item_day + "日"
                                + "　" + String.valueOf(itemTime.get(Calendar.HOUR_OF_DAY)) + "：" + m;
                    }
                } else
                //不是一年的 年月日:时分秒
                {

                    time = String.valueOf(itemTime.getTime());
                }
                textView.setText(time);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static void setYMDTime(String time, TextView textView) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date parse = simpleDateFormat.parse(time);
            String currentTime = android.text.format.DateFormat.format("yyyy-MM-dd", parse).toString();
            textView.setText(currentTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
