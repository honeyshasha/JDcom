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
 * Created by 小傻瓜 on 2017/10/15.
 */

public class ShopNewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    private List<News.DataBean> list;
    //定义两种类型
    private final int TYPE_0=0;
    private final int TYPE_1=1;
    private ViewHolderOne holderOne;
    private ViewHolderTwo holderTwo;

    public ShopNewsAdapter(Context context, List<News.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    //在create里判断item的类型
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==0){
            View view=View.inflate(context, R.layout.item_sou,null);
            holderOne = new ViewHolderOne(view);
            return holderOne;
        }else if(viewType==1){
            View view=View.inflate(context,R.layout.item_sou_two,null);
            holderTwo = new ViewHolderTwo(view);
            return holderTwo;
        }
        return null;
    }
    //赋值
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if(holder instanceof ViewHolderOne){
            String imageUrl=list.get(position).getImages().split("\\|")[0];
            holderOne.item_tv.setText(list.get(position).getTitle());
            holderOne.item_tv_price.setText("¥"+list.get(position).getPrice());
            Glide.with(context).load(imageUrl).into(holderOne.item_one);
            holderOne.item_tv_price.setTextColor(Color.RED);
        }else if(holder instanceof ViewHolderTwo){
            String imageUrl=list.get(position).getImages().split("\\|")[0];
            holderTwo.item_tv_two.setText(list.get(position).getTitle());
            holderTwo.item_tv_two_price.setText("¥"+list.get(position).getPrice());
            Glide.with(context).load(imageUrl).into(holderTwo.item_two);
            holderTwo.item_tv_two_price.setTextColor(Color.RED);
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position%2==0){
            return TYPE_0;
        }else{
            return TYPE_1;
        }
    }

    public class ViewHolderOne extends RecyclerView.ViewHolder{
        public TextView item_tv_price,item_tv;
        public ImageView item_one;
        public ViewHolderOne(View itemView) {
            super(itemView);
            item_tv=itemView.findViewById(R.id.item_tv);
            item_tv_price=itemView.findViewById(R.id.item_tv_price);
            item_one=itemView.findViewById(R.id.item_one);
        }
    }
    public class ViewHolderTwo extends RecyclerView.ViewHolder{
        public TextView item_tv_two,item_tv_two_price;
        public ImageView item_two;
        public ViewHolderTwo(View itemView) {
            super(itemView);
            item_tv_two=itemView.findViewById(R.id.item_tv_two);
            item_tv_two_price=itemView.findViewById(R.id.item_tv_two_price);
            item_two=itemView.findViewById(R.id.item_two);
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

