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

@Component
public class CommentDataRepositoryImpl implements CommentDataRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public CommentData findBySongId(int songId) {
        Query query = new Query(Criteria.where("song_id").is(songId));
        return mongoTemplate.findOne(query, CommentData.class);
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
