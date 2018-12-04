package com.example.zkw.blindview;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int UPDATE_PICTURE = 1;

    private static final String TAG = "MainActivity.TAG";
    public LinearLayout firstLinearLayout;
    public LinearLayout secondLinearLayout;
    public LinearLayout threeLinearLayout;
    MyViewPager mViewPager;
    ViewPagerFragmentAdapter mViewPagerFragmentAdapter;
    FragmentManager mFragmentManager;
    List<Fragment> mFragmentList = new ArrayList<Fragment>();
    private long mExitTime;

    private Fragment picture_draw;
    private Fragment paint_draw;
    private Fragment gray_draw;

    private Bitmap grayBitmap;

    private DealPicture dealPicture = new DealPicture();
    /*
    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case UPDATE_PICTURE:
                    if (PaintFragment.bitmap != null){
                        grayBitmap = dealPicture.getGray(PaintFragment.bitmap);
                    }
                    break;
                default:
                    break;
            }
        }
    };
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.addActivity(this);
        //grayBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.text1);
        mFragmentManager = getSupportFragmentManager();
        setContentView(R.layout.activity_main);
        initFragmetList();
        mViewPagerFragmentAdapter = new ViewPagerFragmentAdapter(mFragmentManager,mFragmentList);
        initView();
        initViewPager();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void initViewPager() {
        mViewPager.addOnPageChangeListener(new ViewPagetOnPagerChangedLisenter());
        mViewPager.setAdapter(mViewPagerFragmentAdapter);
        mViewPager.setCurrentItem(1);
        updateBottomLinearLayoutSelect(true,false,false);
    }

    public void initFragmetList() {
        picture_draw = new PictureFragment();
        paint_draw = new PaintFragment();
        gray_draw = new GrayFragment();
        mFragmentList.add(picture_draw);
        mFragmentList.add(paint_draw);
        mFragmentList.add(gray_draw);
    }

    public void initView() {
        mViewPager = (MyViewPager) findViewById(R.id.ViewPagerLayout);
        firstLinearLayout = (LinearLayout) findViewById(R.id.firstLinearLayout);
        firstLinearLayout.setOnClickListener(this);
        secondLinearLayout = (LinearLayout) findViewById(R.id.secondLinearLayout);
        secondLinearLayout.setBackgroundColor(Color.GRAY);
        secondLinearLayout.setOnClickListener(this);
        threeLinearLayout = (LinearLayout) findViewById(R.id.threeLinearLayout);
        threeLinearLayout.setOnClickListener(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.firstLinearLayout:
                firstLinearLayout.setBackgroundColor(Color.GRAY);
                secondLinearLayout.setBackgroundColor(Color.WHITE);
                threeLinearLayout.setBackgroundColor(Color.WHITE);
                mViewPager.setCurrentItem(0);
                PictureFragment.draw_picture.setImageBitmap(PaintFragment.bitmap);
                updateBottomLinearLayoutSelect(true,false,false);
                break;
            case R.id.secondLinearLayout:
                firstLinearLayout.setBackgroundColor(Color.WHITE);
                secondLinearLayout.setBackgroundColor(Color.GRAY);
                threeLinearLayout.setBackgroundColor(Color.WHITE);
                mViewPager.setCurrentItem(1);
                updateBottomLinearLayoutSelect(false,true,false);
                /*
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message message = new Message();
                        message.what = UPDATE_PICTURE;
                        handler.sendMessage(message);
                    }
                }).start();
                */
                break;
            case R.id.threeLinearLayout:
                firstLinearLayout.setBackgroundColor(Color.WHITE);
                secondLinearLayout.setBackgroundColor(Color.WHITE);
                threeLinearLayout.setBackgroundColor(Color.GRAY);
                mViewPager.setCurrentItem(2);
                updateBottomLinearLayoutSelect(false,false,true);
                if (PaintFragment.bitmap != null){
                    grayBitmap = dealPicture.getGray(PaintFragment.bitmap);
                }
            if (grayBitmap != null){
                GrayFragment.draw_gray.setImageBitmap(grayBitmap);
            }
                break;
            default:
                break;
        }
    }
    private void updateBottomLinearLayoutSelect(boolean f, boolean s, boolean t) {
        firstLinearLayout.setSelected(f);
        secondLinearLayout.setSelected(s);
        threeLinearLayout.setSelected(t);
    }
    class ViewPagetOnPagerChangedLisenter implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//            Log.d(TAG,"onPageScrooled");
        }
        @Override
        public void onPageSelected(int position) {
            Log.d(TAG,"onPageSelected");
        }
        @Override
        public void onPageScrollStateChanged(int state) {
            Log.d(TAG,"onPageScrollStateChanged");
        }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Object mHelperUtils;
                Toast.makeText(this, "返回主菜单", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                ActivityManager.finishAll();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
