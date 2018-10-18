package de.hhu.bsinfo.restTerminal.rest;

import de.hhu.bsinfo.restTerminal.data.ChunkRange;
import de.hhu.bsinfo.restTerminal.data.Message;
import retrofit2.Call;
import retrofit2.http.*;

public interface ChunkService {
    @GET("/chunkcreate")
    Call<Message> chunkCreate(@Query("nid") String nodeID, @Query("size") int size);

    @GET ("/chunkget")
    Call<String> chunkGet(@Query("cid") String chunkID, @Query("type") String type);

    //TODO wie wird DATA übergeben? Wahrscheinlich als einer der <types> und dann vom Server dementsprechend verarbeitet
    @GET("/chunkput")
    Call<Message> chunkPut(@Query("cid") String chunkID, @Query("type") String type, @Query("data") Object data);

    @GET("/chunklist")
    Call<ChunkRange>chunkList(@Query("nid") String nodeID);

    @GET("/chunkdump")
    Call<Message> chunkDump(@Query("cid") String chunkID, @Query("name") String name);

    //Not yet implemented
    //@GET("/chunklocklist?nid={nid}")
    //Call<String>chunkLockList(@Path("nid") int nodeID);

    @GET("/chunkremove")
    Call<Message> chunkRemove(@Query("cid") String chunkID);



}
