package com.itculturalfestival.smartcampus.utils;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * @creation_time: 2017/3/27
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 用于强制下线
 */

public class ActivityCollectorUtil {

    public static List<Activity> activities = new ArrayList<>();

    public static  void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static void finishAllActivity(){
        for (Activity activity: activities){
            if (!activity.isFinishing()){
                activity.finish();
            }
        }
    }
}
