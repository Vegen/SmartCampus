package com.itculturalfestival.smartcampus.entity.db;

import cn.bmob.v3.BmobObject;

/**
 * Created by vegen on 2018/3/26.
 * 轮播图表
 */

public class Banner extends BmobObject {
    private String title;
    private String imgUrl;
    private String nextUrl;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }
}
