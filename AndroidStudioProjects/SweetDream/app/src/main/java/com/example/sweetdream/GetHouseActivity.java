package com.example.sweetdream;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static com.example.sweetdream.WebInteraction.sha512;

public class GetHouseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_housectivity);
        int pic_cnt = UserManage.getInstance().getUserInfo(this).getPic_cnt();
        pic_cnt++;
        UserManage.getInstance().saveUserPicCnt(this, pic_cnt);
        PictureFragment.mList.setAdapter(new Adapter(this, UserManage.getInstance().getUserInfo(this).getPic_cnt()));
        String phone_sha512=sha512(UserManage.getInstance().getUserInfo(getApplicationContext()).getUserName());
        String stringUrl = "http://39.106.155.126:3000/updatepic?user_name="+phone_sha512+"&pic_cnt="+pic_cnt;
        new WebInteraction().execute(stringUrl);
    }

    public void onClickHandler(View view)
    {
        if(view.getId() == R.id.backreturn)
        {
            final Intent intent=new Intent(GetHouseActivity.this, MainPageActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        }
    }
}
