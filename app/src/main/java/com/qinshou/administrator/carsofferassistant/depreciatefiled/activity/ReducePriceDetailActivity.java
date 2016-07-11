package com.qinshou.administrator.carsofferassistant.depreciatefiled.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.qinshou.administrator.carsofferassistant.R;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.ReduceCar;

import java.util.LinkedList;
import java.util.List;

public class ReducePriceDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reduce_price_detail);
        //1.找到界面上控件的实例
        ListView lv_all_car_reduce_price_id = (ListView) findViewById(R.id.lv_all_car_reduce_price_id);
        TextView tv_emptyp_id = (TextView) findViewById(R.id.tv_emptyp_id);
        lv_all_car_reduce_price_id.setEmptyView(tv_emptyp_id);

        //2.数据源
        List<ReduceCar> dataSource = new LinkedList<>();

        //3.适配器


        //4.绑定适配器

        //5.添加监听器
    }
}
