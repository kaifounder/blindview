package com.example.zkw.blindview;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

public class ActivityManager {
    private static List<Activity> activities;
    private static ActivityManager instance;

    /**
     * 单例模式
     * 创建唯一实例
     */
    public static ActivityManager getInstance(){
        if (instance == null){
            instance = new ActivityManager();
        }
        return instance;
    }
    //初始化List集合
    private static void initActivityList(){
        if (activities == null){
            activities = new ArrayList<>();
        }
    }
    //添加当前Activity到堆栈
    public static void addActivity(Activity activity){
        initActivityList();
        activities.add(activity);
    }
    //获取当前的活动实例
    public static Activity currentActivity(){
        Activity activity = activities.get(activities.size()-1);
        return activity;
    }
    //结束指定活动
    public static void finishActivity(Activity activity){
        if (activity != null){
            activities.remove(activity);
            activity.finish();
        }
    }
    //结束当前活动
    public static void finishActivity(){
        Activity activity = activities.get(activities.size()-1);
        activity.finish();
    }
    //结束指定活动之外的所有活动
    public static void finishExcept(Activity exceptActivity){
        for (int i = 0; i < activities.size(); i++){
            if (null != activities.get(i)){
                Activity activity = activities.get(i);
                if (activity != exceptActivity){
                    if (!activity.isFinishing()){
                        activity.finish();
                    }
                }
            }
        }
        activities.clear();
        activities.add(exceptActivity);
    }
    //结束所有活动
    public static void finishAll(){
        for (int i = 0; i < activities.size(); i++){
            if (null != activities.get(i)){
                Activity activity = activities.get(i);
                if (!activity.isFinishing()){
                    activity.finish();
                }
            }
        }
        activities.clear();
    }
}
