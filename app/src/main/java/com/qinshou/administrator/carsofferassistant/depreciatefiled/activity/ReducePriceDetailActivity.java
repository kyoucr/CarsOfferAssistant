package com.qinshou.administrator.carsofferassistant.depreciatefiled.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.qinshou.administrator.carsofferassistant.R;
import com.qinshou.administrator.carsofferassistant.activity.AppointmentActivity;
import com.qinshou.administrator.carsofferassistant.constant.Urls;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.adapter.ReduceCarAdapter;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.DealersBean;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.ListReduceCar;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.bean.ReduceCar;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.inter.ReduceCarDataCallBack;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.network.ReduceCarDetailAsyncTask;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.util.GPSDealerAddress;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.util.ReduceDetailParserUtil;

import org.w3c.dom.Text;

import java.io.InputStream;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.LinkedList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ReducePriceDetailActivity extends AppCompatActivity implements ReduceCarDataCallBack {

    private TextView tv_car_name_id;
    private ImageView iv_car_pic_id;
    private TextView tv_promote_price_id;
    private TextView tv_old_price_id;
    private TextView tv_decreace_price_id_price_id;
    private TextView tv_sale_range_id;
    private TextView tv_day_left_id;
    private ListView lv_all_car_reduce_price_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reduce_price_detail);
        Intent intent = getIntent();
        final DealersBean carMessage = (DealersBean) intent.getSerializableExtra("carMessage");

        //1.找到界面上控件的实例
        tv_car_name_id = (TextView) findViewById(R.id.tv_car_name_id);
        iv_car_pic_id = (ImageView) findViewById(R.id.iv_car_pic_id);
        tv_promote_price_id = (TextView) findViewById(R.id.tv_promote_price_id);
        tv_old_price_id = (TextView) findViewById(R.id.tv_old_price_id);
        tv_decreace_price_id_price_id = (TextView) findViewById(R.id.tv_decreace_price_id_price_id);
        tv_sale_range_id = (TextView) findViewById(R.id.tv_sale_range_id);
        tv_day_left_id = (TextView) findViewById(R.id.tv_day_left_id);
        TextView tv_location_id = (TextView) findViewById(R.id.tv_location_id);

        new GPSDealerAddress(getApplication(), null, null, tv_location_id);//GPS定位功能添加。

        lv_all_car_reduce_price_id = (ListView) findViewById(R.id.lv_all_car_reduce_price_id);
        TextView tv_emptyp_id = (TextView) findViewById(R.id.tv_emptyp_id);
        lv_all_car_reduce_price_id.setEmptyView(tv_emptyp_id);

        String urlStr = Urls.REDUCE_CAR_DETAIL + carMessage.getId();
        new ReduceCarDetailAsyncTask(this).execute(urlStr);
    }

    /**
     * 点击定位
     *
     * @param view
     */
    public void jumpAction(View view) {
        switch (view.getId()) {
            case R.id.tv_location_id:
                startActivity(new Intent(this, GPSDealerAddressActivity.class));
                break;
            case R.id.btn_ask_lowest_price_id:
                startActivity(new Intent(this, AppointmentActivity.class));
                break;
            default:break;
        }

    }


    @Override
    public void dataCallBack(ListReduceCar reduceCar) {
        tv_car_name_id.setText(reduceCar.getCsName() + " " + reduceCar.getCarName() + " " + reduceCar.getCarYear() + "款");
        Glide.with(this).load(reduceCar.getPic()).into(iv_car_pic_id);
        tv_promote_price_id.setText(reduceCar.getOriginal_price());
        tv_old_price_id.setText(reduceCar.getNew_price());
        tv_decreace_price_id_price_id.setText(reduceCar.getReduce());
        tv_sale_range_id.setText(reduceCar.getRange());
        tv_day_left_id.setText("剩" + reduceCar.getLast_day() + "天");

        //2.数据源

        //3.适配器
        List<ReduceCar> listCars = reduceCar.getListCars();
        ReduceCarAdapter adapter = new ReduceCarAdapter(listCars, this);
        //4.绑定适配器
        lv_all_car_reduce_price_id.setAdapter(adapter);
    }
}
