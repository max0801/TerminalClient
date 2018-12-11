package de.hhu.bsinfo.restTerminal.rest;

import de.hhu.bsinfo.restTerminal.data.ChunkRange;
import de.hhu.bsinfo.restTerminal.data.Message;
import de.hhu.bsinfo.restTerminal.request.*;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * Representations of DXRAM-REST-API functions having to do with the creation and
 * modfication of chunks
 */
public interface ChunkService {
    @PUT("/chunkcreate")
    Call<Message> chunkCreate(@Body ChunkCreateRequest p_chunkCreateRequest);

    @PUT ("/chunkget")
    Call<String> chunkGet(@Body ChunkGetRequest p_chunkGetRequest);

    @PUT("/chunkput")
    Call<Message> chunkPut(@Body ChunkPutRequest p_chunkPutRequest);

    @PUT("/chunklist")
    Call<ChunkRange>chunkList(@Body ChunkListRequest p_chunkListRequest);

    @PUT("/chunkdump")
    Call<Message> chunkDump(@Body ChunkDumpRequest p_chunkDumpRequest);

    @PUT("/chunkremove")
    Call<Message> chunkRemove(@Body ChunkRemoveRequest p_chunkRemoveRequest);

}
