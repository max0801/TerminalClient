package de.hhu.bsinfo.restTerminal.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;

public interface ChunkService {
    @POST("/chunkcreate?nid={nid}?=size={size}")
    Call<List<String>> chunkCreate(@Path("nid") int nodeID, @Path("size") int size);

    @GET ("/chunkget?cid={cid}?=type={type}")
    Call<List<String>> chunkGet(@Path("cid") int chunkID, @Path("type") String type);

    @PUT("/chunkput?cid={cid}?type={type}?data={data}")
    Call<List<String>> chunkPut(@Path("cid") int chunkID, @Path("type") String type, @Path("data") Object data);

    @GET("/chunklist?nid={nid}")
    Call<List<String>> chunkList(@Path("nid") int nodeID);

}
