package com.example.jiemian.UI;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.jiemian.R;
import com.example.jiemian.adapter.OnVideoListener;
import com.example.jiemian.adapter.VideoAdapter;
import com.example.jiemian.bean.Shiping;
import com.example.jiemian.sqlite.ShipingDBUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShoucangActivity extends AppCompatActivity implements OnVideoListener {
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_right)
    ImageView tvRight;
    @BindView(R.id.rl_tm)
    RecyclerView rlTm;
    VideoAdapter videoAdapter;
    List<Shiping> userList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shoucang);
        ButterKnife.bind(this);
        tvTitle.setText("我的收藏");
        videoAdapter = new VideoAdapter(ShoucangActivity.this,this);
        rlTm.setLayoutManager(new LinearLayoutManager(ShoucangActivity.this));
        userList = ShipingDBUtils.getInstance(ShoucangActivity.this).FindAllByType("2");
        rlTm.addItemDecoration(new DividerItemDecoration(ShoucangActivity.this, DividerItemDecoration.VERTICAL));
        videoAdapter.bindToRecyclerView(rlTm);
        videoAdapter.disableLoadMoreIfNotFullPage();
        videoAdapter.setNewData(userList);
        videoAdapter.notifyDataSetChanged();
    }
    @Override
    public void onClick(Shiping shiping) {
        Intent intent = new Intent(ShoucangActivity.this, VideoDetailActivity.class);
        intent.putExtra("detail",shiping.getId());
        startActivity(intent);
    }

    @Override
    public void onClick1(Shiping shiping) {


    }

}