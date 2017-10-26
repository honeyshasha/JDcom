package com.honey.jdcom.fragment_shop_son;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.honey.jdcom.R;
import com.honey.jdcom.adapter.ClassDetidalAdapter;
import com.honey.jdcom.adapter.ShopNewsAdapter;
import com.honey.jdcom.common.API;
import com.honey.jdcom.divLayoutManger.FullyGridLayoutManager;
import com.honey.jdcom.entity.News;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import xp.code.okhttp3.utils.OkHttp3Utils;

public class DetialActivity extends AppCompatActivity {
    private RecyclerView recycleView_detidal;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                //分类一的适配器
                detidalAdapter = new ClassDetidalAdapter(DetialActivity.this,data);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(DetialActivity.this);
                recycleView_detidal.setLayoutManager(linearLayoutManager);
                recycleView_detidal.setAdapter(detidalAdapter);
                detidalAdapter.setOnItemCleckListener(new ClassDetidalAdapter.OnItemCleck() {
                    @Override
                    public void setItemCleck(View v, int position) {
                        Intent intent=new Intent(DetialActivity.this,XiangQingActivity.class);
                        intent.putExtra("pid",data.get(position).getPid()+"");
                        intent.putExtra("sellerid",data.get(position).getSellerid()+"");
                        startActivity(intent);
                    }
                });
            }else if(msg.what==2){
                shopNewsAdapter = new ShopNewsAdapter(DetialActivity.this,listData);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(DetialActivity.this);
                recycleView_detidal.setLayoutManager(linearLayoutManager);
                recycleView_detidal.setAdapter(shopNewsAdapter);
            }
        }
    };
    private List<News.DataBean> data;
    private ClassDetidalAdapter detidalAdapter;
    private int pscid;
    private News news;
    private String string;
    private int temp=1;
    private EditText head_detial_ss;
    private List<News.DataBean> listData;
    private ShopNewsAdapter shopNewsAdapter;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detial);
        Intent intent=getIntent();
        pscid = intent.getIntExtra("pscid",0);
        //初始话控件
        initView();
        //详情
        initDetial();
    }
    /**
     * 初始化控件
     */
    private void initView() {

        recycleView_detidal= (RecyclerView) findViewById(R.id.recycleView_detidal);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(DetialActivity.this);
        recycleView_detidal.setLayoutManager(linearLayoutManager);

        head_detial_ss= (EditText) findViewById(R.id.head_detial_ss);
    }
    /**
     * 详情
     */
    private void initDetial() {
        OkHttp3Utils.doGet(API.ZIFENLEI_API+pscid,null, new Callback() {
            private Gson gson;
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                string = response.body().string();
                System.out.println("=====string"+string);
                gson = new Gson();
                System.out.println("=====gson"+gson);
                news = gson.fromJson(string, News.class);
                System.out.println("=====news"+news);
                data = news.getData();
                System.out.println("=====data"+data);
                handler.sendEmptyMessage(1);
            }
        });
    }
    public void detidal(View view){
        temp++;
        if(temp%2==0){
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(DetialActivity.this);
            recycleView_detidal.setLayoutManager(linearLayoutManager);
            detidalAdapter.notifyDataSetChanged();
        }else{
            FullyGridLayoutManager gridLayoutManager=new FullyGridLayoutManager(DetialActivity.this,2);
            recycleView_detidal.setLayoutManager(gridLayoutManager);
        }
    }
    public void detidal_shousuo(View view){
        initDe(head_detial_ss.getText().toString());
    }
    private void initDe(String keywords) {
        OkHttpClient ok = new OkHttpClient();
        FormBody.Builder body = new FormBody.Builder();
        body.add("keywords", keywords);
        FormBody bodyForm = body.build();
        Request request = new Request.Builder().post(bodyForm).url(API.GUANJIAN_API).build();
        ok.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String detidal = response.body().string();
                System.out.println("====说说" + detidal);
                gson = new Gson();
                System.out.println("=====gson"+ gson);
                news = gson.fromJson(detidal, News.class);
                System.out.println("=====news"+ news);
                listData = news.getData();
                System.out.println("=====listData"+ listData);
                handler.sendEmptyMessage(2);
            }
        });
    }
    public void detidal_fan(View view){
        finish();
    }
}
