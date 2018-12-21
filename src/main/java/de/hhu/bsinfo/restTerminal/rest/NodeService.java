package de.hhu.bsinfo.restTerminal.rest;

import de.hhu.bsinfo.restTerminal.response.NodeInfoResponse;
import de.hhu.bsinfo.restTerminal.response.NodeListResponse;
import de.hhu.bsinfo.restTerminal.request.NodeInfoRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;

/**
 * Representations of DXRAM-REST-API functions having to do
 * with nodes in the current DXRAM instance
 */
public interface NodeService {
    @GET("/nodelist")
    Call<NodeListResponse> nodeList();

    @PUT("/nodeinfo")
    Call<NodeInfoResponse> nodeInfo(@Body NodeInfoRequest p_nodeInfoRequest);
}
