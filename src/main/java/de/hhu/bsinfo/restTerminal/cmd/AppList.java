package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
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
import java.util.List;


/**
 * Class for handling the applist command
 */
@ShellComponent
public class AppList extends AbstractCommand  {
    private AppService appService = m_retrofit.create(AppService.class);
    private String folderPath = "AppList" + File.separator;
    private String currentDateTime;
    private String onSuccessMessage;
    private List<String> appListResponse;
    private String errorMessage;
    private boolean print;

    @ShellMethod(value = "Lists available applications of DXRAM.",
            group = "App Commands")

    /**
     * prints the list of all dxram applications
     *
     * @param print if true prints the applist to stdout
     *
     */
    public void applist(
            @ShellOption(
                    value = {"--print", "-p"},
                    help = "print applist to stdout", defaultValue = "false")
                    boolean print) {

        this.print = print;
        currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                m_rootPath + folderPath, true);
        Call<List<String>> call = appService.appList();
        Response<List<String>> response = null;
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
            onSuccessMessage = "AppList has been received";
            appListResponse = response.body();
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
            Path logFilePath = Paths.get(m_rootPath +
                    folderPath + currentDateTime + "log.txt");
            Files.write(logFilePath, onSuccessMessage.getBytes(),
                    StandardOpenOption.CREATE);
            Path dataFilePath = Paths.get(m_rootPath + folderPath +
                    currentDateTime + "data.txt");
            for (String app : appListResponse) {
                if(print){
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
                + m_rootPath + folderPath + currentDateTime + "log.txt");
    }
}

