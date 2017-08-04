package com.fecmobile.jiubeirobot.bean;

import com.fecmobile.jiubeirobot.BuildConfig;

import java.io.Serializable;

/**
 * 类描述    :酒窖信息
 * 包名      : com.fecmobile.jiubeirobot.bean
 * 类名称    : CellarInfoBean
 * 创建人    : ghy
 * 创建时间  : 2017/3/7 14:20
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class CellarInfoBean implements Serializable {

    /**
     * pic10 : group1/M00/02/EC/wKgAD1i1v4-AQ8NnAACM8DDNuhs086.jpg
     * videoUrl : www.baidu.com
     * pic7_url : http://img.jbhjmall.com/group1/M00/02/EC/wKgAD1i1v9qAYSFsAAAdTJEb9ho634.png
     * pic10_url : http://img.jbhjmall.com/group1/M00/02/EC/wKgAD1i1v4-AQ8NnAACM8DDNuhs086.jpg
     * cellerIntroduct : 红葡萄酒酿造和红葡萄酒种植
     * pic6_url : http://img.jbhjmall.com/group1/M00/02/EC/wKgAD1i1v9eAOE6hAAA0VIdSIcQ344.jpg
     * cellerDescprition : 红葡萄酒酿造和红葡萄酒种植红葡萄酒酿造和红葡萄酒种植
     * pic1 : group1/M00/02/EC/wKgAD1i1v8uAdm5aAAF_MD3774U584.jpg
     * id : 3
     * pic2_url : http://img.jbhjmall.com/group1/M00/02/EC/wKgAD1i1v86AGEYqAABKqqD-e3k716.jpg
     * pic9 : group1/M00/02/EC/wKgAD1i1v_OAAzZXAADyznCaOx8768.png
     * pic4_url : http://img.jbhjmall.com/group1/M00/02/EC/wKgAD1i1v9OAXgG5AAArfLkONzI296.jpg
     * pic8 : group1/M00/02/EC/wKgAD1i1v92ALtSxAAB5ADQksj8399.jpg
     * pic1_url : http://img.jbhjmall.com/group1/M00/02/EC/wKgAD1i1v8uAdm5aAAF_MD3774U584.jpg
     * pic6 : group1/M00/02/EC/wKgAD1i1v9eAOE6hAAA0VIdSIcQ344.jpg
     * pic5 : group1/M00/02/EC/wKgAD1i1v9WAO-mZAABLaNSSHTA739.jpg
     * pic3_url : http://img.jbhjmall.com/group1/M00/02/EC/wKgAD1i1v9GAEEBBAAL6GBpY00E099.jpg
     * pic4 : group1/M00/02/EC/wKgAD1i1v9OAXgG5AAArfLkONzI296.jpg
     * pic3 : group1/M00/02/EC/wKgAD1i1v9GAEEBBAAL6GBpY00E099.jpg
     * pic8_url : http://img.jbhjmall.com/group1/M00/02/EC/wKgAD1i1v92ALtSxAAB5ADQksj8399.jpg
     * pic2 : group1/M00/02/EC/wKgAD1i1v86AGEYqAABKqqD-e3k716.jpg
     * pic5_url : http://img.jbhjmall.com/group1/M00/02/EC/wKgAD1i1v9WAO-mZAABLaNSSHTA739.jpg
     */

    private String pic10;
    private String videoUrl;
    private String pic7_url;
    private String pic10_url;
    private String cellerIntroduct;
    private String pic6_url;
    private String cellerDescprition;
    private String pic1;
    private String id;
    private String pic2_url;
    private String pic9_url;
    private String pic4_url;
    private String pic8;
    private String pic1_url;
    private String pic6;
    private String pic5;
    private String pic3_url;
    private String pic4;
    private String pic3;
    private String pic8_url;
    private String pic2;
    private String pic5_url;

    public String getPic10() {
        return pic10;
    }

    public void setPic10(String pic10) {
        this.pic10 = pic10;
    }

    public String getVideoUrl() {
//        if(BuildConfig.DEBUG){
//            return "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4";
//        }
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getPic7_url() {
        return pic7_url;
    }

    public void setPic7_url(String pic7_url) {
        this.pic7_url = pic7_url;
    }

    public String getPic10_url() {
        return pic10_url;
    }

    public void setPic10_url(String pic10_url) {
        this.pic10_url = pic10_url;
    }

    public String getCellerIntroduct() {
        return cellerIntroduct;
    }

    public void setCellerIntroduct(String cellerIntroduct) {
        this.cellerIntroduct = cellerIntroduct;
    }

    public String getPic6_url() {
        return pic6_url;
    }

    public void setPic6_url(String pic6_url) {
        this.pic6_url = pic6_url;
    }

    public String getCellerDescprition() {
        return cellerDescprition;
    }

    public void setCellerDescprition(String cellerDescprition) {
        this.cellerDescprition = cellerDescprition;
    }

    public String getPic1() {
        return pic1;
    }

    public void setPic1(String pic1) {
        this.pic1 = pic1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPic2_url() {
        return pic2_url;
    }

    public void setPic2_url(String pic2_url) {
        this.pic2_url = pic2_url;
    }

    public String getPic9_url() {
        return pic9_url;
    }

    public void setPic9_url(String pic9) {
        this.pic9_url = pic9_url;
    }

    public String getPic4_url() {
        return pic4_url;
    }

    public void setPic4_url(String pic4_url) {
        this.pic4_url = pic4_url;
    }

    public String getPic8() {
        return pic8;
    }

    public void setPic8(String pic8) {
        this.pic8 = pic8;
    }

    public String getPic1_url() {
        return pic1_url;
    }

    public void setPic1_url(String pic1_url) {
        this.pic1_url = pic1_url;
    }

    public String getPic6() {
        return pic6;
    }

    public void setPic6(String pic6) {
        this.pic6 = pic6;
    }

    public String getPic5() {
        return pic5;
    }

    public void setPic5(String pic5) {
        this.pic5 = pic5;
    }

    public String getPic3_url() {
        return pic3_url;
    }

    public void setPic3_url(String pic3_url) {
        this.pic3_url = pic3_url;
    }

    public String getPic4() {
        return pic4;
    }

    public void setPic4(String pic4) {
        this.pic4 = pic4;
    }

    public String getPic3() {
        return pic3;
    }

    public void setPic3(String pic3) {
        this.pic3 = pic3;
    }

    public String getPic8_url() {
        return pic8_url;
    }

    public void setPic8_url(String pic8_url) {
        this.pic8_url = pic8_url;
    }

    public String getPic2() {
        return pic2;
    }

    public void setPic2(String pic2) {
        this.pic2 = pic2;
    }

    public String getPic5_url() {
        return pic5_url;
    }

    public void setPic5_url(String pic5_url) {
        this.pic5_url = pic5_url;
    }
}
