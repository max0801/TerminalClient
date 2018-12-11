package de.hhu.bsinfo.restTerminal.rest;

import de.hhu.bsinfo.restTerminal.data.Message;
import de.hhu.bsinfo.restTerminal.data.NameListData;
import de.hhu.bsinfo.restTerminal.request.NameGetRequest;
import de.hhu.bsinfo.restTerminal.request.NameRegRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Representations of DXRAM-REST-API functions having to do with the naming of chunks
 */
public interface NameService  {
    @GET("/namelist")
    Call<NameListData> nameList();

    @PUT("/namereg")
    Call<Message> nameReg(@Body NameRegRequest p_nameRegRequest);

    @PUT("/nameget")
    Call<String> nameGet(@Body NameGetRequest p_nameGetRequest);

}
