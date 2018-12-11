package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.data.Message;
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
    private AppService appService = m_retrofit.create(AppService.class);
    private String folderPath = "AppRun" + File.separator;
    private String currentDateTime;
    private Message appRunResponse;
    private String errorMessage;

    /**
     * runs a dxram app on a specific node
     * @param nid nodeID of the Node where the dxram app is started
     * @param appName name of app that is supposed to be run
     */
    @ShellMethod(value = "Starts application <app> on remote node <nid>.",
            group = "App Commands")

    public void apprun(
            @ShellOption(
                    value = {"--nid", "-n"},
                    help = "Node <nid> with all available applications")
                    String nid,
            @ShellOption(
                    value = {"--app", "-a"},
                    help = "name of app that is supposed to be run")
                    String appName) {

        currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                m_rootPath + folderPath, false);
       Call<Message> call = appService.appRun(new AppRunRequest(nid, appName));
        Response<Message> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!response.isSuccessful()) {
            APIError error = ErrorUtils.parseError(response, m_retrofit);
            errorMessage = error.getError();
            saveErrorResponse();
        } else {
            appRunResponse = response.body();
            saveSuccessfulResponse();
        }
    }

    @Override
    public void saveErrorResponse() {
        try {
            Path logFilePath = Paths.get(m_rootPath + folderPath
                    + currentDateTime + "log.txt");
            Files.write(logFilePath, errorMessage.getBytes(),
                    StandardOpenOption.CREATE);
            printErrorToTerminal();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveSuccessfulResponse() {
        try {
            Path logFilePath = Paths.get(m_rootPath + folderPath
                    + currentDateTime + "log.txt");
            Files.write(logFilePath, appRunResponse.getMessage().getBytes(),
                    StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printErrorToTerminal() {
        System.out.println("ERRROR: Please check out the following file: "
                + m_rootPath + folderPath + currentDateTime + "log.txt");

    }
}
