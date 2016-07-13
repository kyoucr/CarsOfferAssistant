package com.qinshou.administrator.carsofferassistant.utils;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zyj on 2016/7/12.
 */

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tb_customer(_id  integer primary key autoincrement,name varchar(50) not null, phone varchar(150) not null,gender varchar(4),city varchar(40))");// 书写建表语句
        db.execSQL("create table tb_history(_id  integer primary key autoincrement,url varchar(150) not null, name_string varchar(150) not null,name varchar(50))");// 书写建表语句
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
