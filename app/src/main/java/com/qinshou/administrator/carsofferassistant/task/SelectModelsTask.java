package com.qinshou.administrator.carsofferassistant.task;

import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.ExpandableListView;

import com.qinshou.administrator.carsofferassistant.adapter.SelectModelsAdapter;
import com.qinshou.administrator.carsofferassistant.bean.Car;
import com.qinshou.administrator.carsofferassistant.utils.DownloadXml;
import com.qinshou.administrator.carsofferassistant.utils.ParseXml;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by 禽兽先生 on 2016.07.06
 */

public class SelectModelsTask extends AsyncTask {
    private Context context;
    private ExpandableListView select_models_elv;

    public SelectModelsTask(Context context, ExpandableListView select_models_elv) {
        this.context = context;
        this.select_models_elv = select_models_elv;
    }

    @Override
    protected Map<String, List<Car>> doInBackground(Object[] params) {
        InputStream selectModelsXml = DownloadXml.downloadSelectModels((String) params[0]);
        return ParseXml.parseSelectModels(selectModelsXml);
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        Map<String, List<Car>> map = (Map<String, List<Car>>) o;
        List<String> brandAcronyms = new ArrayList<String>(map.keySet());
        SelectModelsAdapter selectModelsAdapter = new SelectModelsAdapter(context, map, brandAcronyms);
        select_models_elv.setAdapter(selectModelsAdapter);
        select_models_elv.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                return true;
            }
        });
        for (int i = 0; i < selectModelsAdapter.getGroupCount(); i++) {
            select_models_elv.expandGroup(i);
        }
    }
}
