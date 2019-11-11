package com.nju.netease.respository.impl;

import com.nju.netease.model.graph.*;
import com.nju.netease.respository.GraphResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GraphResultRepositoryImpl implements GraphResultRepository {

    @Autowired
    private MongoTemplate mongoTemplate;


    @Override
    public List<IndegreeTopPlaylistNode> getTopPlaylistNodes() {
        Query query = new Query();
        List<IndegreeTopPlaylistNode> list = mongoTemplate.find(query, IndegreeTopPlaylistNode.class);
        return list;
    }

    @Override
    public List<IndegreeTopPlaylistEdge> getTopPlaylistEdges() {
        Query query = new Query();
        List<IndegreeTopPlaylistEdge> list = mongoTemplate.find(query, IndegreeTopPlaylistEdge.class);
        return list;
    }

    @Override
    public List<IndegreeTopSongNode> getTopSongNodes() {
        Query query = new Query();
        List<IndegreeTopSongNode> list = mongoTemplate.find(query, IndegreeTopSongNode.class);

        return list;
    }

    @Override
    public List<IndegreeTopSongEdge> getTopSongEdges() {
        Query query = new Query();
        List<IndegreeTopSongEdge> list = mongoTemplate.find(query, IndegreeTopSongEdge.class);
        return list;
    }

    @Override
    public List<AddDegreeNode> getAddNodes() {
        Query query = new Query();
        List<AddDegreeNode> list = mongoTemplate.find(query, AddDegreeNode.class);
        return list;
    }

    @Override
    public List<AddDegreeEdge> getAddEdges() {
        Query query = new Query();
        List<AddDegreeEdge> list = mongoTemplate.find(query, AddDegreeEdge.class);
        return list;
    }
}
