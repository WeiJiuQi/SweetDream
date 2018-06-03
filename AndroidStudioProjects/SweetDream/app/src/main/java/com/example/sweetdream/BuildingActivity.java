package com.example.sweetdream;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Calendar;

import scut.carson_ho.kawaii_loadingview.Kawaii_LoadingView;

import static com.example.sweetdream.WebInteraction.sha512;

public class BuildingActivity extends AppCompatActivity {

    // 1. 定义控件变量
    private Kawaii_LoadingView Kawaii_LoadingView;
    public MyCountDownTimer mc;
    private Button wakeup;
    private boolean Hasbeenquit=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_building);

        // 2. 绑定控件
        Kawaii_LoadingView = (Kawaii_LoadingView) findViewById(R.id.Kawaii_LoadingView);

        // 3. 使用动画（API说明）
        // 3.1 启动动画
        Kawaii_LoadingView.startMoving();
        // 3.2 停止动画
        //Kawaii_LoadingView.stopMoving();
        wakeup = (Button)findViewById(R.id.wakeup);
        int wakeupHour = UserManage.getInstance().getUserInfo(this).getWakeUpHour();
        int wakeupMinute = UserManage.getInstance().getUserInfo(this).getWakeUpMinute();
        int time = dTime(wakeupHour, wakeupMinute);
        mc = new MyCountDownTimer(time, 1000);
        mc.start();
        UserManage.getInstance().saveIsSleeping(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Hasbeenquit=true;

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Hasbeenquit){
            Hasbeenquit=false;
            Log.e("@@@@@@@@@@@@@@@@@","this is has been quit!!");
            if (UserManage.getInstance().getUserInfo(getApplicationContext()).getIsSleeping()){
                //执行应用切换到后台的逻辑
                Log.v("aa","bb");
                if(isover(UserManage.getInstance().getUserInfo(getApplicationContext()).getSleepHour(), UserManage.getInstance().getUserInfo(getApplicationContext()).getSleepMinute()))
                {
                    Log.v("aaa","bbb");
                    UserManage.getInstance().saveIsNotSleeping(getApplicationContext());
                    UserManage.getInstance().saveUserWakeupTimeToday(getApplicationContext(), UserManage.getInstance().getUserInfo(getApplicationContext()).getSleepHourToday(), UserManage.getInstance().getUserInfo(getApplicationContext()).getSleepMinuteToday());
                    UserManage.getInstance().calUserSleepTime(getApplicationContext());
                    float num1 = (float)UserManage.getInstance().getUserInfo(getApplicationContext()).getNum1();
                    float num2 = (float)UserManage.getInstance().getUserInfo(getApplicationContext()).getNum2();
                    float num3 = (float)UserManage.getInstance().getUserInfo(getApplicationContext()).getNum3();
                    float num4 = (float)UserManage.getInstance().getUserInfo(getApplicationContext()).getNum4();
                    float num5 = (float)UserManage.getInstance().getUserInfo(getApplicationContext()).getNum5();
                    float num6 = (float)UserManage.getInstance().getUserInfo(getApplicationContext()).getNum6();
                    float num7 = (float)UserManage.getInstance().getUserInfo(getApplicationContext()).getNum7();
                    String phone_sha512=sha512(UserManage.getInstance().getUserInfo(getApplicationContext()).getUserName());
                    String stringUrl = "http://39.106.155.126:3000/updatedata?user_name="+phone_sha512+"&time1="+num1+"&time2="+num2+"&time3="+num3+"&time4="+num4+"&time5="+num5+"&time6="+num6+"&time7="+num7;
                    new WebInteraction().execute(stringUrl);
                    final Intent intent=new Intent(getApplicationContext(),DestroyedActivity.class);
                    startActivity(intent);
                }
                else
                {
                    Log.v("aaaa","bbbb");
                    UserManage.getInstance().saveIsNotSleeping(getApplicationContext());
                    final Intent intent=new Intent(getApplicationContext(),MainPageActivity.class);
                    startActivity(intent);
                }
            }


        } else{
            Log.e("@@@@@@@@@@@@@@@@@","this is has not been quit!!");
        }

    }

    public boolean isover(int givenHour, int givenMinute){

        long currentTimeInMillis = System.currentTimeMillis();
        Calendar givenCalendar = Calendar.getInstance();
        int nowHour = givenCalendar.get(Calendar.HOUR_OF_DAY);
        int nowMinute = givenCalendar.get(Calendar.MINUTE);

        Log.v("getSleepHour",String.valueOf(UserManage.getInstance().getUserInfo(getApplicationContext()).getSleepHour()));
        Log.v("getSleepMinute",String.valueOf(UserManage.getInstance().getUserInfo(getApplicationContext()).getSleepMinute()));
        Log.v("nowHour",String.valueOf(nowHour));
        Log.v("nowMinute",String.valueOf(nowMinute));
        if(givenHour>=0)
        {
            if(nowHour>=0)
            {
                if(givenHour>nowHour||(givenHour==nowHour && givenMinute>nowMinute)){
                    return false;
                }
                else
                {
                    return true;
                }
            }
            else
            {
                return false;
            }
        }
        else
        {
            if(nowHour>=0)
            {
                return true;
            }
            else
            {
                if(givenHour>nowHour || (givenHour == nowHour && givenMinute>nowMinute))
                {
                    return false;
                }
                else
                {
                    return true;
                }
            }
        }
    }






    public void onClickHandler(View view)
    {
        Calendar ca = Calendar.getInstance();
        UserManage.getInstance().saveUserWakeupTimeToday(this, ca.get(Calendar.HOUR_OF_DAY), ca.get(Calendar.MINUTE));
//        Log.v("nowHour", String.valueOf(ca.get(Calendar.HOUR_OF_DAY)));
//        Log.v("nowMinute", String.valueOf( ca.get(Calendar.MINUTE)));
//        Log.v("sleepHour",String.valueOf(UserManage.getInstance().getUserInfo(this).getWakeupHourToday()));
//        Log.v("sleepMinute",String.valueOf(UserManage.getInstance().getUserInfo(this).getWakeupMinuteToday()));
        UserManage.getInstance().calUserSleepTime(this);
        mc.cancel();
        wakeup.setVisibility(View.INVISIBLE);
        UserManage.getInstance().saveIsNotSleeping(this);
        float num1 = (float)UserManage.getInstance().getUserInfo(this).getNum1();
        float num2 = (float)UserManage.getInstance().getUserInfo(this).getNum2();
        float num3 = (float)UserManage.getInstance().getUserInfo(this).getNum3();
        float num4 = (float)UserManage.getInstance().getUserInfo(this).getNum4();
        float num5 = (float)UserManage.getInstance().getUserInfo(this).getNum5();
        float num6 = (float)UserManage.getInstance().getUserInfo(this).getNum6();
        float num7 = (float)UserManage.getInstance().getUserInfo(this).getNum7();
        String phone_sha512=sha512(UserManage.getInstance().getUserInfo(this).getUserName());
        String stringUrl = "http://39.106.155.126:3000/updatedata?user_name="+phone_sha512+"&time1="+num1+"&time2="+num2+"&time3="+num3+"&time4="+num4+"&time5="+num5+"&time6="+num6+"&time7="+num7;
        new WebInteraction().execute(stringUrl);
        final Intent intent=new Intent(BuildingActivity.this,GetHouseActivity.class);
        startActivity(intent);
        finish();
        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
    }




    /**只响一次的 时间间隔
     * @param givenHour  自定义的小时
     * @param givenMinute  自定义的分钟
     * @return 返回"当前时间"距"给定时间"的时间差（单位为毫秒）      如果"给定时间"<"当前时间"，则返回的是下一天的"给定时间"
     */
    public int dTime(int givenHour, int givenMinute){

        long currentTimeInMillis = System.currentTimeMillis();
        Calendar givenCalendar = Calendar.getInstance();
//        TimeZone timeZone = TimeZone.getTimeZone("GMT+8");
//        givenCalendar.setTimeZone(timeZone);
        givenCalendar.set(Calendar.HOUR_OF_DAY, givenHour);
        givenCalendar.set(Calendar.MINUTE, givenMinute);
        givenCalendar.set(Calendar.SECOND, 0);
        givenCalendar.set(Calendar.MILLISECOND, 0);
        long givenTimeInMillis = givenCalendar.getTimeInMillis();
        //currentTimeInMillis += 28800000;
        int result;
        if(currentTimeInMillis <= givenTimeInMillis){
            result = (int) (givenTimeInMillis - currentTimeInMillis);
        }else{
            result = (int) (24*60*60*1000 - (currentTimeInMillis - givenTimeInMillis));
        }
        return result;
    }

    public class MyCountDownTimer extends CountDownTimer {
        /**
         *
         * @param millisInFuture
         *      表示以毫秒为单位 倒计时的总数
         *
         *      例如 millisInFuture=1000 表示1秒
         *
         * @param countDownInterval
         *      表示 间隔 多少微秒 调用一次 onTick 方法
         *
         *      例如: countDownInterval =1000 ; 表示每1000毫秒调用一次onTick()
         *
         */

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onFinish() {
            UserManage.getInstance().saveUserWakeupTimeToday(BuildingActivity.this, UserManage.getInstance().getUserInfo(BuildingActivity.this).getSleepHourToday(), UserManage.getInstance().getUserInfo(BuildingActivity.this).getSleepMinuteToday());
            UserManage.getInstance().calUserSleepTime(BuildingActivity.this);
            wakeup.setVisibility(View.INVISIBLE);
            UserManage.getInstance().saveIsNotSleeping(BuildingActivity.this);
            float num1 = (float)UserManage.getInstance().getUserInfo(getApplicationContext()).getNum1();
            float num2 = (float)UserManage.getInstance().getUserInfo(getApplicationContext()).getNum2();
            float num3 = (float)UserManage.getInstance().getUserInfo(getApplicationContext()).getNum3();
            float num4 = (float)UserManage.getInstance().getUserInfo(getApplicationContext()).getNum4();
            float num5 = (float)UserManage.getInstance().getUserInfo(getApplicationContext()).getNum5();
            float num6 = (float)UserManage.getInstance().getUserInfo(getApplicationContext()).getNum6();
            float num7 = (float)UserManage.getInstance().getUserInfo(getApplicationContext()).getNum7();
            String phone_sha512=sha512(UserManage.getInstance().getUserInfo(getApplicationContext()).getUserName());
            String stringUrl = "http://39.106.155.126:3000/updatedata?user_name="+phone_sha512+"&time1="+num1+"&time2="+num2+"&time3="+num3+"&time4="+num4+"&time5="+num5+"&time6="+num6+"&time7="+num7;
            new WebInteraction().execute(stringUrl);
            final Intent intent=new Intent(BuildingActivity.this,DestroyedActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            if(millisUntilFinished < 2*60*60*1000)
            {
                wakeup.setVisibility(View.VISIBLE);
            }
        }
    }
}
