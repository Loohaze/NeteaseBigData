package com.nju.netease.controller;

import com.nju.netease.model.CommentData;
import com.nju.netease.respository.CommentDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentDataController {
    private final CommentDataRepository commentDataRepository;

    @Autowired
    public CommentDataController(CommentDataRepository commentDataRepository) {
        this.commentDataRepository = commentDataRepository;
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
