package com.nju.netease.model;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

@Document(collection="word2vec")
public class Word2Vec implements Serializable {

    private static final long serialVersionUID = -3258839839160856613L;

    @Id
    private Object id;

    @Field("word2vec")
    private List<String> vecs;


    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public List<String> getVecs() {
        return vecs;
    }

    public void setVecs(List<String> vecs) {
        this.vecs = vecs;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"vecs\":" + vecs ;
    }
}
