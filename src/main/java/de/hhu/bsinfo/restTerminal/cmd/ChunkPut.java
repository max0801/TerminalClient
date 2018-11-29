package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.data.Message;
import de.hhu.bsinfo.restTerminal.error.APIError;
import de.hhu.bsinfo.restTerminal.error.ErrorUtils;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.request.ChunkPutRequest;
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
public class ChunkPut extends AbstractCommand {
    private ChunkService chunkService = m_retrofit.create(ChunkService.class);
    private String folderPath = "ChunkPut" + File.separator;
    private String currentDateTime;
    private Message chunkPutResponse;
    private String errorMessage;
    private static final String CHUNK_REGEX = "(0x(.{16}?))|(.{16}?)";

    @ShellMethod(value = "Puts <data> with <type> on chunk <cid>.",
            group = "Chunk Commands")
    public void chunkput(
            @ShellOption(
                    value = {"--cid", "-c"},
                    help = "chunk ID of the submitted chunk")
            @Pattern(
                    regexp = CHUNK_REGEX, message = "Invalid ChunkID") String cid,
            @ShellOption(
                    value = {"--data", "-d"}, help = "data that is saved in the chunk")
                    Object data,
            @ShellOption(
                    value = {"--type", "-t"}, defaultValue = "str",
                    help = "type of the submitted chunk [str,byte,short,int,long]")
            @Pattern(regexp = "str|long|int|byte|short",
                    message = "Datatype is not supported") String type) {

        currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                m_rootPath + folderPath, false);
        Call<Message> call = chunkService.chunkPut(new ChunkPutRequest(cid, data, type));
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
            chunkPutResponse = response.body();
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
            Files.write(logFilePath, chunkPutResponse.getMessage().getBytes(),
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


