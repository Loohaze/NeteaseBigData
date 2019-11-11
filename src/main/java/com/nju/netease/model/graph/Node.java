package com.nju.netease.model.graph;

import org.springframework.data.mongodb.core.mapping.Field;

public class Node {

    @Field("id")
    private Long nodeId;

    @Field("name")
    private String name;

    @Field("type")
    private String prop;

    public Long getNodeId() {
        return nodeId;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }
}
