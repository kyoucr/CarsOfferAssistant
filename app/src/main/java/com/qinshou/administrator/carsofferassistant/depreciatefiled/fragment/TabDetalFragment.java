package com.qinshou.administrator.carsofferassistant.depreciatefiled.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.qinshou.administrator.carsofferassistant.R;

/**
 * Created by yan on 2016/7/6.
 * 用于展示每个tab下ViewPager的fragment。
 * 根据传过来的值，来判断是哪个fragment
 *
 */

public class TabDetalFragment extends Fragment {

    private String tabName;

    private TextView mText;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        tabName = arguments.getString("tabName");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabdetal,container,false);
       // View view = inflater.inflate(R.layout.fragment_tabdetal,null);
        mText = (TextView) view.findViewById(R.id.tv_tab_id);

        mText.setText(tabName);

        return view;
    }
}
