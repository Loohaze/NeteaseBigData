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
        int realId=0;
        int[] array= {359264686,381112666,441060029,1727934259,33114592,1362641907,288426827,284148667,357381331,340667401,351495316,338905674,392453169,485845770,259724495,1875087384,262220552};
        for(int i=0;i<array.length;i++){
            if(userId==array[i]){
                realId=i+1;
                break;
            }
        }


        Query query = new Query(Criteria.where("filter.0").is(realId));
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
    public List<HotWord> getTopList() {
        Query query = new Query();
        List<Word2Vec> vecData = mongoTemplate.find(query, Word2Vec.class);
        String[] filters={"commentList","commentId","content","likedCount","time",
                "userInfo","birthday","city","gender","level","province","userId","userName","发现","真的","看看","心里","今天","都是","总是","不要"};
        List<HotWord> list=new ArrayList<>();
        for(Word2Vec vec:vecData){
            boolean canAdd=true;
            String word=vec.getVecs().get(0);
            for(String compare:filters) {
                if(word.contains(compare)||word.contains("_")){
                    canAdd=false;
                }
            }
            if(canAdd){
                double rate=getSum(vec.getVecs());
                list.add(new HotWord(word,rate));
            }
        }
        return list;
    }

    public double getSum(List<String> vecs){
        double sum=0.0;
        for(int i=1;i<vecs.size();i++){
            sum+=Math.abs(Double.parseDouble(vecs.get(i)));
        }
        return sum;
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
        Pattern p = Pattern.compile("\\s*|\t|\r|\n");
        for (Comment comment : commentData.getCommentList()) {
            Matcher m = p.matcher(comment.getContent());
            String newComment = m.replaceAll("");
            comment.setContent(newComment);
        }
        return commentData;

    }
}
