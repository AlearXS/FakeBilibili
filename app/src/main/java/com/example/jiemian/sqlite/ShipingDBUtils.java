package com.example.jiemian.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.jiemian.bean.Shiping;

import java.util.ArrayList;
import java.util.List;

public class ShipingDBUtils {
    public static final String DB_NAME = "shiping_dbname.db";

    public static final int VERSION = 1;

    private static ShipingDBUtils sqliteDB;

    private SQLiteDatabase db;

    private ShipingDBUtils(Context context) {
        ShipingHelper dbHelper = new ShipingHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * ��ȡSqliteDBʵ��
     * @param context
     */
    public synchronized static ShipingDBUtils getInstance(Context context) {
        if (sqliteDB == null) {
            sqliteDB = new ShipingDBUtils(context);
        }
        return sqliteDB;
    }


    public void delete(Context context,String username) {
        ShipingHelper dbHelper = new ShipingHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getReadableDatabase();
        db.delete("Shiping", "id=?", new String[] { username });
    }
    public void change(Context context, Shiping user) {
        ShipingHelper dbHelper = new ShipingHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("id", user.getId());
        values.put("title", user.getTitle());
        values.put("path", user.getPath());
        values.put("type", user.getType());

        values.put("tupian", user.getTupian());
        values.put("level", user.getLevel());
        db.update("Shiping", values, "id=?", new String[]{user.getId()+""});
    }

    public void insert(Shiping shiping){
        try {
            db.execSQL("insert into Shiping(title,path,type,tupian,level) values(?,?,?,?,?) ", new String[]{
                    shiping.getTitle(),
                    shiping.getPath(),
                    shiping.getType(),
                    shiping.getTupian(),
                    shiping.getLevel()
            });
        } catch (Exception e) {
            Log.d("1", e.getMessage().toString());
        }
    }
    public List<Shiping> FindAll() {
        List<Shiping> list = new ArrayList<>();
        Cursor cursor = db
                .query("Shiping", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Shiping user = new Shiping();
                user.setId(cursor.getInt(cursor.getColumnIndex("id")));
                user.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                user.setPath(cursor.getString(cursor.getColumnIndex("path")));
                user.setLevel(cursor.getString(cursor.getColumnIndex("level")));
                user.setTupian(cursor.getString(cursor.getColumnIndex("tupian")));

                user.setType(cursor.getString(cursor.getColumnIndex("type")));
                list.add(user);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public List<Shiping> FindAllByLevel(String level) {
        List<Shiping> list = new ArrayList<>();
        Cursor cursor = db
                .query("Shiping", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(cursor.getColumnIndex("title")).indexOf(level) != -1||
                        cursor.getString(cursor.getColumnIndex("level")).indexOf(level) != -1){
                    Shiping user = new Shiping();
                    user.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    user.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                    user.setPath(cursor.getString(cursor.getColumnIndex("path")));
                    user.setLevel(cursor.getString(cursor.getColumnIndex("level")));
                    user.setTupian(cursor.getString(cursor.getColumnIndex("tupian")));
                    user.setType(cursor.getString(cursor.getColumnIndex("type")));
                    list.add(user);
                }

            } while (cursor.moveToNext());
        }
        return list;
    }
    public List<Shiping> FindAllByType(String type) {
        List<Shiping> list = new ArrayList<>();
        Cursor cursor = db
                .query("Shiping", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                if (type.equals(cursor.getString(cursor.getColumnIndex("type")))){
                    Shiping user = new Shiping();
                    user.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    user.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                    user.setPath(cursor.getString(cursor.getColumnIndex("path")));
                    user.setLevel(cursor.getString(cursor.getColumnIndex("level")));
                    user.setTupian(cursor.getString(cursor.getColumnIndex("tupian")));
                    user.setType(cursor.getString(cursor.getColumnIndex("type")));
                    list.add(user);
                }
            } while (cursor.moveToNext());
        }
        return list;
    }
    public List<Shiping> FindAllByName(String name) {
        List<Shiping> list = new ArrayList<>();
        Cursor cursor = db
                .query("Shiping", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                if (cursor.getString(cursor.getColumnIndex("title")).indexOf(name) != -1
                || cursor.getString(cursor.getColumnIndex("type")).indexOf(name)!= -1
                || cursor.getString(cursor.getColumnIndex("level")).indexOf(name)!= -1
                ){
                    Shiping user = new Shiping();
                    user.setId(cursor.getInt(cursor.getColumnIndex("id")));
                    user.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                    user.setPath(cursor.getString(cursor.getColumnIndex("path")));
                    user.setTupian(cursor.getString(cursor.getColumnIndex("tupian")));
                    user.setLevel(cursor.getString(cursor.getColumnIndex("level")));
                    user.setType(cursor.getString(cursor.getColumnIndex("type")));
                    list.add(user);
                }

            } while (cursor.moveToNext());
        }
        return list;
    }
}
