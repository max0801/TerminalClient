package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
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

import javax.validation.constraints.Pattern;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


@ShellComponent
public class ChunkGet extends AbstractCommand implements FileSaving {
    private ChunkService chunkService = retrofit.create(ChunkService.class);
    private String ON_FAILURE_MESSAGE = "NO RESPONSE";
    private String FOLDER_PATH = "ChunkGet" + File.separator;
    private String currentDateTime;
    private String ON_SUCCESS_MESSAGE;
    private String CHUNKGET_RESPONSE;
    private String ERROR_MESSAGE;
    private String cid;
    private String type;
    private static final String CHUNK_REGEX = "(0x(.{16}?))|(.{16}?)";

    @ShellMethod(value = "requests a chunk with id <cid> and type <type>", group = "Chunk Commands")
    public void chunkget(
            @ShellOption(value = {"--cid", "-c"}, help = "chunk ID of the requested chunk")
                @Pattern(regexp = CHUNK_REGEX, message = "Invalid ChunkID") String cid,
            @ShellOption(value = {"--type", "-t"}, defaultValue = "str",
                    help = "type of the requested chunk [str,byte,short,int,long]")
                    @Pattern(regexp = "str|long|int|byte|short", message = "Datatype is not supported") String type,
            @ShellOption(value = {"--print", "-p"},
                    help = "print chunk to stdout", defaultValue = "false") boolean print) {
        this.cid = cid;
        this.type = type;

        currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                ROOT_PATH + FOLDER_PATH, true);
        Call<String> call = chunkService.chunkGet(cid, type);
        Response<String> response = null;
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
            ON_SUCCESS_MESSAGE = "Received chunk  "+cid+ " with type "+type+"";
            CHUNKGET_RESPONSE = response.body();
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
            Files.write(dataFilePath, CHUNKGET_RESPONSE.getBytes(), StandardOpenOption.CREATE);
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

