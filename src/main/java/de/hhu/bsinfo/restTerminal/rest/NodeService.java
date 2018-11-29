package de.hhu.bsinfo.restTerminal.rest;

import de.hhu.bsinfo.restTerminal.data.NodeInfoRest;
import de.hhu.bsinfo.restTerminal.request.NodeInfoRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

import java.util.List;

public interface NodeService {
    @PUT("/nodelist")
    Call<List<String>> nodeList();

    @PUT("/nodeinfo")
    Call<NodeInfoRest> nodeInfo(@Body NodeInfoRequest p_nodeInfoRequest);
}
