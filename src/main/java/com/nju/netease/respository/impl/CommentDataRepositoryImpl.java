package com.nju.netease.respository.impl;

import com.nju.netease.model.Comment;
import com.nju.netease.model.CommentData;
import com.nju.netease.respository.CommentDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CommentDataRepositoryImpl implements CommentDataRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public CommentData findBySongId(int songId) {
        Query query = new Query(Criteria.where("song_id").is(songId));
        CommentData commentData = mongoTemplate.findOne(query, CommentData.class);
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        for (Comment comment : commentData.getCommentList()) {
            Matcher m = p.matcher(comment.getContent());
            String newComment = m.replaceAll("");
            comment.setContent(newComment);
        }
        return commentData;
    }

    @Override
    public List<Integer> getAllSongIds() {
        List<Integer> result = new ArrayList<>();
//        Criteria criteria = new Criteria().where("comments").elemMatch(Criteria.where("user_info.level").ne(null).and("user_info.gender").ne(null).and("user_info.user_id").ne(null));
        Query query = new Query();
        List<CommentData> list = mongoTemplate.find(query, CommentData.class);
        for (CommentData commentData : list) {
            if (!result.contains(commentData.getSongId())) {
                result.add(commentData.getSongId());
            }
        }
        return result;
    }


}
