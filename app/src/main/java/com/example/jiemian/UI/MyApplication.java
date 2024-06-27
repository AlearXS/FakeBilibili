package com.example.jiemian.UI;

import android.app.Application;

import com.example.jiemian.bean.User;


public class MyApplication extends Application {

    private static MyApplication instance;

    public static String TAG = "-------";

    public User user = null;
    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;


    }


    public static MyApplication getInstance() {
        return instance;
    }
}
