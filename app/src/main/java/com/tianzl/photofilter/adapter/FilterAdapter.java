package com.tianzl.photofilter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianzl.photofilter.R;
import com.tianzl.photofilter.been.FilterBeen;
import com.tianzl.photofilter.utisl.FilterUtils;

import java.util.List;

/**
 * 自定义滤镜列表适配器
 * Created by tianzl on 2017/8/30.
 */

public class FilterAdapter extends RecyclerView.Adapter<FilterAdapter.ViewHolder> {
    private Context context;
    private List<FilterBeen> mData;
    private LayoutInflater inflater;
    public FilterAdapter(Context context,List<FilterBeen> mData){
        this.context=context;
        this.mData=mData;
        inflater=LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view=inflater.inflate(R.layout.item_filter,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        FilterUtils.imageViewColorFilter(holder.icIcon,mData.get(position).getFilters());
        holder.tvName.setText(mData.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(mData.get(position));
            }
        });
    }

    public interface OnItemClickListener{
        void onItemClick(FilterBeen been);
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
        ImageView icIcon;
        TextView tvName;
        public ViewHolder(View itemView) {
            super(itemView);
            icIcon=itemView.findViewById(R.id.item_filer_icon);
            tvName=itemView.findViewById(R.id.item_filer_name);
        }
    }
}
