package com.honey.jdcom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.honey.jdcom.R;
import com.honey.jdcom.entity.ShopSonEntity;

import java.util.List;

/**
 * Created by 小傻瓜 on 2017/10/12.
 */

public class ShopSonTwoAdapter extends RecyclerView.Adapter<ShopSonTwoAdapter.ViewHolder>{
    private Context context;
    private List<ShopSonEntity.DataBean.ListBean> listBeen;
    private ViewHolder holder;
    private int pscid;
    public ShopSonTwoAdapter(Context context, List<ShopSonEntity.DataBean.ListBean> listBeen) {
        this.context = context;
        this.listBeen = listBeen;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(context,R.layout.shop_son_two_item,null);
        holder = new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Glide.with(context).load(listBeen.get(position).getIcon()).into(holder.shop_image);
        holder.shop_tv.setText(listBeen.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ItemCleck.setItemCleck(view,position,listBeen.get(position).getPscid());
            }
        });
    }
    @Override
    public int getItemCount() {
        return listBeen.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView shop_tv;
        public ImageView shop_image;
        public ViewHolder(View itemView) {
            super(itemView);
            shop_tv=itemView.findViewById(R.id.shop_tv);
            shop_image=itemView.findViewById(R.id.shop_image);
        }
    }
    //接口回掉:recyclerView的点击事件
    private OnItemCleck ItemCleck;
    public interface OnItemCleck{
        void setItemCleck(View v, int position,int pscid);
    }
    public void setOnItemCleckListener(OnItemCleck itemCleck) {
        this.ItemCleck = itemCleck;
    }
    public void getPscid(int postion){
        pscid=listBeen.get(postion).getPscid();
    }
}
