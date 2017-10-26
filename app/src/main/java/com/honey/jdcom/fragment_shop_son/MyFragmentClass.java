package com.honey.jdcom.fragment_shop_son;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.honey.jdcom.R;
import com.honey.jdcom.adapter.ShopSonAdapter;
import com.honey.jdcom.common.API;
import com.honey.jdcom.entity.ShopSonEntity;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import xp.code.okhttp3.utils.OkHttp3Utils;


/**
 * Created by 小傻瓜 on 2017/10/10.
 */

public class MyFragmentClass extends Fragment {
    private View view;
    private int cid;
    private RecyclerView recycler_shop;
    private String result;
    private Gson gson;
    private ShopSonEntity shopSonEntity;
    private ShopSonAdapter shopSonAdapter;
    private LinearLayoutManager line;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==0x123){
                //分类一的适配器
                ShopSonEntity shopSonEntity= (ShopSonEntity) msg.obj;
                shopSonAdapter = new ShopSonAdapter(getActivity(),shopSonEntity.getData());
                recycler_shop.setAdapter(shopSonAdapter);
            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       if(view==null){
           view = inflater.inflate(R.layout.myfragment,container,false);
       }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化控件
        initView();
        //商品
        initData();
    }
    /**
     * 商品
     */
    private void initData() {
        OkHttp3Utils.doGet(API.SHOP_API+cid,null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                result = response.body().string();
                Log.i("xxx",result+"");
                gson = new Gson();
                shopSonEntity = gson.fromJson(result, ShopSonEntity.class);
                Message message=handler.obtainMessage();
                message.obj=shopSonEntity;
                message.what=0x123;
                handler.sendMessage(message);
            }
        });
    }
    /**
     * 获取控件
     */
    private void initView() {
        recycler_shop=view.findViewById(R.id.recycle_shop);
        line = new LinearLayoutManager(getActivity());
        recycler_shop.setLayoutManager(line);
    }
    /**
     * id
     * @param postion
     */
    public void getcid(int postion){
        cid=postion;
    }
}
