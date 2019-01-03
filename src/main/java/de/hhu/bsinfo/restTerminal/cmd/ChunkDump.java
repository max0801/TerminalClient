package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.error.APIError;
import de.hhu.bsinfo.restTerminal.error.ErrorUtils;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.parsing.ParsingCid;
import de.hhu.bsinfo.restTerminal.request.ChunkDumpRequest;
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
 * Class for handling the chunkdump command
 */
@ShellComponent
public class ChunkDump extends AbstractCommand {
    private ChunkService m_chunkService = m_retrofit.create(ChunkService.class);
    private String m_folderPath = "ChunkDump" + File.separator;
    private String m_currentDateTime;
    private String m_chunkDumpResponse;
    private String m_errorMessage;
    private static final String CHUNK_REGEX = "(0x(.{16}?))|(.{16}?)";
    private static final String FILENAME_REGEX = "^[^<>:|?*]*$";

    /**
     * creates a filedump of a chunk
     * @param p_filename p_filename of the dump
     * @param p_cid chunk which a filedump is made from
     */
    @ShellMethod(value = "Creates a filedump of <cid> saved as <filename>.",
            group = "Chunk Commands")
    public void chunkdump(
            @ShellOption(
                    value = {"--filename", "-f"}, help = "filename of the dump")
            @Pattern(
                    regexp = FILENAME_REGEX,
                    message = "Regex Pattern: " + FILENAME_REGEX)
                    String p_filename,
            @ShellOption(
                    value = {"--cid", "-c"},
                    help = "chunk of which a filedump is created")
            @Pattern(
                    regexp = CHUNK_REGEX,
                    message = "Regex Pattern: " + CHUNK_REGEX)
                    String p_cid) {
        long cidLong = ParsingCid.parse(p_cid);
        m_currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                m_rootPath + m_folderPath, false);
        Call<Void> request = m_chunkService.chunkDump(new ChunkDumpRequest(p_filename, cidLong));
        Response<Void> response = null;
        try {
            response = request.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!response.isSuccessful()) {
            APIError error = ErrorUtils.parseError(response, m_retrofit);
            m_errorMessage = error.getError();
            saveErrorResponse();
        } else {
            m_chunkDumpResponse = "Chunk "+ p_cid + " has been successfully dumped " +
                    "with the p_filename "+ p_filename;
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
            Files.write(logFilePath, m_chunkDumpResponse.getBytes(),
                    StandardOpenOption.CREATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printErrorToTerminal() {
        System.out.println("ERROR");
        System.out.println("Please check out the following file: " +
                m_rootPath + m_folderPath + m_currentDateTime + "log.txt");
    }
}
