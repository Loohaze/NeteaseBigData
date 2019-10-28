package com.nju.netease.respository.impl;

import com.nju.netease.model.results.*;
import com.nju.netease.respository.StatisticResultRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class StatisticResultRepositoryImpl implements StatisticResultRepository {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public void insertOneStatisticResult(String type, String result) {
        try {
            JSONArray array = (JSONArray) new JSONParser().parse(result);
            switch (type) {
                case "age":
                    for (Object o : array) {
                        JSONObject jsonObject = (JSONObject) o;
                        AgeDistribution ageDistribution = new AgeDistribution();
                        ageDistribution.setAge((Integer.parseInt(jsonObject.get("age").toString())));
                        ageDistribution.setNumber((Integer.parseInt(jsonObject.get("number").toString())));
                        mongoTemplate.save(ageDistribution);
                    }
                    break;
                case "gender":
                    for (Object o : array) {
                        JSONObject jsonObject = (JSONObject) o;
                        GenderDistribution genderDistribution = new GenderDistribution();
                        genderDistribution.setGender((Integer.parseInt(jsonObject.get("gender").toString())));
                        genderDistribution.setNumber((Integer.parseInt(jsonObject.get("number").toString())));
                        mongoTemplate.save(genderDistribution);
                    }
                    break;
                case "level":
                    for (Object o : array) {
                        JSONObject jsonObject = (JSONObject) o;
                        LevelDistribution levelDistribution = new LevelDistribution();
                        levelDistribution.setLevel((Integer.parseInt(jsonObject.get("level").toString())));
                        levelDistribution.setLevel((Integer.parseInt(jsonObject.get("number").toString())));
                        mongoTemplate.save(levelDistribution);
                    }
                    break;
                case "province":
                    for (Object o : array) {
                        JSONObject jsonObject = (JSONObject) o;
                        ProvinceDistribution provinceDistribution = new ProvinceDistribution();
                        provinceDistribution.setCode(Long.parseLong(jsonObject.get("province").toString()));
                        provinceDistribution.setNumber((Integer.parseInt(jsonObject.get("number").toString())));
                        mongoTemplate.save(provinceDistribution);
                    }
                    break;
                case "time":
                    for (Object o : array) {
                        JSONObject jsonObject = (JSONObject) o;
                        CommentTimeDistribution commentTimeDistribution = new CommentTimeDistribution();
                        commentTimeDistribution.setTime((Integer.parseInt(jsonObject.get("time").toString())));
                        commentTimeDistribution.setNumber((Integer.parseInt(jsonObject.get("number").toString())));
                        mongoTemplate.save(commentTimeDistribution);
                    }
                    break;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<AgeDistribution> getAgeDistribution() {
        Map<Integer, Integer> map = new HashMap<>();

        Query query = new Query();
        List<AgeDistribution> list = mongoTemplate.find(query, AgeDistribution.class);
        for (AgeDistribution ageDistribution : list) {
            Integer age = ageDistribution.getAge();
            if (map.containsKey(age)) {
                map.replace(age, map.get(age) + ageDistribution.getNumber());
            } else {
                map.put(age, ageDistribution.getNumber());
            }
        }
        List<AgeDistribution> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            result.add(new AgeDistribution(entry.getKey(), entry.getValue()));
        }
        return result;
    }

    @Override
    public List<CommentTimeDistribution> getTimeDistribution() {
        Map<Integer, Integer> map = new HashMap<>();
        Query query = new Query();
        List<CommentTimeDistribution> list = mongoTemplate.find(query, CommentTimeDistribution.class);
        for (CommentTimeDistribution commentTimeDistribution : list) {
            Integer time = commentTimeDistribution.getTime();
            if (map.containsKey(time)) {
                map.replace(time, map.get(time) + commentTimeDistribution.getNumber());
            } else {
                map.put(time, commentTimeDistribution.getNumber());
            }
        }
        List<CommentTimeDistribution> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            result.add(new CommentTimeDistribution(entry.getKey(), entry.getValue()));
        }
        return result;
    }

    @Override
    public List<GenderDistribution> getGenderDistribution() {
        Map<Integer, Integer> map = new HashMap<>();
        Query query = new Query();
        List<GenderDistribution> list = mongoTemplate.find(query, GenderDistribution.class);
        for (GenderDistribution genderDistribution : list) {
            Integer gender = genderDistribution.getGender();
            if (map.containsKey(gender)) {
                map.replace(gender, map.get(gender) + genderDistribution.getNumber());
            } else {
                map.put(gender, genderDistribution.getNumber());
            }
        }
        List<GenderDistribution> result = new ArrayList<>();
        for (Map.Entry<Integer,Integer> entry : map.entrySet()) {
            result.add(new GenderDistribution(entry.getKey(),entry.getValue()));
        }
        return result;
    }

    @Override
    public List<LevelDistribution> getLevelDistribution() {
        Map<Integer, Integer> map = new HashMap<>();
        Query query = new Query();
        List<LevelDistribution> list = mongoTemplate.find(query, LevelDistribution.class);
        for (LevelDistribution levelDistribution : list) {
            Integer level = levelDistribution.getLevel();
            if (map.containsKey(level)) {
                map.replace(level, map.get(level) + levelDistribution.getNumber());
            } else {
                map.put(level, levelDistribution.getNumber());
            }
        }
        List<LevelDistribution> result = new ArrayList<>();
        for (Map.Entry<Integer,Integer> entry : map.entrySet()) {
            result.add(new LevelDistribution(entry.getKey(),entry.getValue()));
        }
        return result;
    }

    @Override
    public List<ProvinceDistribution> getProvinceDistribution() {
        Map<Long,Integer> map = new HashMap<>();
        Query query = new Query();
        List<ProvinceDistribution> list = mongoTemplate.find(query, ProvinceDistribution.class);
        for (ProvinceDistribution provinceDistribution : list) {
            Long code = provinceDistribution.getCode();
            if (map.containsKey(code)) {
                map.replace(code, map.get(code)+provinceDistribution.getNumber());
            } else {
                map.put(code,provinceDistribution.getNumber());
            }
        }
        List<ProvinceDistribution> result = new ArrayList<>();
        for (Map.Entry<Long,Integer> entry : map.entrySet()) {
            result.add(new ProvinceDistribution(entry.getKey(),entry.getValue()));
        }
        return result;
    }


}
