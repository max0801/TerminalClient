package de.hhu.bsinfo.restTerminal.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface ChunkService {
    @POST("/chunkcreate?nid={nid}?=size={size}")
    Call<List<String>> chunkCreate(@Path("nid") int nid, @Path("size") int size);

    @GET ("/chunkget?cid={cid}?=type={type}")
    Call<List<String>> chunkGet(@Path("cid") int chunkID, @Path("type") String type);




}
