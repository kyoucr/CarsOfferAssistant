package com.qinshou.administrator.carsofferassistant.depreciatefiled.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qinshou.administrator.carsofferassistant.R;
import com.qinshou.administrator.carsofferassistant.constant.Urls;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.adapter.ReduceZoneAdapter;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.DealerListBean;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.DealersBean;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.inter.TestInterface;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.network.ReducePriceZoneAsyncTask;

import org.w3c.dom.Text;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by yan on 2016/7/6.
 * 用于展示每个tab下ViewPager的fragment。
 * 根据传过来的值，来判断是哪个fragment
 */

public class TabDetalFragment extends Fragment implements TestInterface {

    private ListView lv_reduce_price_detail_id;
    private String cityName;
    private String carSeriesId;
    private int selectType;
    private List<DealersBean> dataSource;
    private ReduceZoneAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        cityName = arguments.getString("cityName");
        carSeriesId = arguments.getString("carSeriesId");
        selectType = arguments.getInt("selectType");

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tabdetal, container, false);
        //1.找到界面上控件的实例
        lv_reduce_price_detail_id = (ListView) view.findViewById(R.id.lv_reduce_price_detail_id);
        TextView tv_emptyp_id = (TextView) view.findViewById(R.id.tv_emptyp_id);
        lv_reduce_price_detail_id.setEmptyView(tv_emptyp_id);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //2.数据源
        dataSource = new LinkedList<>();

        //3.适配器
        adapter = new ReduceZoneAdapter(dataSource, getActivity());

        //4.绑定适配器
        lv_reduce_price_detail_id.setAdapter(adapter);

        //5.添加监听器
        lv_reduce_price_detail_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
//        String format = Urls.REDUCE_PRCE_ZONE_FIRST + cityName + MessageFormat.format(Urls.REDUCE_PRCE_ZONE_SECOND,
//                String.valueOf(carSeriesId), String.valueOf(selectType), String.valueOf(0));
//        String format = String.format(Urls.REDUCE_PRCE_ZONE,cityName,carSeriesId,selectType,0);
//        format = "http://app.cheyooh.com/i.ashx?m=car_reduce_price&uid=dd122ac1318643b993baa7f52a61c245&location_cityid=3&ver=1.1.3&channel=P008%E8%B1%8C%E8%B1%86%E8%8D%9Av1.1.3&key=b5c2d857f489d912f438d8e7bc5ec75b&tagversion=va&appsku=andr_carprice&checkKey=baba4325da61583799415e34422ed5e4&pageEnter=3&cityName=%E5%8C%97%E4%BA%AC&carSeriesId=0&selectType=2&pageIndex=0";
//        format = Urls.REDUCE_PRCE_ZONE_FIRST + java.net.URLEncoder.encode("北京")
//                + Urls.REDUCE_PRCE_ZONE_SECOND + carSeriesId + Urls.REDUCE_PRCE_ZONE_THIRD + selectType + Urls.REDUCE_PRCE_ZONE_FOUR + 0;
//        Log.i("URL:~~~~~~~~~~~~~=", format);
//        REDUCE_PRCE_ZONE
        new ReducePriceZoneAsyncTask(this).execute(MessageFormat.format(Urls.REDUCE_PRCE_ZONE, java.net.URLEncoder.encode(cityName),
                carSeriesId, String.valueOf(selectType), String.valueOf(0)));
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 按钮点击事件触发
     *
     * @param view
     */
    public void reduceZoneAction(View view) {
        Toast.makeText(getActivity(), "点击Button事件！", Toast.LENGTH_LONG).show();
    }

    @Override
    public void textCallBack(DealerListBean dealerListBean) {
        int total = dealerListBean.getTotal();
        int currentPage = dealerListBean.getCurrentPage();
        int totalPage = dealerListBean.getTotalPage();

        List<DealersBean> dealers = dealerListBean.getDealers();
        for (int i = 0; i < dealers.size(); i++) {
            dataSource.add(dealers.get(i));
        }
        adapter.notifyDataSetChanged();
    }
}
