package de.hhu.bsinfo.restTerminal.rest;

import de.hhu.bsinfo.dxram.monitoring.MonitoringDataStructure;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MonitoringService {
    @GET("/monitor")
    Call<MonitoringDataStructure> monitor(@Query("nid") String nid);


}
