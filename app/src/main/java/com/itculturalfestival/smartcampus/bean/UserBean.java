package com.itculturalfestival.smartcampus.bean;


/**
 * @creation_time: 2017/4/5
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 用户个人信息表
 */

public class UserBean {
    private int user_id;                             //用户id
    private String head;                                //头像
    private String name;                                //姓名
    private String sex;                                 //性别
    private String school;                              //学校
    private String faculty;                             //院系
    private String major;                               //专业
    private String education;                           //学历
    private String e_mail;                              //邮箱
    private String tel;                                 //电话
    private String enrollment_Year;                     //入学年份
//    private List<TeamDB> associationList;  //参与社团信息

    public UserBean(){}

    public UserBean(
            int user_id,
            String head,
            String name,
            String sex,
            String school,
            String faculty,
            String major,
            String education,
            String e_mail,
            String tel,
            String enrollment_Year
    ){
        this.user_id = user_id;
        this.head = head;
        this.name = name;
        this.sex = sex;
        this.school = school;
        this.faculty = faculty;
        this.major = major;
        this.education = education;
        this.e_mail = e_mail;
        this.tel = tel;
        this.enrollment_Year = enrollment_Year;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getE_mail() {
        return e_mail;
    }

    public void setE_mail(String e_mail) {
        this.e_mail = e_mail;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEnrollment_Year() {
        return enrollment_Year;
    }

    public void setEnrollment_Year(String enrollment_Year) {
        this.enrollment_Year = enrollment_Year;
    }

//    public List<TeamDB> getAssociationList() {
//        return associationList;
//    }
//
//    public void setAssociationList(List<TeamDB> associationList) {
//        this.associationList = associationList;
//    }
}
