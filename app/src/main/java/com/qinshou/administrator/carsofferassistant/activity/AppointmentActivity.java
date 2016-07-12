package com.qinshou.administrator.carsofferassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qinshou.administrator.carsofferassistant.R;

/**
 * Created by 禽兽先生 on 2016.03.06
 */
public class AppointmentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        Intent intent = getIntent();
        String pic = intent.getStringExtra("pic");
        String name = intent.getStringExtra("name");
        String carName = intent.getStringExtra("carName");

        //1.找到界面上控件的实例
        ImageView appointment_car_iv = (ImageView) findViewById(R.id.appointment_car_iv);
        TextView appointment_serial_name_tv = (TextView) findViewById(R.id.appointment_serial_name_tv);
        TextView appointment_car_name_tv = (TextView) findViewById(R.id.appointment_car_name_tv);

        //2.将数据设置到控件中。
        Glide.with(this).load(pic).into(appointment_car_iv);
        appointment_serial_name_tv.setText(name);
        appointment_car_name_tv.setText(carName);
    }
}
