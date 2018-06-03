package com.example.sweetdream; /**
 * Created by Gin Wei on 2018/5/5.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import java.text.DecimalFormat;
import java.util.Calendar;

/**
 * 保存用户信息的管理类
 */

public class UserManage {

    private static UserManage instance;

    private UserManage() {
    }

    public static UserManage getInstance() {
        if (instance == null) {
            instance = new UserManage();
        }
        return instance;
    }


    /**
     * 保存自动登录的用户信息
     */
    public void saveUserInfo(Context context, String username, String password) {
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);//Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("USER_NAME", username);
        editor.putString("PASSWORD", password);
        editor.commit();
    }


    /**
     * 获取用户信息model
     *
     * @param context
     * @param
     * @param
     */
    public UserInfo getUserInfo(Context context) {
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        UserInfo userInfo = new UserInfo();
        userInfo.setUserName(sp.getString("USER_NAME", ""));
        userInfo.setPassword(sp.getString("PASSWORD", ""));
        userInfo.setSleepHour(sp.getInt("Sleep_Hour", 0));
        userInfo.setSleepMinute(sp.getInt("Sleep_Minute", 0));
        userInfo.setWakeUpHour(sp.getInt("WakeUp_Hour", 0));
        userInfo.setWakeUpMinute(sp.getInt("WakeUp_Minute", 0));
        userInfo.setSleepHourS(sp.getString("Sleep_HourS", "00"));
        userInfo.setSleepMinuteS(sp.getString("Sleep_MinuteS", "00"));
        userInfo.setWakeUpHourS(sp.getString("WakeUp_HourS", "00"));
        userInfo.setWakeUpMinuteS(sp.getString("WakeUp_MinuteS", "00"));
        userInfo.setNum1(sp.getFloat("Num1", (float)0.0));
        userInfo.setNum2(sp.getFloat("Num2", (float)0.0));
        userInfo.setNum3(sp.getFloat("Num3", (float)0.0));
        userInfo.setNum4(sp.getFloat("Num4", (float)0.0));
        userInfo.setNum5(sp.getFloat("Num5", (float)0.0));
        userInfo.setNum6(sp.getFloat("Num6", (float)0.0));
        userInfo.setNum7(sp.getFloat("Num7", (float)0.0));
        userInfo.setSleepHourToday(sp.getInt("Sleep_Hour_Today", 0));
        userInfo.setSleepMinuteToday(sp.getInt("Sleep_Minute_Today", 0));
        userInfo.setWakeupHourToday(sp.getInt("Wakeup_Hour_Today", 0));
        userInfo.setWakeupMinuteToday(sp.getInt("Wakeup_Minute_Today", 0));
        userInfo.setIsSleeping(sp.getBoolean("Is_Sleeping", false));
        userInfo.setPic_cnt(sp.getInt("Pic_Cnt", 0));
        return userInfo;
    }


    /**
     * userInfo中是否有数据
     */
    public boolean hasUserInfo(Context context) {
        UserInfo userInfo = getUserInfo(context);
        if (userInfo != null) {
            if ((!TextUtils.isEmpty(userInfo.getUserName())) && (!TextUtils.isEmpty(userInfo.getPassword()))) {//有数据
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 设置就寝时间
     */
    public void saveUserSleepTime(Context context, int sleepHour, int sleepMinute) {
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);//Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("Sleep_Hour", sleepHour);
        editor.putInt("Sleep_Minute", sleepMinute);
        editor.commit();
    }

    /**
     * 设置起床时间
     */
    public void saveUserWakeUpTime(Context context, int wakeupHour, int wakeupMinute) {
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);//Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("WakeUp_Hour", wakeupHour);
        editor.putInt("WakeUp_Minute", wakeupMinute);
        editor.commit();
    }

    /**
     * 设置就寝时间S
     */
    public void saveUserSleepTimeS(Context context, String sleepHourS, String sleepMinuteS) {
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);//Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("Sleep_HourS", sleepHourS);
        editor.putString("Sleep_MinuteS", sleepMinuteS);
        editor.commit();
    }

    /**
     * 设置起床时间
     */
    public void saveUserWakeUpTimeS(Context context, String wakeupHourS, String wakeupMinuteS) {
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);//Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("WakeUp_HourS", wakeupHourS);
        editor.putString("WakeUp_MinuteS", wakeupMinuteS);
        editor.commit();
    }

    /**
     * 设置当天睡觉时间
     */
    public void saveUserSleepTimeToday(Context context, int sleepHourToday, int sleepMinuteToday){
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);//Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("Sleep_Hour_Today", sleepHourToday);
        editor.putInt("Sleep_Minute_Today", sleepMinuteToday);
        editor.commit();
    }

    /**
     * 设置当天睡觉时间
     */
    public void saveUserWakeupTimeToday(Context context, int wakeupHourToday, int wakeupMinuteToday){
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);//Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("Wakeup_Hour_Today", wakeupHourToday);
        editor.putInt("Wakeup_Minute_Today", wakeupMinuteToday);
        editor.commit();
    }

    /**
     * 统计当天睡眠时间并更新图表
     */
    public void calUserSleepTime(Context context)
    {
        DecimalFormat df = new DecimalFormat("#.0");
        double hourToday = Double.parseDouble(df.format((double)dTime(context)/(double)(1*60*60*1000)));
        Log.v("hourToday",String.valueOf(hourToday));

        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);//Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat("Num1", (float)UserManage.getInstance().getUserInfo(context).getNum2());
        editor.putFloat("Num2", (float)UserManage.getInstance().getUserInfo(context).getNum3());
        editor.putFloat("Num3", (float)UserManage.getInstance().getUserInfo(context).getNum4());
        editor.putFloat("Num4", (float)UserManage.getInstance().getUserInfo(context).getNum5());
        editor.putFloat("Num5", (float)UserManage.getInstance().getUserInfo(context).getNum6());
        editor.putFloat("Num6", (float)UserManage.getInstance().getUserInfo(context).getNum7());
        editor.putFloat("Num7", (float)hourToday);
        editor.putInt("Sleep_Hour_Today", 0);
        editor.putInt("Sleep_Minute_Today", 0);
        editor.putInt("Wakeup_Hour_Today", 0);
        editor.putInt("Wakeup_Minute_Today", 0);
        editor.commit();
    }

    /**
     * 统计当天睡眠时间并更新图表
     */
    public void updateNums(Context context, float num1, float num2, float num3, float num4, float num5, float num6, float num7)
    {
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);//Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
        SharedPreferences.Editor editor = sp.edit();
        editor.putFloat("Num1", num1);
        editor.putFloat("Num2", num2);
        editor.putFloat("Num3", num3);
        editor.putFloat("Num4", num4);
        editor.putFloat("Num5", num5);
        editor.putFloat("Num6", num6);
        editor.putFloat("Num7", num7);
        editor.commit();
    }

    /**
     * 计算睡眠时长
     */
    public int dTime(Context context){

        Calendar givenCalendar = Calendar.getInstance();
        givenCalendar.set(Calendar.HOUR_OF_DAY, UserManage.getInstance().getUserInfo(context).getSleepHourToday());
        givenCalendar.set(Calendar.MINUTE, UserManage.getInstance().getUserInfo(context).getSleepMinuteToday());
        givenCalendar.set(Calendar.SECOND, 0);
        givenCalendar.set(Calendar.MILLISECOND, 0);
        long sleepTimeInMillis = givenCalendar.getTimeInMillis();

        Calendar givenCalendar2 = Calendar.getInstance();
        givenCalendar2.set(Calendar.HOUR_OF_DAY, UserManage.getInstance().getUserInfo(context).getWakeupHourToday());
        givenCalendar2.set(Calendar.MINUTE, UserManage.getInstance().getUserInfo(context).getWakeupMinuteToday());
        givenCalendar2.set(Calendar.SECOND, 0);
        givenCalendar2.set(Calendar.MILLISECOND, 0);
        long wakeupTimeInMillis = givenCalendar2.getTimeInMillis();

        int result;
        if(sleepTimeInMillis <= wakeupTimeInMillis){
            result = (int) (wakeupTimeInMillis - sleepTimeInMillis);
        }else{
            result = (int) (24*60*60*1000 - (sleepTimeInMillis - wakeupTimeInMillis));
        }
        Log.v("debug0",String.valueOf(sleepTimeInMillis));
        Log.v("debug1",String.valueOf(wakeupTimeInMillis));
        Log.v("debug",String.valueOf(result));
        return result;
    }

    /**
     * 设置正在睡觉
     */
    public void saveIsSleeping(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);//Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("Is_Sleeping", true);
        editor.commit();
    }

    /**
     * 设置起床
     */
    public void saveIsNotSleeping(Context context)
    {
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);//Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean("Is_Sleeping", false);
        editor.commit();
    }

    /**
     * 设置当天睡觉时间
     */
    public void saveUserPicCnt(Context context, int pic_cnt){
        SharedPreferences sp = context.getSharedPreferences("userInfo", Context.MODE_PRIVATE);//Context.MODE_PRIVATE表示SharePrefences的数据只有自己应用程序能访问。
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt("Pic_Cnt", pic_cnt);
        editor.commit();
    }
}
