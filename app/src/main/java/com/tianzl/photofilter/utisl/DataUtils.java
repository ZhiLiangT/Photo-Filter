package com.tianzl.photofilter.utisl;

import com.tianzl.photofilter.R;
import com.tianzl.photofilter.been.FilterBeen;
import com.tianzl.photofilter.been.FilterInfo;

import java.util.ArrayList;
import java.util.List;

import jp.co.cyberagent.android.gpuimage.GPUImageBilateralFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBoxBlurFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageBulgeDistortionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageCGAColorspaceFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageColorInvertFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageCrosshatchFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageEmbossFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageExposureFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGaussianBlurFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGlassSphereFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageGrayscaleFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageHalftoneFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageKuwaharaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageLevelsFilter;
import jp.co.cyberagent.android.gpuimage.GPUImagePosterizeFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSepiaFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSharpenFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSketchFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSmoothToonFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageSobelEdgeDetection;
import jp.co.cyberagent.android.gpuimage.GPUImageSphereRefractionFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageToonFilter;
import jp.co.cyberagent.android.gpuimage.GPUImageTransformFilter;

/**
 * Created by tianzl on 2017/8/29.
 */

public class DataUtils {
    //框架滤镜数组
    private static GPUImageFilter[] filters=new GPUImageFilter[]{
            new GPUImageSepiaFilter(),//褐色（怀旧）
            new GPUImageGrayscaleFilter(),  //灰度
            new GPUImageSharpenFilter(),//锐化
            new GPUImageSketchFilter(),//素描
            new GPUImageSobelEdgeDetection(),//阀值素描，形成有噪点的素描
            new GPUImageToonFilter() ,//卡通效果（黑色粗线描边）
            new GPUImageSmoothToonFilter(), //相比上面的效果更细腻，上面是粗旷的画风
            new GPUImageKuwaharaFilter(),  //桑原(Kuwahara)滤波,水粉画的模糊效果；处理时间比较长，慎用
            new GPUImageTransformFilter(),  //形状变化
            new GPUImageCrosshatchFilter(), //交叉线阴影，形成黑白网状画面
            new GPUImageBulgeDistortionFilter(),  //凸起失真，鱼眼效果
            new GPUImageGlassSphereFilter(),   //水晶球效果
            new GPUImageSphereRefractionFilter(),//球形折射，图形倒立
            new GPUImagePosterizeFilter(), //色调分离，形成噪点效果
            new GPUImageCGAColorspaceFilter(),//CGA色彩滤镜，形成黑、浅蓝、紫色块的画面
            new GPUImageEmbossFilter(),   //浮雕效果，带有点3d的感觉
            new GPUImageHalftoneFilter(), //点染,图像黑白化，由黑点构成原图的大致图形

            new GPUImageGaussianBlurFilter(),//高斯模糊
            new GPUImageBoxBlurFilter(),//盒状模糊
            new GPUImageBilateralFilter(),//双边模糊
            new GPUImageLevelsFilter(),//色阶
            new GPUImageColorInvertFilter(),//反色
            new GPUImageExposureFilter()//曝光


    };
    //框架滤镜对象的名称
    private static String[] strTitle=new String[]{
            "褐色","灰度","锐化","素描","噪点素描","卡通","粗狂卡通","桑原","形状变化",
            "交叉阴影","鱼眼","水晶球","倒立","噪点","CGA色彩","浮雕","点染","高斯模糊",
            "盒状模糊","双边模糊","色阶","反色","曝光"
    };
//    //框架滤镜对象的背景图
//    private static  int[] resource=new int[]{
//            R.mipmap.a1,R.mipmap.a2,R.mipmap.a3,R.mipmap.a4,R.mipmap.a5,R.mipmap.a6,R.mipmap.a7,
//            R.mipmap.a8,R.mipmap.a9,R.mipmap.a10,R.mipmap.a11,R.mipmap.a12,R.mipmap.a13,R.mipmap.a14,
//            R.mipmap.a15,R.mipmap.a16,R.mipmap.a17,R.mipmap.a1,R.mipmap.a2,R.mipmap.a3,R.mipmap.a4,
//            R.mipmap.a5,R.mipmap.a6
//    };
    //画笔大小的数组
    public static final int [] paintsize={
            5,10,15,20,25,30,35,40,45,50
    };

