package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.files.FileSaving;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.files.LogFileSaver;
import de.hhu.bsinfo.restTerminal.rest.AppService;
import de.hhu.bsinfo.restTerminal.rest.LogService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@ShellComponent
public class LogInfo extends AbstractCommand implements FileSaving {
    private LogService logService = retrofit.create(LogService.class);
    private String nid;
    private  String ON_FAILURE_MESSAGE = "NO RESPONSE";
    private  String FOLDER_PATH = "LogInfo"+ File.separator ;
    private  String currentDateTime;
    private  String LOGINFO_RESPONSE;
    private  String ERROR_MESSAGE;
    private  String ON_SUCCESS_MESSAGE;
    @ShellMethod(value = "Prints the log utilization of given peer", group = "Log Commands")
    public void loginfo(
            @ShellOption(value = {"--nid", "-n"},
                    help = "Node <nid> where the log info is requested from") String nid) {
        this.nid = nid;

        String message = "Log Info of node "+nid;
        System.out.println(message);
//        Call<String> call = chunkService.chunkCreate(nid, size);
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if (APIError.isError(response.body())) {
//                    APIError error = ErrorUtils.parseError(response);
//                    saveToLogFile(error.getMessage());
//                } else {
//                    String answer = response.body();
//                    saveToLogFile(answer);
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                saveToLogFile(ON_FAILURE_MESSAGE);
//            }
//        });
    }

    @Override
    public void saveErrorResponse() throws IOException {
        Path logFilePath = Paths.get(ROOT_PATH + FOLDER_PATH + currentDateTime + "log.txt");
        Files.write(logFilePath, ERROR_MESSAGE.getBytes(), StandardOpenOption.CREATE);
        printErrorToTerminal();
    }

    @Override
    public void saveSuccessfulResponse() throws IOException {
        Path logFilePath = Paths.get(ROOT_PATH + FOLDER_PATH + currentDateTime + "log.txt");
        Files.write(logFilePath, ON_SUCCESS_MESSAGE.getBytes(), StandardOpenOption.CREATE);

        Path dataFilePath = Paths.get(ROOT_PATH + FOLDER_PATH + currentDateTime + "data.txt");
        Files.write(dataFilePath, LOGINFO_RESPONSE.getBytes(), StandardOpenOption.CREATE);
    }

    @Override
    public void printErrorToTerminal() {
        System.out.println("ERROR");
        System.out.println("Please check out the following file: "
                + ROOT_PATH + FOLDER_PATH + currentDateTime + "log.txt");
    }
}