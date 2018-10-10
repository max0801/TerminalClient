package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
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
public class LogInfo extends AbstractCommand implements LogFileSaver<String> {
    private LogService logService = retrofit.create(LogService.class);
    private int nid;
    private static String ON_FAILURE_MESSAGE = "NO RESPONSE";
    private static String FOLDER_PATH = "LogInfo"+ File.separator ;

    @ShellMethod(value = "Prints the log utilization of given peer", group = "Log Commands")
    public void loginfo(
            @ShellOption(value = {"--nid", "-n"}, help = "Node <nid> where the log info is requested from") int nid) {
        this.nid = nid;

        String message = "Log Info of node "+nid;
        System.out.println(message);
        saveToLogFile(message);

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
    public void saveToLogFile(String chunkCreate) {
        try {
            String dateTime = FolderHierarchy.createDateTimeFolderHierarchy(ROOT_PATH + FOLDER_PATH, false);
            Path logFilePath = Paths.get(ROOT_PATH + FOLDER_PATH + dateTime + "log.txt");
            Files.write(logFilePath, chunkCreate.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }


    }


}
