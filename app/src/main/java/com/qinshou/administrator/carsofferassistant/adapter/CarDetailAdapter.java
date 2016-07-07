package com.qinshou.administrator.carsofferassistant.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.qinshou.administrator.carsofferassistant.R;
import com.qinshou.administrator.carsofferassistant.activity.AppointmentActivity;
import com.qinshou.administrator.carsofferassistant.bean.Car;

import java.util.List;
import java.util.Map;

/**
 * Created by 禽兽先生 on 2016.07.06
 */
public class CarDetailAdapter extends BaseExpandableListAdapter implements Button.OnClickListener {
    private Context context;
    private Map<String, List<Car>> map;
    private List<String> displacements;

    public CarDetailAdapter(Context context, Map<String, List<Car>> map, List<String> displacements) {
        this.context = context;
        this.map = map;
        this.displacements = displacements;
    }

    @Override
    public int getGroupCount() {
        return displacements.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return map.get(displacements.get(groupPosition)).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return displacements.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return map.get(displacements.get(groupPosition)).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.displacements_layout, null);
        TextView displacements_tv = (TextView) view.findViewById(R.id.displacements_tv);
        displacements_tv.setText("\t" + displacements.get(groupPosition));
        return view;

    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.car_detail_layout, null);
        TextView name_tv = (TextView) view.findViewById(R.id.name_tv);
        TextView price_tv = (TextView) view.findViewById(R.id.price_tv);
        TextView gearBox_tv = (TextView) view.findViewById(R.id.gearBox_tv);
        TextView power_tv = (TextView) view.findViewById(R.id.power_tv);
        TextView carReferPrice_tv = (TextView) view.findViewById(R.id.carReferPrice_tv);
        Button free_test_drive_bt = (Button) view.findViewById(R.id.free_test_drive_bt);
        Button group_to_buy_bt = (Button) view.findViewById(R.id.group_to_buy_bt);
        Button consult_floor_price_bt = (Button) view.findViewById(R.id.consult_floor_price_bt);
        name_tv.setText(map.get(displacements.get(groupPosition)).get(childPosition).getName());
        price_tv.setText(map.get(displacements.get(groupPosition)).get(childPosition).getPrice() + "万起");
        gearBox_tv.setText(map.get(displacements.get(groupPosition)).get(childPosition).getGearBox());
        power_tv.setText(map.get(displacements.get(groupPosition)).get(childPosition).getPower());
        carReferPrice_tv.setText("指导价\t" + map.get(displacements.get(groupPosition)).get(childPosition).getCarReferPrice() + "万");
        free_test_drive_bt.setOnClickListener(this);
        group_to_buy_bt.setOnClickListener(this);
        consult_floor_price_bt.setOnClickListener(this);
        return view;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {
        if (observer != null) {
            super.unregisterDataSetObserver(observer);
        }
    }

    @Override
    public void onClick(View v) {
        Intent appointmentIntent = new Intent(context, AppointmentActivity.class);
        context.startActivity(appointmentIntent);
    }
}
