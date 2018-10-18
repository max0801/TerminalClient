package de.hhu.bsinfo.restTerminal.rest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import javax.swing.text.html.HTMLDocument;
import java.util.List;

public interface StatsService {
    @GET("/statsprint")
    Call<HTMLDocument> printStats(@Query("interval") int intervalInSeconds);
}
