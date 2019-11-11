package com.nju.netease.model.graph;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

@Document(collection = "addDegreeGraph_nodes")
public class AddDegreeNode  extends Node implements Serializable {

    private static final long serialVersionUID = -3258839839160856613L;

    @Id
    private Object id;

    @Field("inDegree")
    private Integer inDegree;

    @Field("outDegree")
    private Integer outDegree;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Object getId() {
        return id;
    }

    public void setId(Object id) {
        this.id = id;
    }

    public Integer getInDegree() {
        return inDegree;
    }

    public void setInDegree(Integer inDegree) {
        this.inDegree = inDegree;
    }

    public Integer getOutDegree() {
        return outDegree;
    }

    public void setOutDegree(Integer outDegree) {
        this.outDegree = outDegree;
    }
}
