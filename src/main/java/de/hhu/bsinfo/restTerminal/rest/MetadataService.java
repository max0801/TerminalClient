package de.hhu.bsinfo.restTerminal.rest;

import de.hhu.bsinfo.restTerminal.data.MetadataEntry;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.List;

//Metadata of Superpeers
public interface MetadataService {
    @GET("/metadata")
    Call<MetadataEntry> metadataFromOnePeer(@Query("nid") String nodeID);

    @GET("/metadata")
    Call<List<MetadataEntry>> metadataFromAllPeers();


}
