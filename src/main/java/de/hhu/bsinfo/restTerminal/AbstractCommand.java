package de.hhu.bsinfo.restTerminal;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.io.File;

public abstract class AbstractCommand {
    protected Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://localhost:8009/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    protected String ROOT_PATH = System.getProperty("user.home") + File.separator + "RestTerminal"+ File.separator;

    public abstract void printErrorToTerminal();


}
