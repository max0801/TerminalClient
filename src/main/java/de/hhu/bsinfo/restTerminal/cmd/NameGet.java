package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.error.APIError;
import de.hhu.bsinfo.restTerminal.error.ErrorUtils;
import de.hhu.bsinfo.restTerminal.files.FileSaving;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.rest.NameService;
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

@ShellComponent
public class NameGet extends AbstractCommand implements FileSaving {
    private NameService nameService = retrofit.create(NameService.class);
    private String name;
    private String folderPath = "NameGet" + File.separator;
    private String currentDateTime;
    private String nameGetResponse;
    private String errorMessage;
    private String onSuccessMessage;
    private boolean print;
    @ShellMethod(value = "Get chunk id of a named chunk", group = "Name Commands")
    public void nameget(
            @ShellOption(value = {"--name", "-n"}, help = "name of the chunk which is requested") String name,
            @ShellOption(value = {"--print", "-p"},
                    help = "print chunk to stdout", defaultValue = "false") boolean print) {
        this.name = name;
        this.print = print;
        currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                rootPath + folderPath, true);
        Call<String> call = nameService.nameGet(name);
        Response<String> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!response.isSuccessful()) {
            APIError error = ErrorUtils.parseError(response, retrofit);
            errorMessage = error.getError();
            saveErrorResponse();
        } else {
            onSuccessMessage = "Chunk with the name " + name + " has been received";
            nameGetResponse = response.body();
            saveSuccessfulResponse();
        }
    }

    @Override
    public void saveErrorResponse() {
        try {
            Path logFilePath = Paths.get(rootPath + folderPath + currentDateTime + "log.txt");
            Files.write(logFilePath, errorMessage.getBytes(), StandardOpenOption.CREATE);
            printErrorToTerminal();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveSuccessfulResponse() {
        try {
            Path logFilePath = Paths.get(rootPath + folderPath + currentDateTime + "log.txt");
            Files.write(logFilePath, onSuccessMessage.getBytes(), StandardOpenOption.CREATE);
            Path dataFilePath = Paths.get(rootPath + folderPath + currentDateTime + "data.txt");
            Files.write(dataFilePath, nameGetResponse.getBytes(), StandardOpenOption.CREATE);
            if(print){
                System.out.println(nameGetResponse);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printErrorToTerminal() {
        System.out.println("ERROR");
        System.out.println("Please check out the following file: "
                + rootPath + folderPath + currentDateTime + "log.txt");
    }
}
