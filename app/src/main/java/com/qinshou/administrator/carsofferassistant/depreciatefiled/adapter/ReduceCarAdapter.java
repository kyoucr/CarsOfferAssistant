package com.qinshou.administrator.carsofferassistant.depreciatefiled.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qinshou.administrator.carsofferassistant.R;
import com.qinshou.administrator.carsofferassistant.activity.AppointmentActivity;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.DealersBean;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.ListReduceCar;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.ReduceCar;

import org.w3c.dom.Text;

import java.util.List;

/**
 * 降价详情适配器
 * <p>
 * Created by zyj on 2016/7/10.
 */

public class ReduceCarAdapter extends BaseAdapter {
    private  List<ReduceCar> listCars;
    private Context context;

    public ReduceCarAdapter(List<ReduceCar> dataSource, Context context) {
        this.listCars = dataSource;
        this.context = context;
    }

    @Override
    public int getCount() {
        return listCars.size();
    }

    @Override
    public Object getItem(int position) {
        return listCars.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = View.inflate(context, R.layout.reduce_car_item, null);
            vh.tv_car_name = (TextView) convertView.findViewById(R.id.tv_car_name);
            vh.tv_now_price_id = (TextView) convertView.findViewById(R.id.tv_now_price_id);
            vh.tv_decreace_price_id = (TextView) convertView.findViewById(R.id.tv_decreace_price_id);
            vh.tv_old_price_id = (TextView) convertView.findViewById(R.id.tv_old_price_id);
            vh.btn_ask_lowest_price_id = (Button) convertView.findViewById(R.id.btn_ask_lowest_price_id);
            convertView.setTag(vh);

        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        ReduceCar dealersBean = listCars.get(position);
        vh.tv_car_name.setText(dealersBean.getCsName()+" "+dealersBean.getName()+" "+dealersBean.getCarYear()+"款");
        vh.tv_now_price_id.setText(dealersBean.getPromotePrice());
        vh.tv_decreace_price_id.setText(dealersBean.getReduce());
        vh.tv_old_price_id.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        vh.tv_old_price_id.setText(dealersBean.getVendorPrice());
        vh.btn_ask_lowest_price_id.setText("咨询底价");
        return convertView;
    }

//    @Override
//    public void onClick(View v) {
//        Intent appointmentIntent = new Intent(context, AppointmentActivity.class);
//        context.startActivity(appointmentIntent);
//    }

    private final class ViewHolder {
        TextView tv_car_name;
        TextView tv_now_price_id;
        TextView tv_decreace_price_id;
        TextView tv_old_price_id;
        Button btn_ask_lowest_price_id;
    }
}



