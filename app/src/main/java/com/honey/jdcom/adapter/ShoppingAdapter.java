package com.honey.jdcom.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Icon;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.honey.jdcom.R;
import com.honey.jdcom.entity.ShoppingEntity;

import java.util.List;

/**
 * Created by 小傻瓜 on 2017/10/10.
 */

public class ShoppingAdapter extends RecyclerView.Adapter<ShoppingAdapter.ViewHolder> {
    private Context context;
    private List<ShoppingEntity.DataBean> list;

    public ShoppingAdapter(Context context,List<ShoppingEntity.DataBean> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public ShoppingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =View.inflate(context,R.layout.item_shopping,null);
        ViewHolder holder = new ShoppingAdapter.ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ShoppingAdapter.ViewHolder holder, int position) {
        Log.i("xxx",list.toString());
        holder.tv_shop.setText(list.get(position).getName());
        Glide.with(context).load(list.get(position).getIcon()).into(holder.iv_shop);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_shop;
        public ImageView iv_shop;
        public ViewHolder(View view){
            super(view);
            tv_shop =view.findViewById(R.id.tv_shop);
            iv_shop=view.findViewById(R.id.iv_shop);
        }
    }
}

