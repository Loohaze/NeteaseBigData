package com.nju.netease.model;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

@Document(collection="filter")
public class Recommend implements Serializable {

    private static final long serialVersionUID = -3258839839160856613L;

    @Id
    private Object id;

    @Field("filter")
    private List<Double> recs;


    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public List<Double> getRecs() {
        return recs;
    }

    public void setRecs(List<Double> recs) {
        this.recs = recs;
    }

    @Override
    public String toString() {
        return "{" +
                "\"id\":" + id +
                ", \"recs\":" + recs ;
    }
}