    public static List<FilterInfo>  getFilterList(){
        List<FilterInfo> data=new ArrayList<>();
        for (int i=0;i<filters.length;i++){
            FilterInfo filterInfo=new FilterInfo();
            filterInfo.setTitle(strTitle[i]);
            filterInfo.setFileter(filters[i]);
            //filterInfo.setResource(resource[i]);
            data.add(filterInfo);
        }
        return data;
    }
    public static List<FilterBeen> getFilter(){
        List<FilterBeen> data=new ArrayList<>();
        List<float[]> filters=obFilter();
        for (int i=0;i<filterNameList.length;i++){
            FilterBeen filterBeen=new FilterBeen();
            filterBeen.setFilters(filters.get(i));
            filterBeen.setName(filterNameList[i]);
            data.add(filterBeen);
        }
        return data;
    }
    private  static List<float[]> obFilter(){
        List<float[]> filters=new ArrayList<>();
        filters.add(colormatrix_original);
        filters.add(colormatrix_heibai);
        filters.add(colormatrix_huajiu);
        filters.add(colormatrix_gete);
        filters.add(colormatrix_danya);
        filters.add(colormatrix_landiao);
        filters.add(colormatrix_guangyun);
        filters.add(colormatrix_menghuan);
        filters.add(colormatrix_jiuhong);
        filters.add(colormatrix_fanse);
        filters.add(colormatrix_huguang);
        filters.add(colormatrix_hepian);
        filters.add(colormatrix_fugu);
        filters.add(colormatrix_huan_huang);
        filters.add(colormatrix_chuan_tong);
        filters.add(colormatrix_jiao_pian);
        filters.add(colormatrix_ruise);
        filters.add(colormatrix_qingning);
        filters.add(colormatrix_langman);
        filters.add(colormatrix_yese);
        filters.add(colormatrix_baolilai);
        filters.add(colormatrix_huaijiu);
        filters.add(colormatrix_huidu);
       // filters.add(colormatrix_fanzhuan);
        filters.add(colormatrix_gaobaohe);
        return filters;
    }
    private static String[] filterNameList={
            "原图","黑白","怀旧","哥特","淡雅","蓝调","光晕","梦幻","酒红","胶片","湖光掠影",
            "褐片","复古","泛黄","传统","胶片2","锐色","清宁","浪漫","夜色","宝丽来","怀旧2","灰度",
            //"翻转",
            "高饱和"
    };
    //原图
    public static final float colormatrix_original[]={
            1, 0, 0, 0, 0,
            0, 1, 0, 0, 0,
            0, 0, 1, 0, 0,
            0, 0, 0, 1, 0
    };

