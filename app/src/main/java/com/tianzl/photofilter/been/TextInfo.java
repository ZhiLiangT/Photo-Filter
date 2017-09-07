package com.tianzl.photofilter.been;

/**
 * Created by tianzl on 2017/9/6.
 */

public class TextInfo {
    private String content;
    private int tvSize;
    private int tvColor;
    private String font;

    public TextInfo() {
    }

    public TextInfo(String content, int tvSize, int tvColor) {
        this.content = content;
        this.tvSize = tvSize;
        this.tvColor = tvColor;
    }

    public TextInfo(String content, int tvSize, int tvColor, String font) {
        this.content = content;
        this.tvSize = tvSize;
        this.tvColor = tvColor;
        this.font = font;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTvSize() {
        return tvSize;
    }

    public void setTvSize(int tvSize) {
        this.tvSize = tvSize;
    }

    public int getTvColor() {
        return tvColor;
    }

    public void setTvColor(int tvColor) {
        this.tvColor = tvColor;
    }
}
