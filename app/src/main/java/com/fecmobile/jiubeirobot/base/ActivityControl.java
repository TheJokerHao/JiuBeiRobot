package com.fecmobile.jiubeirobot.base;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;

/**
 * 类描述    :管理所有Activity
 * 包名      : com.fecmobile.jiubeirobot.base
 * 类名称    : ActivityControl
 * 创建人    : ghy
 * 创建时间  : 2017/2/21 19:54
 * 修改人    :
 * 修改时间  :
 * 修改备注  :
 */
public class ActivityControl {
    private static Set<BaseActivity> allActivities = new HashSet<>();
    private WeakReference<BaseActivity> currentAtivity;//弱引用集合
    private static ActivityControl activityControl = new ActivityControl();

    /**
     * 描述      :  设置当前运行的Activity。
     * 方法名    :  setCurrentAtivity
     * param    :  currentAtivity 设置当前运行的Activity
     * 返回类型  :  void
     * 创建人    : ghy
     * 创建时间  : 2017/2/21 19:55
     * 修改人    :
     * 修改时间
     * 修改备注
     * throws
     */
    public void setCurrentAtivity(BaseActivity currentAtivity) {
        this.currentAtivity = new WeakReference<>(currentAtivity);
    }

    /**
     * 描述      :  获取当前运行的Activity,有可能返回null
     * 方法名    :  getCurrentAtivity
     * param    : 无
     * 返回类型  : BaseActivity
     * 创建人    : ghy
     * 创建时间  : 2017/2/21 19:56
     * 修改人    :
     * 修改时间
     * 修改备注
     * throws
     */
    public BaseActivity getCurrentAtivity() {
        return currentAtivity.get();
    }

    private ActivityControl() {
    }

    /**
     * 描述      :获取当前对象，单例模式
     * 方法名    :  getActivityControl
     * param    : 无
     * 返回类型  : ActivityControl 当前实例
     * 创建人    : ghy
     * 创建时间  : 2017/2/21 19:56
     * 修改人    :
     * 修改时间
     * 修改备注
     * throws
     */
    public static ActivityControl getActivityControl() {
        return activityControl;
    }

    /**
     * 描述      : 添加Activity到管理
     * 方法名    :  addActivity
     * param    :   act Activity
     * 返回类型  : void
     * 创建人    : ghy
     * 创建时间  : 2017/2/21 19:57
     * 修改人    :
     * 修改时间
     * 修改备注
     * throws
     */
    public void addActivity(BaseActivity act) {
        if (allActivities == null) {
            allActivities = new HashSet<>();
        }
        allActivities.add(act);
    }

    /**
     * 描述      :从管理器移除Activity，一般在Ondestroy移除，防止强引用内存泄漏
     * 方法名    :  removeActivity
     * param    :   act Activity
     * 返回类型  : void
     * 创建人    : ghy
     * 创建时间  : 2017/2/21 19:57
     * 修改人    :
     * 修改时间
     * 修改备注
     * throws
     */
    public void removeActivity(BaseActivity act) {
        if (allActivities != null) {
            allActivities.remove(act);
        }
    }


    /**
     * 描述      :关闭所有Activity
     * 方法名    :  finishiAll
     * param    :无
     * 返回类型  :void
     * 创建人    : ghy
     * 创建时间  : 2017/2/21 19:58
     * 修改人    :
     * 修改时间
     * 修改备注
     * throws
     */
    public void finishiAll() {
        if (allActivities != null) {
            synchronized (allActivities) {
                for (BaseActivity act : allActivities) {
                    act.finish();
                }
            }
        }
    }
}
