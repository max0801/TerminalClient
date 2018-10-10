package de.hhu.bsinfo.restTerminal.rest;

import de.hhu.bsinfo.restTerminal.data.ChunkRange;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

import java.util.List;

public interface ChunkService {
    @POST("/chunkcreate?nid={nid}?=size={size}")
    Call<String> chunkCreate(@Path("nid") int nodeID, @Path("size") int size);

    @GET ("/chunkget?cid={cid}?=type={type}")
    Call<String> chunkGet(@Path("cid") int chunkID, @Path("type") String type);

    //TODO wie wird DATA übergeben? Wahrscheinlich als einer der <types> und dann vom Server dementsprechend verarbeitet
    @PUT("/chunkput?cid={cid}?type={type}?data={data}")
    Call<String> chunkPut(@Path("cid") int chunkID, @Path("type") String type, @Path("data") Object data);

    @GET("/chunklist?nid={nid}")
    Call<ChunkRange>chunkList(@Path("nid") int nodeID);

    @GET("/chunkdump?cid={cid}?=name={name}")
    Call<String> chunkDump(@Path("cid") int chunkID, @Path("name") String name);

    //Not yet implemented
    //@GET("/chunklocklist?nid={nid}")
    //Call<String>chunkLockList(@Path("nid") int nodeID);

    @POST("/chunkremove?cid={cid}")
    Call<String> chunkRemove(@Path("cid") int chunkID);



}
