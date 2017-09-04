package com.tianzl.photofilter.been;

/**
 * 画笔大小对象
 * Created by tianzl on 2017/9/1.
 */

public class PaintSizeInfo {
    private int icon;
    private String name;

    public PaintSizeInfo() {
    }

    public PaintSizeInfo(int icon, String name) {
        this.icon = icon;
        this.name = name;
    }

    @Override
    public String toString() {
        return "PaintSizeInfo{" +
                "icon=" + icon +
                ", name='" + name + '\'' +
                '}';
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
