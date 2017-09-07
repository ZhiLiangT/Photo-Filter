package com.tianzl.photofilter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianzl.photofilter.R;
import com.tianzl.photofilter.been.FilterInfo;

import java.util.List;

/**
 * 框架滤镜列表适配器
 * Created by tianzl on 2017/8/29.
 */

public class DialogGridAdapter extends RecyclerView.Adapter<DialogGridAdapter.ViewHolder> {
    private Context context;
    private List<FilterInfo> mData;
    private LayoutInflater inflater;
    public DialogGridAdapter(Context context,List<FilterInfo> mData){
        this.context=context;
        this.mData=mData;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.item_dialog_grid,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tvTitle.setText(mData.get(position).getTitle());
//        int resource=mData.get(position).getResource();
//        if (resource!=0){
//            holder.ivIcon.setImageResource(resource);
//        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(mData.get(position));
            }
        });
    }
    public interface OnItemClickListener{
        void onItemClick(FilterInfo filterInfo);
    }
    private OnItemClickListener listener;
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivIcon;
        TextView tvTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            ivIcon=itemView.findViewById(R.id.item_dialog_grid_iv);
            tvTitle=itemView.findViewById(R.id.item_dialog_grid_tv);
        }
    }
}
