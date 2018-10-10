package de.hhu.bsinfo.restTerminal.rest;

import de.hhu.bsinfo.dxram.lookup.overlay.storage.LookupTree;
import de.hhu.bsinfo.restTerminal.data.MetadataEntry;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface MetadataService {
    @GET("/metadata?nid={NID}")
    Call<MetadataEntry> metadataFromOnePeer(@Path("NID") int nodeID);

    @GET("/metadata")
    Call<List<MetadataEntry>> metadataFromAllPeers();


}
