package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.data.Message;
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

import javax.validation.constraints.Pattern;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


@ShellComponent
public class NameReg extends AbstractCommand implements FileSaving {
    private NameService nameService = retrofit.create(NameService.class);
    private String folderPath = "NameReg" + File.separator;
    private String currentDateTime;
    private Message nameRegResponse;
    private String errorMessage;
    private String cid;
    private String name;
    private static final String CHUNK_REGEX = "(0x(.{16}?))|(.{16}?)";

    @ShellMethod(value = "register chunk <cid> with <name>", group = "Name Commands")
    public void namereg(
            @ShellOption(value = {"--cid", "-c"}, help = "chunk <cid> which is named")
                @Pattern(regexp = CHUNK_REGEX, message = "Invalid ChunkID") String cid,
            @ShellOption(value = {"--name", "-n"}, help = "name of the chunk") String name) {
        this.cid = cid;
        this.name = name;
        currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                rootPath + folderPath, false);
        nameService = retrofit.create(NameService.class);
        Call<Message> call = nameService.nameReg(cid, name);
        Response<Message> response = null;
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
            nameRegResponse = response.body();
            saveSuccessfulResponse();
        }
    }

    @Override
    public void printErrorToTerminal() {
        System.out.println("ERROR");
        System.out.println("Please check out the following file: "
                + rootPath + folderPath + currentDateTime + "log.txt");
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
            Files.write(logFilePath, nameRegResponse.getMessage().getBytes(),
                    StandardOpenOption.CREATE);
            Files.write(logFilePath, (" for the following chunk id: " + cid).getBytes(),
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




