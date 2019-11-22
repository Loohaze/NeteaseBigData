package com.nju.netease.model;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;

@Document(collection="HotWord")
public class HotWord implements Serializable {

    private static final long serialVersionUID = -3258839839160856613L;
    @Id
    private Object id;


    @Field("word")
    private String word;

    @Field("rate")
    private Double rate;

    public HotWord(String word, Double rate) {
        this.word = word;
        this.rate = rate;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "{" ;
    }
}
