package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.response.ChunkListResponse;
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
    private ChunkService m_chunkService = m_retrofit.create(ChunkService.class);
    private String m_currentDateTime;
    private ChunkListResponse m_chunkListResponse;
    private String m_errorMessage;
    private String m_onSuccessMessage;
    private String m_folderPath = "ChunkList" + File.separator;
    private boolean m_print;
    private static final String NODE_REGEX = "(0x(.{4}?))|(.{4}?)";

    /**
     * requests the list of all registered chunks on a specific node
     * @param p_nid node to which the chunklist is referring to
     * @param p_print if true, prints list of all chunks to stdout
     */
    @ShellMethod(value = "Lists all chunks on node <nid>.",
            group = "Chunk Commands")
    public void chunklist(
            @ShellOption(
                    value = {"--nid", "-n"},
                    help = "The node <nid> where the list of chunks is referring to")
            @Pattern(
                    regexp = NODE_REGEX,
                    message = "Regex Pattern: " + NODE_REGEX) String p_nid,
            @ShellOption(
                    value = {"--print", "-p"},
                    help = "print chunklist to stdout", defaultValue = "false")
                    boolean p_print) {

        this.m_print = p_print;
        m_currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                m_rootPath + m_folderPath, true);
        Call<ChunkListResponse> call = m_chunkService.chunkList(new ChunkListRequest(p_nid));
        Response<ChunkListResponse> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!response.isSuccessful()) {
            APIError error = ErrorUtils.parseError(response, m_retrofit);
            m_errorMessage = error.getError();
            saveErrorResponse();
        } else {
            m_onSuccessMessage = "Chunklist of node " + p_nid + " has been received";
            m_chunkListResponse = response.body();
            saveSuccessfulResponse();
        }
    }

    @Override
    public void saveErrorResponse() {
        try {
            Path logFilePath = Paths.get(m_rootPath + m_folderPath
                    + m_currentDateTime + "log.txt");
            Files.write(logFilePath, m_errorMessage.getBytes(),
                    StandardOpenOption.CREATE);
            printErrorToTerminal();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveSuccessfulResponse() {
        try {
            Path logFilePath = Paths.get(m_rootPath + m_folderPath
                    + m_currentDateTime + "log.txt");
            Files.write(logFilePath, m_onSuccessMessage.getBytes(),
                    StandardOpenOption.CREATE);
            Path dataFilePath = Paths.get(m_rootPath + m_folderPath
                    + m_currentDateTime + "response.txt");
            Files.write(dataFilePath, m_chunkListResponse.getLocalChunkRanges().getBytes(),
                    StandardOpenOption.APPEND);
            Files.write(dataFilePath, System.lineSeparator().getBytes(),
                    StandardOpenOption.APPEND);
            Files.write(dataFilePath, m_chunkListResponse.getMigratedChunkRanges().getBytes(),
                    StandardOpenOption.APPEND);
            if (m_print) {
                System.out.println("Local Chunk Ranges: "
                        + m_chunkListResponse.getLocalChunkRanges());
                System.out.println("Migrated Chunk Ranges: "
                        + m_chunkListResponse.getMigratedChunkRanges());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void printErrorToTerminal() {
        System.out.println("ERROR");
        System.out.println("Please check out the following file: "
                + m_rootPath + m_folderPath + m_currentDateTime + "log.txt");
    }
}






