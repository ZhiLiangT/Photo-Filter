package com.tianzl.photofilter.been;

import java.util.Arrays;

import jp.co.cyberagent.android.gpuimage.GPUImageFilter;

/**
 * Created by tianzl on 2017/9/4.
 */

public class PhotoOperate {
    private int type;
    private float []hue;
    private GPUImageFilter gpuImageFilter;
    private float [] filters;

    @Override
    public String toString() {
        return "PhotoOperate{" +
                "type=" + type +
                ", hue=" + Arrays.toString(hue) +
                ", gpuImageFilter=" + gpuImageFilter +
                ", filters=" + Arrays.toString(filters) +
                '}';
    }

    public PhotoOperate() {

    }
    public PhotoOperate(int type, float[] hue, GPUImageFilter gpuImageFilter, float[] filters) {
        this.type = type;
        this.hue = hue;
        this.gpuImageFilter = gpuImageFilter;
        this.filters = filters;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public float[] getHue() {
        return hue;
    }

    public void setHue(float[] hue) {
        this.hue = hue;
    }

    public GPUImageFilter getGpuImageFilter() {
        return gpuImageFilter;
    }

    public void setGpuImageFilter(GPUImageFilter gpuImageFilter) {
        this.gpuImageFilter = gpuImageFilter;
    }

    public float[] getFilters() {
        return filters;
    }

    public void setFilters(float[] filters) {
        this.filters = filters;
    }
}
