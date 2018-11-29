package de.hhu.bsinfo.restTerminal.rest;

import de.hhu.bsinfo.restTerminal.cmd.AppRun;
import de.hhu.bsinfo.restTerminal.data.Message;
import de.hhu.bsinfo.restTerminal.request.AppRunRequest;
import retrofit2.Call;
import retrofit2.http.*;

import java.util.List;

public interface AppService {
    @PUT("/applist")
    Call<List<String>>appList();

    @PUT("/apprun")
    Call<Message> appRun(@Body AppRunRequest p_appRunRequest);

}
