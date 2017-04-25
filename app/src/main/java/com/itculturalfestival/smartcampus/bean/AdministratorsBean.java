package com.itculturalfestival.smartcampus.bean;

/**
 * @creation_time: 2017/4/5
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 管理员表：社团/部门
 */

public class AdministratorsBean {
    private String user_id;         //用户id
    private String association_id;  //社团/部门id
    private int jurisdiction;       //权限

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAssociation_id() {
        return association_id;
    }

    public void setAssociation_id(String association_id) {
        this.association_id = association_id;
    }

    public int getJurisdiction() {
        return jurisdiction;
    }

    public void setJurisdiction(int jurisdiction) {
        this.jurisdiction = jurisdiction;
    }
}
