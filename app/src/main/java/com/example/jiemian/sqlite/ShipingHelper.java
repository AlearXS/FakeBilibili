package com.example.jiemian.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ShipingHelper  extends SQLiteOpenHelper {

    //�������
    public static final String CREATE_USER = "create table Shiping ("
            + "id integer primary key autoincrement, "
            + "title text, "
            + "path text, "
            + "type text, "
    + "tupian text, "
            + "level text)";

    public ShipingHelper(Context context, String name, SQLiteDatabase.CursorFactory factory,
                      int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub

        db.execSQL(CREATE_USER);//�����û���
        initData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }
    private void initData(SQLiteDatabase db) {
        // Insert initial data here
        db.execSQL("insert into Shiping (title, path, type, tupian, level) values (?, ?, ?, ?, ?)",
                new String[]{"阿尔卑斯山", "/storage/emulated/0/Movies/sh1.mp4", "1", "/storage/emulated/0/Pictures/sh1.jpg", "生活"});
        db.execSQL("insert into Shiping (title, path, type, tupian, level) values (?, ?, ?, ?, ?)",
                new String[]{"自然与歌声", "/storage/emulated/0/Movies/sh2.mp4", "1", "/storage/emulated/0/Pictures/sh2.png", "生活"});
        db.execSQL("insert into Shiping (title, path, type, tupian, level) values (?, ?, ?, ?, ?)",
                new String[]{"动物世界", "/storage/emulated/0/Movies/jl1.mp4", "2", "/storage/emulated/0/Pictures/jl1.png", "记录"});
        db.execSQL("insert into Shiping (title, path, type, tupian, level) values (?, ?, ?, ?, ?)",
                new String[]{"海滨落日", "/storage/emulated/0/Movies/jl2.mp4", "2", "/storage/emulated/0/Pictures/jl2.png", "记录"});
        db.execSQL("insert into Shiping (title, path, type, tupian, level) values (?, ?, ?, ?, ?)",
                new String[]{"肖申克的救赎", "/storage/emulated/0/Movies/ys1.mp4", "1", "/storage/emulated/0/Pictures/ys1.png", "影视"});
        db.execSQL("insert into Shiping (title, path, type, tupian, level) values (?, ?, ?, ?, ?)",
                new String[]{"你的名字", "/storage/emulated/0/Movies/ys2.mp4", "1", "/storage/emulated/0/Pictures/ys2.png", "影视"});
    }
}

