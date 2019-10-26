package com.nju.netease.respository.impl;

import com.nju.netease.model.CommentData;
import com.nju.netease.respository.CommentDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

@Component
public class CommentDataRepositoryImpl implements CommentDataRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public CommentData findBySongId(int songId) {
        Query query = new Query(Criteria.where("song_id").is(songId));
        return mongoTemplate.findOne(query, CommentData.class);
    }
}
