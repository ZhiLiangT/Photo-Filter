package com.tianzl.photofilter.been;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;

/**
 * 框架滤镜对象
 * Created by tianzl on 2017/8/29.
 */

public class FilterInfo{
    private String title;
    private int resource;
    private GPUImageFilter fileter;

    public FilterInfo(){}

    public FilterInfo(String title, int resource) {
        this.title = title;
        this.resource = resource;
    }

    public FilterInfo(String title, int resource, GPUImageFilter fileter) {
        this.title = title;
        this.resource = resource;
        this.fileter = fileter;
    }

    @Override
    public String toString() {
        return "FilterInfo{" +
                "title='" + title + '\'' +
                ", resource=" + resource +
                ", fileter=" + fileter +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getResource() {
        return resource;
    }

    public void setResource(int resource) {
        this.resource = resource;
    }

    public GPUImageFilter getFileter() {
        return fileter;
    }

    public void setFileter(GPUImageFilter fileter) {
        this.fileter = fileter;
    }
}
