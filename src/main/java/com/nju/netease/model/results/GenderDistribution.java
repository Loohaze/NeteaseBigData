package com.nju.netease.model.results;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "testGender")
public class GenderDistribution extends ResultDistribution {

    public GenderDistribution() {
    }

    public GenderDistribution(int gender, int number) {
        this.gender = gender;
        this.number = number;
    }

    @Field("gender")
    private int gender;

    @Field("num")
    private int number;

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "GenderDistribution{" +
                "gender=" + gender +
                ", number=" + number +
                '}';
    }
}
