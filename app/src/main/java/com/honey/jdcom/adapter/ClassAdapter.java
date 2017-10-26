package com.honey.jdcom.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.honey.jdcom.R;
import com.honey.jdcom.entity.ShoppingEntity;
import com.honey.jdcom.fragment.FragmentClassify;

import java.util.List;

/**
 * Created by 小傻瓜 on 2017/10/10.
 */

public class ClassAdapter extends BaseAdapter{
    private Context context;
    private List<ShoppingEntity.DataBean> list;
    public static int mPosition;
    private TextView tv_fen;
    private View v;

    public ClassAdapter(Context context, List<ShoppingEntity.DataBean> list) {
        this.context = context;
        this.list = list;
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        v =  LayoutInflater.from(context).inflate(R.layout.classitem, null);
        tv_fen = v.findViewById(R.id.tv_fen);
        mPosition = i;
        tv_fen.setText(list.get(i).getName());
        if (i == FragmentClassify.mPosition) {
            tv_fen.setTextColor(Color.RED);
            v.setBackgroundResource(R.drawable.tongcheng_all_bg01);
        } else {
            v.setBackgroundColor(Color.parseColor("#ffffff"));
            tv_fen.setTextColor(Color.parseColor("#000000"));
        }
        return v;
    }
}
