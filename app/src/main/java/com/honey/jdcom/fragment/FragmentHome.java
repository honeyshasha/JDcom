package com.honey.jdcom.fragment;

import android.animation.ArgbEvaluator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.honey.jdcom.R;
import com.honey.jdcom.adapter.JdcomAdapter;
import com.honey.jdcom.adapter.MyRecycleView;
import com.honey.jdcom.adapter.ViewPagerAdapter;
import com.honey.jdcom.common.API;
import com.honey.jdcom.divLayoutManger.FullyGridLayoutManager;
import com.honey.jdcom.entity.ImageEntity;
import com.honey.jdcom.entity.ShoppingEntity;
import com.honey.jdcom.fragment_shop_son.SouActivity;
import com.honey.jdcom.fragment_shop_son.SouTwoActivity;
import com.honey.jdcom.fragment_shop_son.XiangQingActivity;
import com.stx.xhb.xbanner.XBanner;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import xp.code.okhttp3.utils.OkHttp3Utils;


/**
 * Created by 小傻瓜 on 2017/9/29.
 */

public class FragmentHome extends Fragment implements View.OnClickListener {
    private View view;
    private XBanner xBanner;
    private List<String> imgesUrl;
    //轮播图的bean
    //推荐的bean
    private List<ImageEntity.TuijianBean.ListBean> mlist;
    private TextView tvHour;
    private TextView tvMinute;
    private TextView tvSecond;
    private long mHour = 02;
    private long mMin = 15;
    private long mSecond = 36;
    private boolean isRun = true;
    private RelativeLayout rl_sys;
    private RecyclerView recycleView;//recycleView
    private ViewPager viewpager;

