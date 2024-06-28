package com.example.jiemian.adapter;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.jiemian.R;
import com.example.jiemian.bean.Shiping;


public class VideoAdapter extends BaseQuickAdapter<Shiping, BaseViewHolder> {
    private Context context;
    private OnVideoListener listener;
    public VideoAdapter(Context context,OnVideoListener listener) {

        super(R.layout.item_video);
        this.context = context;
        this.listener = listener;
    }
    public VideoAdapter(Context context,OnVideoListener listener, int type) {
        super(R.layout.item_shoucang_video);
        this.context = context;
        this.listener = listener;
    }


    /**
     * 进行视图绑定, 并且设置点击事件
     * RecyclerView 或 ListView 的每个列表项被绘制时被调用
     * @param helper A fully initialized helper. 优化视图列表中的视图绑定操作
     * @param item   The item that needs to be displayed.
     */
    @Override
    protected void convert(BaseViewHolder helper, Shiping item) {
        helper.setText(R.id.tv_title,item.getTitle());
        // 加载图片
        Glide.with(context).load(item.getTupian()).into((ImageView) helper.getView(R.id.image_head));
        // 为播放视频实现点击事件
        helper.getView(R.id.ll).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onClick(item);
                }
            }
        });
        // 为收藏视频设计点击事件
        helper.getView(R.id.tv_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (listener != null){
                    listener.onClick1(item);
                }
            }
        });
    }
}
