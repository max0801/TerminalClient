package de.hhu.bsinfo.restTerminal.rest;

import de.hhu.bsinfo.restTerminal.data.NodeInfoRest;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface NodeService {
    @GET("/nodelist")
    Call<List<String>> nodeList();

    @GET("/nodeinfo?nid={NID}")
    Call<NodeInfoRest> nodeInfo(@Path("NID")int nid);
}
