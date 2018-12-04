package com.example.zkw.blindview;

        import android.app.Activity;
        import android.content.Intent;
        import android.support.v4.view.ViewPager;
        import android.os.Bundle;
        import android.view.KeyEvent;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.widget.EditText;
        import android.widget.Toast;

        import java.util.ArrayList;

public class TestActivity extends Activity {
    private long mExitTime;
    //private EditText pagenum;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityManager.addActivity(this);
        setContentView(R.layout.activity_test);
        //pagenum = (EditText)findViewById(R.id.pagenum);
        View view0 = LayoutInflater.from(this).inflate(R.layout.layout0, null);
        View view1 = LayoutInflater.from(this).inflate(R.layout.layout1, null);
        View view2 =LayoutInflater.from(this).inflate(R.layout.layout2, null);
        View view3 =LayoutInflater.from(this).inflate(R.layout.layout3, null);
        View view4 =LayoutInflater.from(this).inflate(R.layout.layout4, null);
        View view5 =LayoutInflater.from(this).inflate(R.layout.layout5, null);
        View view6 =LayoutInflater.from(this).inflate(R.layout.layout6, null);
        View view7 =LayoutInflater.from(this).inflate(R.layout.layout7, null);
        ArrayList<View> views = new ArrayList<View>();
        views.add(view0);
        views.add(view1);
        views.add(view2);
        views.add(view3);
        views.add(view4);
        views.add(view5);
        views.add(view6);
        views.add(view7);
        initViewPager(views);
    }

    private void initViewPager(final ArrayList<View> views){
        ViewPager viewPager =(ViewPager)findViewById(R.id.viewpager);
        MyViewPagerAdapter adapter = new MyViewPagerAdapter();
        adapter.setViews(views);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0){
                    Intent intent = new Intent(views.get(position).getContext(), WelcomeActivity.class);
                    views.get(position).getContext().startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            ActivityManager.finishAll();
            /*
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Object mHelperUtils;
                Toast.makeText(this, "再按一次返回首界面", Toast.LENGTH_SHORT).show();
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
