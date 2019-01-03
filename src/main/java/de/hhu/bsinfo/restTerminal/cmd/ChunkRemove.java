package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.error.APIError;
import de.hhu.bsinfo.restTerminal.error.ErrorUtils;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.parsing.ParsingCid;
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

/**
 * Class for handling the chunkremove command
 */
@ShellComponent
public class ChunkRemove extends AbstractCommand {
    private ChunkService m_chunkService = m_retrofit.create(ChunkService.class);
    private String m_folderPath = "ChunkRemove" + File.separator;
    private String m_currentDateTime;
    private String m_chunkRemoveResponse;
    private String m_errorMessage;
    private static final String CHUNK_REGEX = "(0x(.{16}?))|(.{16}?)";

    /**
     * deletes a chunk
     *
     * @param p_cid chunk which is deleted
     */
    @ShellMethod(value = "Remove chunk with chunk id <cid>.",
            group = "Chunk Commands")
    public void chunkremove(
            @ShellOption(
                    value = {"--cid", "-c"},
                    help = "chunk that is supposed to be removed")
            @Pattern(
                    regexp = CHUNK_REGEX,
                    message = "Regex Pattern: " + CHUNK_REGEX)
                    String p_cid) {
        long cidLong = ParsingCid.parse(p_cid);
        m_currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                m_rootPath + m_folderPath, false);
        Call<Void> call = m_chunkService.chunkRemove(new ChunkRemoveRequest(cidLong));
        Response<Void> response = null;
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
            m_chunkRemoveResponse = "Chunk " + p_cid + " has been successfully removed";
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
            Files.write(logFilePath, m_chunkRemoveResponse.getBytes(),
                    StandardOpenOption.CREATE);
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
