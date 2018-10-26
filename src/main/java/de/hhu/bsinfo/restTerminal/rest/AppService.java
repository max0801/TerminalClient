package de.hhu.bsinfo.restTerminal.rest;

import de.hhu.bsinfo.restTerminal.data.Message;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface AppService {
    @GET("/applist")
    Call<List<String>>appList();

    @GET("/apprun")
    Call<Message> appRun(@Query("nid") String nid, @Query("app") String appName);

}
