package com.itculturalfestival.smartcampus.entity;

import com.itculturalfestival.smartcampus.bean.UserBean;

import java.util.List;

/**
 * @creation_time: 2017/4/5
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 社团的信息表
 */

public class TeamDB {
    private int id;                                     //社团id
    private String name;                                //社团名称
    private String head;                                //社团头像
    private String style;                                  //类型
    private int level;                                  //级别
    private String introduction;                        //简介
    private List<InformationDB> informationList;      //发布过的资讯
    private List<UserBean> memberList;                  //社团人员

    public TeamDB(){}

    public TeamDB(
            String name,
            String head,
            String style,
            int level,
            String introduction
    ){
        this.name = name;
        this.head = head;
        this.style = style;
        this.level = level;
        this.introduction = introduction;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List<InformationDB> getInformationList() {
        return informationList;
    }

    public void setInformationList(List<InformationDB> informationList) {
        this.informationList = informationList;
    }

    public List<UserBean> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<UserBean> memberList) {
        this.memberList = memberList;
    }
}
