package com.fecmobile.receiptsprint;

import android.annotation.SuppressLint;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 文字打印格式化工具 Created By: Seal.Wu Date: 2015/4/30 Time: 17:01
 */
public class TextFormatUtil {

    /**
     * 打印纸一行最大的字节
     */
    private static final int DEFAULT_LINE_BYTE_SIZE = 35;

    private int lineByteSize = DEFAULT_LINE_BYTE_SIZE;
    /**
     * 分隔符
     */
    private static final String SEPARATOR = "$";

    private static StringBuffer sb = new StringBuffer();
    /**
     * 行元素集合
     */
    private final List<TextWeightBean> lineElements = new ArrayList<TextWeightBean>();

    /**
     * 排版居中标题
     *
     * @param title
     * @return
     */
    public String getLineTitle(String title) {
        sb.delete(0, sb.length());
        for (int i = 0; i < (lineByteSize - getBytesLength(title)) / 2; i++) {
            sb.append(" ");
        }
        sb.append(title);
        sb.append("\n");
        return sb.toString();
    }

    public void setLineByteSize(int lineByteSize) {
        this.lineByteSize = lineByteSize;
    }

    /**
     * 根据比重打印文字，全部文字居左对齐
     *
     * @return
     */
    public String getLineTextAccordingWeight(List<TextWeightBean> list) {
        sb.delete(0, sb.length());
        float totalWeight = 0;
        for (int i = 0; i < list.size(); i++) {
            totalWeight += list.get(i).getWeight();
        }
        for (int i = 0; i < list.size(); i++) {
            TextWeightBean textWeightBean = list.get(i);
            String showText = textWeightBean.getText();
            int holdSize = (int) (textWeightBean.getWeight() / totalWeight * lineByteSize);
            showText = formatText(showText, holdSize);
            sb.append(showText);
        }
        sb.append("\n");
        return sb.toString();
    }

    /**
     * 添加行元素<br>
     * 此方法以endLine方法来获取排版数据
     *
     * @param element 待添加的元素文字
     * @param weight  占一行的比重
     */
    public void addLineElement(String element, float weight) {
        lineElements.add(new TextWeightBean(element, weight));
    }

    /**
     * 结束当前编辑行此行
     *
     * @return 返回当前排版后的一行的数据
     */
    public String endLine() {
        String line = getLineTextAccordingWeight(lineElements);
        lineElements.clear();
        return line;
    }

    /**
     * 显示的文字格式化
     *
     * @param showText
     * @param holdSize
     * @return
     */
    private String formatText(String showText, int holdSize) {
        int textSize = getBytesLength(showText);
        if (textSize > holdSize) {
            showText = subText(showText, holdSize);
        } else {
            for (int j = 0; j < holdSize - textSize; j++) {
                showText += " ";
            }
        }
        return showText;
    }

    @SuppressLint("NewApi")
    private String subText(String showText, int holdSize) {
        int size = 0;
        int index = 0;
        int symbolLength = "..".getBytes(Charset.forName("GB2312")).length;
        for (int j = 0; j < showText.length(); j++) {
            String c = showText.substring(j, j + 1);
            size += c.getBytes(Charset.forName("GB2312")).length;
            index = j;
            if (size > holdSize - symbolLength) {
                break;
            }
        }
        showText = showText.substring(0, index) + "..";

        return formatText(showText, holdSize);
    }

    /**
     * 排版居中内容(以':'对齐)
     * <p/>
     * 例：姓名：李白 病区：5A病区 住院号：11111
     *
     * @return
     */
    String printMiddleMsg(LinkedHashMap<String, String> middleMsgMap) {
        sb.delete(0, sb.length());
        String separated = ":";
        int leftLength = (lineByteSize - getBytesLength(separated)) / 2;
        for (Map.Entry<String, String> middleEntry : middleMsgMap.entrySet()) {
            for (int i = 0; i < (leftLength - getBytesLength(middleEntry.getKey())); i++) {
                sb.append(" ");
            }
            sb.append(middleEntry.getKey() + "：" + middleEntry.getValue());
        }
        return sb.toString();
    }

