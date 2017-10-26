package com.honey.jdcom.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.honey.jdcom.R;
import com.honey.jdcom.entity.News;
import com.honey.jdcom.entity.XiangQingEntity;
import com.honey.jdcom.fragment_shop_son.XiangQingActivity;

import java.util.List;

/**
 * Created by 小傻瓜 on 2017/10/17.
 */

public class XiangQingAdapter extends RecyclerView.Adapter<XiangQingAdapter.ViewHolder>{
    private Context context;
    private List<XiangQingEntity.DataBean> list;

    public XiangQingAdapter(Context context, List<XiangQingEntity.DataBean> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(context,R.layout.xiangqing_item,null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String imageUrl = list.get(position).getImages().split("\\|")[0];
        Glide.with(context).load(imageUrl).into(holder.image_xq);
        holder.tv_title.setText(list.get(position).getTitle());
        holder.tv_subhead.setText(list.get(position).getSubhead());
        holder.tv_xq_xian.setText(list.get(position).getBargainPrice()+" ");
        holder.tv_xq_yuan.setText(list.get(position).getPrice()+" ");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tv_title,tv_subhead,tv_xq_xian,tv_xq_yuan;
        public ImageView image_xq;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_subhead=itemView.findViewById(R.id.tv_subhead);
            tv_xq_xian=itemView.findViewById(R.id.tv_xq_xian);
            tv_xq_yuan=itemView.findViewById(R.id.tv_xq_yuan);
            image_xq=itemView.findViewById(R.id.image_xq);
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
