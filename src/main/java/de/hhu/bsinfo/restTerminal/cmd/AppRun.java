package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.error.APIError;
import de.hhu.bsinfo.restTerminal.error.ErrorUtils;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.request.AppRunRequest;
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
 * Class for handling the apprun command
 */
@ShellComponent
public class AppRun extends AbstractCommand {
    private AppService m_appService = m_retrofit.create(AppService.class);
    private String m_folderPath = "AppRun" + File.separator;
    private String m_currentDateTime;
    private String m_appRunResponse;
    private String m_errorMessage;

    /**
     * runs a dxram app on a specific node
     * @param p_nid nodeID of the Node where the dxram app is started
     * @param p_appName name of app that is supposed to be run
     */
    @ShellMethod(value = "Starts application <app> on remote node <nid>.",
            group = "App Commands")

    public void apprun(
            @ShellOption(
                    value = {"--nid", "-n"},
                    help = "Node <nid> with all available applications")
                    String p_nid,
            @ShellOption(
                    value = {"--app", "-a"},
                    help = "name of app that is supposed to be run")
                    String p_appName) {

        m_currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                m_rootPath + m_folderPath, false);
       Call<Void> call = m_appService.appRun(new AppRunRequest(p_nid, p_appName));
        Response<Void> response = null;
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
            m_appRunResponse = "DXRAM app "+ p_appName + " started succesful";
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
            Path logFilePath = Paths.get(m_rootPath + m_folderPath
                    + m_currentDateTime + "log.txt");
            Files.write(logFilePath, m_appRunResponse.getBytes(),
                    StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printErrorToTerminal() {
        System.out.println("ERRROR: Please check out the following file: "
                + m_rootPath + m_folderPath + m_currentDateTime + "log.txt");

    }
}
