package com.fecmobile.jiubeirobot.utils;

import android.support.annotation.Nullable;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 创建人：TheJoker丶豪 on
 * 创建时间：2017/5/23.
 */

@SuppressWarnings("ALL")
public class HtmlRegexpUtil {

    private final static String regxpForHtml = "<([^>]*)>"; // 过滤所有以<开头以>结尾的标签

    @SuppressWarnings("unused")
    private final static String regxpForImaTagSrcAttrib = "src=\"([^\"]+)\""; // 找出IMG标签的SRC属性

    /**
     *
     */
    @SuppressWarnings("unused")
    public HtmlRegexpUtil() {
    }

    /**
     * 基本功能：替换标记以正常显示
     * <p>
     *
     * @param input
     * @return String
     */
    @SuppressWarnings("unused")
    public String replaceTag(String input) {
        if (!hasSpecialChars(input)) {
            return input;
        }
        StringBuffer filtered = new StringBuffer(input.length());
        char c;
        for (int i = 0; i <= input.length() - 1; i++) {
            c = input.charAt(i);
            switch (c) {
                case '<':
                    filtered.append("&lt;");
                    break;
                case '>':
                    filtered.append("&gt;");
                    break;
                case '"':
                    filtered.append("&quot;");
                    break;
                case '&':
                    filtered.append("&amp;");
                    break;
                default:
                    filtered.append(c);
            }

        }
        return (filtered.toString());
    }

    /**
     * 基本功能：判断标记是否存在
     * <p>
     *
     * @param input
     * @return boolean
     */
    private boolean hasSpecialChars(String input) {
        boolean flag = false;
        if ((input != null) && (input.length() > 0)) {
            char c;
            for (int i = 0; i <= input.length() - 1; i++) {
                c = input.charAt(i);
                switch (c) {
                    case '>':
                        flag = true;
                        break;
                    case '<':
                        flag = true;
                        break;
                    case '"':
                        flag = true;
                        break;
                    case '&':
                        flag = true;
                        break;
                }
            }
        }
        return flag;
    }

    /**
     * 基本功能：过滤所有以"<"开头以">"结尾的标签
     * <p>
     *
     * @param str
     * @return String
     */
    @SuppressWarnings("unused")
    public static String filterHtml(String str) {
        Pattern pattern = Pattern.compile(regxpForHtml);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        boolean result1 = matcher.find();
        while (result1) {
            matcher.appendReplacement(sb, "");
            result1 = matcher.find();
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 基本功能：过滤指定标签
     * <p>
     *
     * @param str
     * @param tag 指定标签
     * @return String
     */
    @SuppressWarnings("unused")
    public static String fiterHtmlTag(String str, String tag) {
        String regxp = "<\\s*" + tag + "\\s+([^>]*)\\s*>";
        Pattern pattern = Pattern.compile(regxp);
        Matcher matcher = pattern.matcher(str);
        StringBuffer sb = new StringBuffer();
        boolean result1 = matcher.find();
        while (result1) {
            matcher.appendReplacement(sb, "");
            result1 = matcher.find();
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    /**
     * 基本功能：替换指定的标签
     * <p>
     *
     * @param str
     * @param beforeTag 要替换的标签
     * @param tagAttrib 要替换的标签属性值
     * @param startTag  新标签开始标记
     * @param endTag    新标签结束标记
     * @return String
     * @如：替换img标签的src属性值为[img]属性值[/img]
     */
    @SuppressWarnings("unused")
    public static String replaceHtmlTag(String str, String beforeTag,
                                        String tagAttrib, String startTag, String endTag) {
        String regxpForTag = "<\\s*" + beforeTag + "\\s+([^>]*)\\s*>";
        String regxpForTagAttrib = tagAttrib + "=\"([^\"]+)\"";
        Pattern patternForTag = Pattern.compile(regxpForTag);
        Pattern patternForAttrib = Pattern.compile(regxpForTagAttrib);
        Matcher matcherForTag = patternForTag.matcher(str);
        StringBuffer sb = new StringBuffer();
        boolean result = matcherForTag.find();
        while (result) {
            StringBuffer sbreplace = new StringBuffer();
            Matcher matcherForAttrib = patternForAttrib.matcher(matcherForTag
                    .group(1));
            if (matcherForAttrib.find()) {
                matcherForAttrib.appendReplacement(sbreplace, startTag
                        + matcherForAttrib.group(1) + endTag);
            }
            matcherForTag.appendReplacement(sb, sbreplace.toString());
            result = matcherForTag.find();
        }
        matcherForTag.appendTail(sb);
        return sb.toString();
    }

    /**
     * 获取指定HTML标签的指定属性的值
     *
     * @param source  要匹配的源文本
     * @param element 标签名称
     * @param attr    标签的属性名称
     * @return 属性值列表
     */
    @SuppressWarnings({"unused", "Convert2Diamond"})
    public static List<String> match(String source, String element, String attr) {
        //noinspection Convert2Diamond,Convert2Diamond,Convert2Diamond
        @SuppressWarnings("Convert2Diamond") List<String> result = new ArrayList<String>();
        String reg = "<p>" + element + "[^<>]*?\\s" + attr + "=['\"]?(.*?)['\"]?(\\s.*?)?>";
        Matcher m = Pattern.compile(reg).matcher(source);
        while (m.find()) {
            String r = m.group(1);
            result.add(r);
        }
        return result;
    }


    @Nullable
    public static String getTextHTMLToCellarDesc(String htmlStr) {

        Document doc = Jsoup.parse(htmlStr);

        String textstr = doc.text();

        StringBuilder builder = new StringBuilder(textstr);

        int index = 0;
        while (builder.length() > index) {
            char tmp = builder.charAt(index);
            if (Character.isSpaceChar(tmp) || Character.isWhitespace(tmp)) {
                builder.setCharAt(index, ' ');
            }
            index++;
        }
        textstr = builder.toString().replaceAll(" +", " ").trim();
        return textstr;

    }
}