package com.honey.jdcom.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.honey.jdcom.R;
import com.honey.jdcom.adapter.ClassAdapter;
import com.honey.jdcom.common.API;
import com.honey.jdcom.entity.ImageEntity;
import com.honey.jdcom.entity.ShoppingEntity;
import com.honey.jdcom.fragment_shop_son.MyFragmentClass;
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

public class FragmentClassify extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener {
    public static int mPosition;
    public static int mHigth=0;
    private View view;
    private ListView litView_class;
    private FrameLayout frame_class;
    private XBanner xBanner_class;
    private List<String> imgesUrl;
    private Gson gson;
    private ShoppingEntity shoppingEntity;
    private List<ShoppingEntity.DataBean> shopList;
    private RelativeLayout rl_sys;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                adapter = new ClassAdapter(getActivity(),shopList);
                litView_class.setAdapter(adapter);
            }
            else if(msg.what==2){
                //无限轮播
                xBanner_class.setData(imgesUrl,null);
                xBanner_class.setPoinstPosition(XBanner.RIGHT);
                xBanner_class.setmAdapter(new XBanner.XBannerAdapter() {
                    @Override
                    public void loadBanner(XBanner banner, View view, int position) {
                        Glide.with(getActivity()).load(imgesUrl.get(position)).into((ImageView) view);
                    }
                });
            }
        }
    };
    private MyFragmentClass fragmentClass;
    private ClassAdapter adapter;
    private String result;
    private Bundle bundle;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(view==null){
            view=inflater.inflate(R.layout.fragment_classify,container,false);
        }
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化控件
        initView();
        //分类的列表的数据
        initData();
        //XBanner轮播图
        initBanner();

    }
    /**
     * XBanner轮播图
     */
    private void initBanner() {
        OkHttp3Utils.doPost(API.SHOUYE_API, null,null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //返回的json
                String jsonStr=response.body().string();
                ImageEntity entity=new Gson().fromJson(jsonStr,ImageEntity.class);
                final List<ImageEntity.DataBean> data = entity.getData();

                imgesUrl=new ArrayList<>();
                for(ImageEntity.DataBean bean:data)
                {
                    String url=bean.getIcon();
                    imgesUrl.add(url);
                }
                //通知Handle进行修改子线程
                handler.sendEmptyMessage(2);
            }
        });
    }
    /**
     * 分类的列表的数据
     */
    private void initData() {
        OkHttp3Utils.doPost(API.SHOPPING_API, null,null, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                result = response.body().string();
                gson = new Gson();
                shoppingEntity = gson.fromJson(result, ShoppingEntity.class);
                shopList = shoppingEntity.getData();
                handler.sendEmptyMessage(1);
            }
        });
    }
    /**
     * 获取控件
     */
    private void initView() {
        litView_class = view.findViewById(R.id.litView_class);
        frame_class = view.findViewById(R.id.frame_class);
        //图片
        xBanner_class=view.findViewById(R.id.xBanner_class);
        rl_sys=view.findViewById(R.id.rl_sys);
        rl_sys.setOnClickListener(this);
        //点击条目
        litView_class.setOnItemClickListener(this);

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
     * //点击条目
     * @param adapterView
     * @param view
     * @param i
     * @param l
     */
    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //拿到当前位置
        mPosition = i;
        //点击居中
        mHigth=litView_class.getMeasuredHeight()-120;
        litView_class.smoothScrollToPositionFromTop(i,mHigth/2,50);
        //即使刷新adapter
        adapter.notifyDataSetChanged();
        fragmentClass = new MyFragmentClass();
        fragmentClass.getcid(i);
        bundle = new Bundle();
        bundle.putInt("cid",shopList.get(i).getCid());
        fragmentClass.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.frame_class,fragmentClass).commit();
    }

}
