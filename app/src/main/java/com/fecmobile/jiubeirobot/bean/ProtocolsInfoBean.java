package com.fecmobile.jiubeirobot.bean;

/**
 * 文件名：
 * 描    述：
 * 作    者：ghy
 * 时    间：2017/3/7 20:40
 */

public class ProtocolsInfoBean {

    /**
     * site_wx_pic : http://192.168.0.56:89/group1/M00/03/0A/wKgAD1i-Q2mANTwqAAAXAj5hwNY544.png
     * site_logo : http://192.168.0.56:89/group1/M00/03/0A/wKgAD1i-Q2OAZn33AAAeDgexAJc603.png
     * site_reg_info : 阿凡达撒的发生的撒地方十大方式
     * site_search_hot_words : 新疆枣,葵瓜子,耗牛肉干,土特产
     */

    private String site_wx_pic;
    private String site_logo;
    private String site_reg_info;
    private String site_search_hot_words;

    public String getSite_wx_pic() {
        return site_wx_pic;
    }

    public void setSite_wx_pic(String site_wx_pic) {
        this.site_wx_pic = site_wx_pic;
    }

    public String getSite_logo() {
        return site_logo;
    }

    public void setSite_logo(String site_logo) {
        this.site_logo = site_logo;
    }

    public String getSite_reg_info() {
        return site_reg_info;
    }

    public void setSite_reg_info(String site_reg_info) {
        this.site_reg_info = site_reg_info;
    }

    public String getSite_search_hot_words() {
        return site_search_hot_words;
    }

    public void setSite_search_hot_words(String site_search_hot_words) {
        this.site_search_hot_words = site_search_hot_words;
    }
}
