package org.techtown.list;

//2019-7-28
public class SingerItem {

    private String name;
    private String mobile;
    private int resId;

    SingerItem(String name, String mobile, int resId) {
        this.name = name;
        this.mobile = mobile;
        this.resId = resId;
    }

    String getName() {
        return name;
    }

    String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setName(String name) {
        this.name = name;
    }

    int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    @Override
    public String toString() {
        return "SingerItem{" + "name: " + name + ", mobile: " + mobile + "}";
    }
}
