package com.itculturalfestival.smartcampus.entity.db;

import cn.bmob.v3.BmobObject;

/**
 * Created by vegen on 2018/3/26.
 * 失物表
 */

public class Lost extends BmobObject {
    private Integer type;       // lost or found
    private String img;         // 附图
    private String title;       // 标题
    private String description; // 描述
    private String beTime;      // 丢失拾获时间
    private Boolean solve;      // 是否已解决
    private String contact;     // 联系方式
    private SmartUser smartUser;// 关联发布的用户

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBeTime() {
        return beTime;
    }

    public void setBeTime(String beTime) {
        this.beTime = beTime;
    }

    public Boolean getSolve() {
        return solve;
    }

    public void setSolve(Boolean solve) {
        this.solve = solve;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public SmartUser getSmartUser() {
        return smartUser;
    }

    public void setSmartUser(SmartUser smartUser) {
        this.smartUser = smartUser;
    }
}
