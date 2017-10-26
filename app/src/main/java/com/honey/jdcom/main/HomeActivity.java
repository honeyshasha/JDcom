package com.honey.jdcom.main;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;

import com.honey.jdcom.R;
import com.honey.jdcom.fragment.FragmentClassify;
import com.honey.jdcom.fragment.FragmentDiscovery;
import com.honey.jdcom.fragment.FragmentHome;
import com.honey.jdcom.fragment.FragmentMe;
import com.honey.jdcom.fragment.GouwucheFragment;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private FrameLayout frame;
    private RadioButton but_shou;
    private RadioButton but_fen;
    private RadioButton but_fa;
    private RadioButton but_gou;
    private RadioButton but_wo;
    private FragmentHome fHome;
    private FragmentClassify fClassify;
    private FragmentDiscovery fDiscovery;
    private GouwucheFragment fShoppingCart;
    private FragmentMe fMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //初始化控件
        initView();
    }
    /**
     * 获取控件的方法
     */
    private void initView() {
        frame= (FrameLayout) findViewById(R.id.frame);
        but_shou= (RadioButton) findViewById(R.id.but_shou);
        but_fen= (RadioButton) findViewById(R.id.but_fen);
        but_fa= (RadioButton) findViewById(R.id.but_fa);
        but_gou= (RadioButton) findViewById(R.id.but_gou);
        but_wo= (RadioButton) findViewById(R.id.but_wo);
        //实例化fragment
        //主页
        fHome = new FragmentHome();
        //分类
        fClassify = new FragmentClassify();
        //发现
        fDiscovery = new FragmentDiscovery();
        //购物车
        fShoppingCart = new GouwucheFragment();
        //我的
        fMe = new FragmentMe();
        //展示fragment
        getSupportFragmentManager().beginTransaction().add(R.id.frame, fHome).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.frame, fClassify).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.frame, fDiscovery).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.frame, fShoppingCart).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.frame, fMe).commit();
        //默认展示的fragment
        getSupportFragmentManager().beginTransaction().show(fHome).commit();
        //隐藏其他
        getSupportFragmentManager().beginTransaction().hide(fClassify).commit();
        getSupportFragmentManager().beginTransaction().hide(fDiscovery).commit();
        getSupportFragmentManager().beginTransaction().hide(fShoppingCart).commit();
        getSupportFragmentManager().beginTransaction().hide(fMe).commit();
        //默认的字体和图片
        but_shou.setChecked(true);
        but_shou.setTextColor(Color.RED);
        but_fen.setChecked(false);
        but_fen.setTextColor(Color.BLACK);
        but_fa.setChecked(false);
        but_fa.setTextColor(Color.BLACK);
        but_gou.setChecked(false);
        but_gou.setTextColor(Color.BLACK);
        but_wo.setChecked(false);
        but_wo.setTextColor(Color.BLACK);
        //点击
        but_shou.setOnClickListener(this);
        but_fen.setOnClickListener(this);
        but_fa.setOnClickListener(this);
        but_gou.setOnClickListener(this);
        but_wo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.but_shou:
                getSupportFragmentManager().beginTransaction().show(fHome).commit();
                getSupportFragmentManager().beginTransaction().hide(fClassify).commit();
                getSupportFragmentManager().beginTransaction().hide(fDiscovery).commit();
                getSupportFragmentManager().beginTransaction().hide(fShoppingCart).commit();
                getSupportFragmentManager().beginTransaction().hide(fMe).commit();
                but_shou.setChecked(true);
                but_shou.setTextColor(Color.RED);
                but_fen.setChecked(false);
                but_fen.setTextColor(Color.BLACK);
                but_fa.setChecked(false);
                but_fa.setTextColor(Color.BLACK);
                but_gou.setChecked(false);
                but_gou.setTextColor(Color.BLACK);
                but_wo.setChecked(false);
                but_wo.setTextColor(Color.BLACK);
                break;
            case R.id.but_fen:
                getSupportFragmentManager().beginTransaction().hide(fHome).commit();
                getSupportFragmentManager().beginTransaction().show(fClassify).commit();
                getSupportFragmentManager().beginTransaction().hide(fDiscovery).commit();
                getSupportFragmentManager().beginTransaction().hide(fShoppingCart).commit();
                getSupportFragmentManager().beginTransaction().hide(fMe).commit();
                but_shou.setChecked(false);
                but_shou.setTextColor(Color.BLACK);
                but_fen.setChecked(true);
                but_fen.setTextColor(Color.RED);
                but_fa.setChecked(false);
                but_fa.setTextColor(Color.BLACK);
                but_gou.setChecked(false);
                but_gou.setTextColor(Color.BLACK);
                but_wo.setChecked(false);
                but_wo.setTextColor(Color.BLACK);
                break;
            case R.id.but_fa:
                getSupportFragmentManager().beginTransaction().hide(fHome).commit();
                getSupportFragmentManager().beginTransaction().hide(fClassify).commit();
                getSupportFragmentManager().beginTransaction().show(fDiscovery).commit();
                getSupportFragmentManager().beginTransaction().hide(fShoppingCart).commit();
                getSupportFragmentManager().beginTransaction().hide(fMe).commit();
                but_shou.setChecked(false);
                but_shou.setTextColor(Color.BLACK);
                but_fen.setChecked(false);
                but_fen.setTextColor(Color.BLACK);
                but_fa.setChecked(true);
                but_fa.setTextColor(Color.RED);
                but_gou.setChecked(false);
                but_gou.setTextColor(Color.BLACK);
                but_wo.setChecked(false);
                but_wo.setTextColor(Color.BLACK);
                break;
            case R.id.but_gou:
                getSupportFragmentManager().beginTransaction().hide(fHome).commit();
                getSupportFragmentManager().beginTransaction().hide(fClassify).commit();
                getSupportFragmentManager().beginTransaction().hide(fDiscovery).commit();
                getSupportFragmentManager().beginTransaction().show(fShoppingCart).commit();
                getSupportFragmentManager().beginTransaction().hide(fMe).commit();
                but_shou.setChecked(false);
                but_shou.setTextColor(Color.BLACK);
                but_fen.setChecked(false);
                but_fen.setTextColor(Color.BLACK);
                but_fa.setChecked(false);
                but_fa.setTextColor(Color.BLACK);
                but_gou.setChecked(true);
                but_gou.setTextColor(Color.RED);
                but_wo.setChecked(false);
                but_wo.setTextColor(Color.BLACK);
                break;
            case R.id.but_wo:
                getSupportFragmentManager().beginTransaction().hide(fHome).commit();
                getSupportFragmentManager().beginTransaction().hide(fClassify).commit();
                getSupportFragmentManager().beginTransaction().hide(fDiscovery).commit();
                getSupportFragmentManager().beginTransaction().hide(fShoppingCart).commit();
                getSupportFragmentManager().beginTransaction().show(fMe).commit();
                but_shou.setChecked(false);
                but_shou.setTextColor(Color.BLACK);
                but_fen.setChecked(false);
                but_fen.setTextColor(Color.BLACK);
                but_fa.setChecked(false);
                but_fa.setTextColor(Color.BLACK);
                but_gou.setChecked(false);
                but_gou.setTextColor(Color.BLACK);
                but_wo.setChecked(true);
                but_wo.setTextColor(Color.RED);
                break;
        }
    }
}
