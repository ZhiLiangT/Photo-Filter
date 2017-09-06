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

import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by tianzl on 2017/8/31.
 */

public class PaletteImageView extends SimpleDraweeView {

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
    private static final int MAX_CACHE_STEP = 20;
    private PaletteView.Callback mCallback;
    private boolean mCanEraser;
    private List<DrawingInfo> mDrawingList;
    private List<DrawingInfo> mRemovedList;

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
        try {
            super.onDraw(canvas);
        } catch (Exception e) {
        }
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
                    saveDrawingPath();
                }
                mPath.reset();
                break;
        }
        return true;
    }
    //保存绘制路径
    private void saveDrawingPath(){
        if (mDrawingList == null) {
            mDrawingList = new ArrayList<>(MAX_CACHE_STEP);
        } else if (mDrawingList.size() == MAX_CACHE_STEP) {
            mDrawingList.remove(0);
        }
        Path cachePath = new Path(mPath);
        Paint cachePaint = new Paint(paint);
        PathDrawingInfo info = new PathDrawingInfo();
        info.path = cachePath;
        info.paint = cachePaint;
        mDrawingList.add(info);
        mCanEraser = true;
        if (mCallback != null) {
            mCallback.onUndoRedoStatusChanged();
        }
    }
    private abstract static class DrawingInfo {
        Paint paint;
        abstract void draw(Canvas canvas);
    }
    public void clearList(){
        if (mDrawingList!=null){
            mDrawingList.clear();
        }
        if (mRemovedList!=null){
            mRemovedList.clear();
        }
    }

    // 撤销
    public void undo() {
        int size = mDrawingList == null ? 0 : mDrawingList.size();
        if (size > 0) {
            DrawingInfo info = mDrawingList.remove(size - 1);
            if (mRemovedList == null) {
                mRemovedList = new ArrayList<>(MAX_CACHE_STEP);
            }
            if (size == 1) {
                mCanEraser = false;
            }
            mRemovedList.add(info);
            reDraw();
            if (mCallback != null) {
                mCallback.onUndoRedoStatusChanged();
            }
        }
    }
    //反撤销
    public void redo() {
        int size = mRemovedList == null ? 0 : mRemovedList.size();
        if (size > 0) {
            DrawingInfo info = mRemovedList.remove(size - 1);
            mDrawingList.add(info);
            mCanEraser = true;
            reDraw();
            if (mCallback != null) {
                mCallback.onUndoRedoStatusChanged();
            }
        }
    }
    private void reDraw(){
        if (mDrawingList != null) {
            mBufferBitmap.eraseColor(Color.TRANSPARENT);
            for (DrawingInfo drawingInfo : mDrawingList) {
                drawingInfo.draw(canvas);
            }
            invalidate();
        }
    }
    private static class PathDrawingInfo extends DrawingInfo {

        Path path;
        @Override
        void draw(Canvas canvas) {
            canvas.drawPath(path, paint);
        }
    }
    public void drawText(String content,int size,int color,float x,float y){
        Paint paint=new Paint();
        paint.setColor(color);
        paint.setTextSize(size);
        canvas.drawText(content,x,y,paint);

    }

    public interface Callback {
        void onUndoRedoStatusChanged();
    }

    public void setCallback(PaletteView.Callback callback){
        mCallback = callback;
    }

}
