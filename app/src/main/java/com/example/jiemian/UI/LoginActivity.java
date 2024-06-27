package com.example.jiemian.UI;

import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.jiemian.R;
import com.example.jiemian.Utils.utils1;
import com.example.jiemian.base.BaseActivity;
import com.example.jiemian.sqlite.SqliteDBUtils;

import butterknife.BindView;

public class LoginActivity extends BaseActivity implements View.OnClickListener{

    // 使用了 butterknife 框架, 简化开发
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.tv_register)
    TextView tvRegister;
    @BindView(R.id.tv_login)
    TextView tvLogin;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void init() {
        tvRegister.setOnClickListener(this);
        tvLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        long sTime = System.currentTimeMillis();
        switch (v.getId()) {
            case R.id.tv_register:
                // 注册
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                // 并没有 break , 使得注册后直接跳转到登录页面
                // 登录
            case R.id.tv_login:
                String intname = etPhone.getText().toString().trim();
                String intpwd = etPwd.getText().toString().trim();
                // 判断登录时出入的内容是否合法
                if (TextUtils.isEmpty(intname) || TextUtils.isEmpty(intpwd)) {
                    showToast("请检查输入内容");
                    return;
                } else {
                    utils1.passname = intname;
                    utils1.passpwd = intpwd;
                    int i = SqliteDBUtils.getInstance(LoginActivity.this).Quer(intpwd, intname);
                    if (i == 1) {
                        MyApplication.getInstance().user = SqliteDBUtils.getInstance(getApplicationContext()).loadUserByName(intname);
                        Intent intent1 = new Intent(LoginActivity.this, MainActivity.class);
                        long eTime = System.currentTimeMillis();
                        Log.i("登录使用的时间为: ", String.valueOf(eTime-sTime) + "ms");
                        startActivity(intent1);

                        showToast("登录成功");
                        finish();
                    } else if (i == -1) {
                        showToast("密码错误");
                        return;
                    } else {
                        showToast("无此用户");
                        return;
                    }
                }

        }
        }
    
}