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
import android.widget.RelativeLayout;

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

public class SouActivity extends AppCompatActivity {
    private EditText head_sou_two;
    private RecyclerView recycleView_sou_two;
    private int temp;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==2){
                shopNewsAdapter = new ShopNewsAdapter(SouActivity.this,listData);
                LinearLayoutManager linearLayoutManager=new LinearLayoutManager(SouActivity.this);
                recycleView_sou_two.setLayoutManager(linearLayoutManager);
                recycleView_sou_two.setAdapter(shopNewsAdapter);
                shopNewsAdapter.setOnItemCleckListener(new ShopNewsAdapter.OnItemCleck() {
                    @Override
                    public void setItemCleck(View v, int position) {
                        Intent intent=new Intent(SouActivity.this,XiangQingActivity.class);
                        intent.putExtra("pid",listData.get(position).getPid()+"");
                        intent.putExtra("sellerid",listData.get(position).getSellerid()+"");
                        startActivity(intent);
                    }
                });
            }
        }
    };
    private News news;
    private List<News.DataBean> listData;
    private ShopNewsAdapter shopNewsAdapter;
    private Intent intent;
    private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sou);
        intent = getIntent();
        name = intent.getStringExtra("name");
        initView();
    }
    private void initView() {
        head_sou_two= (EditText) findViewById(R.id.head_sou_two);
        head_sou_two.setText(name);
        recycleView_sou_two= (RecyclerView) findViewById(R.id.recycleView_sou_two);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(SouActivity.this);
        recycleView_sou_two.setLayoutManager(linearLayoutManager);
        getData(name);
    }
    private void getData(String keywords) {
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
                Gson gson = new Gson();
                System.out.println("=====gson"+ gson);
                news = gson.fromJson(detidal, News.class);
                System.out.println("=====news"+ news);
                listData = news.getData();
                System.out.println("=====listData"+ listData);
                handler.sendEmptyMessage(2);
            }
        });
    }
    public void sou_two(View view){
        temp++;
        if(temp%2==0){
            LinearLayoutManager linearLayoutManager=new LinearLayoutManager(SouActivity.this);
            recycleView_sou_two.setLayoutManager(linearLayoutManager);
            shopNewsAdapter.notifyDataSetChanged();
        }else{
            FullyGridLayoutManager gridLayoutManager=new FullyGridLayoutManager(SouActivity.this,2);
            recycleView_sou_two.setLayoutManager(gridLayoutManager);
        }
    }
    public void sou_two_fan(View view){
        finish();
    }

    /**
     * 点击搜索
     * @param view
     */
    public void sou_two_shousuo(View view){
        Intent intent=new Intent(SouActivity.this,SouActivity.class);
        intent.putExtra("name",head_sou_two.getText().toString());
        startActivity(intent);
    }
}
