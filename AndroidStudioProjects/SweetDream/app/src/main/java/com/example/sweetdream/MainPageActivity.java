package com.example.sweetdream;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainPageActivity extends AppCompatActivity implements RadioGroup.OnCheckedChangeListener, MainFragment.OnFragmentInteractionListener, DataFragment.OnFragmentInteractionListener, SettingFragment.OnFragmentInteractionListener, PictureFragment.OnFragmentInteractionListener {


    private RadioGroup rg_tab_bar;
    private RadioButton rb_channel;

    //Fragment Object
    private MainFragment fg1;
    private DataFragment fg2;
    private PictureFragment fg3;
    private SettingFragment fg4;
    private FragmentManager fManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);
        fManager = getFragmentManager();
        rg_tab_bar = (RadioGroup) findViewById(R.id.rg_tab_bar);
        rg_tab_bar.setOnCheckedChangeListener(this);
        //获取第一个单选按钮，并设置其为选中状态
        rb_channel = (RadioButton) findViewById(R.id.rb_channel);
        rb_channel.setChecked(true);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        hideAllFragment(fTransaction);
        switch (checkedId){
            case R.id.rb_channel:
                if(fg1 == null){
                    fg1 = new MainFragment();
                    fTransaction.add(R.id.ly_content,fg1);
                }else{
                    fTransaction.show(fg1);
                }
                break;
            case R.id.rb_message:
                if(fg2 == null){
                    fg2 = new DataFragment();
                    fTransaction.add(R.id.ly_content,fg2);
                }else{
                    fTransaction.show(fg2);
                }
                break;
            case R.id.rb_better:
                if(fg3 == null){
                    fg3 = new PictureFragment();
                    fTransaction.add(R.id.ly_content,fg3);
                }else{
                    fTransaction.show(fg3);
                }
                break;
            case R.id.rb_setting:
                if(fg4 == null){
                    fg4 = new SettingFragment();
                    fTransaction.add(R.id.ly_content,fg4);
                }else{
                    fTransaction.show(fg4);
                }
                break;
        }
        fTransaction.commit();
    }

    //隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction){
        if(fg1 != null)fragmentTransaction.hide(fg1);
        if(fg2 != null)fragmentTransaction.hide(fg2);
        if(fg3 != null)fragmentTransaction.hide(fg3);
        if(fg4 != null)fragmentTransaction.hide(fg4);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    public void flush(int time)
    {
        fg1.mc.cancel();
        fg1.mc = fg1.new MyCountDownTimer(time, 1000);
        fg1.mc.start();
        String sleepHourS = UserManage.getInstance().getUserInfo(this).getSleepHourS();
        String sleepMinuteS = UserManage.getInstance().getUserInfo(this).getSleepMinuteS();
        fg1.sleepTime.setText(sleepHourS+":"+sleepMinuteS);
        String wakeupHourS = UserManage.getInstance().getUserInfo(this).getWakeUpHourS();
        String wakeupMinuteS = UserManage.getInstance().getUserInfo(this).getWakeUpMinuteS();
        fg1.wakeupTime.setText(wakeupHourS+":"+wakeupMinuteS);
    }
}
