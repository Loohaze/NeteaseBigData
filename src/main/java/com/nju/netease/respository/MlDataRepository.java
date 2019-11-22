package com.nju.netease.respository;

import com.nju.netease.model.CommentData;
import com.nju.netease.model.Recommend;
import com.nju.netease.model.UserInfo;

import java.util.List;

public interface MlDataRepository {
    //top 10
    List<Recommend> getRecommendByUser(int userId);

    //top 50
    List<String> getTopList();

    List<UserInfo> getAllUsers();

    public CommentData getMlSong(int songId);


}
