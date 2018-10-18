package de.hhu.bsinfo.restTerminal.rest;

import de.hhu.bsinfo.restTerminal.data.NodeInfoRest;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface NodeService {
    @GET("/nodelist")
    Call<List<String>> nodeList();

    @GET("/nodeinfo")
    Call<NodeInfoRest> nodeInfo(@Query("nid") String nid);
}
