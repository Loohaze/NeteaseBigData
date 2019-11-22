package com.nju.netease.respository.impl;

import com.nju.netease.model.*;
import com.nju.netease.respository.CommentDataRepository;
import com.nju.netease.respository.MlDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class MlDataRepositoryImpl implements MlDataRepository {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Autowired
    private CommentDataRepository commentDataRepository;


    @Override
    public List<Recommend> getRecommendByUser(int userId) {
        //处理id

        Query query = new Query(Criteria.where("filter.0").is(userId));
        List<Recommend> recData = mongoTemplate.find(query, Recommend.class);
        List<Recommend> list=recData.subList(0,10);
        for(int i =1;i<list.size();i++) {
            for(int j=0;j<list.size()-i;j++) {
                if(list.get(j).getRecs().get(2)<list.get(j+1).getRecs().get(2)) {
                    Recommend temp = list.get(j);

                    list.set(j,list.get(j+1));

                    list.set((j+1),temp);
                }
            }
        }

        return list;
    }

    @Override
    public List<String> getTopList() {
        Query query = new Query();
        List<Word2Vec> vecData = mongoTemplate.find(query, Word2Vec.class);
        String[] filters={"commentList","commentId","content","likedCount","time",
                "userInfo","birthday","city","gender","level","province","userId","userName"};
        List<String> list=new ArrayList<>();
        for(Word2Vec vec:vecData){
            boolean canAdd=true;
            String word=vec.getVecs().get(0);
            for(String compare:filters) {
                if(word.contains(compare)||word.contains("_")){
                    canAdd=false;
                }
            }
            if(canAdd){
                list.add(word);
            }
        }
        return list;
    }

    @Override
    public List<UserInfo> getAllUsers() {
        Query query = new Query(Criteria.where("song_id").is(1649115));
        CommentData commentData = mongoTemplate.findOne(query, CommentData.class);
        List<Comment> comments=commentData.getCommentList();
        List<UserInfo> list=new ArrayList<>();
        for(int i=0;i<17;i++){
            UserInfo info=comments.get(i).getUserInfo();
            list.add(info);
        }
        return list;
    }

    public CommentData getMlSong(int songId){
        //处理Id
        List<Integer> list=commentDataRepository.getAllSongIds();
        int Id=list.get(songId%list.size());

        Query query = new Query(Criteria.where("song_id").is(Id));
        CommentData commentData = mongoTemplate.findOne(query, CommentData.class);

        return commentData;
    }
}
