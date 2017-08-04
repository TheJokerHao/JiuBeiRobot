package com.fecmobile.jiubeirobot.utils;

/**
 * 类描述    :分页工具类
 * 包名      : com.fecmobile.jiubeirobot.utils
 * 类名称    : PageUtils
 * 创建人    : ghy
 * 创建时间  : 2017/3/16 14:31
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class PageUtils {
    private int pageIndex = 0;//页码
    private int pageSize = 10;//每页大小
    private int totalNum;//总数量
    private boolean isMore;//是否有下页
    private boolean curentLoadMode;//false刷新、true加载更多

    public boolean isCurentLoadMode() {
        return curentLoadMode;
    }

    public void setCurentLoadMode(boolean curentLoadMode) {
        if (curentLoadMode) {
            pageIndex++;
        } else {
            pageIndex = 0;
        }
        this.curentLoadMode = curentLoadMode;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        if (pageSize <= 0) {
            isMore = false;
        }
        this.pageSize = pageSize;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public boolean isMore() {
        isMore = (pageIndex + 1) * pageSize < totalNum;
        L.i("pageIndex：" + (pageIndex + 1) + "   pageSize：" + pageSize + "   totalNum：" + totalNum + "  isMore：" + isMore);
        return isMore;
    }
}
