package com.example.zkw.blindview;

import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

public class MyViewPagerAdapter extends PagerAdapter {
    private ArrayList<View> views;
    public static String text1;
    public static String text2;
    public static String text3;
    public static String text4;
    public static String text5;
    public static String text6;
    public static String text7;

    public void setViews(ArrayList<View> views){
        this.views = views;
    }
    @Override
    public int getCount() {
        return views.size();
    }
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(views.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(views.get(position));
        /*
        switch (position){
            case 12:
                Button last = (Button)views.get(12).findViewById(R.id.last);
                Button next = (Button)views.get(12).findViewById(R.id.next);
                last.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(),views.get(1).getContext().getClass());
                        view.getContext().startActivity(intent);
                    }
                });
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(),ResultActivity.class);
                        view.getContext().startActivity(intent);
                    }
                });
                break;
                default:
                    break;
        }
        */
        switch (position){
            case 1:
                RadioGroup group1 = (RadioGroup)views.get(1).findViewById(R.id.group1);
                group1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        RadioButton radio1 = (RadioButton)views.get(1).findViewById(radioGroup.getCheckedRadioButtonId());
                        text1 = radio1.getText().toString();
                    }
                });
                break;
            case 2:
                RadioGroup group2 = (RadioGroup)views.get(2).findViewById(R.id.group2);
                group2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        RadioButton radio2 = (RadioButton)views.get(2).findViewById(radioGroup.getCheckedRadioButtonId());
                        text2 = radio2.getText().toString();
                    }
                });
                break;
            case 3:
                RadioGroup group3 = (RadioGroup)views.get(3).findViewById(R.id.group3);
                group3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        RadioButton radio3 = (RadioButton)views.get(3).findViewById(radioGroup.getCheckedRadioButtonId());
                        text3 = radio3.getText().toString();
                    }
                });
                break;
            case 4:
                RadioGroup group4 = (RadioGroup)views.get(4).findViewById(R.id.group4);
                group4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        RadioButton radio4 = (RadioButton)views.get(4).findViewById(radioGroup.getCheckedRadioButtonId());
                        text4 = radio4.getText().toString();
                    }
                });
                break;
            case 5:
                RadioGroup group5 = (RadioGroup)views.get(5).findViewById(R.id.group5);
                group5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        RadioButton radio5 = (RadioButton)views.get(5).findViewById(radioGroup.getCheckedRadioButtonId());
                        text5 = radio5.getText().toString();
                    }
                });
                break;
            case 6:
                RadioGroup group6 = (RadioGroup)views.get(6).findViewById(R.id.group6);
                group6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        RadioButton radio6 = (RadioButton)views.get(6).findViewById(radioGroup.getCheckedRadioButtonId());
                        text6 = radio6.getText().toString();
                    }
                });
                break;
            case 7:
                RadioGroup group7 = (RadioGroup)views.get(7).findViewById(R.id.group7);
                group7.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int i) {
                        RadioButton radio7 = (RadioButton)views.get(7).findViewById(radioGroup.getCheckedRadioButtonId());
                        text7 = radio7.getText().toString();
                    }
                });
                Button last = (Button)views.get(7).findViewById(R.id.last);
                Button next = (Button)views.get(7).findViewById(R.id.next);
                last.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(),views.get(1).getContext().getClass());
                        view.getContext().startActivity(intent);
                    }
                });
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(view.getContext(),ResultActivity.class);
                        view.getContext().startActivity(intent);
                    }
                });
                break;
            default:
                break;
        }
        return views.get(position);
    }

}
