package com.nju.netease.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

public class UserInfo {

    @Field("user_id")
    private int userId;

    @Field("user_name")
    private String userName;

    @Field("level")
    private int level;

    @Field("birthday")
    private long birthday;

    @Field("gender")
    private int gender;

    @Field("city")
    private int city;

    @Field("province")
    private int province;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public long getBirthday() {
        return birthday;
    }

    public void setBirthday(long birthday) {
        this.birthday = birthday;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    @Override
    public String toString() {
        return "{" +
                "\"user_id\":" + userId +
                ", \"user_name\":\"" + userName + '\"' +
                ", \"level\":" + level +
                ", \"birthday\":" + birthday +
                ", \"gender\":" + gender +
                ", \"city\":" + city +
                ", \"province\":" + province +
                '}';
    }
}
