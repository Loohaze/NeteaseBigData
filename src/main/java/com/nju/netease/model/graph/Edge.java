package com.nju.netease.model.graph;

import org.springframework.data.mongodb.core.mapping.Field;

public class Edge {

    @Field("srcId")
    private Long srcId;

    @Field("dstId")
    private Long dstId;

    @Field("type")
    private String prop;

    public Long getSrcId() {
        return srcId;
    }

    public void setSrcId(Long srcId) {
        this.srcId = srcId;
    }

    public Long getDstId() {
        return dstId;
    }

    public void setDstId(Long dstId) {
        this.dstId = dstId;
    }

    public String getProp() {
        return prop;
    }

    public void setProp(String prop) {
        this.prop = prop;
    }
}
