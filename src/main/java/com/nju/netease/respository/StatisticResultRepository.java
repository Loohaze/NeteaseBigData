package com.nju.netease.respository;

import com.nju.netease.model.results.*;

import java.util.List;

public interface StatisticResultRepository {

    void insertOneStatisticResult(String type, String result);

    List<AgeDistribution> getAgeDistribution();

    List<CommentTimeDistribution> getTimeDistribution();

    List<GenderDistribution> getGenderDistribution();

    List<LevelDistribution> getLevelDistribution();

    List<ProvinceDistribution> getProvinceDistribution();
}
