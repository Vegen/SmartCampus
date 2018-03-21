package com.itculturalfestival.smartcampus.entity;

import java.util.List;

/**
 * Created by vegen on 2018/3/10.
 */

public class NewsList {
    private int newsType;
    private List<News> newsList;

    public NewsList(){}

    public NewsList(int type, List<News> newsList){
        this.newsType = type;
        this.newsList = newsList;
    }

    public int getNewsType() {
        return newsType;
    }

    public void setNewsType(int newsType) {
        this.newsType = newsType;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }
}
