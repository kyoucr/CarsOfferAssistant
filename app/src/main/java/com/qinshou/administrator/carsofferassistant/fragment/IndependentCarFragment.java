package com.qinshou.administrator.carsofferassistant.fragment;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.qinshou.administrator.carsofferassistant.R;
import com.qinshou.administrator.carsofferassistant.bean.Series;
import com.qinshou.administrator.carsofferassistant.constant.Urls;
import com.qinshou.administrator.carsofferassistant.inter.IndependenceDataCallback;
import com.qinshou.administrator.carsofferassistant.task.IndependenceAsyncTask;
import com.qinshou.administrator.carsofferassistant.task.IndependenceImageAsyncTask;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class IndependentCarFragment extends android.app.Fragment implements IndependenceDataCallback {
    private String[] prices;//价格区间
    private String[] compartments;//车型
    private String[] countries;//成产地
    private Spinner sp_brand_place_id;
    private Spinner sp_car_style_id;
    private Spinner sp_price_range_id;
    private ListView lv_car_list_id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Resources resources = getActivity().getResources();
        prices = resources.getStringArray(R.array.c_price);
        compartments = resources.getStringArray(R.array.c_compartment);
        countries = resources.getStringArray(R.array.c_country);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_self_help, container, false);
        sp_price_range_id = (Spinner) view.findViewById(R.id.sp_price_range_id);
        sp_car_style_id = (Spinner) view.findViewById(R.id.sp_car_style_id);
        sp_brand_place_id = (Spinner) view.findViewById(R.id.sp_brand_place_id);

        lv_car_list_id = (ListView) view.findViewById(R.id.lv_car_list_id);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        //思路：
        //1.数据源
        //2.适配器
        ArrayAdapter<String> adapterPrice = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, prices);
        ArrayAdapter<String> adapterCompartments = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, compartments);
        ArrayAdapter<String> adapterCountries = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, countries);
        //3.绑定适配器
        sp_price_range_id.setAdapter(adapterPrice);
        sp_car_style_id.setAdapter(adapterCompartments);
        sp_brand_place_id.setAdapter(adapterCountries);

        //4.添加监听器
        MyOnItemSelectedListener listener = new MyOnItemSelectedListener();
        sp_price_range_id.setOnItemSelectedListener(listener);
        sp_car_style_id.setOnItemSelectedListener(listener);
        sp_brand_place_id.setOnItemSelectedListener(listener);

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void callBack(List<Series> seriesList) {
        //关于List的操作
        //1.找到界面上控件的实例
        //2.数据源，并把数据源设置到控件中。
        //3.适配器
//        Context context, List<? extends Map<String, ?>> data,
//        @LayoutRes int resource, String[] from, @IdRes int[] to
        List<Map<String, Object>> dataSource = new LinkedList<>();
        for (int i = 0; i < seriesList.size(); i++) {
            Map<String,Object> map = new LinkedHashMap();
            map.put("iv_self_log_id", R.drawable.default_car);
            map.put("tv_self_car_name_id", seriesList.get(i).getName());
            map.put("tv_self_car_price_range_id", seriesList.get(i).getPrice_range());
            map.put("iv_self_right_icon_id", R.mipmap.icon_arrow_right);

            dataSource.add(map);
        }
        MyAdapter myAdapter = new MyAdapter(dataSource,getActivity(),seriesList);
        //4.绑定适配器
        lv_car_list_id.setAdapter(myAdapter);
        //5.给ListView添加监听器
    }

    private final class MyAdapter extends BaseAdapter {
        private List<Map<String, Object>> dataSource;
        private Context context;
        private List<Series> seriesList;

        public MyAdapter() {
        }

        public MyAdapter(List<Map<String, Object>> dataSource, Context context, List<Series> seriesList) {
            this.dataSource = dataSource;
            this.context = context;
            this.seriesList = seriesList;
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
            if (convertView == null){
                vh = new ViewHolder();
                convertView = View.inflate(context, R.layout.inpedence_item, null);
                vh.iv_self_log_id = (ImageView) convertView.findViewById(R.id.iv_self_log_id);
                vh.tv_self_car_name_id = (TextView) convertView.findViewById(R.id.tv_self_car_name_id);
                vh.tv_self_car_price_range_id = (TextView) convertView.findViewById(R.id.tv_self_car_price_range_id);
                vh.iv_self_right_icon_id = (ImageView) convertView.findViewById(R.id.iv_self_right_icon_id);
                convertView.setTag(vh);
            }else {
                vh = (ViewHolder) convertView.getTag();
            }
            Map<String, Object> perItemDs = dataSource.get(position);
            vh.iv_self_log_id.setImageResource(R.mipmap.ic_launcher);
            vh.tv_self_car_name_id.setText(perItemDs.get("tv_self_car_name_id").toString());
            vh.tv_self_car_price_range_id.setText(perItemDs.get("tv_self_car_price_range_id").toString());
            vh.iv_self_right_icon_id.setImageResource(R.mipmap.icon_arrow_right);

            new IndependenceImageAsyncTask(vh.iv_self_log_id).execute(seriesList.get(position).getPic());
            return convertView;
        }
    }
    /**
     * 控件实例复用类
     */
    private final class ViewHolder {
        private ImageView iv_self_log_id;
        private TextView tv_self_car_name_id;
        private TextView tv_self_car_price_range_id;
        private ImageView iv_self_right_icon_id;
    }



    @Override
    public void imageCallBack(Map<String, Object> map, Bitmap bitmap) {
//        map.put("iv_self_log_id",bitmap);
    }


    /**
     * 下拉列表监听器
     */
    private final class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            new IndependenceAsyncTask(IndependentCarFragment.this).execute(Urls.CAR_AUTO_SELECT);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }
}
