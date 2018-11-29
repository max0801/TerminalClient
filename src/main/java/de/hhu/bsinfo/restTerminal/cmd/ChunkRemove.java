package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.data.Message;
import de.hhu.bsinfo.restTerminal.error.APIError;
import de.hhu.bsinfo.restTerminal.error.ErrorUtils;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.request.ChunkPutRequest;
import de.hhu.bsinfo.restTerminal.request.ChunkRemoveRequest;
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
public class ChunkRemove extends AbstractCommand  {
    private ChunkService chunkService = m_retrofit.create(ChunkService.class);
    private String folderPath = "ChunkRemove" + File.separator;
    private String currentDateTime;
    private Message chunkRemoveResponse;
    private String errorMessage;
    private static final String CHUNK_REGEX = "(0x(.{16}?))|(.{16}?)";


    @ShellMethod(value = "Remove chunk with chunk id <cid>.",
            group = "Chunk Commands")
    public void chunkremove(
            @ShellOption(
                    value = {"--cid", "-c"},
                    help = "chunk that is supposed to be removed")
            @Pattern(regexp = CHUNK_REGEX, message = "Invalid ChunkID")
                    String cid) {

        currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                m_rootPath + folderPath, false);
        Call<Message> call = chunkService.chunkRemove(new ChunkRemoveRequest(cid));
        Response<Message> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!response.isSuccessful()) {
            APIError error = ErrorUtils.parseError(response, m_retrofit);
            errorMessage = error.getError();
            saveErrorResponse();
        } else {
            chunkRemoveResponse = response.body();
            saveSuccessfulResponse();
        }
    }

    @Override
    public void saveErrorResponse() {
        try {
            Path logFilePath = Paths.get(m_rootPath + folderPath
                    + currentDateTime + "log.txt");
            Files.write(logFilePath, errorMessage.getBytes(),
                    StandardOpenOption.CREATE);
            printErrorToTerminal();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveSuccessfulResponse() {
        try {
            Path logFilePath = Paths.get(m_rootPath + folderPath
                    + currentDateTime + "log.txt");
            Files.write(logFilePath, chunkRemoveResponse.getMessage().getBytes(),
                    StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printErrorToTerminal() {
        System.out.println("ERROR");
        System.out.println("Please check out the following file: "
                + m_rootPath + folderPath + currentDateTime + "log.txt");
    }
}
