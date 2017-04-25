package com.itculturalfestival.smartcampus.bean.team;

/**
 * @creation_time: 2017/4/9
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 团队首页的列表所需的bean
 */

public class ExpandableListBean {
    private String head;
    private String what;

    public ExpandableListBean(){}

    public ExpandableListBean(String head, String what){
        this.head = head;
        this.what = what;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getWhat() {
        return what;
    }

    public void setWhat(String what) {
        this.what = what;
    }
}
