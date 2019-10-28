package com.nju.netease.model.results;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "testProvince")
public class ProvinceDistribution extends ResultDistribution {

    public ProvinceDistribution() {
    }

    public ProvinceDistribution(long code, int number) {
        this.code = code;
        this.number = number;
    }

    @Field("province")
    private long code;

    @Field("num")
    private int number;


    public long getCode() {
        return code;
    }

    public void setCode(long code) {
        this.code = code;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "ProvinceDistribution{" +
                "code=" + code +
                ", number=" + number +
                '}';
    }
}
