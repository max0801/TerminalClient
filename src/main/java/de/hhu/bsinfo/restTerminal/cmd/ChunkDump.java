package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.customization.CustomPromptProvider;
import de.hhu.bsinfo.restTerminal.data.Message;
import de.hhu.bsinfo.restTerminal.error.APIError;
import de.hhu.bsinfo.restTerminal.error.ErrorUtils;
import de.hhu.bsinfo.restTerminal.files.FileSaving;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.files.LogFileSaver;
import de.hhu.bsinfo.restTerminal.rest.ChunkService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@ShellComponent
public class ChunkDump extends AbstractCommand implements FileSaving {
    private ChunkService chunkService = retrofit.create(ChunkService.class);
    private String FOLDER_PATH = "ChunkDump" + File.separator;
    private String currentDateTime;
    private Message CHUNKDUMP_RESPONSE;
    private String ERROR_MESSAGE;
    private String filename;
    private String cid;
    @ShellMethod(value = "creates a filedump of <cid> saved as <name>", group = "Chunk Commands")
    public void chunkdump(
            @ShellOption(value = {"--filename", "-f"}, help = "filename of the dump") String filename,
            @ShellOption(value = {"--cid", "-c"}, help = "chunk of which a filedump is created") String cid) {
        this.filename = filename;
        this.cid = cid;
        currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                ROOT_PATH + FOLDER_PATH, false);
        Call<Message> request = chunkService.chunkDump(cid, filename);

        Response<Message> response = null;
        try {
            response = request.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!response.isSuccessful()) {
            APIError error = ErrorUtils.parseError(response, retrofit);
            ERROR_MESSAGE = error.getError();
            saveErrorResponse();
        } else {
            CHUNKDUMP_RESPONSE = response.body();
            saveSuccessfulResponse();
        }
    }

    @Override
    public void saveErrorResponse() {
        try {
            Path logFilePath = Paths.get(ROOT_PATH + FOLDER_PATH + currentDateTime + "log.txt");
            Files.write(logFilePath, ERROR_MESSAGE.getBytes(), StandardOpenOption.CREATE);
            printErrorToTerminal();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveSuccessfulResponse() {
        try {
            Path logFilePath = Paths.get(ROOT_PATH + FOLDER_PATH + currentDateTime + "log.txt");
            Files.write(logFilePath, CHUNKDUMP_RESPONSE.getMessage().getBytes(), StandardOpenOption.CREATE);
            System.out.println("created Filedump with name " + filename + " of Chunk " + cid);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printErrorToTerminal() {
        System.out.println("ERROR");
        System.out.println("Please check out the following file: " +
                ROOT_PATH + FOLDER_PATH + currentDateTime + "log.txt");
    }
}
