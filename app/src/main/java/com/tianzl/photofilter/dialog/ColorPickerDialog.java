package com.tianzl.photofilter.dialog;

import android.os.Bundle;
        import android.support.annotation.Nullable;
        import android.support.v4.app.DialogFragment;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.view.Window;
        import android.widget.ImageButton;
        import android.widget.TextView;

        import com.larswerkman.holocolorpicker.ColorPicker;
        import com.larswerkman.holocolorpicker.OpacityBar;
        import com.larswerkman.holocolorpicker.SVBar;
        import com.tianzl.photofilter.R;

/**
 * 颜色选择器Dialog
 * Created by tianzl on 2017/9/1.
 */

public class ColorPickerDialog extends DialogFragment {
    private ImageButton ibBack;
    private ImageButton ibEnsure;
    private TextView tvTitle;
    private ColorPicker colorPicker;
    private SVBar svBar;
    private OpacityBar opacityBar;
    private TextView tvExamples;
    private int color;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view=inflater.inflate(R.layout.dialog_color_picker,container,false);
        initView(view);
        initData();
        initEvent();
        return view;
    }
    private void initData() {
        colorPicker.addSVBar(svBar);
        colorPicker.addOpacityBar(opacityBar);
        tvTitle.setText("颜色选择器");
    }
    private void initView(View view) {
        ibBack=view.findViewById(R.id.bar_title_left);
        ibEnsure=view.findViewById(R.id.bar_title_right);
        tvTitle=view.findViewById(R.id.bar_title_title);
        colorPicker=view.findViewById(R.id.dialog_color_picker_colorPicker);
        svBar=view.findViewById(R.id.dialog_color_picker_svbar);
        opacityBar=view.findViewById(R.id.dialog_color_picker_opacity);
        tvExamples=view.findViewById(R.id.dialog_color_picker_examples);
    }
    private void initEvent() {
        colorPicker.setOnColorChangedListener(new ColorPicker.OnColorChangedListener() {
            @Override
            public void onColorChanged(int colors) {
                tvExamples.setTextColor(colors);
                color=colors;
            }
        });
        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onBack();
                dismiss();
            }
        });
        ibEnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onEnsure(color);
                dismiss();
            }
        });
    }
    public interface OnColorListener{
        void onBack();
        void onEnsure(int color);
    }
    private OnColorListener listener;
    public void setOnColorChangeListenter(OnColorListener listener){
        this.listener=listener;
    }
}
