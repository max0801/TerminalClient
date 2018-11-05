package de.hhu.bsinfo.restTerminal.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface StatsService {
    @GET("/statsprint")
    Call<String> printStats(@Query("interval") int intervalInSeconds);
}