    private EditText head_sou;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                //无限轮播
                xBanner.setData(imgesUrl,null);
                xBanner.setPoinstPosition(XBanner.RIGHT);
                xBanner.setmAdapter(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, View view, int position) {
                        Glide.with(getActivity()).load(imgesUrl.get(position)).into((ImageView) view);
                    }
                });
            }
            else if(msg.what==0x112)
            {
                //viewpager的适配器
                viewPagerAdapter = new ViewPagerAdapter(getActivity(),shoplist);
                viewpager.setAdapter(viewPagerAdapter);
            }else if(msg.what==0x113){
                //创建并设置Adapter
                adapter = new MyRecycleView(getActivity(),mlist);
                recycleView.setAdapter(adapter);
                adapter.setOnItemCleckListener(new MyRecycleView.OnItemCleck() {
                    @Override
                    public void setItemCleck(View v, int position) {
                        Intent intent=new Intent(getActivity(), XiangQingActivity.class);
                        intent.putExtra("pid",mlist.get(position).getPid()+"");
                        intent.putExtra("sellerid",mlist.get(position).getSellerid()+"");
                        startActivity(intent);
                    }
                });
            }else if(msg.what==0x11){
                //创建秒杀适配器
                jdcomAdapter = new JdcomAdapter(getActivity(),miaoShaList);
                recycle_miao.setAdapter(jdcomAdapter);
            }else if(msg.what==3){
                computeTime();
                if (mHour<10){
                    tvHour.setText("0"+mHour+"");
                }else {
                    tvHour.setText("0"+mHour+"");
                }
                if (mMin<10){
                    tvMinute.setText("0"+mMin+"");
                }else {
                    tvMinute.setText(mMin+"");
                }
                if (mSecond<10){
                    tvSecond.setText("0"+mSecond+"");
                }else {
                    tvSecond.setText(mSecond+"");
                }
            }
        }
    };
    private MyRecycleView adapter;
    private String result;
    private ImageEntity bean;
    private String result_shop;
    private ShoppingEntity shoppingEntity;
    private List<ShoppingEntity.DataBean> shoplist;
    private ArgbEvaluator evaluator = new ArgbEvaluator();
    private LinearLayout lll;
    private ViewPagerAdapter viewPagerAdapter;
    //京东秒杀
    private RecyclerView recycle_miao;
    private List<ImageEntity.MiaoshaBean.ListBeanX> miaoShaList;
    private JdcomAdapter jdcomAdapter;
    private String jdresult;
    private ImageEntity jdbean;
    private LinearLayoutManager line;
    private List<ImageEntity.DataBean> data;
    private String url;
    private FullyGridLayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       if(view==null){
           this.view=inflater.inflate(R.layout.fragment_home,container,false);
       }
        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化控件
        initView();
        //倒计时
        startRun();
        //初始化XBanner轮播图
        initBanner();
        //底部的推荐
        initRecycley();
        //商品
        initShop();
        //圆点
        initDot();
        //初始化秒杀
        initMiao();

    }
    /**
     * 秒杀
     */
    private void initMiao() {
        OkHttp3Utils.doPost(API.SHOUYE_API, null,null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                jdresult = response.body().string();
                Gson gson=new Gson();
                jdbean = gson.fromJson(jdresult, ImageEntity.class);
                miaoShaList = jdbean.getMiaosha().getList();
                handler.sendEmptyMessage(0x11);
            }
        });
    }
    /**
     * 圆点的
     */
    private void initDot() {
    }
    /**
     * 商品--->分类
     */
    private void initShop() {
        OkHttp3Utils.doPost(API.SHOPPING_API, null, null,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                result_shop = response.body().string();
                Gson gson=new Gson();
                shoppingEntity = gson.fromJson(result_shop, ShoppingEntity.class);
                shoplist = shoppingEntity.getData();
                handler.sendEmptyMessage(0x112);
            }
        });
    }
    /**
     * 为你--->推荐
     */
    private void initRecycley() {
        OkHttp3Utils.doPost(API.SHOUYE_API, null,null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                result = response.body().string();
                System.out.println("========="+result);
                Gson gson=new Gson();
                bean = gson.fromJson(result, ImageEntity.class);
                mlist= bean.getTuijian().getList();
                handler.sendEmptyMessage(0x113);
            }
        });
    }
    /**
     * 获取控件
     */
    private void initView() {
        lll = view.findViewById(R.id.lll);
        //xBanner的控件
        xBanner = view.findViewById(R.id.xBanner);
        //扫一扫的控件
        rl_sys=view.findViewById(R.id.rl_sys);
        //recycleView的控件
        recycleView=view.findViewById(R.id.recycleView);
        //分类
        viewpager=view.findViewById(R.id.viewpager);
        //京东秒杀
        recycle_miao=view.findViewById(R.id.recycle_miao);
        //点击扫一扫
        rl_sys.setOnClickListener(this);
        //创建本人自定义的网格类LayoutManager
        //GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
        mLayoutManager = new FullyGridLayoutManager(getActivity(),2);
        recycleView.setLayoutManager(mLayoutManager);
        recycleView.setHasFixedSize(true);

        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        line = new LinearLayoutManager(getActivity());
        line.setOrientation(LinearLayoutManager.HORIZONTAL);
        recycle_miao.setLayoutManager(line);


        tvHour=view.findViewById(R.id.tv_hour);
        tvMinute=view.findViewById(R.id.tv_minute);
        tvSecond=view.findViewById(R.id.tv_second);

        head_sou=view.findViewById(R.id.head_sou);
        head_sou.setOnClickListener(this);

    }
    /**
     * XBanner轮播图
     */
    private void initBanner() {
        OkHttp3Utils.doPost(API.SHOUYE_API, null, null,new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //返回的json
                String jsonStr=response.body().string();
                ImageEntity entity=new Gson().fromJson(jsonStr,ImageEntity.class);
                data = entity.getData();
                imgesUrl=new ArrayList<>();
                for(ImageEntity.DataBean bean: data){
                    url = bean.getIcon();
                    imgesUrl.add(url);
                }
                //通知Handle进行修改子线程
                handler.sendEmptyMessage(1);
            }
        });
    }
    /**
     * 扫一扫的点击事件
     * @param view
     */
    @Override
    public void onClick(View view) {
        //扫一扫的点击事件
        switch (view.getId()){
            case R.id.rl_sys:
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, 2);
                break;
            case R.id.head_sou:
                Intent in=new Intent(getActivity(), SouTwoActivity.class);
                startActivity(in);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == 2) {
                //处理扫描结果（在界面上显示）
                if (null != data) {
                    Bundle bundle = data.getExtras();
                    if (bundle == null) {
                        return;
                    }
                    if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                        String result = bundle.getString(CodeUtils.RESULT_STRING);
                        Toast.makeText(getActivity(), "解析结果:" + result, Toast.LENGTH_LONG).show();
                    } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                        Toast.makeText(getActivity(), "解析二维码失败", Toast.LENGTH_LONG).show();
                    }
                }
            }
    }
    /**
     * 秒杀开启倒计时
     */
    private void startRun() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRun) {
                    try {
                        Thread.sleep(1000); // sleep 1000ms
                        Message msg = Message.obtain();
                        msg.what =3;
                        handler.sendMessage(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
    /**
     * 倒计时计算
     */
    private void computeTime() {
        mSecond--;
        if (mSecond < 0) {
            mMin--;
            mSecond = 59;
            if (mMin < 0) {
                mMin = 59;
                mHour--;
            }
        }
    }
}
