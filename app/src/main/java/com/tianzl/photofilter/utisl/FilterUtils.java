package com.tianzl.photofilter.utisl;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.widget.ImageView;

/**
 * Created by tianzl on 2017/8/30.
 */

public class FilterUtils {
    /**
     * 为imageView设置颜色滤镜
     *
     * @param imageView
     * @param colormatrix
     */
    public static void imageViewColorFilter(ImageView imageView, float[] colormatrix) {
        setColorMatrixColorFilter(imageView, new ColorMatrixColorFilter(new ColorMatrix(colormatrix)));
    }

    /**
     * 为imageView设置颜色偏向滤镜
     *
     * @param imageView
     * @param color
     */
    public static void imageViewColorFilter(ImageView imageView, int color) {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setScale(Color.alpha(color), Color.red(color), Color.green(color), Color.blue(color));
        setColorMatrixColorFilter(imageView, new ColorMatrixColorFilter(colorMatrix));
    }

    /**
     * 生成对应颜色偏向滤镜的图片，并回收原图
     *
     * @param bitmap
     * @param color
     * @return
     */
    public static Bitmap bitmapColorFilter(Bitmap bitmap, int color) {
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setScale(Color.alpha(color), Color.red(color), Color.green(color), Color.blue(color));
        return setColorMatrixColorFilter(bitmap, new ColorMatrixColorFilter(colorMatrix), true);
    }

    /**
     * 生成对应颜色滤镜的图片，并回收原图
     *
     * @param bitmap
     * @param colormatrix
     * @return
     */
    public static Bitmap bitmapColorFilter(Bitmap bitmap, float[] colormatrix) {
        return setColorMatrix(bitmap, colormatrix, true);
    }

    /**
     * 生成对应颜色滤镜的图片
     *
     * @param bitmap
     * @param colormatrix
     * @param isRecycle
     * @return
     */
    public static Bitmap setColorMatrix(Bitmap bitmap, float[] colormatrix, boolean isRecycle) {
        return setColorMatrixColorFilter(bitmap, new ColorMatrixColorFilter(new ColorMatrix(colormatrix)), isRecycle);
    }


    public static void setColorMatrixColorFilter(ImageView imageView, ColorMatrixColorFilter matrixColorFilter) {
        imageView.setColorFilter(matrixColorFilter);
    }

    public static Bitmap setColorMatrixColorFilter(Bitmap bitmap, ColorMatrixColorFilter matrixColorFilter, boolean isRecycle) {
        Bitmap resource = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColorFilter(matrixColorFilter);
        Canvas canvas = new Canvas(resource);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        if (isRecycle)
            BitmapUtils.destroyBitmap(bitmap);
        return resource;
    }
}
