package com.qinshou.administrator.carsofferassistant.depreciatefiled.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.qinshou.administrator.carsofferassistant.R;

public class SelectCityActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(R.string.choose_city);

        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true); // 决定左上角图标的右侧是否有向左的小箭头, true

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void back(View view){

        Intent intent = new Intent();
        intent.putExtra("values","我是个开心的人哦");
        setResult(202,intent);
        finish();

    }
}
