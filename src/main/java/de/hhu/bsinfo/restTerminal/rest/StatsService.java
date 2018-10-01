package de.hhu.bsinfo.restTerminal.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

import java.util.List;

public interface StatsService {
    @GET("/statsprint?interval={interval}")
    Call<List<String>> printStats(@Path("interval") int intervalInSeconds);
}
