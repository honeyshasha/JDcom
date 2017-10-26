package com.honey.jdcom.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.honey.jdcom.R;
import com.honey.jdcom.entity.ImageEntity;
import com.honey.jdcom.fragment.FragmentHome;

import java.util.List;

/**
 * Created by 小傻瓜 on 2017/10/9.
 */

public class MyRecycleView extends RecyclerView.Adapter<MyRecycleView.ViewHolder> {
    private Context context;
    private List<ImageEntity.TuijianBean.ListBean> list;

    public MyRecycleView(Context context,List<ImageEntity.TuijianBean.ListBean> list) {
        this.list = list;
        this.context = context;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       // ViewHolder holder=new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item,null,false));
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Log.i("xxx",list.toString());
        String imageUrl=list.get(position).getImages().split("\\|")[0];
        Log.i("xxx","imageurl   --- >   "+imageUrl);
        holder.item_text.setText(list.get(position).getTitle());
        holder.item_money.setText("¥"+list.get(position).getPrice());
        Glide.with(context).load(imageUrl).into(holder.item_jd);
        holder.item_money.setTextColor(Color.RED);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemCleck.setItemCleck(view,position);
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView item_text,item_money;
        public ImageView item_jd;
        public ViewHolder(View view){
            super(view);
            item_text =view.findViewById(R.id.item_text);
            item_jd=view.findViewById(R.id.item_jd);
            item_money =view.findViewById(R.id.item_money);
        }
    }
    //接口回掉:recyclerView的点击事件
    private OnItemCleck ItemCleck;

    public interface OnItemCleck{
        void setItemCleck(View v, int position);
    }
    public void setOnItemCleckListener(OnItemCleck itemCleck) {
        ItemCleck = itemCleck;
    }
}

