package com.honey.jdcom.fragment_shop_son;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.honey.jdcom.R;
import com.honey.jdcom.common.API;
import com.honey.jdcom.entity.ShopCar;
import com.honey.jdcom.entity.ShopCarService;
import com.honey.jdcom.entity.XiangQingEntity;
import com.honey.jdcom.entity.XiangQingImage;
import com.stx.xhb.xbanner.XBanner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import xp.code.okhttp3.utils.OkHttp3Utils;

public class XiangQingActivity extends AppCompatActivity implements View.OnClickListener {
    private XBanner desc_image;
    private String pid;
    private String uid;
    private TextView desc_content;
    private TextView desc_content2;
    private TextView desc_money;
    private TextView desc_oldmoney;
    private String result;
    private Gson gson;
    private XiangQingEntity qingEntity;
    private XiangQingEntity.DataBean data;
    private Button add_goodcar;
    private RadioButton car_goods;
    private List<String> imgesUrl;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==100){
                desc_content.setText(data.getTitle());
                desc_content2.setText(data.getSubhead());
                desc_money.setText(data.getPrice()+"");
                desc_oldmoney.setText(data.getBargainPrice()+"");
                //商家的id
                sellerid = seller.getSellerid()+"";
            }else if(msg.what==120){
                Toast.makeText(XiangQingActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
            }else if(msg.what==1){
                //无限轮播
                desc_image.setData(imgesUrl,null);
                Log.i("xxx","图片的地址:"+imgesUrl.toString());
                desc_image.setPoinstPosition(XBanner.RIGHT);
                desc_image.setmAdapter(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, View view, int position) {
                        Glide.with(XiangQingActivity.this).load(imgesUrl.get(position)).into((ImageView) view);
                    }
                });
            }
        }
    };
    private ShopCar shopCar;
    private Gson gson1;
    private String result1;
    private int code;
    private String result2;
    private Gson gsoncar;
    private ShopCarService shopcar;
    private List<ShopCarService.DataBean> dataShopCar;
    private XiangQingEntity.SellerBean seller;
    private String sellerid;
    private String images1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiang_qing);
        Intent intent=getIntent();
        //传过来的pic
        pid = intent.getStringExtra("pid");
        sellerid=intent.getStringExtra("sellerid");
        //初始化控件
        initView();
        intiData();
    }
    /**
     *
     * Xbanner的图片
     */
    private void intiData() {
        HashMap<String,String> params=new HashMap<>();
        params.put("pid",getIntent().getStringExtra("pid"));
        OkHttp3Utils.doPost(API.DETAL_API, null, params,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                Gson gson=new Gson();
                XiangQingImage xiangQingImage = gson.fromJson(string, XiangQingImage.class);
                images1 = xiangQingImage.getData().getImages();
                imgesUrl=new ArrayList<>();
                String[] strings=images1.split("\\|");
                for(int a=0;a<strings.length;a++)
                {
                    imgesUrl.add(strings[a]);
                }
                //通知Handle进行修改子线程
                handler.sendEmptyMessage(1);
            }
        });
    }
    /**
     * 获取控件
     */
    private void initView() {
        desc_image= (XBanner) findViewById(R.id.desc_image);
        desc_content = (TextView) findViewById(R.id.desc_content);
        desc_content2 = (TextView) findViewById(R.id.desc_content2);
        desc_money = (TextView) findViewById(R.id.desc_money);
        desc_oldmoney = (TextView) findViewById(R.id.desc_oldmoney);
        add_goodcar= (Button) findViewById(R.id.add_goodcar);
        car_goods= (RadioButton) findViewById(R.id.car_goods);
        //点击添加购物车
        add_goodcar.setOnClickListener(this);
        car_goods.setOnClickListener(this);
        initData(pid);
    }
    /**
     * 获取数据
     */
    private void initData(String pid) {
        OkHttpClient ok = new OkHttpClient();
        FormBody.Builder body = new FormBody.Builder();
        body.add("pid", pid+"");
        FormBody bodyForm = body.build();
        Request request = new Request.Builder().post(bodyForm).url(API.DETAL_API).build();
        ok.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                result = response.body().string();
                System.out.println("xxxxx  result"+result);
                gson = new Gson();
                System.out.println("xxxxx  gson"+gson);
                qingEntity = gson.fromJson(result, XiangQingEntity.class);
                System.out.println("xxxxx  qingEntity"+qingEntity);
                //商品用户的seller
                seller = qingEntity.getSeller();
                System.out.println("xxxxx  seller"+seller);
                //商品的data
                data = qingEntity.getData();
                System.out.println("xxxxx  data"+data);
                handler.sendEmptyMessage(100);
            }
        });
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            /**
             * 查询购物车的详情
             */
            case R.id.car_goods:
                OkHttpClient client = new OkHttpClient();
                FormBody.Builder bodyBuilder = new FormBody.Builder();
                uid= API.sp.getString("uid","");
                bodyBuilder.add("uid", uid+"");
                FormBody formBody2 = bodyBuilder.build();
                final Request request2= new Request.Builder().post(formBody2).url(API.GETCARTS_API).build();
                client.newCall(request2).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        result2 = response.body().string();
                        System.out.println("=====添加成功 result2 "+result2);
                        gsoncar = new Gson();
                        System.out.println("=====添加成功 gsoncar "+gsoncar);
                        shopcar = gsoncar.fromJson(result2, ShopCarService.class);
                        System.out.println("=====添加成功 shopcar "+ shopcar);
                        dataShopCar = shopcar.getData();
                        System.out.println("=====查询成功 dataShopCar "+ dataShopCar);
                    }
                });
                break;
            /**
             * 添加购物车
             */
            case R.id.add_goodcar:
                OkHttpClient okHttpClient = new OkHttpClient();
                FormBody.Builder body = new FormBody.Builder();
                uid=API.sp.getString("uid","");
                body.add("uid",uid);
                body.add("pid", pid+"");
                body.add("sellerid",sellerid+"");
                FormBody bodyForm1 = body.build();
                Request request = new Request.Builder().post(bodyForm1).url(API.ADDCART_API).build();
                okHttpClient.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        result1 = response.body().string();
                        System.out.println("=====添加购物车成功 result1 "+result1);
                        gson1 = new Gson();
                        shopCar = gson1.fromJson(result1, ShopCar.class);
                        System.out.println("========shopCar "+shopCar);
                        code = Integer.parseInt(shopCar.getCode());
                        System.out.println("========code "+ code);
                        String msg = shopCar.getMsg();
                        System.out.println("=======msg "+ msg);
                        int pid = shopCar.getPid();
                        System.out.println("=======pid "+ pid);
                        int sellerid = shopCar.getSellerid();
                        System.out.println("=======sellerid "+ sellerid);
                        int uid = shopCar.getUid();
                        System.out.println("=======uid "+ uid);
                        if(code ==0){
                            handler.sendEmptyMessage(120);
                        }

                    }
                });
        }
    }
}
