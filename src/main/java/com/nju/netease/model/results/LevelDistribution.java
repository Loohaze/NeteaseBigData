package com.nju.netease.model.results;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "testLevel")
public class LevelDistribution extends ResultDistribution {

    public LevelDistribution() {
    }

    public LevelDistribution(int level, int number) {
        this.level = level;
        this.number = number;
    }

    @Field("level")
    private int level;

    @Field("num")
    private int number;

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "LevelDistribution{" +
                "level=" + level +
                ", number=" + number +
                '}';
    }
}
