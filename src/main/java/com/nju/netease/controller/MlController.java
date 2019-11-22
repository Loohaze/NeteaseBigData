package com.nju.netease.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.nju.netease.model.*;
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

    @PostMapping("/Song")
    @ResponseBody
    public JSONObject getSong(@RequestParam("songId") int id) throws IOException, InterruptedException {
        JSONObject jsonObject=new JSONObject();
        CommentData mlSong = mlDataRepository.getMlSong(id);
        jsonObject.put("songId",mlSong.getSongId());
        jsonObject.put("songName",mlSong.getSongName());
        return jsonObject;
    }


    @RequestMapping("/topList")
    public List<HotWord> getToplist() throws IOException, InterruptedException {
        return mlDataRepository.getTopList();
    }


    @PostMapping(value = "/rec")
    @ResponseBody
    public List<Recommend> getOneCommentDataBySongId(@RequestParam("userId") int userId) {
        return mlDataRepository.getRecommendByUser(userId);
    }


}
