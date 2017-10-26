package com.honey.jdcom.adapter;

import android.content.Context;
import android.graphics.drawable.Icon;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.honey.jdcom.R;
import com.honey.jdcom.entity.ShoppingEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 小傻瓜 on 2017/10/10.
 */

public class ViewPagerAdapter extends PagerAdapter{
    private Context context;
    private List<List<ShoppingEntity.DataBean>> dataBeen;
    private RecyclerView recyclerView_two;

    public ViewPagerAdapter(Context context, List<ShoppingEntity.DataBean> dataBeen) {
        this.context = context;
        List<ShoppingEntity.DataBean> dataBean_1=new ArrayList<>();
        List<ShoppingEntity.DataBean> dataBean_2=new ArrayList<>();
        for (int i=0;i<19;i++)
        {
            ShoppingEntity.DataBean dataBean_one=dataBeen.get(i);
            if(i<10)
            {
                dataBean_1.add(dataBean_one);
            }
            else
            {
                dataBean_2.add(dataBean_one);
            }
        }
        this.dataBeen=new ArrayList<>();
        this.dataBeen.add(dataBean_1);
        this.dataBeen.add(dataBean_2);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view==object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view=View.inflate(context, R.layout.recycleview,null);
        recyclerView_two = view.findViewById(R.id.recycleView_two);
        recyclerView_two.setLayoutManager(new GridLayoutManager(context,5));
        recyclerView_two.setAdapter(new ShoppingAdapter(context,dataBeen.get(position)));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }
}
