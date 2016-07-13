package com.qinshou.administrator.carsofferassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.qinshou.administrator.carsofferassistant.R;

/**
 * Created by 禽兽先生 on 2016.03.06
 */
public class LoadingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
    }

    public void start(View view) {
        Intent intent = new Intent(LoadingActivity.this, MainActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade, R.anim.hold);
        finish();
    }
}
