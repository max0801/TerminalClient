package de.hhu.bsinfo.restTerminal.rest;

import de.hhu.bsinfo.restTerminal.cmd.AppRun;
import de.hhu.bsinfo.restTerminal.data.Message;
import de.hhu.bsinfo.restTerminal.request.AppRunRequest;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

/**
 * Representations of DXRAM-REST-API functions having to do with dxram applications
 */
public interface AppService {
    @GET("/applist")
    Call<List<String>>appList();

    @PUT("/apprun")
    Call<Message> appRun(@Body AppRunRequest p_appRunRequest);

}
