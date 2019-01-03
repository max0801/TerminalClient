package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.response.AppListResponse;
import de.hhu.bsinfo.restTerminal.error.APIError;
import de.hhu.bsinfo.restTerminal.error.ErrorUtils;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.rest.AppService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import retrofit2.Call;
import retrofit2.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


/**
 * Class for handling the applist command
 */
@ShellComponent
public class AppList extends AbstractCommand  {
    private AppService m_appService = m_retrofit.create(AppService.class);
    private String m_folderPath = "AppList" + File.separator;
    private String m_currentDateTime;
    private String m_onSuccessMessage;
    private AppListResponse m_appListResponse;
    private String m_errorMessage;
    private boolean m_print;

    @ShellMethod(value = "Lists available applications of DXRAM.",
            group = "App Commands")

    /**
     * prints the list of all dxram applications
     *
     * @param p_print if true prints the applist to stdout
     *
     */
    public void applist(
            @ShellOption(
                    value = {"--print", "-p"},
                    help = "print applist to stdout", defaultValue = "false")
                    boolean p_print) {

        this.m_print = p_print;
        m_currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                m_rootPath + m_folderPath, true);
        Call<AppListResponse> call = m_appService.appList();
        Response<AppListResponse> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!response.isSuccessful()) {
            APIError error = ErrorUtils.parseError(response, m_retrofit);
            m_errorMessage = error.getError();
            saveErrorResponse();
        } else {
            m_onSuccessMessage = "AppList has been received";
            m_appListResponse = response.body();
            saveSuccessfulResponse();
        }
    }

    @Override
    public void saveErrorResponse() {
        try {
            Path logFilePath = Paths.get(m_rootPath + m_folderPath
                    + m_currentDateTime + "log.txt");
            Files.write(logFilePath, m_errorMessage.getBytes(),
                    StandardOpenOption.CREATE);
            printErrorToTerminal();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveSuccessfulResponse() {
        try {
            Path logFilePath = Paths.get(m_rootPath +
                    m_folderPath + m_currentDateTime + "log.txt");
            Files.write(logFilePath, m_onSuccessMessage.getBytes(),
                    StandardOpenOption.CREATE);
            Path dataFilePath = Paths.get(m_rootPath + m_folderPath +
                    m_currentDateTime + "response.txt");
            for (String app : m_appListResponse.getApplist()) {
                if(m_print){
                    System.out.println(app);
                }
                Files.write(dataFilePath, app.getBytes(),
                        StandardOpenOption.APPEND);
                Files.write(dataFilePath, System.lineSeparator().getBytes(),
                        StandardOpenOption.APPEND);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void printErrorToTerminal() {
        System.out.println("ERROR");
        System.out.println("Please check out the following file: "
                + m_rootPath + m_folderPath + m_currentDateTime + "log.txt");
    }
}

