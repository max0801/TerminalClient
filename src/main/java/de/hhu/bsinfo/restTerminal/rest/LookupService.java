package de.hhu.bsinfo.restTerminal.rest;

import de.hhu.bsinfo.dxram.lookup.overlay.storage.LookupTree;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LookupService {
    @GET("/lookuptree")
    Call<LookupTree> logInfo(@Query("nid") int nodeID);

}
