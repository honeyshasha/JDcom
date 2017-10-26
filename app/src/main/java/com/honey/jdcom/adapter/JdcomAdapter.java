package com.honey.jdcom.adapter;

import android.content.Context;
import android.graphics.Color;
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

import java.util.List;

/**
 * Created by 小傻瓜 on 2017/10/11.
 */

public class JdcomAdapter extends RecyclerView.Adapter<JdcomAdapter.ViewHolder> {
    private Context context;
    private List<ImageEntity.MiaoshaBean.ListBeanX> jdlist;

    public JdcomAdapter(Context context,List<ImageEntity.MiaoshaBean.ListBeanX> jdlist) {
        this.jdlist = jdlist;
        this.context = context;
        // notifyDataSetChanged();
    }
    @Override
    public JdcomAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.jdcom_item,parent,false);
        JdcomAdapter.ViewHolder vh = new JdcomAdapter.ViewHolder(view);
        return vh;
    }
    @Override
    public void onBindViewHolder(JdcomAdapter.ViewHolder holder, int position) {
        Log.i("xxx",jdlist.toString());
        String imageUrl=jdlist.get(position).getImages().split("\\|")[0];
        Log.i("xxx","imageurl   --- >   "+imageUrl);
        Glide.with(context).load(imageUrl).into(holder.image_miao);
        holder.tv_money.setText("¥"+jdlist.get(position).getPrice());
        holder.tv_money1.setText("¥"+jdlist.get(position).getBargainPrice());
        holder.tv_money.setTextColor(Color.RED);
        holder.tv_money1.setTextColor(Color.GRAY);
    }
    @Override
    public int getItemCount() {
        return jdlist.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_money,tv_money1;
        public ImageView image_miao;
        public ViewHolder(View view){
            super(view);
            tv_money =view.findViewById(R.id.tv_money);
            tv_money1=view.findViewById(R.id.tv_money1);
            image_miao =view.findViewById(R.id.image_miao);
        }
    }
}
