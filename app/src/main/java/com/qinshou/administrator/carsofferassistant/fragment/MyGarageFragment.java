package com.qinshou.administrator.carsofferassistant.fragment;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.qinshou.administrator.carsofferassistant.R;
import com.qinshou.administrator.carsofferassistant.activity.CarDetailActivity;
import com.qinshou.administrator.carsofferassistant.bean.CarGarage;
import com.qinshou.administrator.carsofferassistant.bean.Series;
import com.qinshou.administrator.carsofferassistant.task.IndependenceImageAsyncTask;
import com.qinshou.administrator.carsofferassistant.utils.DBHelper;

import org.w3c.dom.Text;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by 禽兽先生 on 2016.07.05
 */

public class MyGarageFragment extends Fragment {
    private List<CarGarage> dataSource;
    private MyAdapter myAdapter;
    private SQLiteDatabase db;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_garage, container, false);

        ListView lv_car_list_id = (ListView) view.findViewById(R.id.lv_car_list_id);
        TextView tv_emptyp_id = (TextView) view.findViewById(R.id.tv_emptyp_id);
        lv_car_list_id.setEmptyView(tv_emptyp_id);

        //思路：
        //1.数据源
        dataSource = new LinkedList<>();

        DBHelper helper = new DBHelper(getActivity(), "history.db", null, 1);//tb_history
        db = helper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select _id,url,name_string,name from tb_history", new String[]{});
        selectDetailOperate(cursor);

        //2.适配器
        myAdapter = new MyAdapter();
        //3.绑定适配器
        lv_car_list_id.setAdapter(myAdapter);
        registerForContextMenu(lv_car_list_id);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.delete_all_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        deleteRecordFromDB();
        return super.onOptionsItemSelected(item);
    }

    /**
     * 删除表记录
     */
    private void deleteRecordFromDB() {
        dataSource.clear();
        try {
            // ?-->占位符
            db.execSQL("delete from tb_history");
            Toast.makeText(getActivity(), "恭喜！删除记录成功！", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Sorry！删除记录失败！555.。。。", Toast.LENGTH_SHORT)
                    .show();
        }
        myAdapter.notifyDataSetChanged();

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getActivity().getMenuInflater().inflate(R.menu.delete_car_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ContextMenu.ContextMenuInfo menuInfo = item.getMenuInfo();
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
        int position = acmi.position;

        try {
            // ?-->占位符
            db.execSQL("delete from tb_history");
            Toast.makeText(getActivity(), "恭喜！删除记录成功！", Toast.LENGTH_SHORT).show();
        } catch (SQLException e) {
            e.printStackTrace();
            Toast.makeText(getActivity(), "Sorry！删除记录失败！555.。。。", Toast.LENGTH_SHORT)
                    .show();
        }
        dataSource.remove(position);
        myAdapter.notifyDataSetChanged();
        return super.onContextItemSelected(item);
    }

    private void selectDetailOperate(Cursor cursor) {
        // 每循环一次，取出表记录，并将其封装成User对象，存入容器
        while (cursor.moveToNext()) {
            int _id = cursor.getInt(cursor.getColumnIndex("_id"));
            String url = cursor.getString(cursor.getColumnIndex("url"));
            String name_string = cursor.getString(cursor.getColumnIndex("name_string"));
            String name = cursor.getString(cursor.getColumnIndex("name"));

            CarGarage car = new CarGarage(url, name_string, name);
            dataSource.add(car);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        aboutActionBar();
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 关于ActionBar的操作
     */
    private void aboutActionBar() {
        AppCompatActivity activity = ((AppCompatActivity) getActivity());
        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.main_tb);
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setTitle("我的车库");
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
                convertView = View.inflate(getActivity(), R.layout.my_car_garage_item, null);
                vh.iv_self_log_id = (ImageView) convertView.findViewById(R.id.iv_self_log_id);
                vh.tv_self_car_name_id = (TextView) convertView.findViewById(R.id.tv_self_car_name_id);
                vh.tv_self_car_string_id = (TextView) convertView.findViewById(R.id.tv_self_car_string_id);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            CarGarage carGarage = dataSource.get(position);
            Glide.with(getActivity()).load(carGarage.getUrl()).into(vh.iv_self_log_id);
            vh.tv_self_car_name_id.setText(carGarage.getName());
            vh.tv_self_car_string_id.setText(carGarage.getName_string());
            return convertView;
        }
    }

    /**
     * 控件实例复用类
     */
    private final class ViewHolder {
        private ImageView iv_self_log_id;
        private TextView tv_self_car_name_id;
        private TextView tv_self_car_string_id;
    }

}
