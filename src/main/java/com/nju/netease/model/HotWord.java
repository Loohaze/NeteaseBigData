package com.nju.netease.model;

import com.alibaba.fastjson.JSONObject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.List;


public class HotWord implements Serializable {

    private static final long serialVersionUID = -3258839839160856613L;



    private String word;

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
