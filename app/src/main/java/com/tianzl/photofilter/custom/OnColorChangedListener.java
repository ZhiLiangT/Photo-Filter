package com.tianzl.photofilter.custom;

/**
 * Created by tianzl on 2017/9/1.
 */

public interface OnColorChangedListener {
    /**
     * Color changed event happened.
     *
     * @param source
     *            event source object
     * @param type
     *            ColorChooserType
     * @param color
     *            color int value
     */
    public void colorChanged(Object source, ColorChooserType type, int color);
}
