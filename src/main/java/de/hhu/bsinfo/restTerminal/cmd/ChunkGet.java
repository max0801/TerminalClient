package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.response.ChunkGetResponse;
import de.hhu.bsinfo.restTerminal.error.APIError;
import de.hhu.bsinfo.restTerminal.error.ErrorUtils;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.parsing.ParsingCid;
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
    private ChunkService m_chunkService = m_retrofit.create(ChunkService.class);
    private String m_folderPath = "ChunkGet" + File.separator;
    private String m_currentDateTime;
    private String m_onSuccessMessage;
    private ChunkGetResponse m_chunkGetResponse;
    private String m_errorMessage;
    private boolean m_print;
    private static final String CHUNK_REGEX = "(0x(.{16}?))|(.{16}?)";

    /**
     * requests the content of a chunk
     * @param p_cid chunk which is requested
     * @param p_type representation of the chunk response
     * @param p_print if true, prints the content of chunk to stdout
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
                    String p_cid,
            @ShellOption(
                    value = {"--type", "-t"}, defaultValue = "str",
                    help = "type of the requested chunk [str,byte,short,int,long]")
            @Pattern(
                    regexp = "str|long|int|byte|short",
                    message = "Supported datatypes: str,byte,short,int,long")
                    String p_type,
            @ShellOption(
                    value = {"--print", "-p"}, help = "print chunk to stdout",
                    defaultValue = "false") boolean p_print) {

        this.m_print = p_print;
        long cidLong = ParsingCid.parse(p_cid);
        m_currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                m_rootPath + m_folderPath, true);
        Call<ChunkGetResponse> call = m_chunkService.chunkGet(new ChunkGetRequest(cidLong, p_type));
        Response<ChunkGetResponse> response = null;
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
            m_onSuccessMessage = "Received chunk  " + p_cid + " with p_type " + p_type + "";
            m_chunkGetResponse = response.body();
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
            Files.write(dataFilePath, String.valueOf(m_chunkGetResponse.getContent()).getBytes(),
                    StandardOpenOption.CREATE);
            if (m_print) {
                System.out.println("Content of Chunk: " + m_chunkGetResponse.getContent());
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

