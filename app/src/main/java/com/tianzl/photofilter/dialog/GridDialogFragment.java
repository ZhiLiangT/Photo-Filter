package com.tianzl.photofilter.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.tianzl.photofilter.R;
import com.tianzl.photofilter.adapter.DialogGridAdapter;
import com.tianzl.photofilter.been.FilterInfo;
import com.tianzl.photofilter.utisl.DataUtils;

/**
 * 框架滤镜选择Dialog
 * Created by tianzl on 2017/8/29.
 */

public class GridDialogFragment extends DialogFragment{

    private TextView tvTitle;
    private RecyclerView recyclerView;
    private DialogGridAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View view=inflater.inflate(R.layout.dialog_grid_img,container,false);
        initView(view);
        initData();
        initEvent();
        return view;
    }

    private void initEvent() {
        adapter.setOnItemClickListener(new DialogGridAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(FilterInfo filterInfo) {
                click.OnItem(filterInfo);
                dismiss();
            }
        });
    }
    public interface OnItemClick{
        void OnItem(FilterInfo filterInfo);
    }
    private OnItemClick click;
    public void setOnItemClick(OnItemClick click){
        this.click=click;
    }

    private void initData() {
        adapter=new DialogGridAdapter(getActivity(), DataUtils.getFilterList());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),
                3,GridLayoutManager.HORIZONTAL,false));
    }
    private void initView(View view) {
        tvTitle=view.findViewById(R.id.dialog_grid_img_title);
        recyclerView=view.findViewById(R.id.dialog_grid_img_recycler);
    }


}
