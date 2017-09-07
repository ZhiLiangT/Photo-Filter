package com.tianzl.photofilter.utisl;

import android.content.Context;

/**
 * Android px、dp、sp之间相互转换
 * Created by Administrator on 2017-02-21.
 */

public class DisplayUtils {
    /**
     * 将px转换为dp或dip，保证尺寸大小不变
     */
    public static int pxTodp(Context context,float pxValue){
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue/density+0.5f);
    }
    /**
     * 将dp或dip转换为px，保证了尺寸大小不变
     */
    public static int dpTopx(Context context,float dpValue){
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue*density+0.5f);
    }
    /**
     * 将px转换为sp
     */
    public static int pxTosp(Context context,float pxValue){
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue/scaledDensity+0.5f);
    }
    /**
     * 将sp转换为px
     */
    public static int spTopx(Context context , float spValue){
        float scaledDensity = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue*scaledDensity+0.5f);
    }


}
