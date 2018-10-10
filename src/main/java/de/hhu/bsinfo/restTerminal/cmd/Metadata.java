package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.files.LogFileSaver;
import de.hhu.bsinfo.restTerminal.rest.AppService;
import de.hhu.bsinfo.restTerminal.rest.MetadataService;
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
public class Metadata extends AbstractCommand implements LogFileSaver<String> {
    private MetadataService metadataService = retrofit.create(MetadataService.class);
    private int nid;
    private static String ON_FAILURE_MESSAGE = "NO RESPONSE";
    private static String FOLDER_PATH = "Metadata"+ File.separator ;

    @ShellMethod(value = "Get summary of all or one superper's metadata", group = "Metadata Commands")
    public void metadata(
            @ShellOption(value = {"--nid", "-n"}, defaultValue = "-1", help = "Node <nid> where the metadata is requested from") int nid) {
        this.nid = nid;
        //TODO add functionality to distinguish between getting metadata from one or all peers
        String message = "print metadata";
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
