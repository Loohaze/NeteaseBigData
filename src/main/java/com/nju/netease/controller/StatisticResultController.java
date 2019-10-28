package com.nju.netease.controller;

import com.nju.netease.model.results.*;
import com.nju.netease.respository.StatisticResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistic")
public class StatisticResultController {
    private final StatisticResultRepository statisticResultRepository;

    @Autowired
    public StatisticResultController(StatisticResultRepository statisticResultRepository) {
        this.statisticResultRepository = statisticResultRepository;
    }


    @GetMapping("/age")
    public List<AgeDistribution> getAgeDistribution() {
        return statisticResultRepository.getAgeDistribution();
    }

    @GetMapping("/time")
    public List<CommentTimeDistribution> getTimeDistribution() {
        return statisticResultRepository.getTimeDistribution();
    }

    @GetMapping("/gender")
    public List<GenderDistribution> getGenderDistribution() {
        return statisticResultRepository.getGenderDistribution();
    }

    @GetMapping("/level")
    public List<LevelDistribution> getLevelDistribution() {
        return statisticResultRepository.getLevelDistribution();
    }

    @GetMapping("/province")
    public List<ProvinceDistribution> getProvinceDistribution() {
        return statisticResultRepository.getProvinceDistribution();
    }
}
