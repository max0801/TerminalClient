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
import retrofit2.Response;

import javax.validation.constraints.Pattern;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@ShellComponent
public class ChunkDump extends AbstractCommand implements FileSaving {
    private ChunkService chunkService = retrofit.create(ChunkService.class);
    private String folderPath = "ChunkDump" + File.separator;
    private String currentDateTime;
    private Message chunkDumpResponse;
    private String errorMessage;
    private String filename;
    private String cid;
    private static final String CHUNK_REGEX = "(0x(.{16}?))|(.{16}?)";
    private static final String FILENAME_REGEX = "^[^<>:|?*]*$";
    @ShellMethod(value = "creates a filedump of <cid> saved as <name>", group = "Chunk Commands")
    public void chunkdump(
            @ShellOption(value = {"--filename", "-f"}, help = "filename of the dump")
                    @Pattern(regexp = FILENAME_REGEX, message = "Invalid filename") String filename,
            @ShellOption(value = {"--cid", "-c"}, help = "chunk of which a filedump is created")
            @Pattern(regexp = CHUNK_REGEX, message = "Invalid ChunkID") String cid) {
        this.filename = filename;
        this.cid = cid;
        currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                rootPath + folderPath, false);
        Call<Message> request = chunkService.chunkDump(cid, filename);

        Response<Message> response = null;
        try {
            response = request.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!response.isSuccessful()) {
            APIError error = ErrorUtils.parseError(response, retrofit);
            errorMessage = error.getError();
            saveErrorResponse();
        } else {
            chunkDumpResponse = response.body();
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
            Files.write(logFilePath, chunkDumpResponse.getMessage().getBytes(), StandardOpenOption.CREATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printErrorToTerminal() {
        System.out.println("ERROR");
        System.out.println("Please check out the following file: " +
                rootPath + folderPath + currentDateTime + "log.txt");
    }
}
