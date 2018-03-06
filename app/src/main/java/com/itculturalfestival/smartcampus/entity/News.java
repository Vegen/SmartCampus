package com.itculturalfestival.smartcampus.entity;

/**
 * Created by vegen on 2018/3/6.
 * 新闻类
 */

public class News {
    private String news_id;
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

    public News(String id, String title, String pic, String url, String date){
        this.news_id = id;
        this.news_title = title;
        this.news_pic = pic;
        this.news_url = url;
        this.news_date = date;
    }

    public String getNews_id() {
        return news_id;
    }

    public void setNews_id(String news_id) {
        this.news_id = news_id;
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
