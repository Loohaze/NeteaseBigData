package com.nju.netease.model;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

@Document(collection="userComments")
public class UserComments implements Serializable {

    private static final long serialVersionUID = -3258839839160856613L;

    @Id
    private Object id;

    @Field("user_id")
    private int userId;

    @Field("user_name")
    private String userName;

    @Field("comments")
    private List<Comment> commentList;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"user_id\":" + userId +
                ", \"user_name\":'" + userName + '\'' +
                ", \"comments\":" + commentList +
                '}';
    }
}
