package com.qinshou.administrator.carsofferassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.qinshou.administrator.carsofferassistant.R;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.activity.SelectCityActivity;

/**
 * Created by 禽兽先生 on 2016.03.06
 */
public class AppointmentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
    }

    public void selectCity(View view) {
        Intent intent = new Intent(this, SelectCityActivity.class);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        TextView textView = (TextView) findViewById(R.id.show_city_tv);
        if (requestCode == 101 && resultCode == 202) {
            String resultCityName = data.getStringExtra("cityName");
            if (resultCityName != null && resultCityName.length() > 0) {
                textView.setText(resultCityName);
            }else{
                textView.setText("获取城市失败，点击重试...");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
