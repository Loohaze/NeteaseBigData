package com.nju.netease.controller;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.nju.netease.model.Comment;
import com.nju.netease.model.CommentData;
import com.nju.netease.respository.CommentDataRepository;
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
            hadoopUtils.createHadoopFile("testStreaming"+i+".json", JSONObject.toJSONString(commentData)+"\r\n");
            Thread.sleep(1000);

        }
    }

    @RequestMapping("/transferData2Local")
    public void transferData2Local() throws IOException, InterruptedException {
        List<Integer> list = commentDataRepository.getAllSongIds();
        Writer wr;
        for (int i=0;i<list.size();i++){
            int songId=list.get(i);
            CommentData commentData = commentDataRepository.findBySongId(songId);
            File file=new File("F:\\网易云数据\\songComments\\"+songId+".txt");
            wr=new FileWriter(file,true);
            String data=JSONObject.toJSONString(commentData.getCommentList());
            wr.write(data);


        }
//        wr.close();
    }


    @RequestMapping("/saveUserComments")
    public void saveUserComments() throws IOException, InterruptedException {
        List<Integer> list = commentDataRepository.getAllSongIds();
        Writer wr;
        for (int i=0;i<list.size();i++){
            int songId=list.get(i);
            CommentData commentData = commentDataRepository.findBySongId(songId);
            for(Comment comment:commentData.getCommentList()){
                int userId=comment.getUserInfo().getUserId();
                File file=new File("F:\\网易云数据\\userComments\\"+userId+".txt");
                wr=new FileWriter(file,true);
//                String data=JSONObject.toJSONString(comment);
                wr.write(comment.getContent()+"\n");
            }
        }
//        wr.close();
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
