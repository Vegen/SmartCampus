package com.itculturalfestival.smartcampus.entity;

import java.util.List;

/**
 * Created by vegen on 2018/3/10.
 */

public class NewsList {
    private String newsType;
    private List<News> newsList;

    public NewsList(){}

    public NewsList(String type, List<News> newsList){
        this.newsType = type;
        this.newsList = newsList;
    }

    public String getNewsType() {
        return newsType;
    }

    public void setNewsType(String newsType) {
        this.newsType = newsType;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }
}
