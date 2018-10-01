package de.hhu.bsinfo.restTerminal;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AbstractCommand {
    protected Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://localhost:8009/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
}
