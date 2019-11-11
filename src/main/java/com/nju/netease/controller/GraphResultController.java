package com.nju.netease.controller;

import com.nju.netease.model.graph.*;
import com.nju.netease.respository.GraphResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/graph")
public class GraphResultController {

    private final GraphResultRepository graphResultRepository;

    @Autowired
    public GraphResultController(GraphResultRepository graphResultRepository) {
        this.graphResultRepository = graphResultRepository;
    }

    @GetMapping(value = "/getAddNodes")
    public List<AddDegreeNode> getNodes() {
        return graphResultRepository.getAddNodes();
    }

    @GetMapping(value = "/getAddEdges")
    public List<AddDegreeEdge> getEdges() {
        return graphResultRepository.getAddEdges();
    }

    @GetMapping(value = "/getTopSongNodes")
    public List<IndegreeTopSongNode> getTopSongNodes() {
        return graphResultRepository.getTopSongNodes();
    }

    @GetMapping(value = "/getTopSongEdges")
    public List<IndegreeTopSongEdge> getTopSongEdges() {
        return graphResultRepository.getTopSongEdges();
    }

    @GetMapping(value = "/getTopListNodes")
    public List<IndegreeTopPlaylistNode> getTopListNodes() {
        return graphResultRepository.getTopPlaylistNodes();
    }

    @GetMapping(value = "/getTopListEdges")
    public List<IndegreeTopPlaylistEdge> getTopListEdges() {
        return graphResultRepository.getTopPlaylistEdges();
    }
}
