package com.itculturalfestival.smartcampus.entity;

import com.itculturalfestival.smartcampus.bean.Information.CommentBean;

import java.util.Date;
import java.util.List;

/**
 * @creation_time: 2017/4/5
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 资讯表的bean
 */

public class InformationDB {
    private int id;                                //资讯的id
    private int user_id;                            //发布人id
    private String title;                           //标题
    private String content;                         //内容
    private String illustration;                    //固定插图
    private String html;                            //原文链接
    private Date ctime;                           //发布时间
    private String source;                          //发布组织
    private int team_id;                            //发布团队的id
    private String type;                               //类型
    private int read;                               //阅读量
    private int likes;                              //点赞数
    private int comments;                           //评论数
    private List<CommentBean> commentBeanList;      //评论列表

    public InformationDB(
            int user_id,
            String title,
            String content,
            String illustration,
            String html,
            Date ctime,
            String source,
            int team_id,
            String type){
        this.user_id = user_id;
        this.title = title;
        this.content = content;
        this.illustration = illustration;
        this.html = html;
        this.ctime = ctime;
        this.source = source;
        this.team_id = team_id;
        this.type = type;
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

    public String getIllustration() {
        return illustration;
    }

    public void setIllustration(String illustration) {
        this.illustration = illustration;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public Date getCtime() {
        return ctime;
    }

    public void setCtime(Date ctime) {
        this.ctime = ctime;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getRead() {
        return read;
    }

    public void setRead(int read) {
        this.read = read;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public List<CommentBean> getCommentBeanList() {
        return commentBeanList;
    }

    public void setCommentBeanList(List<CommentBean> commentBeanList) {
        this.commentBeanList = commentBeanList;
    }
}
