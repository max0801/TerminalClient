package de.hhu.bsinfo.restTerminal.rest;

import de.hhu.bsinfo.restTerminal.data.*;
import de.hhu.bsinfo.restTerminal.request.*;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * Representations of DXRAM-REST-API functions having to do with the creation and
 * modfication of chunks
 */
public interface ChunkService {
    @PUT("/chunkcreate")
    Call<ChunkCreateResponse> chunkCreate(@Body ChunkCreateRequest p_chunkCreateRequest);

    @PUT ("/chunkget")
    Call<ChunkGetResponse> chunkGet(@Body ChunkGetRequest p_chunkGetRequest);

    @PUT("/chunkput")
    Call<Void> chunkPut(@Body ChunkPutRequest p_chunkPutRequest);

    @PUT("/chunklist")
    Call<ChunkListResponse>chunkList(@Body ChunkListRequest p_chunkListRequest);

    @PUT("/chunkdump")
    Call<Void> chunkDump(@Body ChunkDumpRequest p_chunkDumpRequest);

    @PUT("/chunkremove")
    Call<Void> chunkRemove(@Body ChunkRemoveRequest p_chunkRemoveRequest);

}
