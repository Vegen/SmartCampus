package com.itculturalfestival.smartcampus.entity.db;

import cn.bmob.v3.BmobUser;

/**
 * Created by vegen on 2018/3/26.
 * 用户表
 */

public class SmartUser extends BmobUser {
    private String headUrl;     // 头像

    public String getHeadUrl() {
        return headUrl;
    }

    public void setHeadUrl(String headUrl) {
        this.headUrl = headUrl;
    }
}
