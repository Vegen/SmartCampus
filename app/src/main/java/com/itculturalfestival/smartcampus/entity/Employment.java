package com.itculturalfestival.smartcampus.entity;

/**
 * Created by Huguang on 2018/3/25.
 */

public class Employment {
    private String title;       // 标题
    private String content;     // 内容
    private String tips;        // 提示
    private String time;        // 日期
    private String nextUrl;     // 指向的页面

    public Employment(){}

    public Employment(
            String title,
            String content,
            String tips,
            String time,
            String nextUrl
    ){
       this.title = title;
       this.content = content;
       this.tips = tips;
       this.time = time;
       this.nextUrl = nextUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTips() {
        return tips;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNextUrl() {
        return nextUrl;
    }

    public void setNextUrl(String nextUrl) {
        this.nextUrl = nextUrl;
    }
}
