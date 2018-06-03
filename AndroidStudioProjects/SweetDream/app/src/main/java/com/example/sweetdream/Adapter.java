package com.example.sweetdream;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

/**
 * Created by WYFWWZ on 2018/5/4.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {

    private int pic_cnt;
    private Context mContext;

    private String[] mColors = {
            "http://myhouse.oss-cn-beijing.aliyuncs.com/house_picture/1.jpg",
            "http://myhouse.oss-cn-beijing.aliyuncs.com/house_picture/2.jpg",
            "http://myhouse.oss-cn-beijing.aliyuncs.com/house_picture/3.jpg",
            "http://myhouse.oss-cn-beijing.aliyuncs.com/house_picture/4.jpg",
            "http://myhouse.oss-cn-beijing.aliyuncs.com/house_picture/5.jpg",
            "http://myhouse.oss-cn-beijing.aliyuncs.com/house_picture/6.jpg",
            "http://myhouse.oss-cn-beijing.aliyuncs.com/house_picture/7.jpg",
            "http://myhouse.oss-cn-beijing.aliyuncs.com/house_picture/8.jpg",
            "http://myhouse.oss-cn-beijing.aliyuncs.com/house_picture/9.jpg",
            "http://myhouse.oss-cn-beijing.aliyuncs.com/house_picture/10.jpg",
            "http://myhouse.oss-cn-beijing.aliyuncs.com/house_picture/11.jpg",
            "http://myhouse.oss-cn-beijing.aliyuncs.com/house_picture/12.jpg",
            "http://myhouse.oss-cn-beijing.aliyuncs.com/house_picture/13.jpg",
            "http://myhouse.oss-cn-beijing.aliyuncs.com/house_picture/14.jpg",
            "http://myhouse.oss-cn-beijing.aliyuncs.com/house_picture/15.jpg",
            "http://myhouse.oss-cn-beijing.aliyuncs.com/house_picture/16.jpg",
            "http://myhouse.oss-cn-beijing.aliyuncs.com/house_picture/17.jpg",
            "http://myhouse.oss-cn-beijing.aliyuncs.com/house_picture/18.jpg",
            "http://myhouse.oss-cn-beijing.aliyuncs.com/house_picture/19.jpg",
            "http://myhouse.oss-cn-beijing.aliyuncs.com/house_picture/20.jpg"
    };

    public Adapter(Context c, int pic_cnt) {
        this.pic_cnt = pic_cnt;
        mContext = c;
    }



    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.layout_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        Glide.with(mContext).load(mColors[position % mColors.length])
                .into(holder.img);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                    Toast.makeText(mContext, "点击了："+position, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return pic_cnt;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        public ViewHolder(View itemView) {
            super(itemView);
            img = (ImageView) itemView.findViewById(R.id.img);
        }
    }
}