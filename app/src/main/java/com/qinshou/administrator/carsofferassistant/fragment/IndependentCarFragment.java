package com.qinshou.administrator.carsofferassistant.fragment;

import android.app.ActionBar;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.qinshou.administrator.carsofferassistant.R;
import com.qinshou.administrator.carsofferassistant.activity.CarDetailActivity;
import com.qinshou.administrator.carsofferassistant.bean.Series;
import com.qinshou.administrator.carsofferassistant.constant.Urls;
import com.qinshou.administrator.carsofferassistant.inter.IndependenceDataCallback;
import com.qinshou.administrator.carsofferassistant.task.IndependenceAsyncTask;
import com.qinshou.administrator.carsofferassistant.task.IndependenceImageAsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.MessageFormat;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class IndependentCarFragment extends android.support.v4.app.Fragment implements IndependenceDataCallback {
    private String[] prices;//价格区间
    private String[] compartments;//车型
    private String[] countries;//成产地
    private Spinner sp_brand_place_id;
    private Spinner sp_car_style_id;
    private Spinner sp_price_range_id;
    private ListView lv_car_list_id;
    private ProgressDialog dialog;
    private int minPrice = 0;
    private int maxPrice = 5;
    private int compartment = 1;
    private int country = 1;
    private int pageIndex = 0;// 页码计数器（初始值是第一页）

    private boolean isOver;//当前所在页数据是否加载完毕。
    private List<Map<String, Object>> dataSource;
    private MyAdapter myAdapter;
    private List<Series> detailUserSeriesList;
    //    File searchCondition = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Resources resources = getActivity().getResources();
        prices = resources.getStringArray(R.array.c_price);
        compartments = resources.getStringArray(R.array.c_compartment);
        countries = resources.getStringArray(R.array.c_country);
        detailUserSeriesList = new LinkedList<>();



        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.main_tb);
        activity.setSupportActionBar(toolbar);
        android.support.v7.app.ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("自主选车");


        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_self_help, container, false);
        sp_price_range_id = (Spinner) view.findViewById(R.id.sp_price_range_id);
        sp_car_style_id = (Spinner) view.findViewById(R.id.sp_car_style_id);
        sp_brand_place_id = (Spinner) view.findViewById(R.id.sp_brand_place_id);

        lv_car_list_id = (ListView) view.findViewById(R.id.lv_car_list_id);
        TextView tv_emptyp_id = (TextView) view.findViewById(R.id.tv_emptyp_id);
        lv_car_list_id.setEmptyView(tv_emptyp_id);
        dialog = new ProgressDialog(getActivity());//进度条
        dialog.setMessage("数据加载中...");
        //思路：
        //1.数据源
        dataSource = new LinkedList<>();
        //2.适配器
        myAdapter = new MyAdapter();
        //3.绑定适配器
        lv_car_list_id.setAdapter(myAdapter);
        //4.设置监听器
        lv_car_list_id.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (isOver && scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    //minPrice={0}&maxPrice={1}&compartment={2}&country=(3)&pageIndex={4}
                    ++pageIndex;
                    RecordAndDownload();
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                isOver = firstVisibleItem + visibleItemCount == totalItemCount;
            }
        });
        lv_car_list_id.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity().getApplicationContext(), CarDetailActivity.class);
                intent.putExtra("serialId",detailUserSeriesList.get(position).getId());
                intent.putExtra("URL_Flg",true);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

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
    public void onDestroy() {
        File searchCondition = new File(getActivity().getFilesDir(), "searchCondition.txt");
        if (searchCondition.exists()) {
            searchCondition.delete();
        }
        super.onDestroy();
    }

    /**
     * 下拉列表监听器
     */
    private final class MyOnItemSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (parent.getId()) {
                case R.id.sp_price_range_id:
                    choosePriceRange(position);
                    break;
                case R.id.sp_car_style_id:
                    compartment = position + 1;
                    break;
                case R.id.sp_brand_place_id:
                    country = position + 1;
                    break;
                default:
                    break;
            }
            RecordAndDownload();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    /**
     * 每次点击下拉列表，读取配置文件。若是同一个选项则不需重新下载数据。若是不同的选项，则需重新下载数据。
     */
    private void RecordAndDownload() {
       File searchCondition = new File(getActivity().getFilesDir(), "searchCondition.txt");
        if (!searchCondition.exists()) {
            try {
                searchCondition.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedReader reader = null;
        BufferedWriter writer = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getActivity().openFileInput("searchCondition.txt")));
            String content = null;
            StringBuffer buffer = new StringBuffer();
            if ((content = reader.readLine()) != null) { //1.配置文件存在数据，数据发生改变，从网络重新下载数据。
                String[] splits = content.split("##");
                if (splits.length == 4 && (Integer.parseInt(splits[0]) != minPrice || Integer.parseInt(splits[1]) != maxPrice ||
                        Integer.parseInt(splits[2]) != compartment ||
                        Integer.parseInt(splits[3]) != country)) {
                    buffer.append(minPrice).append("##").append(maxPrice)
                            .append("##").append(compartment).append("##").append(country);//1##2#3##4
                    writer = new BufferedWriter(new OutputStreamWriter(getActivity().openFileOutput("searchCondition.txt",Context.MODE_PRIVATE)));   //new BufferedWriter(new OutputStreamWriter(new FileOutputStream(searchCondition)));.
                    writer.write(buffer.toString());
                    writer.flush();
                    pageIndex = 0;
                    dataSource.clear();
                }
            } else {//2.配置文件不存在，直接从网络下载数据。
                writer = new BufferedWriter(new OutputStreamWriter(getActivity().openFileOutput("searchCondition.txt",Context.MODE_PRIVATE)));
                buffer.append(minPrice).append("##").append(maxPrice)
                        .append("##").append(compartment).append("##").append(country);
                writer.write(buffer.toString());
                writer.flush();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            new IndependenceAsyncTask(IndependentCarFragment.this, dialog)
                    .execute(MessageFormat.format(Urls.CAR_AUTO_SELECT, String.valueOf(minPrice), String.valueOf(maxPrice),
                            String.valueOf(compartment), String.valueOf(country), String.valueOf(pageIndex)));
        }
    }

    @Override
    public void callBack(List<Series> seriesList) {
        //关于List的操作
        //1.找到界面上控件的实例
        //2.数据源，并把数据源设置到控件中。
        //3.适配器
        if (seriesList != null && seriesList.size() > 0){
            detailUserSeriesList = seriesList;
            for (int i = 0; i < seriesList.size(); i++) {
                Map<String, Object> map = new LinkedHashMap();
                map.put("iv_self_log_id", seriesList.get(i).getPic());
                map.put("tv_self_car_name_id", seriesList.get(i).getName());
                map.put("tv_self_car_price_range_id", seriesList.get(i).getPrice_range());
                map.put("iv_self_right_icon_id", R.mipmap.icon_arrow_right);
                dataSource.add(map);
            }
            myAdapter.notifyDataSetChanged();
            //4.绑定适配器
            //5.给ListView添加监听器
        }
    }

    private final class MyAdapter extends BaseAdapter {

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
                convertView = View.inflate(getActivity(), R.layout.inpedence_item, null);
                vh.iv_self_log_id = (ImageView) convertView.findViewById(R.id.iv_self_log_id);
                vh.tv_self_car_name_id = (TextView) convertView.findViewById(R.id.tv_self_car_name_id);
                vh.tv_self_car_price_range_id = (TextView) convertView.findViewById(R.id.tv_self_car_price_range_id);
                vh.iv_self_right_icon_id = (ImageView) convertView.findViewById(R.id.iv_self_right_icon_id);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            Map<String, Object> perItemDs = dataSource.get(position);
            vh.iv_self_log_id.setImageResource(R.drawable.default_car);
            vh.tv_self_car_name_id.setText(perItemDs.get("tv_self_car_name_id").toString());
            vh.tv_self_car_price_range_id.setText(perItemDs.get("tv_self_car_price_range_id").toString());
            vh.iv_self_right_icon_id.setImageResource(R.mipmap.icon_arrow_right);

            new IndependenceImageAsyncTask(vh.iv_self_log_id).execute(dataSource.get(position).get("iv_self_log_id").toString());
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


    /**
     * 选择价格区间
     *
     * @param pos
     */
    private void choosePriceRange(int pos) {
        switch (pos) {
            case 0:
                minPrice = 0;
                maxPrice = 5;
                break;
            case 1:
                minPrice = 5;
                maxPrice = 10;
                break;
            case 2:
                minPrice = 10;
                maxPrice = 18;
                break;
            case 3:
                minPrice = 18;
                maxPrice = 25;
                break;
            case 4:
                minPrice = 25;
                maxPrice = 40;
                break;
            case 5:
                minPrice = 40;
                maxPrice = 60;
                break;
            case 6:
                minPrice = 60;
                maxPrice = 100;
                break;
            case 7:
                minPrice = 100;
                maxPrice = -1;
                break;
            default:
                break;
        }
    }

}
