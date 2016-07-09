package com.qinshou.administrator.carsofferassistant.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.qinshou.administrator.carsofferassistant.R;
import com.qinshou.administrator.carsofferassistant.activity.DetailImageShowActivity;

import java.util.List;
import java.util.Map;

/**
 * Created by zyj on 2016/7/9.
 */

public class MyDetailImageAdapter extends BaseAdapter {
    private List<Map<String, Object>> dataSource;
    private Context context;

    public MyDetailImageAdapter(List<Map<String, Object>> dataSource, Context context) {
        this.dataSource = dataSource;
        this.context = context;
    }

    @Override
    public int getCount() {
        return dataSource.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView view = null;

        if (convertView == null) {
            convertView = View.inflate(context,
                    R.layout.detail_image_item, null);
            view = (ImageView) convertView;
        } else {
            view = (ImageView) convertView;
        }

        Map<String,Object> map = dataSource.get(position);
        Object detailImageId = map.get("iv_default_detail_image_id");
        if (detailImageId instanceof Integer){
            view.setImageResource((Integer) detailImageId);
        }else if(detailImageId instanceof Bitmap){
            view.setImageBitmap((Bitmap) detailImageId);
        }
        return view;
    }
}
