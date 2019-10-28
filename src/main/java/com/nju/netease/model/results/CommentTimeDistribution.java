package com.nju.netease.model.results;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.stereotype.Component;

@Document(collection = "testTime")
public class CommentTimeDistribution extends ResultDistribution{

    public CommentTimeDistribution() {
    }

    public CommentTimeDistribution(int time, int number) {
        this.time = time;
        this.number = number;
    }

    @Field("time")
    private int time;

    @Field("num")
    private int number;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "CommentTimeDistribution{" +
                "time=" + time +
                ", number=" + number +
                '}';
    }
}
