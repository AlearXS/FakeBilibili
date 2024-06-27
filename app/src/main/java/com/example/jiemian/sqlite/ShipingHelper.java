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
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub

    }
}
