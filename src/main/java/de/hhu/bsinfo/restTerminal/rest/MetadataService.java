package de.hhu.bsinfo.restTerminal.rest;

import de.hhu.bsinfo.restTerminal.data.MetadataEntry;
import de.hhu.bsinfo.restTerminal.request.MetadataRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

import java.util.List;

//Metadata of Superpeers
public interface MetadataService {
    @PUT("/metadata")
    Call<MetadataEntry> metadataFromOnePeer(@Body MetadataRequest p_metadataRequest);

    @PUT("/metadata")
    Call<List<MetadataEntry>> metadataFromAllPeers();


}