    /**滤镜*/
    // 黑白
    public static final float colormatrix_heibai[] = {0.8f, 1.6f, 0.2f, 0,
            -163.9f, 0.8f, 1.6f, 0.2f, 0, -163.9f, 0.8f, 1.6f, 0.2f, 0,
            -163.9f, 0, 0, 0, 1.0f, 0};
    // 怀旧
    public static final float colormatrix_huajiu[] = {
            0.2f, 0.5f, 0.1f, 0, 40.8f,
            0.2f, 0.5f, 0.1f, 0, 40.8f,
            0.2f, 0.5f, 0.1f, 0, 40.8f,
            0, 0, 0, 1, 0};
    // 哥特
    public static final float colormatrix_gete[] = {1.9f, -0.3f, -0.2f, 0,
            -87.0f, -0.2f, 1.7f, -0.1f, 0, -87.0f, -0.1f, -0.6f, 2.0f, 0,
            -87.0f, 0, 0, 0, 1.0f, 0};
    // 淡雅
    public static final float colormatrix_danya[] = {0.6f, 0.3f, 0.1f, 0,
            73.3f, 0.2f, 0.7f, 0.1f, 0, 73.3f, 0.2f, 0.3f, 0.4f, 0, 73.3f, 0,
            0, 0, 1.0f, 0};
    // 蓝调
    public static final float colormatrix_landiao[] = {2.1f, -1.4f, 0.6f,
            0.0f, -71.0f, -0.3f, 2.0f, -0.3f, 0.0f, -71.0f, -1.1f, -0.2f, 2.6f,
            0.0f, -71.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    // 光晕
    public static final float colormatrix_guangyun[] = {0.9f, 0, 0, 0, 64.9f,
            0, 0.9f, 0, 0, 64.9f, 0, 0, 0.9f, 0, 64.9f, 0, 0, 0, 1.0f, 0};

    // 梦幻
    public static final float colormatrix_menghuan[] = {0.8f, 0.3f, 0.1f,
            0.0f, 46.5f, 0.1f, 0.9f, 0.0f, 0.0f, 46.5f, 0.1f, 0.3f, 0.7f, 0.0f,
            46.5f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    // 酒红
    public static final float colormatrix_jiuhong[] = {1.2f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.9f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.8f, 0.0f, 0.0f,
            0, 0, 0, 1.0f, 0};
    // 胶片
    public static final float colormatrix_fanse[] = {-1.0f, 0.0f, 0.0f, 0.0f,
            255.0f, 0.0f, -1.0f, 0.0f, 0.0f, 255.0f, 0.0f, 0.0f, -1.0f, 0.0f,
            255.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    // 湖光掠影
    public static final float colormatrix_huguang[] = {0.8f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.9f, 0.0f, 0.0f,
            0, 0, 0, 1.0f, 0};
    // 褐片
    public static final float colormatrix_hepian[] = {1.0f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.8f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.8f, 0.0f, 0.0f,
            0, 0, 0, 1.0f, 0};
    // 复古
    public static final float colormatrix_fugu[] = {0.9f, 0.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.8f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f, 0.0f,
            0, 0, 0, 1.0f, 0};
    // 泛黄
    public static final float colormatrix_huan_huang[] = {1.0f, 0.0f, 0.0f,
            0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.5f, 0.0f,
            0.0f, 0, 0, 0, 1.0f, 0};
    // 传统
    public static final float colormatrix_chuan_tong[] = {1.0f, 0.0f, 0.0f, 0,
            -10f, 0.0f, 1.0f, 0.0f, 0, -10f, 0.0f, 0.0f, 1.0f, 0, -10f, 0, 0,
            0, 1, 0};
    // 胶片2
    public static final float colormatrix_jiao_pian[] = {0.71f, 0.2f, 0.0f,
            0.0f, 60.0f, 0.0f, 0.94f, 0.0f, 0.0f, 60.0f, 0.0f, 0.0f, 0.62f,
            0.0f, 60.0f, 0, 0, 0, 1.0f, 0};

    // 锐色
    public static final float colormatrix_ruise[] = {4.8f, -1.0f, -0.1f, 0,
            -388.4f, -0.5f, 4.4f, -0.1f, 0, -388.4f, -0.5f, -1.0f, 5.2f, 0,
            -388.4f, 0, 0, 0, 1.0f, 0};
    // 清宁
    public static final float colormatrix_qingning[] = {0.9f, 0, 0, 0, 0, 0,
            1.1f, 0, 0, 0, 0, 0, 0.9f, 0, 0, 0, 0, 0, 1.0f, 0};
    // 浪漫
    public static final float colormatrix_langman[] = {0.9f, 0, 0, 0, 63.0f,
            0, 0.9f, 0, 0, 63.0f, 0, 0, 0.9f, 0, 63.0f, 0, 0, 0, 1.0f, 0};
    // 夜色
    public static final float colormatrix_yese[] = {1.0f, 0.0f, 0.0f, 0.0f,
            -66.6f, 0.0f, 1.1f, 0.0f, 0.0f, -66.6f, 0.0f, 0.0f, 1.0f, 0.0f,
            -66.6f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f};
    //宝丽来彩色
    private static final float[] colormatrix_baolilai = {
            1.438f, -0.062f, -0.062f, 0, 0,
            -0.122f, 1.378f, -0.122f, 0, 0,
            -0.016f, -0.016f, 1.483f, 0, 0,
            -0.03f, 0.05f, -0.02f, 1, 0};
    //怀旧效果
    private static final float[] colormatrix_huaijiu = {
            0.393f,0.769f,0.189f,0,0,
            0.349f,0.686f,0.168f,0,0,
            0.272f,0.534f,0.131f,0,0,
            0,0,0,1,0};
    //灰度效果
    private static final float[] colormatrix_huidu={
            0.33F ,0.59F ,0.11F ,0F ,0F,
            0.33F ,0.59F ,0.11F ,0F, 0F,
            0.33F ,0.59F ,0.11F ,0F, 0F,
            0F, 0F, 0F, 1F, 0F
    };
    //图像翻转
    private static final float[] colormatrix_fanzhuan={
            -1F, 0F, 0F, 1F,1F,
            0F ,-1F, 0F ,1F, 1F,
            0F , 0F, -1 ,1F, 1F,
            0F , 0F, 0F ,1F, 0F
    };
    //高饱和度.
    private static final float[] colormatrix_gaobaohe={
            1.438F, -0.122F, -0.016F, 0F, -0.03F,
            -0.062F, 1.378F,-0.016F, 0F,0.05F,
            -0.062F, -0.122F, 1.483F,0F,-0.02F,
            0F, 0F, 0F, 1F, 0F
    };
}
