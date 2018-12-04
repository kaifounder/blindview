package com.example.zkw.blindview;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ColorChooseActivity extends AppCompatActivity {

    private TextView choose_color;
    private Button submit_color;
    public int color_s;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_choose);
        Resources res = getResources();
        Drawable drawable = res.getDrawable(R.color.transparent);
        this.getWindow().setBackgroundDrawable(drawable);

        choose_color = (TextView)findViewById(R.id.color_choose);
        submit_color = (Button)findViewById(R.id.submit_color);

        submit_color.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DrawView.change_color(color_s);
                finish();
            }
        });

        ColorPickerView cpv=(ColorPickerView)this.findViewById(R.id.cpv);
        cpv.setOnColorChangedListenner(new ColorPickerView.OnColorChangedListener() {
            /**
             * 手指抬起，选定颜色时
             */
            @Override
            public void onColorChanged(int r, int g, int b) {
                if(r==0 && g==0 && b==0){
                    return;
                }
            }

            /**
             * 颜色移动的时候
             */
            @Override
            public void onMoveColor(int r, int g, int b) {
                if(r==0 && g==0 && b==0){
                    return;
                }
                color_s = Color.argb(255,r,g,b);
                choose_color.setBackgroundColor(Color.argb(255,r,g,b));
            }
        });
    }
}
