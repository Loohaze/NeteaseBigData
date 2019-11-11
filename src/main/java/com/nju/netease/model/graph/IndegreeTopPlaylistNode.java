package com.nju.netease.model.graph;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Document(collection = "inDegreeTopPlaylistGraph_nodes")
public class IndegreeTopPlaylistNode extends Node implements Serializable {

    private static final long serialVersionUID = -3258839839160856613L;

    @Id
    private Object id;



    public IndegreeTopPlaylistNode(Object id) {
        this.id = id;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }


}
