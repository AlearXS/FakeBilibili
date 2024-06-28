package com.example.jiemian.fragment;

import android.content.Intent;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jiemian.R;
import com.example.jiemian.UI.AddVideoActivity;
import com.example.jiemian.UI.VideoActivity;
import com.example.jiemian.UI.VideoDetailActivity;
import com.example.jiemian.adapter.OnVideoListener;
import com.example.jiemian.adapter.VideoAdapter;
import com.example.jiemian.base.LazyFragment;
import com.example.jiemian.bean.Shiping;
import com.example.jiemian.sqlite.ShipingDBUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class ShouyeFragment extends LazyFragment implements OnVideoListener {
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
    @BindView(R.id.quanbu)
    TextView quanbu;
    @BindView(R.id.bihua)
    TextView bihua;
    @BindView(R.id.zitie)
    TextView zitie;
    @BindView(R.id.kaiti)
    TextView kaiti;
    @BindView(R.id.add)
    ImageView add;
    @BindView(R.id.et_input)
    EditText etinput;
    @BindView(R.id.tv_search)
    TextView tvsearch;
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_shouye;
    }

    @Override
    protected void loadData() {
        tvTitle.setText("视频");
        videoAdapter = new VideoAdapter(getActivity(),this);
        rlTm.setLayoutManager(new LinearLayoutManager(getActivity()));
        userList = ShipingDBUtils.getInstance(getActivity()).FindAll();
        // DividerItemDecoration 是一个用于在 RecyclerView 的列表项之间添加分割线的装饰器
        rlTm.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        // adapter 与 recycleView 绑定
        videoAdapter.bindToRecyclerView(rlTm);
        // 如果 RecyclerView 中的数据没有填满一整页，则禁用加载更多功能
        videoAdapter.disableLoadMoreIfNotFullPage();
        videoAdapter.setNewData(userList);
        // RecyclerView 重新绘制列表项，并显示最新的数据
        videoAdapter.notifyDataSetChanged();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), AddVideoActivity.class);
                        startActivityForResult(intent,105);
            }
        });
        tvsearch.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                long sTime = System.currentTimeMillis();
                if(TextUtils.isEmpty(etinput.getText().toString())){

                    Toast.makeText(getActivity(),"请输入查询信息",Toast.LENGTH_SHORT).show();
                } else {
                    userList = ShipingDBUtils.getInstance(getActivity()).FindAllByLevel(etinput.getText().toString());
                    videoAdapter.setNewData(userList);
                    videoAdapter.notifyDataSetChanged();
                }
                long eTime = System.currentTimeMillis();
                Log.i("搜索视频使用的时间为: ", String.valueOf(eTime-sTime) + "ms");;
            }
        });
        quanbu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userList = ShipingDBUtils.getInstance(getActivity()).FindAll();
                videoAdapter.setNewData(userList);
                videoAdapter.notifyDataSetChanged();
            }
        });
        bihua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userList = ShipingDBUtils.getInstance(getActivity()).FindAllByLevel("生活");
                videoAdapter.setNewData(userList);
                videoAdapter.notifyDataSetChanged();
            }
        });
        zitie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userList = ShipingDBUtils.getInstance(getActivity()).FindAllByLevel("记录");
                videoAdapter.setNewData(userList);
                videoAdapter.notifyDataSetChanged();
            }
        });
        kaiti.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                long sTime = System.currentTimeMillis();
                userList = ShipingDBUtils.getInstance(getActivity()).FindAllByLevel("影视");
                videoAdapter.setNewData(userList);
                videoAdapter.notifyDataSetChanged();
                long eTime = System.currentTimeMillis();
                Log.i("分类展示视频使用的时间为: ", String.valueOf(eTime-sTime) + "ms");
            }
        });
    }


    /**
     *  播放视频
     * @param shiping
     */
    @Override
    public void onClick(Shiping shiping) {
        long sTime = System.currentTimeMillis();
        Intent intent = new Intent(getActivity(), VideoDetailActivity.class);
        intent.putExtra("detail",String.valueOf(shiping.getId()));
        long eTime = System.currentTimeMillis();
        Log.i("从点击视频到播放视频使用的时间为: ", String.valueOf(eTime - sTime) + "ms");
        startActivity(intent);
    }


    /**
     *  收藏视频
     * @param shiping
     */
    @Override
    public void onClick1(Shiping shiping) {
        long sTime = System.currentTimeMillis();
        shiping.setType("2");
        ShipingDBUtils.getInstance(getActivity()).change(getActivity(),shiping);
        long eTime = System.currentTimeMillis();
        Log.i("收藏视频使用的时间为: ", String.valueOf(eTime-sTime) + "ms");
        showToast("收藏成功");
    }

    /**
     *  重新回到首页
     */
    @Override
    public void onResume() {
        super.onResume();
        if (rlTm!= null &&videoAdapter!= null){
            List<Shiping> userList = ShipingDBUtils.getInstance(getActivity()).FindAll();
            videoAdapter.setNewData(userList);
            videoAdapter.notifyDataSetChanged();
        }
    }
}