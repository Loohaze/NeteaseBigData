package com.nju.netease.controller;

import com.alibaba.fastjson.JSONObject;
import com.nju.netease.model.CommentData;
import com.nju.netease.respository.CommentDataRepository;
import com.nju.netease.utils.HadoopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentDataController {
    private final CommentDataRepository commentDataRepository;

    private HadoopUtils hadoopUtils=new HadoopUtils("/testHadoop/","hdfs://172.19.240.124:9000/");

    @Autowired
    public CommentDataController(CommentDataRepository commentDataRepository) {
        this.commentDataRepository = commentDataRepository;
    }

    @RequestMapping("/transferData")
    public void transferDataToHDFS() throws IOException, InterruptedException {
        List<Integer> list = commentDataRepository.getAllSongIds();
        for (int i=0;i<list.size();i++){
            int songId=list.get(i);
            CommentData commentData = commentDataRepository.findBySongId(songId);
            hadoopUtils.appendFile("testStreaming.json", JSONObject.toJSONString(commentData)+"\r\n");
            if (i%499==0){
                Thread.sleep(3000);
            }
        }
    }

    @GetMapping(value = "/getComment")
    @ResponseBody
    public ResponseEntity<CommentData> getOneCommentDataBySongId(@RequestParam("songId") int songId) {
        CommentData commentData = commentDataRepository.findBySongId(songId);
        return new ResponseEntity<>(commentData, HttpStatus.OK);
    }

    @GetMapping(value = "/songIds")
    public ResponseEntity<List<Integer>> getAllSongIds() {
        List<Integer> list = commentDataRepository.getAllSongIds();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
