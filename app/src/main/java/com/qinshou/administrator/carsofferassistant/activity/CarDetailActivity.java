package com.qinshou.administrator.carsofferassistant.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qinshou.administrator.carsofferassistant.R;
import com.qinshou.administrator.carsofferassistant.bean.CarIntro;
import com.qinshou.administrator.carsofferassistant.constant.Urls;
import com.qinshou.administrator.carsofferassistant.task.CarDetailTask;
import com.qinshou.administrator.carsofferassistant.utils.DownloadXml;
import com.qinshou.administrator.carsofferassistant.utils.ParseXml;

/**
 * Created by 禽兽先生 on 2016.07.06
 */
public class CarDetailActivity extends AppCompatActivity {
    private String carDetailUrl = Urls.BEFORE_SERIALID;
    private ExpandableListView car_detail_elv;
    private ImageView car_pic_iv;
    private TextView car_name_tv, country_tv, level_tv, displacement_tv;
    private String serialId;
    private CarIntro carIntro;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cardetail);
        Intent intent = getIntent();
        serialId = intent.getStringExtra("serialId");
        if (intent.getBooleanExtra("URL_Flg", false)) {
            carDetailUrl = Urls.BEFORE_SERIALID_KEY;
        }
        init();
        new CarDetailTask(this, car_detail_elv, null).execute(carDetailUrl + serialId);
        new Thread() {
            @Override
            public void run() {
                super.run();
                carIntro = ParseXml.parseCarIntro(DownloadXml.downloadInputStream(carDetailUrl + serialId));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(CarDetailActivity.this).load(carIntro.getPic()).into(car_pic_iv);
                        car_name_tv.setText(carIntro.getName());
                        country_tv.setText(carIntro.getCountry());
                        level_tv.setText(carIntro.getLevel());
                        displacement_tv.setText(carIntro.getDisplacement());
                    }
                });
            }
        }.start();
    }

    private void init() {
        car_detail_elv = (ExpandableListView) findViewById(R.id.car_detail_elv);
        car_pic_iv = (ImageView) findViewById(R.id.car_pic_iv);
        car_name_tv = (TextView) findViewById(R.id.car_name_tv);
        country_tv = (TextView) findViewById(R.id.country_tv);
        level_tv = (TextView) findViewById(R.id.level_tv);
        displacement_tv = (TextView) findViewById(R.id.displacement_tv);
    }

    /**
     * 点击图片显示详细图片
     *
     * @param view
     */
    public void showDetailImage(View view) {
        Intent intent = new Intent(this,DetailImageShowActivity.class);
        intent.putExtra("detailImageURL",Urls.GRID_IMAGE+serialId);
        startActivity(intent);
    }


}
