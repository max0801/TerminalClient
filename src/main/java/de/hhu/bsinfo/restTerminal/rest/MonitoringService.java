package de.hhu.bsinfo.restTerminal.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface MonitoringService {
    @GET("/monitor?nid={nid}")
    Call<List<String>> monitor(@Path("nid") int nid);


}
