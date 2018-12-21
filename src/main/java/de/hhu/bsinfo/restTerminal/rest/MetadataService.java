package de.hhu.bsinfo.restTerminal.rest;

import de.hhu.bsinfo.restTerminal.data.MetadataResponseAllPeers;
import de.hhu.bsinfo.restTerminal.data.MetadataResponseOnePeer;
import de.hhu.bsinfo.restTerminal.request.MetadataRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;

/**
 * Representations of DXRAM-REST-API functions having to do with metadata
 */
public interface MetadataService {
    @PUT("/metadata")
    Call<MetadataResponseOnePeer> metadataFromOnePeer(@Body MetadataRequest p_metadataRequest);

    @PUT("/metadata")
    Call<MetadataResponseAllPeers> metadataFromAllPeers();


}
