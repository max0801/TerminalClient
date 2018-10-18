package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.data.ChunkRange;
import de.hhu.bsinfo.restTerminal.data.Message;
import de.hhu.bsinfo.restTerminal.error.APIError;
import de.hhu.bsinfo.restTerminal.error.ErrorUtils;
import de.hhu.bsinfo.restTerminal.files.FileSaving;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
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
public class ChunkList extends AbstractCommand implements FileSaving {
    private ChunkService chunkService = retrofit.create(ChunkService.class);
    private String currentDateTime;
    private ChunkRange CHUNKLIST_RESPONSE;
    private String ERROR_MESSAGE;
    private String ON_SUCCESS_MESSAGE;
    private String FOLDER_PATH = "ChunkList" + File.separator;
    private String nid;

    @ShellMethod(value = "list all chunks on node <nid>", group = "Chunk Commands")
    public void chunklist(
            @ShellOption(value = {"--nid", "-n"},
                    help = "The node <nid> where the list of chunks is referring to") String nid,
            @ShellOption(value = {"--print", "-p"},
                    help = "print chunklist to stdout", defaultValue = "false") boolean print) {

        this.nid = nid;

        currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                ROOT_PATH + FOLDER_PATH, true);
        Call<ChunkRange> call = chunkService.chunkList(nid);
        Response<ChunkRange> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!response.isSuccessful()) {
            APIError error = ErrorUtils.parseError(response, retrofit);
            ERROR_MESSAGE = error.getError();
            saveErrorResponse();
        } else {
            CHUNKLIST_RESPONSE = response.body();
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
            Files.write(logFilePath, ON_SUCCESS_MESSAGE.getBytes(), StandardOpenOption.CREATE);
            Path dataFilePath = Paths.get(ROOT_PATH + FOLDER_PATH + currentDateTime + "data.txt");
            Files.write(dataFilePath, CHUNKLIST_RESPONSE.getLocalChunkRanges().getBytes(),
                    StandardOpenOption.APPEND);
            Files.write(dataFilePath, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
            Files.write(dataFilePath, CHUNKLIST_RESPONSE.getMigratedChunkRanges().getBytes(),
                    StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void printErrorToTerminal() {
        System.out.println("ERROR");
        System.out.println("Please check out the following file: "
                + ROOT_PATH + FOLDER_PATH + currentDateTime + "log.txt");
    }
}






