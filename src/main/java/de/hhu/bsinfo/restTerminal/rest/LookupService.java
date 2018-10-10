package de.hhu.bsinfo.restTerminal.rest;

import de.hhu.bsinfo.dxram.lookup.overlay.storage.LookupTree;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LookupService {
    @GET("/lookuptree?nid={NID}")
    Call<LookupTree> logInfo(@Path("NID") int nodeID);

}
