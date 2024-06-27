package com.example.jiemian.UI;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.example.jiemian.R;
import com.example.jiemian.base.BaseActivity;
import com.example.jiemian.bean.User;
import com.example.jiemian.sqlite.SqliteDBUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 注册界面
 */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.ll_name)
    LinearLayout llName;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.ll_pwd)
    LinearLayout llPwd;
    @BindView(R.id.tv_next_step)
    TextView tvNextStep;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    protected void init() {

    }

    @OnClick({R.id.rl_back, R.id.tv_next_step})
    public void onViewClicked(View view) {
        long sTime = System.currentTimeMillis();
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.tv_next_step:
                if (TextUtils.isEmpty(etPwd.getText().toString()) || TextUtils.isEmpty(etName.getText().toString())) {
                    showToast("检查输入信息");
                    return;
                } else if (etPwd.getText().toString().length() < 6) {
                    showToast("密码长度不能小于6位数");
                    return;
                } else {
                    User user = new User();
                    user.setUsername(etName.getText().toString());
                    user.setUserpwd(etPwd.getText().toString());
                    user.setHead_url("");
                    int i = SqliteDBUtils.getInstance(getApplicationContext()).saveUser(user);
                    if (i == 1) {
                        long eTime = System.currentTimeMillis();
                        Log.i("注册使用的时间为: ", String.valueOf(eTime-sTime) + "ms");
                        showToast("注册成功");
                        finish();
                    } else {
                        showToast("用户已存在, 注册失败");
                    }
                }
                break;
        }
    }

}
