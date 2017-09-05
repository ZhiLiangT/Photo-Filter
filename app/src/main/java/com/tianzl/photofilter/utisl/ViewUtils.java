package com.tianzl.photofilter.utisl;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Point;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.TextAppearanceSpan;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

/**
 * Created by tianzl on 2017/9/1.
 */

public class ViewUtils {
    public static int BT_IMG_LOCA_LEFT=1;
    public static int BT_IMG_LOCA_RIGHT=2;
    public static int BT_IMG_LOCA_TOP=3;
    public static int BT_IMG_LOCA_BOTTOM=4;
    public static int WINDOW_WIDTH=5;
    public static int WINDOW_HEIGHT=6;
    public static int LEFT=7;
    public static int RIGHT=8;
    public static int TOP=9;
    public static int BOTTOM=10;
    /**
     * 获取屏幕的高度和宽度
     * type 宽或高
     */
    public static int getScreen(Context context, int type){
        WindowManager wm= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display=wm.getDefaultDisplay();
        Point size=new Point();
        display.getSize(size);
        if (type==WINDOW_WIDTH){
            return size.x;
        }else if (type==WINDOW_HEIGHT){
            return size.y;
        }
        return 0;
    }
    /**
     * 动态给View设置宽和高
     */
    public static void setViewWidthOrHeight(View view, int width, int height){
        ViewGroup.LayoutParams lp=view.getLayoutParams();
        lp.width=width;
        lp.height=height;
        view.setLayoutParams(lp);
    }
    /**
     * 获取View的内边距
     * type 左上右下
     */
    public static int getViewToMargin(View view,int type){
        ViewGroup.MarginLayoutParams layoutParams= (ViewGroup.MarginLayoutParams) view.getLayoutParams();
        int leftMargin=layoutParams.leftMargin;
        int rightMargin=layoutParams.rightMargin;
        int topMaigin=layoutParams.topMargin;
        int bottomMargin=layoutParams.bottomMargin;
        if (type==LEFT){
            return leftMargin;
        }else if (type==RIGHT){
            return rightMargin;
        }else if (type==TOP){
            return topMaigin;
        }else if (type==BOTTOM){
            return bottomMargin;
        }
        return 0;
    }
    /**
     * TextView显示不同大小的文字(只限从开始到某个位置为一种大小（包括分隔符），后面为一种大小)
     * context 上下文
     * view，控件
     * text 控件文本内容
     * st 分割符
     * start 开始字体Style
     * end 结束字体Style
     */
    public static void showDifferentText(Context context, TextView textView, String content, String fenge, int start, int end){
        SpannableString styledText = new SpannableString(content);
        styledText.setSpan(new TextAppearanceSpan(context, start), 0, content.indexOf(fenge)+1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        styledText.setSpan(new TextAppearanceSpan(context, end), content.indexOf(fenge)+1, content.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(styledText, TextView.BufferType.SPANNABLE);
    }
    //弹出一个普通的Dialog
    public static void showDialgo(Context context, String title, String content, final CallBack callback){
        final AlertDialog.Builder normalDialog=new AlertDialog.Builder(context);
        normalDialog.setTitle(title);
        normalDialog.setMessage(content);
        normalDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                callback.onConfirm();
            }
        });
        normalDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        normalDialog.show();
    }
    public interface CallBack{
        void onConfirm();
    }
    private static CallBack callBack;

    public void setOnConfirm(CallBack callBack){
        this.callBack=callBack;
    }

}
