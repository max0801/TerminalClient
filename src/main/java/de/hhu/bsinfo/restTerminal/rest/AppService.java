package de.hhu.bsinfo.restTerminal.rest;

import de.hhu.bsinfo.restTerminal.response.AppListResponse;
import de.hhu.bsinfo.restTerminal.request.AppRunRequest;
import retrofit2.Call;
import retrofit2.http.*;

/**
 * Representations of DXRAM-REST-API functions having to do with dxram applications
 */
public interface AppService {
    @GET("/applist")
    Call<AppListResponse> appList();

    @PUT("/apprun")
    Call<Void> appRun(@Body AppRunRequest p_appRunRequest);

}
