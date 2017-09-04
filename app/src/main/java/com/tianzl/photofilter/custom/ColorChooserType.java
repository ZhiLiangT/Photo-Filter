package com.tianzl.photofilter.custom;

/**
 * Created by tianzl on 2017/9/1.
 */

public class ColorChooserType {
    private int type = 0;
    private static final int DEFINED_COLOR = 1;
    private static final int UNIVERSAL_COLOR = 2;
    public static final ColorChooserType DEFINED_COLOR_TYPE = new ColorChooserType(
            DEFINED_COLOR);
    public static final ColorChooserType UNIVERSAL_COLOR_TYPE = new ColorChooserType(
            UNIVERSAL_COLOR);
    private ColorChooserType(int type) {
        this.type = type;
    }
    public int getType() {
        return type;
    }
    @Override
    public boolean equals(Object type) {
        if (type instanceof ColorChooserType) {
            if (this.getType() == ((ColorChooserType) type).getType()) {
                return true;
            }
        }
        return false;
    }
    @Override
    public int hashCode() {
        return this.getType();
    }
}
