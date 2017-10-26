package com.honey.jdcom.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.honey.jdcom.R;
import com.honey.jdcom.adapter.CartAdapter;
import com.honey.jdcom.common.API;
import com.honey.jdcom.entity.CarEntity;
import com.honey.jdcom.main.PayDemoActivity;
import com.honey.jdcom.shopcarfragment.ChildBean;
import com.honey.jdcom.shopcarfragment.ParentBean;
import com.honey.jdcom.shopcarfragment.TopBar;
import com.honey.jdcom.view.ICartView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import xp.code.okhttp3.utils.OkHttp3Utils;
import xp.code.okhttp3.utils.callback.GsonObjCallback;

/**
 * Created by XP on 2017/10/18.
 */
public class GouwucheFragment extends Fragment implements View.OnClickListener,ICartView {
    private View view;
    private TopBar mTopbar;
    private ExpandableListView expandableListView;
    private CartAdapter adapter;
    private List<ParentBean> parentList;
    private List<List<ChildBean>> childList;
    private CheckBox gouwuche_footer_check;
    private TextView gouwuche_footer_jiesuan;
    private TextView gouwuche_footer_price;
    private TextView gouwuche_footer_heji;
    private TextView gouwuche_tv;
    private SharedPreferences sp;
    private int sum=0;//总价
    private String uid;
    private CarEntity.DataBean.ListBean listBean;
    private ChildBean bean;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_gouwuche, container, false);
        initView(view);
        initData();
        createEvent();
        return view;
    }

    private void initData() {
        this.sp= API.sp;
        if(sp.getBoolean("islogin",false))
        {
            gouwuche_tv.setVisibility(View.GONE);
            expandableListView.setVisibility(View.VISIBLE);
            //调用P层获取数据
            uid = sp.getString("uid","");
            HashMap<String,String> params=new HashMap<>();
            params.put("uid", uid);
            OkHttp3Utils.doPost(API.GETCARTS_API, null, params, new GsonObjCallback<CarEntity>() {
                @Override
                public void onFail(Call call, IOException e) {
                }
                @Override
                public void onSucc(CarEntity carEntity) {
                    parentList = new ArrayList<>();
                    childList = new ArrayList<>();

                    //获取数据
                    List<CarEntity.DataBean> dataBeen = carEntity.getData();
                    for (int i=0;i<dataBeen.size();i++)
                    {
                        ParentBean parentBean=new ParentBean(dataBeen.get(i).getSellerName(),false,true);
                        parentList.add(parentBean);

                        List<ChildBean> childBeen=new ArrayList<ChildBean>();
                        List<CarEntity.DataBean.ListBean> listBeen = dataBeen.get(i).getList();
                        for(int j=0;j<listBeen.size();j++)
                        {
                            listBean = listBeen.get(j);
                            bean = new ChildBean(sp.getString("uid",""), listBean.getPid()+"", listBean.getTitle(), listBean.getNum(),false,(int) listBean.getPrice(),true, listBean.getImages().split("\\|")[0]);
                            childBeen.add(bean);
                        }
                        childList.add(childBeen);
                    }

                    adapter = new CartAdapter(getContext(), parentList, childList,GouwucheFragment.this);
                    expandableListView.setAdapter(adapter);

                    expandableListView.setGroupIndicator(null);
                    for (int i = 0; i < adapter.getGroupCount(); i++) {
                        expandableListView.expandGroup(i);
                    }
                }
            });
        }
        else
        {
            //显示购物车为空
            gouwuche_tv.setVisibility(View.VISIBLE);
            expandableListView.setVisibility(View.GONE);
        }
    }
    private void initView(View view) {
        mTopbar = (TopBar) view.findViewById(R.id.gouwuche_topbar);
        expandableListView = (ExpandableListView) view.findViewById(R.id.gouwuche_expanded);
        gouwuche_footer_check = (CheckBox) view.findViewById(R.id.gouwuche_footer_check);
        gouwuche_footer_jiesuan = (TextView) view.findViewById(R.id.gouwuche_footer_jiesuan);
        gouwuche_footer_jiesuan.setOnClickListener(this);
        gouwuche_footer_price = (TextView) view.findViewById(R.id.gouwuche_footer_price);
        gouwuche_footer_heji = (TextView) view.findViewById(R.id.gouwuche_footer_heji);
        gouwuche_tv= (TextView) view.findViewById(R.id.gouwuche_tv);
    }
    private void createEvent() {
        mTopbar.setOnTopBarClickListener(new TopBar.TopBarClickListener() {
            @Override
            public void leftClick() {
                //ToastUtil.show(getContext(), "扫一扫");
            }
            @Override
            public void rightClick() {
               // ToastUtil.show(getContext(), "消息中心功能敬请期待");
            }
        });
        //设置选中监听去实现全选
        gouwuche_footer_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true)
                {
                    adapter.allCheck(true);
                }
            }
        });
        //设置点击监听去实现反选
        gouwuche_footer_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取购物车的CheckBox的选中状态
                boolean isCheck=gouwuche_footer_check.isChecked();
                if(!isCheck)
                {
                    adapter.allCheck(false);
                }
            }
        });
    }
    /**
     * 结算
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.gouwuche_footer_jiesuan:
//                Intent intent=new Intent(getContext(), PayDemoActivity.class);
//                startActivity(intent);
                //创建订单
                Map<String,String> map=new HashMap<>();
                map.put("uid",uid);
                map.put("price",listBean.getPrice()+"");
                OkHttp3Utils.doPost(API.CREATE_API, null,map, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {

                    }
                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String result = response.body().string();
                        System.out.println("创建订单=========="+result);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "订单创建成功", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                break;
        }
    }
    //修改全选按钮的状态
    @Override
    public void changeCheckBtn(boolean flag) {
        gouwuche_footer_check.setChecked(flag);
    }
    //计算总价的方法
    @Override
    public void addPrice() {
        //初始化总价
        sum=0;
        int count=0;
        //遍历所有的子集合
        for(int i=0;i<adapter.getGroupCount();i++)
        {
            for (int j=0;j<adapter.getChildrenCount(i);j++)
            {
                ChildBean child=adapter.getChild(i,j);
                //如果该对象被选中,则加上这个对象中的价钱
                if(child.isCheck)
                {
                    sum+=child.price*child.saleNum;
                    count+=child.saleNum;
                }
            }
        }
        //得到总价,更新UI控件
        gouwuche_footer_price.setText(sum+"");
        gouwuche_footer_jiesuan.setText("结算"+"("+count+")");
    }
    @Override
    public void delete(String uid,String pid) {
        //删除的接口回调
        HashMap<String,String> params=new HashMap<>();
        params.put("uid",uid);
        params.put("pid",pid);
        OkHttp3Utils.doPost(API.DELCART_API, null, params, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String json=response.body().string();
                parentList.clear();
                childList.clear();
                initData();
            }
        });
    }
}