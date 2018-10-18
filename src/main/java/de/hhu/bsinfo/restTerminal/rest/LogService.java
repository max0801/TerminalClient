package de.hhu.bsinfo.restTerminal.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LogService {
    @GET("/loginfo")
    Call<String> logInfo(@Query("nid") int nodeID);


}
