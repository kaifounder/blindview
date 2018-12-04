package com.example.zkw.blindview;

/*
 * 新型算法，根据不同的图片获取不同的灰度化的值
 * @param num 选择的结果
 * */

import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;

import java.util.Random;

public class GrayChoose {
    private double[][] W = {
            {0,0,1.0000},
            {0,0.1000,0.9000},
            {0,0.2000,0.8000},
            {0,0.3000,0.7000},
            {0,0.4000,0.6000},
            {0,0.5000,0.5000},
            {0,0.6000,0.4000},
            {0,0.7000,0.3000},
            {0,0.8000,0.2000},
            {0,0.9000,0.1000},
            {0,1.0000,0},
            {0.1000,0,0.9000},
            {0.1000,0.1000,0.8000},
            {0.1000,0.2000,0.7000},
            {0.1000,0.3000,0.6000},
            {0.1000,0.4000,0.5000},
            {0.1000,0.5000,0.4000},
            {0.1000,0.6000,0.3000},
            {0.1000,0.7000,0.2000},
            {0.1000,0.8000,0.1000},
            {0.1000,0.9000,0},
            {0.2000,0,0.8000},
            {0.2000,0.1000,0.7000},
            {0.2000,0.2000,0.6000},
            {0.2000,0.3000,0.5000},
            {0.2000,0.4000,0.4000},
            {0.2000,0.5000,0.3000},
            {0.2000,0.6000,0.2000},
            {0.2000,0.7000,0.1000},
            {0.2000,0.8000,0},
            {0.3000,0,0.7000},
            {0.3000,0.1000,0.6000},
            {0.3000,0.2000,0.5000},
            {0.3000,0.3000,0.4000},
            {0.3000,0.4000,0.3000},
            {0.3000,0.5000,0.2000},
            {0.3000,0.6000,0.1000},
            {0.3000,0.7000,0.0000},
            {0.4000,0,0.6000},
            {0.4000,0.1000,0.5000},
            {0.4000,0.2000,0.4000},
            {0.4000,0.3000,0.3000},
            {0.4000,0.4000,0.2000},
            {0.4000,0.5000,0.1000},
            {0.4000,0.6000,0.0000},
            {0.5000,0,0.5000},
            {0.5000,0.1000,0.4000},
            {0.5000,0.2000,0.3000},
            {0.5000,0.3000,0.2000},
            {0.5000,0.4000,0.1000},
            {0.5000,0.5000,0},
            {0.6000,0,0.4000},
            {0.6000,0.1000,0.3000},
            {0.6000,0.2000,0.2000},
            {0.6000,0.3000,0.1000},
            {0.6000,0.4000,0.0000},
            {0.7000,0,0.3000},
            {0.7000,0.1000,0.2000},
            {0.7000,0.2000,0.1000},
            {0.7000,0.3000,0.0000},
            {0.8000,0,0.2000},
            {0.8000,0.1000,0.1000},
            {0.8000,0.2000,0.0000},
            {0.9000,0,0.1000},
            {0.9000,0.1000,0.0000},
            {1.0000,0,0}
    };
    private Random random = new Random();
    //像素点
    private int grey;
    private int ggrey;
    //像素点的rgb值
    private int red;
    private int green;
    private int blue;
    //另一个像素点的rgb值
    private int gred;
    private int ggreen;
    private int gblue;
    //两个像素点rgb的相差
    private int dred;
    private int dgreen;
    private int dblue;
    //像素点rgb平方和开根
    private double[] squareint;
    //进行最后运算的结果
    private double[][] finallyint;

    private int[] pixels;
    private int[] gpixels;
    //66种情况所对应的所有像素点灰度化之后的值 width*height  66
    private int[][] groupgrey;
    //每一种情况对应的所有像素点的运算结果进行累加
    private double[] sum;
    //最大的一种情况的角标
    private int num = 0;

    private int xwidth = 64;
    private int xheight = 64;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public int getw(Bitmap bitmap){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        pixels = new int[xwidth*xheight];
        groupgrey = new int[xwidth*xheight][66];
        finallyint = new double[xwidth*xheight][66];
        squareint = new double[xwidth*xheight];
        sum = new double[66];

        for (int i = 0; i < xheight; i ++){
            for (int j = 0; j < xwidth; j ++){
                pixels[xwidth*i+j] = bitmap.getPixel(width*j/64,height*i/64);
            }
        }

        gpixels = pixels.clone();
        for (int i = 0; i < gpixels.length; i++){ //随机排序像素数组
            int p = random.nextInt(i+1);
            int tmp = gpixels[i];
            gpixels[i] = gpixels[p];
            gpixels[p] = tmp;
        }

        for (int i = 0;i < xheight; i++){
            for (int j = 0; j < xwidth; j++){
                grey = pixels[xwidth*i+j];
                ggrey = gpixels[xwidth*i+j];
                red = ((grey  & 0x00FF0000 ) >> 16);
                green = ((grey & 0x0000FF00) >> 8);
                blue = (grey & 0x000000FF);

                gred = ((ggrey  & 0x00FF0000 ) >> 16);
                ggreen = ((ggrey & 0x0000FF00) >> 8);
                gblue = (ggrey & 0x000000FF);

                dred = Math.abs(red-gred);
                dblue = Math.abs(blue-gblue);
                dgreen = Math.abs(green-ggreen);
                //squareint[width*i+j] = Math.sqrt(dred*dred + dgreen*dgreen + dblue*dblue);
                for (int k = 0; k < 66; k++){
                    groupgrey[xwidth*i+j][k] = (int)((float) dred * W[k][0] + (float)dgreen * W[k][1] + (float)dblue * W[k][2]);
                    squareint[xwidth*i+j] = Math.sqrt(dred*dred + dgreen*dgreen + dblue*dblue);
                    //finallyint[width*i/2+j][k] = Math.abs(groupgrey[width*i/2+j][k] - squareint[width*i/2+j]);
                    finallyint[xwidth*i+j][k] = Math.exp(-0.2*Math.abs(Math.log(Math.abs(groupgrey[xwidth*i+j][k])/(squareint[xwidth*i+j]+0.07))));
                    sum[k] += finallyint[xwidth*i+j][k];
                }
            }
        }
        double max = sum[0];
        for (int i =0; i < 66; i++){
            if (max < sum[i]){
                max = sum[i];
                num = i;
            }
        }
        return num;
    }
}
