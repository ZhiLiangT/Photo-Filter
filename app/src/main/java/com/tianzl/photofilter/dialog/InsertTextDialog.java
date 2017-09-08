package com.tianzl.photofilter.dialog;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.larswerkman.holocolorpicker.ColorPicker;
import com.tianzl.photofilter.R;
import com.tianzl.photofilter.been.TextInfo;
import com.tianzl.photofilter.utisl.Constants;


/**
 * Created by tianzl on 2017/9/6.
 */

public class InsertTextDialog extends DialogFragment implements View.OnClickListener{
    private SeekBar seekBar;
    private EditText etContent;
    private TextView tvExamples;
    private ColorPicker colorPicker;
    private TextView tvTextSize;
    private ImageButton ibBack;
    private ImageButton ibConfirm;
    private TextView tvFont;
    private TextView tvFontOne;
    private TextView tvFontTwo;
    private TextView tvFontThree;
    private int textSize;
    private int textColor;
    private String textContent;
    private static final int TEXT_MIN_SIZE=10;
    private TextInfo textInfo;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view=inflater.inflate(R.layout.dialog_insert_text,container,false);
        initView(view);
        initData();
        initEvent();
        return view;
    }

    private void initData() {
        textInfo=new TextInfo();
        seekBar.setMax(100);
        seekBar.setProgress(50);
        textSize=50/4+TEXT_MIN_SIZE;
        tvExamples.setTextSize(textSize);
        textColor= Color.BLACK;
        //设置字体
        tvFontOne.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), Constants.FONT_ONE));
        tvFontTwo.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), Constants.FONT_TWO));
        tvFontThree.setTypeface(Typeface.createFromAsset(getActivity().getAssets(), Constants.FONT_FOUR));
    }
    private void initEvent() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                textSize=seekBar.getProgress()/4+TEXT_MIN_SIZE;
                tvExamples.setTextSize(textSize);
                tvTextSize.setText(""+textSize);
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        colorPicker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int color) {
                textColor=color;
                tvExamples.setTextColor(color);
            }
        });

        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // 输入前的监听
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // 输入的内容变化的监textContent= (String) charSequence;
                textContent=charSequence.toString();
                tvExamples.setText(charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // 输入后的监听
            }
        });
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
        ibConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                textInfo.setTvColor(textColor);
                textInfo.setTvSize(textSize);
                textInfo.setContent(textContent);
                listener.onClick(textInfo);
                dismiss();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.dialog_insert_text_font_one:
                tvExamples.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                        Constants.FONT_ONE));
                textInfo.setFont(Constants.FONT_ONE);
                break;
            case R.id.dialog_insert_text_font_two:
                tvExamples.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                        Constants.FONT_TWO));
                textInfo.setFont(Constants.FONT_TWO);
                break;
            case R.id.dialog_insert_text_font_three:
                tvExamples.setTypeface(Typeface.createFromAsset(getActivity().getAssets(),
                        Constants.FONT_FOUR));
                textInfo.setFont(Constants.FONT_FOUR);
                break;
            case R.id.dialog_insert_text_fonttv:
                tvExamples.setTypeface(Typeface.DEFAULT);
                textInfo.setFont("");
                break;
        }
    }

    public interface OnClickListener{
        void onClick(TextInfo textInfo);
    }
    private OnClickListener listener;
    public void setOnClickListener(OnClickListener listener){
        this.listener=listener;
    }

    private void initView(View view) {
        tvFont=view.findViewById(R.id.dialog_insert_text_fonttv);
        tvFont.setOnClickListener(this);
        tvFontOne=view.findViewById(R.id.dialog_insert_text_font_one);
        tvFontOne.setOnClickListener(this);
        tvFontTwo=view.findViewById(R.id.dialog_insert_text_font_two);
        tvFontTwo.setOnClickListener(this);
        tvFontThree=view.findViewById(R.id.dialog_insert_text_font_three);
        tvFontThree.setOnClickListener(this);
        ibBack=view.findViewById(R.id.bar_title_left);
        ibConfirm=view.findViewById(R.id.bar_title_right);
        seekBar=view.findViewById(R.id.dialog_insert_text_seekbar_tvsize);
        etContent=view.findViewById(R.id.dialog_insert_text_et);
        tvExamples=view.findViewById(R.id.dialog_insert_text_examples);
        colorPicker=view.findViewById(R.id.dialog_insert_text_colorPicker);
        tvTextSize=view.findViewById(R.id.dialog_insert_text_seekbar_tv_size);
    }
}
