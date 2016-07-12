package com.qinshou.administrator.carsofferassistant.task;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ExpandableListView;

import com.qinshou.administrator.carsofferassistant.adapter.CarDetailAdapter;
import com.qinshou.administrator.carsofferassistant.bean.Car;
import com.qinshou.administrator.carsofferassistant.bean.CarIntro;
import com.qinshou.administrator.carsofferassistant.bean.ModelsSeries;
import com.qinshou.administrator.carsofferassistant.utils.DownloadXml;
import com.qinshou.administrator.carsofferassistant.utils.ParseXml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 禽兽先生 on 2016.07.06
 */

public class CarDetailTask extends AsyncTask {
    private Context context;
    private ExpandableListView car_detail_elv;
    private CallBack callBack;
    private CarIntro carIntro;

    public CarDetailTask(Context context, ExpandableListView car_detail_elv, CallBack callBack,CarIntro carIntro) {
        this.context = context;
        this.car_detail_elv = car_detail_elv;
        this.callBack = callBack;
        this.carIntro = carIntro;
    }

    @Override
    protected Map<String, List<Car>> doInBackground(Object... params) {
        InputStream carDetailXml = DownloadXml.downloadInputStream((String) params[0]);
        return ParseXml.parseCarDetail(carDetailXml);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        Map<String, List<Car>> map = (Map<String, List<Car>>) o;
        List<String> displacements = new ArrayList<String>(map.keySet());
        CarDetailAdapter carDetailAdapter = new CarDetailAdapter(context, map, displacements,carIntro);
        car_detail_elv.setAdapter(carDetailAdapter);
        car_detail_elv.setGroupIndicator(null);
        car_detail_elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        for (int i = 0; i < carDetailAdapter.getGroupCount(); i++) {
            car_detail_elv.expandGroup(i);
        }
    }

    public interface CallBack {
        public void getBrandDetail(Map<String, List<ModelsSeries>> map, List<String> list);
    }
}
