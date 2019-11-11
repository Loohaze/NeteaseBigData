package com.nju.netease.tests;

import com.alibaba.fastjson.JSONObject;
import com.nju.netease.model.Comment;
import com.nju.netease.model.CommentData;
import com.nju.netease.model.graph.IndegreeTopPlaylistNode;
import com.nju.netease.model.results.*;
import com.nju.netease.respository.CommentDataRepository;
import com.nju.netease.respository.GraphResultRepository;
import com.nju.netease.respository.StatisticResultRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private CommentDataRepository commentDataRepository;
    @Autowired
    private StatisticResultRepository statisticResultRepository;
    @Autowired
    private GraphResultRepository graphResultRepository;



    @Test
    public void test1() {
        CommentData commentData = commentDataRepository.findBySongId(442314077);
        System.out.println(JSONObject.toJSONString(commentData));
        List<Comment> comments = commentData.getCommentList();
//        for (Comment comment : comments) {
//            System.out.println(comment.toString());
//        }
        System.out.println(commentData.toString());
    }

    @Test
    public void test2() {
        List<Integer> list = commentDataRepository.getAllSongIds();
        System.out.println(list.size());
        for (Integer i : list) {
            System.out.println(i);
        }
    }

    @Test
    public void test3() {


    }

    @Test
    public void testStatistic1() {
        List<AgeDistribution> result = statisticResultRepository.getAgeDistribution();
        for (AgeDistribution a : result) {
            System.out.println(a.toString());
        }
    }

    @Test
    public void testStatistic2() {
        List<CommentTimeDistribution> result = statisticResultRepository.getTimeDistribution();
        for (CommentTimeDistribution a : result) {
            System.out.println(a.toString());
        }
    }

    @Test
    public void testStatistic3() {
        List<GenderDistribution> result = statisticResultRepository.getGenderDistribution();
        for (GenderDistribution a : result) {
            System.out.println(a.toString());
        }
    }

    @Test
    public void testStatistic4() {
        List<LevelDistribution> result = statisticResultRepository.getLevelDistribution();
        for (LevelDistribution a : result) {
            System.out.println(a.toString());
        }
    }

    @Test
    public void testStatistic5() {
        List<ProvinceDistribution> result = statisticResultRepository.getProvinceDistribution();
        for (ProvinceDistribution a : result) {
            System.out.println(a.toString());
        }
    }

    @Test
    public void testGraph1(){
        List<IndegreeTopPlaylistNode> nodes =graphResultRepository.getTopPlaylistNodes();
    }
}
