package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.error.APIError;
import de.hhu.bsinfo.restTerminal.error.ErrorUtils;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.parsing.ParsingCid;
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


/**
 * Class for handling the chunkput command
 */
@ShellComponent
public class ChunkPut extends AbstractCommand {
    private ChunkService m_chunkService = m_retrofit.create(ChunkService.class);
    private String m_folderPath = "ChunkPut" + File.separator;
    private String m_currentDateTime;
    private String m_chunkPutResponse;
    private String m_errorMessage;
    private static final String CHUNK_REGEX = "(0x(.{16}?))|(.{16}?)";

    /**
     * stores response on a chunk
     * @param p_cid chunk where response is saved
     * @param p_data response which is stored on the chunk
     * @param p_type response type of the stored response
     */
    @ShellMethod(value = "Puts <response> with <type> on chunk <cid>.",
            group = "Chunk Commands")
    public void chunkput(
            @ShellOption(
                    value = {"--cid", "-c"},
                    help = "chunk ID of the submitted chunk")
            @Pattern(
                    regexp = CHUNK_REGEX,
                    message = "Regex Pattern: " + CHUNK_REGEX) String p_cid,
            @ShellOption(
                    value = {"--response", "-d"}, help = "response that is saved in the chunk")
                    Object p_data,
            @ShellOption(
                    value = {"--type", "-t"}, defaultValue = "str",
                    help = "type of the submitted chunk [str,byte,short,int,long]")
            @Pattern(
                    regexp = "str|long|int|byte|short",
                    message = "Supported datatypes: str,byte,short,int,long")
                    String p_type) {
        long cidLong = ParsingCid.parse(p_cid);
        m_currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                m_rootPath + m_folderPath, false);
        Call<Void> call = m_chunkService.chunkPut(new ChunkPutRequest(cidLong, p_data, p_type));
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
            m_chunkPutResponse = "The following data has been succesfully put " +
                    "on chunk "+ p_cid + ": "+ p_data;
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
            Files.write(logFilePath, m_chunkPutResponse.getBytes(),
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


