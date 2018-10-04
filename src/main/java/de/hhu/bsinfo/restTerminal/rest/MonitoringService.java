package de.hhu.bsinfo.restTerminal.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import java.util.List;
public interface MonitoringService {
    //TODO handle MonitoringDataStructure
    @GET("/monitor?nid={nid}")
    Call<String> monitor(@Path("nid") int nid);


}
