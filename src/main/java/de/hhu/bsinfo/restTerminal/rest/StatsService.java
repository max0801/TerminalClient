package de.hhu.bsinfo.restTerminal.rest;

import de.hhu.bsinfo.restTerminal.cmd.StatsPrint;
import de.hhu.bsinfo.restTerminal.request.StatsPrintRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface StatsService {
    @PUT("/statsprint")
    Call<String> printStats(@Body StatsPrintRequest p_statsPrintRequest);
}
