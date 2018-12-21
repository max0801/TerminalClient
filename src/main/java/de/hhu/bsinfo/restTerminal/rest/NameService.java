package de.hhu.bsinfo.restTerminal.rest;

import de.hhu.bsinfo.restTerminal.data.NameGetResponse;
import de.hhu.bsinfo.restTerminal.data.NameListResponse;
import de.hhu.bsinfo.restTerminal.request.NameGetRequest;
import de.hhu.bsinfo.restTerminal.request.NameRegRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;

/**
 * Representations of DXRAM-REST-API functions having to do with the naming of chunks
 */
public interface NameService  {
    @GET("/namelist")
    Call<NameListResponse> nameList();

    @PUT("/namereg")
    Call<Void> nameReg(@Body NameRegRequest p_nameRegRequest);

    @PUT("/nameget")
    Call<NameGetResponse> nameGet(@Body NameGetRequest p_nameGetRequest);

}
