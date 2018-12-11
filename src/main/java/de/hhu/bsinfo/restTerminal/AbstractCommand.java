package de.hhu.bsinfo.restTerminal;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.File;

/**
 * Base Class for all commands
 */
public abstract class AbstractCommand {
    protected Retrofit m_retrofit = new Retrofit.Builder()
            .baseUrl("http://localhost:8009/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    protected String m_rootPath = System.getProperty("user.home")
            + File.separator + "RestTerminal"+ File.separator;

    /**
     * print an error message to the user indicating the location of the logfile
     */
    public abstract void printErrorToTerminal();


    /**
     * saves the content of error responses to the filesystem
     */
    public abstract void saveErrorResponse();

    /**
     * saves the content of successful responses to the filesystem
     */
    public abstract void saveSuccessfulResponse();

}
