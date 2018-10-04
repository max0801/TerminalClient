package de.hhu.bsinfo.restTerminal.rest;

import de.hhu.bsinfo.restTerminal.data.NameListData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

import java.util.List;

public interface NameService  {
    @GET("/namelist")
    Call<NameListData> nameList();

    @POST("/namereg?cid={cid}?=name={name}")
    Call<String> nameReg(@Path("cid")int cid, @Path("name") String name);


}
