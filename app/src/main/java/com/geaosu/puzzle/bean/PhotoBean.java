package com.geaosu.puzzle.bean;

/**
 * iten的javabean
 */
public class PhotoBean {
    public int resId;           // 图片id
    public boolean checked;     // 图片是否被选中

    public PhotoBean() {
    }

    public PhotoBean(int resId, boolean checked) {
        this.resId = resId;
        this.checked = checked;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
