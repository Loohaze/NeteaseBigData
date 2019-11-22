package com.nju.netease.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.nju.netease.model.Comment;
import com.nju.netease.model.CommentData;
import com.nju.netease.model.Recommend;
import com.nju.netease.model.UserInfo;
import com.nju.netease.respository.CommentDataRepository;
import com.nju.netease.respository.MlDataRepository;
import com.nju.netease.utils.HadoopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

@RestController
@RequestMapping("/ml")
public class MlController {
    @Autowired
    private  MlDataRepository mlDataRepository;
//    @Autowired
//    public MlController(MlDataRepository mlDataRepository) {
//        this.mlDataRepository = mlDataRepository;
//    }


    @RequestMapping("/Users")
    public List<UserInfo> getUsers() throws IOException, InterruptedException {
        return mlDataRepository.getAllUsers();
    }

    @RequestMapping("/Song")
    @ResponseBody
    public CommentData getSong(@RequestParam("songId") int id) throws IOException, InterruptedException {
        return mlDataRepository.getMlSong(id);
    }


    @RequestMapping("/topList")
    public List<String> getToplist() throws IOException, InterruptedException {
        return mlDataRepository.getTopList();
    }


    @PostMapping(value = "/rec")
    @ResponseBody
    public List<Recommend> getOneCommentDataBySongId(@RequestParam("userId") int userId) {
        return mlDataRepository.getRecommendByUser(userId);
    }


}
