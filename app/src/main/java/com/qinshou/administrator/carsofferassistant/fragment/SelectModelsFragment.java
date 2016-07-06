package com.qinshou.administrator.carsofferassistant.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.qinshou.administrator.carsofferassistant.R;
import com.qinshou.administrator.carsofferassistant.constant.Urls;
import com.qinshou.administrator.carsofferassistant.task.SelectModelsTask;

/**
 * Created by 禽兽先生 on 2016.07.05
 */

public class SelectModelsFragment extends android.app.Fragment {
    private String url = Urls.SELECT_MODELS;
    private ExpandableListView select_models_elv;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.select_models_fragment, null);
        select_models_elv = (ExpandableListView) view.findViewById(R.id.select_models_elv);
        new SelectModelsTask(getActivity(), select_models_elv).execute(url);
        return view;
    }
}
