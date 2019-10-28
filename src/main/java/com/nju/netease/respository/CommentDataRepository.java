package com.nju.netease.respository;

import com.nju.netease.model.CommentData;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CommentDataRepository {

    CommentData findBySongId(int songId);

    List<Integer> getAllSongIds();

}
