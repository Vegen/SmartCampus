package com.itculturalfestival.smartcampus.bean;

import java.util.List;

/**
 * @creation_time: 2017/4/5
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 部门表
 */

public class DepartmentBean {
    private String department_id;       //部门id
    private String name;                //名称
    private List<UserBean> memberList;  //成员列表

    public String getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(String department_id) {
        this.department_id = department_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<UserBean> getMemberList() {
        return memberList;
    }

    public void setMemberList(List<UserBean> memberList) {
        this.memberList = memberList;
    }
}
