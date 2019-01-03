package de.hhu.bsinfo.restTerminal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.File;

/**
 * Base Class for all commands
 */
public abstract class AbstractCommand {
    //private String baseUrl = System.getProperty("BASE_URL");
    //@Value("${app.baseUrl}")
    @Autowired
    private Environment m_environment;
    private String baseUrl = m_environment.getProperty("BASEURL");
    protected Retrofit m_retrofit = new Retrofit.Builder()
            //.baseUrl("http://localhost:8009/")
            .baseUrl(baseUrl)
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
