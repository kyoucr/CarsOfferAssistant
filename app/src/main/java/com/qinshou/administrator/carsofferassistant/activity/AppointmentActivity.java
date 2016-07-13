package com.qinshou.administrator.carsofferassistant.activity;

import android.content.Intent;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qinshou.administrator.carsofferassistant.R;
import com.qinshou.administrator.carsofferassistant.bean.Customer;
import com.qinshou.administrator.carsofferassistant.depreciatefiled.activity.SelectCityActivity;
import com.qinshou.administrator.carsofferassistant.utils.DBHelper;

/**
 * Created by 禽兽先生 on 2016.03.06
 */
public class AppointmentActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);
        //1.找到界面上控件的实例
        ImageView appointment_car_iv = (ImageView) findViewById(R.id.appointment_car_iv);
        TextView appointment_serial_name_tv = (TextView) findViewById(R.id.appointment_serial_name_tv);
        TextView appointment_car_name_tv = (TextView) findViewById(R.id.appointment_car_name_tv);

        Intent intent = getIntent();
        String car_name_string = intent.getStringExtra("car_name_string");
        String car_name = intent.getStringExtra("car_name");
        String car_picture = intent.getStringExtra("car_picture");
        //2.将数据设置到控件中。
        Glide.with(this).load(car_picture).into(appointment_car_iv);
        appointment_serial_name_tv.setText(car_name_string);
        appointment_car_name_tv.setText(car_name);

        DBHelper helper = new DBHelper(this, "history.db", null, 1);//tb_history
        SQLiteDatabase db = helper.getWritableDatabase();

        try {
            db.execSQL("insert into tb_history(url,name_string,name) values(?,?,?)",new Object[]{car_picture,car_name_string,car_name});
//            Toast.makeText(this, "恭喜！插入记录成功！", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
//            Toast.makeText(this, "Sorry！插入记录失败！555.。。。", Toast.LENGTH_SHORT)
//                    .show();
        }


    }

    /**
     * 保存客户提交的数据
     *
     * @param view
     */
    public void saveCustomerData(View view) {
        EditText customer_name_et = (EditText) findViewById(R.id.customer_name_et);
        EditText customer_phone_et = (EditText) findViewById(R.id.customer_phone_et);
        RadioGroup rg_gender_id = (RadioGroup) findViewById(R.id.rg_gender_id);
        TextView show_city_tv = (TextView) findViewById(R.id.show_city_tv);

        String name = customer_name_et.getText().toString();
        String phone = customer_phone_et.getText().toString();
        int checkedRadioButtonId = rg_gender_id.getCheckedRadioButtonId();
        String gender = null;
        if (checkedRadioButtonId == R.id.rb_male_id) {
            gender = "男";
        } else {
            gender = "女";
        }
        String city = (String) show_city_tv.getText();
        Customer customer = new Customer(name, phone, gender, city);
        DBHelper helper = new DBHelper(this, "customer.db", null, 1);
        SQLiteDatabase database = helper.getWritableDatabase();
        try {
            database.execSQL("insert into tb_customer(name,phone,gender,city) values(?,?,?,?)",
                    new Object[]{customer.getName(), customer.getPhone(), customer.getGender(), customer.getCity()});
            Toast.makeText(this, "提交成功！", Toast.LENGTH_LONG).show();
            finish();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(this, "提交失败！", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 城市选择
     * @param view
     */
    public void selectCity(View view) {
        Intent intent = new Intent(this, SelectCityActivity.class);
        startActivityForResult(intent, 101);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        TextView textView = (TextView) findViewById(R.id.show_city_tv);
        if (requestCode == 101 && resultCode == 202) {
            String resultCityName = data.getStringExtra("cityName");
            if (resultCityName != null && resultCityName.length() > 0) {
                textView.setText(resultCityName);
            } else {
                textView.setText("获取城市失败，点击重试...");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
