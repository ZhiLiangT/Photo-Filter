package com.tianzl.photofilter.utisl;

import android.content.Context;
import android.widget.Toast;

/**
 * Toast统一管理类
 * Created by Administrator on 2017-01-10.
 */

public class T {

    private T(){
        /**不能被实例化*/
        throw new UnsupportedOperationException("cannot be instantiated");
    }
    public static boolean isShow=true;

    /**
     * 短时间显示Toast
     */
    public static void showShort(Context context, CharSequence msg){
        if (isShow){
            Toast.makeText(context,msg, Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 短时间显示Toast
     */
    public static void showShort(Context context, int msg){
        if (isShow){
            Toast.makeText(context,msg, Toast.LENGTH_SHORT).show();
        }
    }
    /**
     * 长时间显示Toast
     */
    public static void showLong(Context context, CharSequence msg){
        if (isShow){
            Toast.makeText(context,msg, Toast.LENGTH_LONG).show();
        }
    }
    /**
     * 长时间显示Toast
     */
    public static void showLong(Context context, int msg){
        if (isShow){
            Toast.makeText(context,msg, Toast.LENGTH_LONG).show();
        }
    }
    /**
     * 自定义显示Toast时间
     */
    public static void show(Context context, CharSequence msg, int duration){
        if (isShow){
            Toast.makeText(context,msg,duration).show();
        }
    }
    /**
     * 自定义显示Toast时间
     */
    public static void show(Context context, int msg, int duration){
        if (isShow){
            Toast.makeText(context,msg,duration).show();
        }
    }
}
