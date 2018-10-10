package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.files.LogFileSaver;
import de.hhu.bsinfo.restTerminal.rest.AppService;
import de.hhu.bsinfo.restTerminal.rest.NameService;
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
public class NameGet extends AbstractCommand implements LogFileSaver<String> {
    private NameService nameService = retrofit.create(NameService.class);
    private String name;
    private static String ON_FAILURE_MESSAGE = "NO RESPONSE";
    private static String FOLDER_PATH = "NameGet"+ File.separator ;

    @ShellMethod(value = "Get chunk by name from nameservice", group = "Name Commands")
    public void nameget(
            @ShellOption(value = {"--name", "-n"}, help = "name of the chunk which is requested") String name) {
        this.name = name;
        String message = "print Chunk with name "+name;
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
