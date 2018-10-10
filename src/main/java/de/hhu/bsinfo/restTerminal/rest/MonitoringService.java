package de.hhu.bsinfo.restTerminal.rest;

import de.hhu.bsinfo.dxram.monitoring.MonitoringDataStructure;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
public interface MonitoringService {
    @GET("/monitor?nid={nid}")
    Call<MonitoringDataStructure> monitor(@Path("nid") int nid);


}
