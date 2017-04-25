package com.itculturalfestival.smartcampus.entity;

import org.litepal.crud.DataSupport;

/**
 * @creation_time: 2017/4/11
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe:
 */

public class UserDB extends DataSupport {
    private int id;                             //用户id
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
    private String password;
//    private List<TeamDB> associationList;  //参与社团信息

    public UserDB(){}
    public UserDB(String name, String tel, String password){
        this.name = name;
        this.password = password;
        this.tel = tel;
    }
    public UserDB(
            String head,
            String name,
            String sex,
            String school,
            String faculty,
            String major,
            String education,
            String e_mail,
            String tel,
            String password,
            String enrollment_Year
    ){
        this.head = head;
        this.name = name;
        this.sex = sex;
        this.school = school;
        this.faculty = faculty;
        this.major = major;
        this.education = education;
        this.e_mail = e_mail;
        this.tel = tel;
        this.password = password;
        this.enrollment_Year = enrollment_Year;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEnrollment_Year() {
        return enrollment_Year;
    }

    public void setEnrollment_Year(String enrollment_Year) {
        this.enrollment_Year = enrollment_Year;
    }
}
