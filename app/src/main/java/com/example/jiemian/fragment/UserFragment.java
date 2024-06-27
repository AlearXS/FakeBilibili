package com.example.jiemian.fragment;

import android.content.DialogInterface;
import android.content.Intent;

import android.os.Process;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.bumptech.glide.Glide;
import com.example.jiemian.R;
import com.example.jiemian.UI.AboutActivity;
import com.example.jiemian.UI.ChangeUserInfoActivity;
import com.example.jiemian.UI.MyApplication;
import com.example.jiemian.UI.ShoucangActivity;
import com.example.jiemian.UI.UserInfoActivity;
import com.example.jiemian.Utils.CircleImageView;
import com.example.jiemian.base.LazyFragment;
import com.example.jiemian.sqlite.SqliteDBUtils;

import butterknife.BindView;
import butterknife.OnClick;

public class UserFragment extends LazyFragment  {
    @BindView(R.id.image_head)
    CircleImageView imageHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.rl_userinfo)
    RelativeLayout rlUserinfo;
    @BindView(R.id.rl_change_pwd)
    RelativeLayout rlChangePwd;
    @BindView(R.id.rl_about)
    RelativeLayout rlAbout;
    @BindView(R.id.rl_restart)
    RelativeLayout rlRestart;
    @BindView(R.id.rl_zhuxiao)
    RelativeLayout rlZhuxiao;
    @BindView(R.id.shoucang)
    RelativeLayout shoucang;

    @BindView(R.id.tv_login)
    TextView tvLogin;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void loadData() {
        tvName.setText(MyApplication.getInstance().user.getUsername());
        if (!TextUtils.isEmpty(MyApplication.getInstance().user.getHead_url())){
            Glide.with(getActivity()).load(MyApplication.getInstance().user.getHead_url()).into(imageHead);
        }else {
            Glide.with(getActivity()).load(R.mipmap.head).into(imageHead);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (tvName != null && MyApplication.getInstance().user != null){
            tvName.setText(MyApplication.getInstance().user.getUsername());
            if (!TextUtils.isEmpty(MyApplication.getInstance().user.getHead_url())){
                Glide.with(getActivity()).load(MyApplication.getInstance().user.getHead_url()).into(imageHead);
            }else {
                Glide.with(getActivity()).load(R.mipmap.head).into(imageHead);
            }
        }
    }

    @OnClick({R.id.shoucang,R.id.rl_userinfo, R.id.rl_change_pwd, R.id.rl_about, R.id.rl_restart, R.id.rl_zhuxiao, R.id.tv_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_userinfo:
                startActivity(new Intent(getActivity(), UserInfoActivity.class));
                break;
            case R.id.shoucang:
                startActivity(new Intent(getActivity(), ShoucangActivity.class));
                break;
            case R.id.rl_change_pwd:
                startActivity(new Intent(getActivity(), ChangeUserInfoActivity.class));//修改密码
                break;
            case R.id.rl_about:
                startActivity(new Intent(getActivity(), AboutActivity.class));
                break;
            case R.id.rl_restart:
                reStartApp();
                break;
            case R.id.rl_zhuxiao:
                SqliteDBUtils.getInstance(getActivity()).delete(getActivity(),MyApplication.getInstance().user.getId()+"");
                reStartApp();
                break;
            case R.id.tv_login:
                dialog();
                break;
        }
    }

    public void reStartApp() {
        Intent intent = getActivity().getBaseContext().getPackageManager().getLaunchIntentForPackage(getActivity().getBaseContext().getPackageName());
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//与正常页面跳转一样可传递序列化数据,在Launch页面内获得
        intent.putExtra("REBOOT", "reboot");
        startActivity(intent);
        getActivity().finish();
    }

    protected void dialog() {
        androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("确认退出吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                try {
                    //正常退出
                    Process.killProcess(Process.myPid());
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

}