package com.itculturalfestival.smartcampus.entity;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * @creation_time: 2017/4/11
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 记录的数据库
 */

public class RecordDB extends DataSupport {
    private int id;
    private int userId;
    private String title;
    private String content;
    private Date ctime;
    public RecordDB(){}
    public RecordDB(int userId, String title, String content, Date ctime){
        this.userId = userId;
        this.title = title;
        this.content = content;
        this.ctime = ctime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }
}
