package com.qinshou.administrator.carsofferassistant.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.qinshou.administrator.carsofferassistant.R;
import com.qinshou.administrator.carsofferassistant.adapter.MyDetailImageAdapter;
import com.qinshou.administrator.carsofferassistant.inter.ImageShowCallback;
import com.qinshou.administrator.carsofferassistant.task.DetailCarImageDataAsyncTask;
import com.qinshou.administrator.carsofferassistant.task.DetailCarImageUrlAsyncTask;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DetailImageShowActivity extends AppCompatActivity implements ImageShowCallback{

    private List<Map<String, Object>> dataSource;
    private MyDetailImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_image_show);
        Intent intent = getIntent();
        String detailImageURL = intent.getStringExtra("detailImageURL");
        //1.找到界面上控件的实例
        GridView gv_car_images = (GridView) findViewById(R.id.gv_car_images);

        //2.数据源
        dataSource = new LinkedList<>();

        //3.适配器
        adapter = new MyDetailImageAdapter(dataSource,this);

        //4.绑定适配器
        gv_car_images.setAdapter(adapter);

        //5.添加监听器
        new DetailCarImageDataAsyncTask(this).execute(detailImageURL);
    }

    @Override
    public void callBack(List<String> result) {

        if (result != null && result.size() > 0) {
            for (int i = 0; i < result.size(); i++) {
                Map<String, Object> map = new LinkedHashMap<>();
                map.put("iv_default_detail_image_id", R.drawable.default_car);
                dataSource.add(map);
                new DetailCarImageUrlAsyncTask(dataSource, i, adapter,this).execute(result.get(i));
            }
            adapter.notifyDataSetChanged();
        }else {
           finish();//没有图片则不显示界面
            Toast.makeText(this,"该车没有图片可供查看。",Toast.LENGTH_LONG).show();
        }
    }
}
