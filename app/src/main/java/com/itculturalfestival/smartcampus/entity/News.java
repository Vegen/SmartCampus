package com.itculturalfestival.smartcampus.entity;

/**
 * Created by vegen on 2018/3/6.
 * 新闻类
 */

public class News {
    private Integer news_type;
    private String news_title;
    private String news_pic;
    private String news_url;
    private String news_date;

    public News(){}

    public News(String title, String pic, String url, String date){
        this.news_title = title;
        this.news_pic = pic;
        this.news_url = url;
        this.news_date = date;
    }

    public News(Integer type, String title, String pic, String url, String date){
        this.news_type = type;
        this.news_title = title;
        this.news_pic = pic;
        this.news_url = url;
        this.news_date = date;
    }

    public Integer getNews_type() {
        return news_type;
    }

    public void setNews_type(Integer news_type) {
        this.news_type = news_type;
    }

    public String getNews_title() {
        return news_title;
    }

    public void setNews_title(String news_title) {
        this.news_title = news_title;
    }

    public String getNews_pic() {
        return news_pic;
    }

    public void setNews_pic(String news_pic) {
        this.news_pic = news_pic;
    }

    public String getNews_url() {
        return news_url;
    }

    public void setNews_url(String news_url) {
        this.news_url = news_url;
    }

    public String getNews_date() {
        return news_date;
    }

    public void setNews_date(String news_date) {
        this.news_date = news_date;
    }
}
