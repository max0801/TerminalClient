package de.hhu.bsinfo.restTerminal.rest;

import de.hhu.bsinfo.restTerminal.data.Message;
import de.hhu.bsinfo.restTerminal.data.NameListData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface NameService  {
    @GET("/namelist")
    Call<NameListData> nameList();

    @GET("/namereg")
    Call<Message> nameReg(@Query("cid") String cid, @Query("name") String name);

    @GET("/nameget")
    Call<String> nameGet(@Query("name") String name);

}
