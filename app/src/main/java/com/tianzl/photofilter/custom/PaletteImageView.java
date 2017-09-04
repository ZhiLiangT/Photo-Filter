package com.tianzl.photofilter.custom;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.MotionEvent;


/**
 * Created by tianzl on 2017/8/31.
 */

public class PaletteImageView extends android.support.v7.widget.AppCompatImageView {

    private float mLastX;
    private float mLastY;
    private Paint paint;
    private Path mPath;
    private float paintSize;
    private float eraserSize;
    private Bitmap mBufferBitmap;
    private Bitmap bitmap;
    private Canvas canvas;
    private Xfermode xfermode;
    private boolean isSwitch;
    public enum Mode {
        DRAW,
        ERASER
    }

    private Mode mMode = Mode.DRAW;


    public PaletteImageView(Context context) {
        super(context);
    }

    public PaletteImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PaletteImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 初始化
     */
    private void init() {
        //初始化画笔
        paint=new Paint(Paint.ANTI_ALIAS_FLAG | Paint.DITHER_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setFilterBitmap(true);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(paintSize);
        //初始化画笔颜色
        paint.setColor(Color.BLACK);
        //初始化 画笔宽度和橡皮擦宽度
        paintSize=20;
        eraserSize=20;
        //所绘制的不会提交到画布上
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.CLEAR);
    }
    //画板的开关
    public void setSwitch(boolean isSwitch){
        this.isSwitch=isSwitch;
    }

    /**
     * 设置画笔的类型
     * @param mode 画笔or橡皮擦
     */
    public void setMode(Mode mode){
        if (mode!=null){
            mMode=mode;
            if (mMode==Mode.DRAW){
                paint.setXfermode(null);
            }else {
                paint.setXfermode(xfermode);
                paint.setStrokeWidth(eraserSize);
            }
        }
    }
    //获取图片
    public Bitmap buildBitmap() {
        Bitmap bm = getDrawingCache();
        Bitmap result = Bitmap.createBitmap(bm);
        destroyDrawingCache();
        return result;
    }

    private void initBuffer(){
        mBufferBitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        canvas = new Canvas(mBufferBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mBufferBitmap!=null){
            canvas.drawBitmap(mBufferBitmap,0,0,paint);
        }
    }
    //设置画笔颜色
    public void setColor(int  color){
        paint.setColor(color);
    }
    //设置画笔大小
    public void setSize(int size){
        paint.setStrokeWidth(size);
    }
    //清除
    public void clear() {
        if (mBufferBitmap != null) {
            mBufferBitmap.eraseColor(Color.TRANSPARENT);
            invalidate();
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //画笔开关
        if (!isSwitch){
            return true;
        }
        final int action = event.getAction() & MotionEvent.ACTION_MASK;
        final float x = event.getX();
        final float y = event.getY();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mLastX = x;
                mLastY = y;
                if (mPath == null) {
                    mPath = new Path();
                }
                mPath.moveTo(x,y);
                break;
            case MotionEvent.ACTION_MOVE:
                //这里终点设为两点的中心点的目的在于使绘制的曲线更平滑，
                // 如果终点直接设置为x,y，
                // 效果和lineto是一样的,实际是折线效果
                mPath.quadTo(mLastX, mLastY, (x + mLastX) / 2, (y + mLastY) / 2);
                if (mBufferBitmap == null) {
                    initBuffer();
                }
                if (mMode == Mode.ERASER) {
                    break;
                }
                canvas.drawPath(mPath,paint);
                invalidate();
                mLastX = x;
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                if (mMode == Mode.DRAW ) {
                }
                mPath.reset();
                break;
        }
        return true;
    }

}
