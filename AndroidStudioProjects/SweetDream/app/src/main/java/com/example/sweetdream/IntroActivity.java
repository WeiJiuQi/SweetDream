package com.example.sweetdream;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import java.util.Timer;
import java.util.TimerTask;

public class IntroActivity extends AppCompatActivity {

    private static final int GO_HOME = 0;//去主页
    private static final int GO_LOGIN = 1;//去登录页
    /**
     * 跳转判断
     */
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME://去主页
                    final Intent intent1=new Intent(IntroActivity.this,MainPageActivity.class);
                    Timer timer1 = new Timer();
                    TimerTask timerTask1 = new TimerTask() {
                        @Override
                        public void run() {
                            startActivity(intent1);
                            finish();
                            overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                        }
                    };
                    timer1.schedule(timerTask1,1000);
//                    Intent intent = new Intent(IntroActivity.this, MainPageActivity.class);
//                    startActivity(intent);
//                    finish();
                    break;
                case GO_LOGIN://去登录页
                    final Intent intent2=new Intent(IntroActivity.this,LoginActivity.class);
                    Timer timer2 = new Timer();
                    TimerTask timerTask2 = new TimerTask() {
                        @Override
                        public void run() {
                            startActivity(intent2);
                            finish();
                            overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                        }
                    };
                    timer2.schedule(timerTask2,1000);
//                    Intent intent2 = new Intent(IntroActivity.this, LoginActivity.class);
//                    startActivity(intent2);
//                    finish();
                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        if (UserManage.getInstance().hasUserInfo(this))//自动登录判断，SharePrefences中有数据，则跳转到主页，没数据则跳转到登录页
        {
            mHandler.sendEmptyMessageDelayed(GO_HOME, 2000);
        } else {
            mHandler.sendEmptyMessageAtTime(GO_LOGIN, 2000);
        }
    }
}
