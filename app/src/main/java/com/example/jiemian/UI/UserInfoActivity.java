package com.example.jiemian.UI;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.jiemian.R;
import com.example.jiemian.Utils.CircleImageView;
import com.example.jiemian.base.BaseActivity;


import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author admin
 * @description:
 */
public class UserInfoActivity extends BaseActivity {
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    ImageView tvRight;
    @BindView(R.id.image_head)
    CircleImageView imageHead;
    @BindView(R.id.rl_head)
    RelativeLayout rlHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_password)
    TextView tvPassword;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_userinfo;
    }

    @Override
    protected void init() {
        tvTitle.setText("用户信息");
        if (!TextUtils.isEmpty(MyApplication.getInstance().user.getHead_url())){
            Glide.with(this).load(MyApplication.getInstance().user.getHead_url()).into(imageHead);
        }else {
            Glide.with(this).load(R.mipmap.head).into(imageHead);
        }
        tvName.setText(MyApplication.getInstance().user.getUsername());
        tvPassword.setText(MyApplication.getInstance().user.getUserpwd());
    }


    @OnClick({R.id.rl_back, R.id.rl_head})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_back:
                finish();
                break;
            case R.id.rl_head:
                break;
        }
    }
}
