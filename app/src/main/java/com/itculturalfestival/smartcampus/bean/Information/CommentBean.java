package com.itculturalfestival.smartcampus.bean.Information;

/**
 * @creation_time: 2017/4/5
 * @author: Vegen
 * @e-mail: vegenhu@163.com
 * @describe: 评论表的bean
 */

public class CommentBean {
    private String information_id;  //资讯的id
    private String user_id;         //发布评论用户
    private int type;               //对资讯建立新的评论为1，发布者回复为2，其他人回复为3
    private String comment_time;    //发布评论时间
    private String to_user_id;      //评论或回复的用户
    private String reply_msg;       //评论或回复的内容
    private int likes;              //评论或回复的点赞数

    public String getInformation_id() {
        return information_id;
    }

    public void setInformation_id(String information_id) {
        this.information_id = information_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getComment_time() {
        return comment_time;
    }

    public void setComment_time(String comment_time) {
        this.comment_time = comment_time;
    }

    public String getTo_user_id() {
        return to_user_id;
    }

    public void setTo_user_id(String to_user_id) {
        this.to_user_id = to_user_id;
    }

    public String getReply_msg() {
        return reply_msg;
    }

    public void setReply_msg(String reply_msg) {
        this.reply_msg = reply_msg;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }
}
