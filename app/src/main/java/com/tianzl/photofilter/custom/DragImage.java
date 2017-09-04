package com.tianzl.photofilter.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by tianzl on 2017/8/29.
 */

public class DragImage extends SurfaceView implements SurfaceHolder.Callback2 {

    private Context context;
    private SurfaceHolder holder;
    private Bitmap bitmap;

    public DragImage(Context context) {
        super(context);
        init(context);
    }

    public DragImage(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public DragImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    private void init(Context context) {
        this.context=context;
        holder=this.getHolder();
        holder.addCallback(this);
    }
    public void setImg(Bitmap bitmap){
        this.bitmap=bitmap;
    }

    @Override
    public void surfaceRedrawNeeded(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
