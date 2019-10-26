package com.nju.netease.respository;

import com.nju.netease.model.CommentData;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentDataRepository {

    CommentData findBySongId(int songId);
}
