package com.nju.netease.model.results;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "testAge")
public class AgeDistribution extends ResultDistribution{

    public AgeDistribution() {
    }

    public AgeDistribution(int age, int number) {
        this.age = age;
        this.number = number;
    }

    @Field("age")
    private int age;

    @Field("num")
    private int number;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "AgeDistribution{" +
                "age=" + age +
                ", number=" + number +
                '}';
    }
}
