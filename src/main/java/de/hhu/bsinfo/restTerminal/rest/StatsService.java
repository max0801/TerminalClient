package de.hhu.bsinfo.restTerminal.rest;

import de.hhu.bsinfo.restTerminal.response.StatsPrintResponse;
import de.hhu.bsinfo.restTerminal.request.StatsPrintRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;

/**
 * Representations of DXRAM-REST-API functions having to do with statistics of
 * the current DXRAM instance
 */
public interface StatsService {
    @PUT("/statsprint")
    Call<StatsPrintResponse> printStats(@Body StatsPrintRequest p_interval);
}
