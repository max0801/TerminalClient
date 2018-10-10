package de.hhu.bsinfo.restTerminal.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface LogService {
    @GET("/loginfo?nid={NID}")
    Call<String> logInfo(@Path("NID") int nodeID);


}
