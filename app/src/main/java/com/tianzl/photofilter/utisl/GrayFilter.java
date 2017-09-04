package com.tianzl.photofilter.utisl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

/**
 * Created by tianzl on 2017/8/29.
 */

public class GrayFilter {
    //宝丽来彩色
    private static final float[] arr0 = {
            1.438f, -0.062f, -0.062f, 0, 0,
            -0.122f, 1.378f, -0.122f, 0, 0,
            -0.016f, -0.016f, 1.483f, 0, 0,
            -0.03f, 0.05f, -0.02f, 1, 0};

    //怀旧效果
    private static final float[] arr1 = {
            0.393f,0.769f,0.189f,0,0,
            0.349f,0.686f,0.168f,0,0,
            0.272f,0.534f,0.131f,0,0,
            0,0,0,1,0};
    //泛红
    private static final float[] arr2={
            2,0,0,0,0,
            0,1,0,0,0,
            0,0,1,0,0,
            0,0,0,1,0
        };
    //泛绿（荧光绿）
    private static final  float[] arr3={
            1,0,0,0,0,
            0,1.4f,0,0,0,
            0,0,1,0,0,
            0,0,0,1,0
    };
    //泛蓝（宝石蓝）
    private static final float[] arr4={
            1,0,0,0,0,
            0,1,0,0,0,
            0,0,1.6f,0,0,
            0,0,0,1,0
    };
    //泛黄（把红色 跟  绿色分量都加50）
    private static final float[] arr5={
            1,0,0,0,50,
            0,1,0,0,50,
            0,0,1,0,0,
            0,0,0,1,0
    };
    //灰度效果
    private static final float[] arr6={
            0.33F ,0.59F ,0.11F ,0F ,0F,
            0.33F ,0.59F ,0.11F ,0F, 0F,
            0.33F ,0.59F ,0.11F ,0F, 0F,
            0F, 0F, 0F, 1F, 0F
    };
    //图像翻转
    private static final float[] arr7={
            -1F, 0F, 0F, 1F,1F,
            0F ,-1F, 0F ,1F, 1F,
            0F , 0F, -1 ,1F, 1F,
            0F , 0F, 0F ,1F, 0F
    };
    //高饱和度.
    private static final float[] arr8={
            1.438F, -0.122F, -0.016F, 0F, -0.03F,
            -0.062F, 1.378F,-0.016F, 0F,0.05F,
            -0.062F, -0.122F, 1.483F,0F,-0.02F,
            0F, 0F, 0F, 1F, 0F
    };
    public static Bitmap changeToGray(Bitmap bitmap,int type){
        int width,height;
        width=bitmap.getWidth();
        height=bitmap.getHeight();
        Bitmap grayBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
        Canvas canvas = new Canvas(grayBitmap);
        Paint paint = new Paint();
        paint.setAntiAlias(true); // 设置抗锯齿
        //二，把饱和度设置为0 就可以得到灰色（黑白)的图片
        ColorMatrix colorMatrix = new ColorMatrix();
        switch (type){
            case 0:
                colorMatrix.set(arr0);
                break;
            case 1:
                colorMatrix.set(arr1);
                break;
            case 2:
                colorMatrix.set(arr2);
                break;
            case 3:
                colorMatrix.set(arr3);
                break;
            case 4:
                colorMatrix.set(arr4);
                break;
            case 5:
                colorMatrix.set(arr5);
                break;
        }
       // colorMatrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(filter);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        return grayBitmap;
    }
    public static void setColorMatrix(float[] colorMatrix){

    }


}
