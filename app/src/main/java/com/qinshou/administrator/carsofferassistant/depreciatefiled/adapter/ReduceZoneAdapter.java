package com.qinshou.administrator.carsofferassistant.depreciatefiled.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qinshou.administrator.carsofferassistant.R;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.DealersBean;

import java.util.List;
import java.util.Map;

/**
 * 降价详情适配器
 * <p>
 * Created by zyj on 2016/7/10.
 */

public class ReduceZoneAdapter extends BaseAdapter {
    private List<DealersBean> dataSource;
    private Context context;

    public ReduceZoneAdapter(List<DealersBean> dataSource, Context context) {
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
        ViewHolder vh = null;
        if (convertView == null) {
            vh = new ViewHolder();
            convertView = View.inflate(context, R.layout.tabdetal_item, null);

            vh.iv_car_pic_id = (ImageView) convertView.findViewById(R.id.iv_car_pic_id);
            vh.tv_car_name = (TextView) convertView.findViewById(R.id.tv_car_name);
            vh.tv_now_price_id = (TextView) convertView.findViewById(R.id.tv_now_price_id);
            vh.tv_vendor_price = (TextView) convertView.findViewById(R.id.tv_vendor_price);
            vh.tv_reduce_price_id = (TextView) convertView.findViewById(R.id.tv_reduce_price_id);
            vh.tv_dealer_type_id = (TextView) convertView.findViewById(R.id.tv_dealer_type_id);
            vh.tv_sale_range_id = (TextView) convertView.findViewById(R.id.tv_sale_range_id);
            convertView.setTag(vh);

        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        DealersBean dealersBean = dataSource.get(position);
        String pic = dealersBean.getPic();
        String dealerType = dealersBean.getDealerType();
        String csName = dealersBean.getCsName();
        String name = dealersBean.getName();
        String vendorPrice = dealersBean.getVendorPrice();
        String dealerName = dealersBean.getDealerName();
        String carYear = dealersBean.getCarYear();
        String reduce = dealersBean.getReduce();
        String saleRange = dealersBean.getSaleRange();
        String promotePrice = dealersBean.getPromotePrice();

        String id = dealersBean.getId();
        String dealerId = dealersBean.getDealerId();
        String carId = dealersBean.getCarId();
        String csPic = dealersBean.getCsPic();

        Glide.with(context).load(pic).into(vh.iv_car_pic_id);
        vh.tv_car_name.setText(csName+name+carYear+"款");
        vh.tv_now_price_id.setText(promotePrice+"万");
        vh.tv_vendor_price.setText(vendorPrice+"万");
        vh.tv_vendor_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        vh.tv_reduce_price_id.setText(reduce+"万");
        vh.tv_dealer_type_id.setText(dealerType+" "+dealerName);
        vh.tv_sale_range_id.setText("售"+saleRange);
        return convertView;
    }
    private final class ViewHolder {
        ImageView iv_car_pic_id;
        TextView tv_car_name;
        TextView tv_now_price_id;
        TextView tv_vendor_price;
        TextView tv_reduce_price_id;
        TextView tv_dealer_type_id;
        TextView tv_sale_range_id;
    }
}



