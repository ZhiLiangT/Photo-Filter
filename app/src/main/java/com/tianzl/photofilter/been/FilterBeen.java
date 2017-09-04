package com.tianzl.photofilter.been;

import java.util.Arrays;

/**
 * 自定义滤镜对象
 * Created by tianzl on 2017/8/30.
 */

public class FilterBeen {
    private float [] filters;
    private String name;

    @Override
    public String toString() {
        return "FilterBeen{" +
                "filters=" + Arrays.toString(filters) +
                ", name='" + name + '\'' +
                '}';
    }

    public FilterBeen() {
    }

    public FilterBeen(float[] filters, String name) {
        this.filters = filters;
        this.name = name;
    }

    public float[] getFilters() {
        return filters;
    }

    public void setFilters(float[] filters) {
        this.filters = filters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
