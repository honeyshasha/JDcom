package com.honey.jdcom.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.honey.jdcom.R;
import com.honey.jdcom.divLayoutManger.FullyGridLayoutManager;
import com.honey.jdcom.entity.ShopSonEntity;
import com.honey.jdcom.fragment_shop_son.DetialActivity;
import com.honey.jdcom.fragment_shop_son.XiangQingActivity;

import java.util.List;

/**
 * Created by 小傻瓜 on 2017/10/12.
 */

public class ShopSonAdapter extends RecyclerView.Adapter<ShopSonAdapter.ViewHolder>{
    private Context context;
    private List<ShopSonEntity.DataBean> list;
    private List<ShopSonEntity.DataBean.ListBean> listSon;
    private ShopSonTwoAdapter twoAdapter;
    private FullyGridLayoutManager mLayoutManager;
    public ShopSonAdapter(Context context, List<ShopSonEntity.DataBean> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(context,R.layout.shop_son_one,null);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
          holder.tv_shi.setText(list.get(position).getName());
          listSon = this.list.get(position).getList();

        twoAdapter = new ShopSonTwoAdapter(context,listSon);
        twoAdapter.setOnItemCleckListener(new ShopSonTwoAdapter.OnItemCleck() {
            @Override
            public void setItemCleck(View v, int position, int pscid) {
                Intent intent=new Intent(context, DetialActivity.class);
                intent.putExtra("pscid",pscid);
                context.startActivity(intent);
            }
        });
        holder.recycle_shop_two.setAdapter(twoAdapter);
        mLayoutManager = new FullyGridLayoutManager(context,3);
        holder.recycle_shop_two.setLayoutManager(mLayoutManager);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    //自定义的viewholder，持有每个item的所有界面元素
    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_shi;
        public RecyclerView recycle_shop_two;
        public ViewHolder(View view) {
            super(view);
            tv_shi=view.findViewById(R.id.tv_shi);
            recycle_shop_two=view.findViewById(R.id.recycle_shop_two);
        }
    }
}
