package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.data.ChunkRange;
import de.hhu.bsinfo.restTerminal.error.APIError;
import de.hhu.bsinfo.restTerminal.error.ErrorUtils;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.request.ChunkListRequest;
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
 * Class for handling the chunklist command
 */
@ShellComponent
public class ChunkList extends AbstractCommand {
    private ChunkService chunkService = m_retrofit.create(ChunkService.class);
    private String currentDateTime;
    private ChunkRange chunkListResponse;
    private String errorMessage;
    private String onSuccessMessage;
    private String folderPath = "ChunkList" + File.separator;
    private boolean print;
    private static final String NODE_REGEX = "(0x(.{4}?))|(.{4}?)";

    /**
     * requests the list of all registered chunks on a specific node
     * @param nid node to which the chunklist is referring to
     * @param print if true, prints list of all chunks to stdout
     */
    @ShellMethod(value = "Lists all chunks on node <nid>.",
            group = "Chunk Commands")
    public void chunklist(
            @ShellOption(
                    value = {"--nid", "-n"},
                    help = "The node <nid> where the list of chunks is referring to")
            @Pattern(
                    regexp = NODE_REGEX,
                    message = "Regex Pattern: " + NODE_REGEX) String nid,
            @ShellOption(
                    value = {"--print", "-p"},
                    help = "print chunklist to stdout", defaultValue = "false")
                    boolean print) {

        this.print = print;
        currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                m_rootPath + folderPath, true);
        Call<ChunkRange> call = chunkService.chunkList(new ChunkListRequest(nid));
        Response<ChunkRange> response = null;
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
            onSuccessMessage = "Chunklist of node " + nid + " has been received";
            chunkListResponse = response.body();
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
            Files.write(dataFilePath, chunkListResponse.getLocalChunkRanges().getBytes(),
                    StandardOpenOption.APPEND);
            Files.write(dataFilePath, System.lineSeparator().getBytes(),
                    StandardOpenOption.APPEND);
            Files.write(dataFilePath, chunkListResponse.getMigratedChunkRanges().getBytes(),
                    StandardOpenOption.APPEND);
            if (print) {
                System.out.println("Local Chunk Ranges: "
                        + chunkListResponse.getLocalChunkRanges());
                System.out.println("Migrated Chunk Ranges: "
                        + chunkListResponse.getMigratedChunkRanges());
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






