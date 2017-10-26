package com.honey.jdcom.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.honey.jdcom.R;

import java.util.List;


public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder>{
    Context context;
    List<OrderBean.DataBean> list;
    public OrderAdapter(Context context, List<OrderBean.DataBean> list) {
        this.context=context;
        this.list=list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=View.inflate(context, R.layout.order_layout,null);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        String price = list.get(position).getPrice() + "";
        holder.order_price.setText("¥"+list.get(position).getPrice()+"");
        holder.order_price.setTextColor(Color.RED);
        holder.order_date.setText(list.get(position).getCreatetime());
        holder.order_pid.setText("商品编号:"+list.get(position).getOrderid());
        holder.but_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, PayDemoActivity.class);
                context.startActivity(intent);
                holder.but_order.setText("已支付");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView order_price,order_date,order_pid;
        public Button but_order;

        public ViewHolder(View itemView) {
            super(itemView);
            order_price = itemView.findViewById(R.id.order_price);
            order_date = itemView.findViewById(R.id.order_date);
            order_pid = itemView.findViewById(R.id.order_pid);
            but_order = itemView.findViewById(R.id.but_order);
        }
    }
}
