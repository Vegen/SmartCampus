package com.itculturalfestival.smartcampus.entity;

/**
 * Created by vegen on 2018/3/24.
 */

public class RecruitAndEmployment {
    private int imgRes;
    private String title;
    private String url;

    public RecruitAndEmployment(int imgRes, String title, String url){
        this.imgRes = imgRes;
        this.title = title;
        this.url = url;
    }

    public int getImgRes() {
        return imgRes;
    }

    public void setImgRes(int imgRes) {
        this.imgRes = imgRes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
