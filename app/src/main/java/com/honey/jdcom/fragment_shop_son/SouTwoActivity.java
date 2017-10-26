package com.honey.jdcom.fragment_shop_son;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.honey.jdcom.R;

public class SouTwoActivity extends AppCompatActivity {
    private EditText head_sou_two;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sou_two);
        initView();
    }
    private void initView() {
        head_sou_two= (EditText) findViewById(R.id.head_sou_two);
    }
    public void sou_two_sousuo(View view){
        Intent intent=new Intent(SouTwoActivity.this,SouActivity.class);
        intent.putExtra("name",head_sou_two.getText().toString());
        startActivity(intent);
    }
    public void fanhui(View v){
        finish();
    }
}
