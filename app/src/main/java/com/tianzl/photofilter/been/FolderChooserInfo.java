package com.tianzl.photofilter.been;

import java.io.File;

/**
 * 文件夹对象
 * Created by tianzl on 2017/8/31.
 */

public class FolderChooserInfo {
    private String name;
    private File file;
    private int image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
