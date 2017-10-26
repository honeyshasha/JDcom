package com.honey.jdcom.main;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.honey.jdcom.R;

public class QiDongActivity extends AppCompatActivity {
    private ImageView image_view;
    private int time=3;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            time--;
            if(time==0){
                Intent intent=new Intent(QiDongActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
            handler.sendEmptyMessageDelayed(100,2000);
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qidong);
        handler.sendEmptyMessageDelayed(100,2000);
        //初始化控件
        initView();
    }
    /**
     * 获取控件的方法
     */
    private void initView() {
        image_view= (ImageView) findViewById(R.id.image_view);
    }
}
