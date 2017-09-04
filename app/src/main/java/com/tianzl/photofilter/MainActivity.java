package com.tianzl.photofilter;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tianzl.photofilter.adapter.FilterAdapter;
import com.tianzl.photofilter.adapter.PaintSizeAdapter;
import com.tianzl.photofilter.been.FilterBeen;
import com.tianzl.photofilter.been.FilterInfo;
import com.tianzl.photofilter.custom.PaletteImageView;
import com.tianzl.photofilter.dialog.ColorPickerDialog;
import com.tianzl.photofilter.dialog.FoldersDialogFragment;
import com.tianzl.photofilter.dialog.GridDialogFragment;
import com.tianzl.photofilter.utisl.Constants;
import com.tianzl.photofilter.utisl.DataUtils;
import com.tianzl.photofilter.utisl.FilterUtils;
import com.tianzl.photofilter.utisl.TimeUtils;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import jp.co.cyberagent.android.gpuimage.GPUImage;


public class MainActivity extends AppCompatActivity implements
        View.OnClickListener,SeekBar.OnSeekBarChangeListener{

    private final String TAG=this.getClass().getSimpleName();
    private ImageView ivSave,ivOpen;                                                //打开图库和保存图片
    private ImageView ivHue,ivFilter,ivPaint,ivColor,ivSize;                        //功能模块按钮
    private ImageButton ibBack,ibGo,ibPaint,ibEraser,ibPaintColor,ibPaintSize;      //画板模块按钮
    private SeekBar seekRed,seekGreen,seekBlue;             //RGB色调进度条
    private TextView tvHueRed,tvHueGreen,tvHueBlue;         //RGB色调百分比
    private PaletteImageView surfaceView;                   //图片
    private RecyclerView rvfilter;                          //自定义滤镜列表
    private RelativeLayout rgbLayout;                       //色调布局
    private TextView tvPrompt;                              //无图片时：背景
    private LinearLayout llPalette;                         //画板布局
    private RelativeLayout rlImage;                         //图片的外围布局
    private RecyclerView rvPaintSize;                       //选择画笔Size的RV
    private PaintSizeAdapter paintSizeAdapter;              //画笔size的适配器
    private FilterAdapter adapter;                          //滤镜的适配器
    private GPUImage gpuImage;                              //GPUImage框架对象
    private Bitmap mBitmap;
    private static final int SELECT_PHOTO=1;                //打开图库请求码
    private Canvas canvas;
    private Paint paint;
    private Bitmap copyBitmap;
    private float redValue=1f,greenValue=1f,blueValue=1f;   //RGB色调初始值
    private int newPalettePosition=0;                       //画板模块上次点击的位置
    private ImageButton[] ibs=new ImageButton[6];           //画板模块按钮
    private int bitmapWidth;                                //原图的宽
    private int bitmapHeight;                               //原图的高


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// 固定横屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   // 隐藏状态栏
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        initEvent();
    }

    /**
     * 事件监听
     */
    private void initEvent() {
        //RGB色调SeekBar滑动监听
        seekRed.setOnSeekBarChangeListener(this);
        seekGreen.setOnSeekBarChangeListener(this);
        seekBlue.setOnSeekBarChangeListener(this);
        //自定义滤镜item点击事件
        adapter.setOnItemClickListener(new FilterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FilterBeen been) {
                FilterUtils.imageViewColorFilter(surfaceView,been.getFilters());
            }
        });
        //画笔粗细item点击事件
        paintSizeAdapter.setOnItemClickListener(new PaintSizeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int size) {
                surfaceView.setSize(size);
                rvPaintSize.setVisibility(View.INVISIBLE);
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        surfaceView.setDrawingCacheEnabled(true);
        //将画板模块按钮添加进数组
        ibs[0]=ibBack;
        ibs[1]=ibGo;
        ibs[2]=ibPaint;
        ibs[3]=ibEraser;
        ibs[4]=ibPaintColor;
        ibs[5]=ibPaintSize;
        //初始化框架滤镜对象
        gpuImage = new GPUImage(this);
        setYuan();
        //色调seekbar设置标记
        seekRed.setTag(0);
        seekGreen.setTag(1);
        seekBlue.setTag(2);
        //设置自定义滤镜列表
        adapter=new FilterAdapter(this, DataUtils.getFilter());
        rvfilter.setAdapter(adapter);
        rvfilter.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        //设置画笔粗细列表
        paintSizeAdapter=new PaintSizeAdapter(this,DataUtils.paintsize);
        rvPaintSize.setAdapter(paintSizeAdapter);
        rvPaintSize.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }

    /**
     * 初始化View
     */
    private void initView() {
        rvPaintSize= (RecyclerView) findViewById(R.id.main_paint_size_rv);
        tvHueRed= (TextView) findViewById(R.id.main_hue_tv_red);
        tvHueGreen= (TextView) findViewById(R.id.main_hue_tv_green);
        tvHueBlue= (TextView) findViewById(R.id.main_hue_tv_blue);
        llPalette= (LinearLayout) findViewById(R.id.main_ll_palette);
        tvPrompt= (TextView) findViewById(R.id.main_tv_prompt);
        rgbLayout= (RelativeLayout) findViewById(R.id.main_rl_bottom);
        rvfilter= (RecyclerView) findViewById(R.id.main_filter_rv);
        surfaceView= (PaletteImageView) findViewById(R.id.main_surface);
        ivSave= (ImageView) findViewById(R.id.main_img_save);
        ivSave.setOnClickListener(this);
        ivOpen= (ImageView) findViewById(R.id.main_img_open);
        ivOpen.setOnClickListener(this);
        ivHue= (ImageView) findViewById(R.id.main_img_hue);
        ivHue.setOnClickListener(this);
        ivFilter= (ImageView) findViewById(R.id.main_img_filter);
        ivFilter.setOnClickListener(this);
        ivPaint= (ImageView) findViewById(R.id.main_img_paint);
        ivPaint.setOnClickListener(this);
        ivColor= (ImageView) findViewById(R.id.main_img_color);
        ivColor.setOnClickListener(this);
        ivSize= (ImageView) findViewById(R.id.main_img_reduction);
        ivSize.setOnClickListener(this);
        seekRed= (SeekBar) findViewById(R.id.main_seek_saturation);
        seekGreen= (SeekBar) findViewById(R.id.main_seek_hue);
        seekBlue= (SeekBar) findViewById(R.id.main_seek_brightness);
        ibBack= (ImageButton) findViewById(R.id.main_palette_back);
        ibBack.setOnClickListener(this);
        ibGo= (ImageButton) findViewById(R.id.main_palette_go);
        ibGo.setOnClickListener(this);
        ibPaint= (ImageButton) findViewById(R.id.main_palette_paint);
        ibPaint.setOnClickListener(this);
        ibEraser= (ImageButton) findViewById(R.id.main_palette_eraser);
        ibEraser.setOnClickListener(this);
        ibPaintColor= (ImageButton) findViewById(R.id.main_palette_paint_color);
        ibPaintColor.setOnClickListener(this);
        ibPaintSize= (ImageButton) findViewById(R.id.main_palette_paint_size);
        ibPaintSize.setOnClickListener(this);
    }
    //初始化
    public void initialization(){
        setYuan();
        surfaceView.clear();
        surfaceView.setSize(5);
        surfaceView.setMode(PaletteImageView.Mode.DRAW);
        surfaceView.setColor(Color.BLACK);
        FilterUtils.imageViewColorFilter(surfaceView,DataUtils.colormatrix_original);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_img_save:
                //保存图片
                openFilePath();
                break;
            case R.id.main_img_open:
                //打开图库，选择一张图片，初始化滤镜为原图,清除画板轨迹，RGB色调置为1
                initialization();
                OpenImg();
                break;
            case R.id.main_img_hue:
                //显示色调模块
                isShowModule(Constants.SHOW_HUE);
                break;
            case R.id.main_img_filter:
                //打开框架提供的滤镜 dialog形式
                OpenFilterList();
                break;
            case R.id.main_img_paint:
                //显示画板模块，默认显示画笔开启
                isShowModule(Constants.SHOW_PALETTE);
                showPalete(2);
                break;
            case R.id.main_img_color:
                //显示自定义滤镜模块
                isShowModule(Constants.SHOW_FILTER);
                break;
            case R.id.main_img_reduction:
                break;
            /**画板模块功能点击事件*/
            case R.id.main_palette_back:
                //清空 画板轨迹
                showPalete(0);
                surfaceView.clear();
                break;
            case R.id.main_palette_go:
                //反撤销
                showPalete(1);
                break;
            case R.id.main_palette_paint:
                //使用画笔，打开开关
                showPalete(2);
                surfaceView.setMode(PaletteImageView.Mode.DRAW);
                surfaceView.setSwitch(true);
                break;
            case R.id.main_palette_eraser:
                //使用橡皮擦
                showPalete(3);
                surfaceView.setMode(PaletteImageView.Mode.ERASER);
                break;
            case R.id.main_palette_paint_color:
                //选择画笔颜色
                showPalete(4);
                showPaintColorDialog();
                break;
            case R.id.main_palette_paint_size:
                //选择画笔的粗细
                showPalete(5);
                rvPaintSize.setVisibility(View.VISIBLE);
                break;
        }
    }

    /**
     * 选择颜色的dialog
     */
    public void showPaintColorDialog(){
        ColorPickerDialog colorPickerDialog=new ColorPickerDialog();
        colorPickerDialog.show(getSupportFragmentManager(),"dialog_color");
        colorPickerDialog.setOnColorChangeListenter(new ColorPickerDialog.OnColorListener() {
            @Override
            public void onEnsure(int color) {
                if (color==0){
                    surfaceView.setColor(Color.BLACK);
                }
                surfaceView.setColor(color);
            }
            @Override
            public void onBack() {
            }
        });
    }

    /**
     * 选择画板下模块，显示当前点击和隐藏上次点击按钮
     */
    public void showPalete(int position){
        if (position==5){
            rvPaintSize.setVisibility(View.VISIBLE);
        }else {
            rvPaintSize.setVisibility(View.INVISIBLE);
        }
        if (newPalettePosition!=position){
            ibs[newPalettePosition].setSelected(false);
            ibs[position].setSelected(true);
            newPalettePosition=position;
        }
    }
    /**
     *  显示不同的模块
     *  色调、滤镜和画板
     */
    public void isShowModule(int type){
        switch (type){
            case Constants.SHOW_HUE:
                setBitmap();
                llPalette.setVisibility(View.INVISIBLE);
                rvfilter.setVisibility(View.INVISIBLE);
                rgbLayout.setVisibility(View.VISIBLE);
                break;
            case Constants.SHOW_FILTER:
                setBitmap();
                llPalette.setVisibility(View.INVISIBLE);
                rvfilter.setVisibility(View.VISIBLE);
                rgbLayout.setVisibility(View.INVISIBLE);
                break;
            case Constants.SHOW_PALETTE:
                surfaceView.setSwitch(true);
                llPalette.setVisibility(View.VISIBLE);
                rvfilter.setVisibility(View.INVISIBLE);
                rgbLayout.setVisibility(View.INVISIBLE);
                break;
        }
        rvPaintSize.setVisibility(View.INVISIBLE);
    }
    /**将bitmap重新绘制到ImageView上*/
    public void setBitmap(){
        mBitmap=surfaceView.buildBitmap();
        surfaceView.setImageBitmap(mBitmap);
        surfaceView.setSwitch(false);
        surfaceView.clear();
    }
    /**打开框架滤镜Dialog*/
    private void OpenFilterList() {
        GridDialogFragment dialogFragment=new GridDialogFragment();
        dialogFragment.show(getSupportFragmentManager(),"dialog_grid");
        dialogFragment.setOnItemClick(new GridDialogFragment.OnItemClick() {
            @Override
            public void OnItem(FilterInfo filterInfo) {
                if (mBitmap!=null){
                    gpuImage.setImage(mBitmap);
                    gpuImage.setFilter(filterInfo.getFileter());
                    mBitmap = gpuImage.getBitmapWithFilterApplied();
                    //显示处理后的图片
                    surfaceView.setImageBitmap(mBitmap);
                    setYuan();
                }else {
                    Toast.makeText(MainActivity.this,"尚未添加图片，请选择图片之后再进行操作",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    /**打开相册*/
    private void OpenImg() {
        Intent intent = new Intent(Intent.ACTION_PICK,null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent,SELECT_PHOTO);
    }
    /**打开文件管理，选择文件夹*/
    public void openFilePath(){
        FoldersDialogFragment dialogFragment=new FoldersDialogFragment();
        dialogFragment.show(getSupportFragmentManager(),"dialog_grid");
        dialogFragment.setCallBack(new FoldersDialogFragment.PathCallBack() {
            @Override
            public void callBack(String path) {
                Toast.makeText(MainActivity.this,"保存路径"+path,Toast.LENGTH_SHORT).show();
                if (path!=null){
                    //保存图片
                    Bitmap bitmap=surfaceView.buildBitmap();
                    saveBitmap(bitmap,path);
                    Toast.makeText(MainActivity.this,"保存成功",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    /**保存图片*/
    public void saveBitmap(Bitmap bitmap,String path){
        String imgName= "img_"+TimeUtils.getStringToday()+".png";
        File file = new File(path,imgName);
        FileOutputStream out;
        try {
            out = new FileOutputStream(file);
            if(bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out))
            {
                out.flush();
                out.close();
            }
        } catch (java.io.IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==SELECT_PHOTO&&resultCode == RESULT_OK){
            Uri uri = data.getData();
            try {
                mBitmap=BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                bitmapWidth=mBitmap.getWidth();
                bitmapHeight=mBitmap.getHeight();
                surfaceView.setImageBitmap(mBitmap);
                tvPrompt.setVisibility(View.INVISIBLE);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    /**设置 SeekBar 的初始值*/
    public void setYuan(){
        seekGreen.setProgress(50);
        seekRed.setProgress(50);
        seekBlue.setProgress(50);
    }

    /**色调 SeekBar RGB滑动监听事件*/
    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        int num=seekBar.getProgress();
        float count = num/ 50f;
        int flag= (int) seekBar.getTag();
        switch (flag){
            case 0:
                this.redValue = count;
                tvHueRed.setText(num+"%");
                break;
            case 1:
                this.greenValue = count;
                tvHueGreen.setText(num+"%");
                break;
            case 2:
                this.blueValue = count;
                tvHueBlue.setText(num+"%");
                break;
        }
        initBitmap();
    }
    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {}
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {}
    private void initBitmap() {
        if (mBitmap==null){
            return;
        }
        //先加载出一张原图(baseBitmap)，然后复制出来新的图片(copyBitmap)来，因为andorid不允许对原图进行修改.
        //baseBitmap= BitmapFactory.decodeResource(getResources(), R.drawable.link);
        //既然是复制一张与原图一模一样的图片那么这张复制图片的画纸的宽度和高度以及分辨率都要与原图一样,copyBitmap就为一张与原图相同尺寸分辨率的空白画纸
        copyBitmap=Bitmap.createBitmap(mBitmap.getWidth(), mBitmap.getHeight(), mBitmap.getConfig());
        canvas=new Canvas(copyBitmap);//将画纸固定在画布上
        paint=new Paint();//实例画笔对象
        float[] colorArray=new float[]{
                redValue,0,0,0,0,
                0,greenValue,0,0,0,
                0,0,blueValue,0,0,
                0,0,0,1,0
        };
        ColorMatrix colorMatrix=new ColorMatrix(colorArray);//将保存的颜色矩阵的数组作为参数传入
        ColorMatrixColorFilter colorFilter=new ColorMatrixColorFilter(colorMatrix);//再把该colorMatrix作为参数传入来实例化ColorMatrixColorFilter
        paint.setColorFilter(colorFilter);//并把该过滤器设置给画笔
        canvas.drawBitmap(mBitmap, new Matrix(), paint);//传如baseBitmap表示按照原图样式开始绘制，将得到是复制后的图片
        surfaceView.setImageBitmap(copyBitmap);
    }

}
