package com.qinshou.administrator.carsofferassistant.depreciatefiled.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.qinshou.administrator.carsofferassistant.R;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.activity.ReducePriceDetailActivity;
import com.qinshou.administrator.carsofferassistant.constant.Urls;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.adapter.ReduceZoneAdapter;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.DealerListBean;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.DealersBean;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.inter.TestInterface;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.network.ReducePriceZoneAsyncTask;

import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

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
    private boolean isOver;//当前所在页数据是否加载完毕。
    private int pageIndex = 0;// 页码计数器（初始值是第一页）

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
                DealersBean dealersBean = dataSource.get(position);
                Intent intent = new Intent(getActivity(), ReducePriceDetailActivity.class);
                intent.putExtra("carMessage",dealersBean);
                startActivity(intent);
            }
        });
        lv_reduce_price_detail_id.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (isOver && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    new ReducePriceZoneAsyncTask(TabDetalFragment.this).execute(MessageFormat.format(Urls.REDUCE_PRCE_ZONE, java.net.URLEncoder.encode(cityName),
                            carSeriesId, String.valueOf(selectType), String.valueOf(++pageIndex)));
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isOver = firstVisibleItem + visibleItemCount == totalItemCount;
            }
        });
        new ReducePriceZoneAsyncTask(this).execute(MessageFormat.format(Urls.REDUCE_PRCE_ZONE, java.net.URLEncoder.encode(cityName),
                carSeriesId, String.valueOf(selectType), String.valueOf(pageIndex)));
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
