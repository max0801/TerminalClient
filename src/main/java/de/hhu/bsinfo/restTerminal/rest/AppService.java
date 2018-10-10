package de.hhu.bsinfo.restTerminal.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface AppService {
    @GET("/applist?={NID}")
    Call<List<String>>appList(@Path("NID") int nodeID);

    @POST("/apprun?=[NID]?={APP}")
    Call<String> appRun(@Path("NID")int nid, @Path("APP")String appName);

}
