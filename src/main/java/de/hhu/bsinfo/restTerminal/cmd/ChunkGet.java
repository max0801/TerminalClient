package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.error.APIError;
import de.hhu.bsinfo.restTerminal.error.ErrorUtils;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.request.ChunkGetRequest;
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

/**
 * Class for handling the chunkget command
 */
@ShellComponent
public class ChunkGet extends AbstractCommand {
    private ChunkService chunkService = m_retrofit.create(ChunkService.class);
    private String folderPath = "ChunkGet" + File.separator;
    private String currentDateTime;
    private String onSuccessMessage;
    private String chunkGetResponse;
    private String errorMessage;
    private boolean print;
    private static final String CHUNK_REGEX = "(0x(.{16}?))|(.{16}?)";

    /**
     * requests the content of a chunk
     * @param cid chunk which is requested
     * @param type representation of the chunk data
     * @param print if true, prints the content of chunk to stdout
     */
    @ShellMethod(value = "Requests a chunk with chunk id <cid> and type <type>.",
            group = "Chunk Commands")
    public void chunkget(
            @ShellOption(
                    value = {"--cid", "-c"},
                    help = "chunk ID of the requested chunk")
            @Pattern(
                    regexp = CHUNK_REGEX,
                    message = "Regex Pattern: " + CHUNK_REGEX)
                    String cid,
            @ShellOption(
                    value = {"--type", "-t"}, defaultValue = "str",
                    help = "type of the requested chunk [str,byte,short,int,long]")
            @Pattern(
                    regexp = "str|long|int|byte|short",
                    message = "Supported datatypes: str,byte,short,int,long")
                    String type,
            @ShellOption(
                    value = {"--print", "-p"}, help = "print chunk to stdout",
                    defaultValue = "false") boolean print) {

        this.print = print;

        currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                m_rootPath + folderPath, true);
        Call<String> call = chunkService.chunkGet(new ChunkGetRequest(cid, type));
        Response<String> response = null;
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
            onSuccessMessage = "Received chunk  " + cid + " with type " + type + "";
            chunkGetResponse = response.body();
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
            Files.write(logFilePath, onSuccessMessage.getBytes(),
                    StandardOpenOption.CREATE);
            Path dataFilePath = Paths.get(m_rootPath + folderPath
                    + currentDateTime + "data.txt");
            Files.write(dataFilePath, chunkGetResponse.getBytes(),
                    StandardOpenOption.CREATE);
            if (print) {
                System.out.println("Content of Chunk: " + chunkGetResponse);
            }
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

