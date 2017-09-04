package com.tianzl.photofilter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tianzl.photofilter.R;
import com.tianzl.photofilter.utisl.ViewUtils;


/**
 * 画笔大小列表适配器
 * Created by tianzl on 2017/9/1.
 */

public class PaintSizeAdapter extends RecyclerView.Adapter<PaintSizeAdapter.ViewHolder> {

    private Context context;
    private int[] mData;
    private LayoutInflater inflater;
    public PaintSizeAdapter(Context context,int[] mData){
        this.context=context;
        this.mData=mData;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.item_paint_size,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        ViewUtils.setViewWidthOrHeight(holder.icon,mData[position],mData[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(mData[position]);
            }
        });

    }
    public interface OnItemClickListener{
        void onItemClick(int size);
    }
    public OnItemClickListener listener;

    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }
    @Override
    public int getItemCount() {
        return mData.length;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        View icon;
        public ViewHolder(View itemView) {
            super(itemView);
            icon=itemView.findViewById(R.id.item_paint_size_icon);
        }
    }
}