    /**
     * 排版左右对称内容(以':'对齐)
     * <p/>
     * 例：姓名：李白 住院号：111111 病区：5A病区 科室：五官科 体重：130kg
     *
     * @param leftMsgMap  左边部分要打印内容 左边内容大小可大于右边内容大小 反之右边过大时会忽略内容
     * @param rightMsgMap 右边部分要打印内容
     * @return
     */
    String printSymmetryMSG(LinkedHashMap<String, String> leftMsgMap, LinkedHashMap<String, String> rightMsgMap) {
        sb.delete(0, sb.length());
        int leftPrefixLength = getMaxLength(leftMsgMap.keySet().toArray());
        int rightValueLength = getMaxLength(rightMsgMap.values().toArray());
        Object rightMsgKeys[] = rightMsgMap.keySet().toArray();
        int position = 0;
        for (Map.Entry<String, String> leftEntry : leftMsgMap.entrySet()) {
            String leftMsgPrefix = leftEntry.getKey();
            String leftMsgValue = leftEntry.getValue();
            for (int leftI = 0; leftI < (leftPrefixLength - getBytesLength(leftMsgPrefix)); leftI++) {
                sb.append(" ");
            }
            String leftMsg = leftMsgPrefix + "：" + leftMsgValue;
            sb.append(leftMsg);

            if (position > rightMsgKeys.length - 1)
                continue;
            int leftLength = leftPrefixLength + getBytesLength("：" + leftMsgValue);
            String rightMsgPrefix = rightMsgKeys[position] + "：";
            int rightLength = getBytesLength(rightMsgPrefix) + rightValueLength;
            String rightMsgValue = rightMsgMap.get(rightMsgKeys[position]);

            for (int middle = 0; middle < (lineByteSize - leftLength - rightLength); middle++) {
                sb.append(" ");
            }
            sb.append(rightMsgPrefix + rightMsgValue);
            position++;
        }
        return sb.toString();
    }

    /**
     * 打印订餐单 (左中右对称)
     * <p/>
     * 例: 菜名 数量 单价 早餐： 豆沙包 1 3.0 蛋 1 1.5 午餐： 包子 3 11.0 晚餐： 土豆 2 4.1
     *
     * @param menuMsgMap Key为餐次 Value为 菜谱字符串数组 格式为：豆沙包$数量$单价
     * @return
     */
    String printMenuMSG(LinkedHashMap<String, LinkedList<String>> menuMsgMap) {
        sb.delete(0, sb.length());

        String menus[] = null;
        List<String> menuNames = new ArrayList<String>();
        List<String> menuPrices = new ArrayList<String>();
        for (List<String> strList : menuMsgMap.values()) {
            for (String str : strList) {
                if (str.contains(SEPARATOR)) {
                    menus = str.split("[" + SEPARATOR + "]");
                    if (menus != null && menus.length != 0) {
                        menuNames.add(menus[0]);
                        menuPrices.add(menus[2]);
                    }
                }
            }
        }

        String menuNameTxt = "菜名";
        String numTxt = "数量";
        String priceTxt = "单价\n";

        int leftPrefixLength = getMaxLength(menuNames.toArray());
        int rightPrefixLength = getMaxLength(menuPrices.toArray());
        if (rightPrefixLength < getBytesLength(priceTxt))
            rightPrefixLength = getBytesLength(priceTxt);

        int leftMiddleNameLength = (leftPrefixLength - getBytesLength(menuNameTxt)) / 2;
        for (int i = 0; i < leftMiddleNameLength; i++) {
            sb.append(" ");
        }
        sb.append(menuNameTxt);
        int middleNumTxtLength = (lineByteSize - leftPrefixLength - rightPrefixLength - getBytesLength(numTxt)) / 2;
        for (int i = 0; i < middleNumTxtLength + leftMiddleNameLength; i++) {
            sb.append(" ");
        }
        sb.append(numTxt);
        int middlePriceTxtLength = (rightPrefixLength - getBytesLength(priceTxt)) / 2;
        for (int i = 0; i < middleNumTxtLength + middlePriceTxtLength; i++) {
            sb.append(" ");
        }
        sb.append(priceTxt);

        for (Map.Entry<String, LinkedList<String>> entry : menuMsgMap.entrySet()) {
            if (!"".equals(entry.getKey()))
                sb.append(entry.getKey() + "\n");
            for (String menu : entry.getValue()) {
                if (menu.contains(SEPARATOR)) {
                    menus = menu.split("[" + SEPARATOR + "]");
                    if (menus != null && menus.length != 0) {
                        sb.append(menus[0]);
                        for (int i = 0; i < (leftPrefixLength - getBytesLength(menus[0]) + middleNumTxtLength
                                + getBytesLength(numTxt) / 2 - 1); i++) {
                            sb.append(" ");
                        }
                        sb.append(menus[1]);
                        for (int i = 0; i < (middleNumTxtLength + getBytesLength(numTxt) / 2 + 1
                                - getBytesLength(menus[1]) + middlePriceTxtLength); i++) {
                            sb.append(" ");
                        }
                        sb.append(menus[2] + "\n");
                    }
                } else { // 不包含分隔符 直接打印
                    for (int i = 0; i < lineByteSize / getBytesLength(menu) - getBytesLength("\n"); i++) {
                        sb.append(menu);
                    }
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }

    /**
     * 获取最大长度
     *
     * @param msgs
     * @return
     */
    private static int getMaxLength(Object[] msgs) {
        int max = 0;
        int tmp;
        for (Object oo : msgs) {
            tmp = getBytesLength(oo.toString());
            if (tmp > max) {
                max = tmp;
            }
        }
        return max;
    }

    /**
     * 获取数据长度
     *
     * @param msg
     * @return
     */
    @SuppressLint("NewApi")
    private static int getBytesLength(String msg) {
        if (msg == null) {
            return "null".getBytes(Charset.forName("GB2312")).length;
        }
        return msg.getBytes(Charset.forName("GB2312")).length;
    }
}