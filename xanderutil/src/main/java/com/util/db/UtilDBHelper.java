package com.util.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UtilDBHelper extends SQLiteOpenHelper {
    public UtilDBHelper(Context context,String tableName) {
        super(context, tableName, null, 1);
    }
    /**
     * Called when the database is created for the first time
     * 当 数据第一次创建的时候调用
     * 该方法适合做 表结构的初始化
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table info(_id integer primary key autoincrement," +
                "name varchar(20)," +
                "phone varchar(20))");
    }
    /**
     * 当数据库需要升级的时候调用
     * 该方法适合做 表结构修改
     *
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("onUpgrade");
    }
}

