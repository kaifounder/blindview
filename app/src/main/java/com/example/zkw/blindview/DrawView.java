package com.example.zkw.blindview;

/*
* 画板的各种属性设置
* */

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DrawView extends View {
    private static int model = 1;
    private int view_width = 0;     //屏幕的宽度
    private int view_heigth = 0;
    private float preX;      //起始点x坐标值
    private float preY;
    private Path path;
    private Paint mEraserPaint;
    public static Paint paint = null;   //画笔
    Bitmap cacheBitmap = null;  //定义一个内存中的图片，该图片作为缓冲区
    Canvas cacheCanvas = null;

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        view_width = context.getResources().getDisplayMetrics().widthPixels;
        view_heigth = context.getResources().getDisplayMetrics().heightPixels;
        initPaint();
    }

    public DrawView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs,defStyle);
    }
    public DrawView(Context context){
        super(context);
        initPaint();
    }

    private void initPaint(){
        cacheBitmap = Bitmap.createBitmap(view_width, view_heigth, Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas();
        cacheCanvas.setBitmap(cacheBitmap);
        path = new Path();
        paint = new Paint();
        paint.setColor(Color.BLACK);//设置默认的画笔颜色
        //设置画笔风格
        paint.setStyle(Paint.Style.STROKE);//填充且描边
        paint.setStrokeJoin(Paint.Join.ROUND);  //设置笔刷的图形样式
        paint.setStrokeCap(Paint.Cap.ROUND);    //设置画笔转弯处的连接风格
        paint.setStrokeWidth(5);   //设置默认笔触的宽度为5像素
        paint.setAntiAlias(true);   //使用抗锯齿
        paint.setDither(true);     //使用抖动效果
        //橡皮擦
        mEraserPaint = new Paint();
        mEraserPaint.setAlpha(0);
        //这个属性是设置paint为橡皮擦重中之重
        //这是重点
        //下面这句代码是橡皮擦设置的重点
        mEraserPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        //上面这句代码是橡皮擦设置的重点（重要的事是不是一定要说三遍）
        mEraserPaint.setAntiAlias(true);
        mEraserPaint.setDither(true);
        mEraserPaint.setStyle(Paint.Style.STROKE);
        mEraserPaint.setStrokeJoin(Paint.Join.ROUND);
        mEraserPaint.setStrokeWidth(30);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //canvas.drawColor(Color.WHITE);
        //Paint bmpPaint = new Paint();
        canvas.drawBitmap(cacheBitmap,0 ,0, paint);
        if (model == 1){
            cacheCanvas.drawPath(path, paint);
        }else if (model == 0){
            cacheCanvas.drawPath(path, mEraserPaint);
        }
        canvas.save();
    }
    /*
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (cacheCanvas == null){
            cacheBitmap = Bitmap.createBitmap(view_width,view_heigth,Bitmap.Config.ARGB_8888);
            cacheCanvas.setBitmap(cacheBitmap);
        }
    }
    */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //获取触摸事件的发生位置
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                preX = x;
                preY = y;
                break;
            case MotionEvent.ACTION_MOVE:
                float dx = Math.abs(x - preX);
                float dy = Math.abs(y - preY);
                if (dx >= 5 || dy >= 5){
                    path.quadTo(preX, preY, (x + preX)/2, (y + preY)/2);
                    preX = x;
                    preY = y;
                }
                if (model == 1){
                    cacheCanvas.drawPath(path, paint);
                }else if (model == 0){
                    cacheCanvas.drawPath(path, mEraserPaint);
                }
                break;
            case MotionEvent.ACTION_UP:
                //cacheCanvas.drawPath(path, paint);
                path.reset();
                break;
            default:
                break;
        }
        invalidate();//刷新View
        return true;
    }
    //换背景
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setbackground(Bitmap bitmap){
        BitmapDrawable drawable1 = new BitmapDrawable(bitmap);
        setBackground(drawable1);
    }
    //橡皮擦功能
    public void clear(){
        /*将所绘制的图形的像素与Canvas中对应位置的像素按照一定规则进行混合
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        paint.setAlpha(0);
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(30);
        */
        model = 0;
    }
    //绘画功能
    public void hua(){
        model = 1;
        paint.setXfermode(null);
        paint.setColor(Color.BLACK);//设置默认的画笔颜色
        paint.setStrokeWidth(5);
    }
    //设置画笔颜色
    public static void change_color(int color_num){
        model = 1;
        paint.setXfermode(null);
        paint.setColor(color_num);//设置画笔颜色
    }
    //设置笔的大小
    public void pen_size(int pensize){
        paint.setStrokeWidth(pensize);
    }
    //设置橡皮的大小
    public void eraser_size(int eraser_size){
        mEraserPaint.setStrokeWidth(eraser_size);
    }
    //重置
    public void nothing(){
        cacheBitmap = null;
        cacheBitmap = Bitmap.createBitmap(view_width, view_heigth, Bitmap.Config.ARGB_8888);
        cacheCanvas = new Canvas();
        cacheCanvas.setBitmap(cacheBitmap);
        invalidate();
    }

    //保存画图  model==1，保存到相册  model==0，保存到APP目录
    public void save(int num, int model){
        try {
            saveBitmap("DrawingPicture","DrawingPicture_"+ num, model);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void saveBitmap(String dir, String fileName, int model) throws IOException {
        String dirPath;
        dirPath = getFilePath(getContext(), dir, fileName, model);
        File file = new File(dirPath);//创建文件对象
        try {
            file.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            cacheBitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);//将绘图内容压缩成png格式图片
            fileOutputStream.flush();
            fileOutputStream.close();
            Toast.makeText(getContext(),"成功保存到"+dirPath, Toast.LENGTH_SHORT).show();
        }catch (IOException e){
            Toast.makeText(getContext(),e.toString(), Toast.LENGTH_SHORT).show();
        }
        //发送广播，更新图库
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri uri = Uri.fromFile(file);
        intent.setData(uri);
        getContext().sendBroadcast(intent);
    }

    public String getFilePath(Context context, String dir, String fileName, int model) {
        String dirPath = "";
        if (model == 0){
            //判断SD卡是否可用
            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())){
                dirPath = context.getExternalFilesDir(dir).getAbsolutePath() +
                        File.separator + fileName + ".png";
            }else {
                //没存储卡，就存到机身内部
                dirPath = context.getFilesDir().getAbsolutePath() + File.separator + dir
                        + File.separator + fileName + ".png";
            }
        }else if (model == 1){
            dirPath = Environment.getExternalStorageDirectory() + File.separator +
                    Environment.DIRECTORY_DCIM + File.separator + "Camera" +
                    File.separator + fileName + ".png";
        }
        return dirPath;
    }
}
