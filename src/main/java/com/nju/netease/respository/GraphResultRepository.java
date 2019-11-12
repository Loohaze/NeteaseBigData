package com.nju.netease.respository;

import com.nju.netease.model.graph.*;

import java.util.List;

public interface GraphResultRepository {

    List<IndegreeTopPlaylistNode> getTopPlaylistNodes();

    List<IndegreeTopPlaylistEdge> getTopPlaylistEdges();

    List<IndegreeTopSongNode> getTopSongNodes();

    List<IndegreeTopSongEdge> getTopSongEdges();

    List<AddDegreeNode> getAddNodes();

    List<AddDegreeEdge> getAddEdges();

    List<SongRank> getSongRanks();

    List<PlaylistRank> getPlayListRanks();

    List<AvgLevelRank> getAvgLevelRanks();


}
