package com.example.sweetdream; /**
 * Created by Gin Wei on 2018/5/5.
 */

/**
 * 用户信息model
 */

public class UserInfo {

    /**
     * 用户名
     */
    private String userName;

    /**
     * 密码
     */
    private String password;

    /**
     * 就寝目标
     */
    private int sleepHour;
    private int sleepMinute;
    private String sleepHourS;
    private String sleepMinuteS;

    /**
     * 起床目标
     */
    private int wakeupHour;
    private int wakeupMinute;
    private String wakeupHourS;
    private String wakeupMinuteS;

    /**
     * 睡眠数据
     */
    public double num1;
    public double num2;
    public double num3;
    public double num4;
    public double num5;
    public double num6;
    public double num7;


    /**
     * 当天睡眠和起床时间
     */
    public int sleepHourToday;
    public int sleepMinuteToday;
    public int wakeupHourToday;
    public int wakeupMinuteToday;

    /**
     * 图鉴数量
     */
    public int pic_cnt;


    /**
     * 是否正在睡觉
     */

    boolean isSleeping = false;


    public int getPic_cnt() {
        return pic_cnt;
    }

    public void setPic_cnt(int pic_cnt) {
        this.pic_cnt = pic_cnt;
    }

    public boolean getIsSleeping() {
        return isSleeping;
    }

    public void setIsSleeping(boolean isSleeping) {
        this.isSleeping = isSleeping;
    }

    public int getSleepHourToday() {
        return sleepHourToday;
    }

    public void setSleepHourToday(int sleepHourToday) {
        this.sleepHourToday = sleepHourToday;
    }

    public int getSleepMinuteToday() {
        return sleepMinuteToday;
    }

    public void setSleepMinuteToday(int sleepMinuteToday) {
        this.sleepMinuteToday = sleepMinuteToday;
    }

    public int getWakeupHourToday() {
        return wakeupHourToday;
    }

    public void setWakeupHourToday(int wakeupHourToday) {
        this.wakeupHourToday = wakeupHourToday;
    }

    public int getWakeupMinuteToday() {
        return wakeupMinuteToday;
    }

    public void setWakeupMinuteToday(int wakeupMinuteToday) {
        this.wakeupMinuteToday = wakeupMinuteToday;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getSleepHour() {
        return sleepHour;
    }

    public void setSleepHour(int sleepHour) { this.sleepHour = sleepHour; }

    public int getSleepMinute() { return sleepMinute; }

    public void setSleepMinute(int sleepMinute) {
        this.sleepMinute = sleepMinute;
    }

    public int getWakeUpHour() {
        return wakeupHour;
    }

    public void setWakeUpHour(int wakeupHour) {
        this.wakeupHour = wakeupHour;
    }

    public int getWakeUpMinute() {
        return wakeupMinute;
    }

    public void setWakeUpMinute(int wakeupMinute) {
        this.wakeupMinute = wakeupMinute;
    }

    public String getSleepHourS() {
        return sleepHourS;
    }

    public void setSleepHourS(String sleepHourS) {
        this.sleepHourS = sleepHourS;
    }

    public String getSleepMinuteS() {
        return sleepMinuteS;
    }

    public void setSleepMinuteS(String sleepMinuteS) {
        this.sleepMinuteS = sleepMinuteS;
    }

    public String getWakeUpHourS() {
        return wakeupHourS;
    }

    public void setWakeUpHourS(String wakeupHourS) {
        this.wakeupHourS = wakeupHourS;
    }

    public String getWakeUpMinuteS() { return wakeupMinuteS;}

    public void setWakeUpMinuteS(String wakeupMinuteS) {
        this.wakeupMinuteS = wakeupMinuteS;
    }

    public double getNum1(){ return num1; }

    public void setNum1(double num1){ this.num1 = num1; }

    public double getNum2(){ return num2; }

    public void setNum2(double num2){ this.num2 = num2; }

    public double getNum3(){ return num3; }

    public void setNum3(double num3){ this.num3 = num3; }

    public double getNum4(){ return num4; }

    public void setNum4(double num4){ this.num4 = num4; }

    public double getNum5(){ return num5; }

    public void setNum5(double num5){ this.num5 = num5; }

    public double getNum6(){ return num6; }

    public void setNum6(double num6){ this.num6 = num6; }

    public double getNum7(){ return num7; }

    public void setNum7(double num7){ this.num7 = num7; }

}