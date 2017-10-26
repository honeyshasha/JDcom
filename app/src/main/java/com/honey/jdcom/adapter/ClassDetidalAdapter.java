package com.honey.jdcom.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.honey.jdcom.R;
import com.honey.jdcom.entity.News;

import java.util.List;

/**
 * Created by 小傻瓜 on 2017/10/16.
 */

public class ClassDetidalAdapter extends RecyclerView.Adapter<ClassDetidalAdapter.ViewHolder>{
    private Context context;
    private List<News.DataBean> list;

    public ClassDetidalAdapter(Context context, List<News.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(context,R.layout.item_detidal_fen,null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        String stringList = list.get(position).getImages().split("\\|")[0];
        Glide.with(context).load(stringList).into(holder.image_fen);
        holder.tv_fen.setText(list.get(position).getTitle());
        holder.fen_price.setText("¥"+list.get(position).getPrice());
        holder.fen_price.setTextColor(Color.RED);
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
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_fen,fen_price;
        public ImageView image_fen;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_fen=itemView.findViewById(R.id.tv_fen);
            fen_price=itemView.findViewById(R.id.fen_price);
            image_fen=itemView.findViewById(R.id.image_fen);
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
