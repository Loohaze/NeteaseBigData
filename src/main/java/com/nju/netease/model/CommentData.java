package com.nju.netease.model;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection="comments")
public class CommentData {

    @Id
    private Object id;

    @Field("song_id")
    private int songId;

    @Field("song_name")
    private String songName;

    @Field("comments")
    private List<Comment> commentList;

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
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
                ", \"song_id\":" + songId +
                ", \"song_name\":'" + songName + '\'' +
                ", \"comments\":" + commentList +
                '}';
    }
}
