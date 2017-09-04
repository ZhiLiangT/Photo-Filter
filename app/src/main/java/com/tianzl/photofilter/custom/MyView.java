package com.tianzl.photofilter.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.opengl.Matrix;
import android.util.DisplayMetrics;
import android.view.View;

import com.tianzl.photofilter.R;

/**
 * Created by tianzl on 2017/8/29.
 */

public class MyView extends View {
    private Bitmap mBitmap;
    private Matrix mMatrix = new Matrix();

    private static int mScreenWidth;
    private static int mScreenHeight;

    public MyView(Context context) {
        super(context);
        initialize();
    }

    private void initialize() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        mScreenWidth = dm.widthPixels;
        mScreenHeight = dm.heightPixels;

        Bitmap bmp = ((BitmapDrawable)getResources().getDrawable(R.drawable.link)).getBitmap();
        mBitmap = Bitmap.createScaledBitmap(bmp, mScreenWidth, mScreenHeight, true);
    }

    @Override protected void onDraw(Canvas canvas) {
//      super.onDraw(canvas);  //当然，如果界面上还有其他元素需要绘制，只需要将这句话写上就行了。
        canvas.drawBitmap(mBitmap, 0, 0, null);
    }

}
