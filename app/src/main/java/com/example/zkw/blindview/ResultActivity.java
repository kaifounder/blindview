package com.example.zkw.blindview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {
    private Button backup;
    private Button cre;
    private TextView res;
    //private long mExitTime;

    private int redB = 0;
    private int greenB = 0;
    private int lightB = 0;
    private int severeB = 0;
    private int allB = 0;
    private int normal = 0;

    private String text1 = "";
    private String text2 = "";
    private String text3 = "";
    private String text4 = "";
    private String text5 = "";
    private String text6 = "";
    private String text7 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        ActivityManager.addActivity(this);
        res = (TextView)findViewById(R.id.result_text);
        backup = (Button)findViewById(R.id.backup);
        cre = (Button)findViewById(R.id.creat);
        backup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this,TestActivity.class);
                startActivity(intent);
            }
        });
        cre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

        if (MyViewPagerAdapter.text1 != null){
            text1 = MyViewPagerAdapter.text1;
        }
        if (MyViewPagerAdapter.text2 != null){
            text2 = MyViewPagerAdapter.text2;
        }
        if (MyViewPagerAdapter.text3 != null){
            text3 = MyViewPagerAdapter.text3;
        }
        if (MyViewPagerAdapter.text4 != null){
            text4 = MyViewPagerAdapter.text4;
        }
        if (MyViewPagerAdapter.text5 != null){
            text5 = MyViewPagerAdapter.text5;
        }
        if (MyViewPagerAdapter.text6 != null){
            text6 = MyViewPagerAdapter.text6;
        }
        if (MyViewPagerAdapter.text7 != null){
            text7 = MyViewPagerAdapter.text7;
        }

        switch (text1){
            case "6":
                normal++;break;
            case "5":
                redB++;greenB++;break;
            case "没有":
                lightB++;severeB++;break;
            default:
                break;
        }

        switch (text2){
            case "26":
                normal++;break;
            case "2":
                greenB++;break;
            case "6":
                redB++;break;
            default:
                break;
        }

        switch (text3){
            case "98":
                normal++;break;
            case "没有":
                allB++;break;
            default:
                break;
        }

        switch (text4){
            case "896":
                normal++;break;
            case "8":
                redB++;greenB++;severeB++;break;
            case "89":
                lightB++;break;
            default:
                break;
        }

        switch (text5){
            case "A":
               normal++;break;
            case "没有":
                redB++;break;
            default:
                break;
        }

        switch (text6){
            case "C":
                normal++;break;
            case "没有":
                greenB++;break;
            default:
                break;
        }

        switch (text7){
            case "牛":
                normal++;break;
            case "鹿":
                allB++;lightB++;severeB++;break;
            default:
                break;
        }

        if (normal >= 5){
            res.setText("正常");
        }else if (redB >= 3){
            res.setText("红色色盲");
        }else if (greenB >= 3){
            res.setText("绿色色盲");
        }else if (allB >= 2){
            res.setText("全色盲");
        }else if (lightB >= 2){
            res.setText("轻度色弱");
        }else if (severeB >= 2){
            res.setText("重度色弱");
        }else {
            res.setText("请重新检测");
        }
        //res.setText(text1 + "\n" + text2 + "\n" + text3 + "\n" + text4 + "\n" +text5 + "\n" +text6 + "\n" + text7 + "\n" );
        //res.setText(normal+"\n"+redB+"\n"+greenB+"\n"+lightB+"\n"+severeB+"\n"+allB+"\n");
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ActivityManager.finishAll();
            /*
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Object mHelperUtils;
                Toast.makeText(this, "返回主菜单", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                ActivityManager.finishAll();
            }
            */
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
