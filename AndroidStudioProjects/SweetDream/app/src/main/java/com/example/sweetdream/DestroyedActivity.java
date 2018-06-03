package com.example.sweetdream;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class DestroyedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destroyed);
    }

    public void onClickHandler(View view)
    {
        if(view.getId() == R.id.iknow)
        {
            final Intent intent=new Intent(DestroyedActivity.this, MainPageActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
        }
    }
}
