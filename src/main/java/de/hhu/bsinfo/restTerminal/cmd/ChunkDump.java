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
    private ChunkService chunkService = m_retrofit.create(ChunkService.class);
    private String folderPath = "ChunkDump" + File.separator;
    private String currentDateTime;
    private String chunkDumpResponse;
    private String errorMessage;
    private static final String CHUNK_REGEX = "(0x(.{16}?))|(.{16}?)";
    private static final String FILENAME_REGEX = "^[^<>:|?*]*$";

    /**
     * creates a filedump of a chunk
     * @param filename filename of the dump
     * @param cid chunk which a filedump is made from
     */
    @ShellMethod(value = "Creates a filedump of <cid> saved as <filename>.",
            group = "Chunk Commands")
    public void chunkdump(
            @ShellOption(
                    value = {"--filename", "-f"}, help = "filename of the dump")
            @Pattern(
                    regexp = FILENAME_REGEX,
                    message = "Regex Pattern: " + FILENAME_REGEX)
                    String filename,
            @ShellOption(
                    value = {"--cid", "-c"},
                    help = "chunk of which a filedump is created")
            @Pattern(
                    regexp = CHUNK_REGEX,
                    message = "Regex Pattern: " + CHUNK_REGEX)
                    String cid) {
        long cidLong = ParsingCid.parse(cid);
        currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                m_rootPath + folderPath, false);
        Call<Void> request = chunkService.chunkDump(new ChunkDumpRequest(filename, cidLong));
        Response<Void> response = null;
        try {
            response = request.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!response.isSuccessful()) {
            APIError error = ErrorUtils.parseError(response, m_retrofit);
            errorMessage = error.getError();
            saveErrorResponse();
        } else {
            chunkDumpResponse = "Chunk "+ cid + " has been successfully dumped " +
                    "with the filename "+ filename;
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
            Files.write(logFilePath, chunkDumpResponse.getBytes(),
                    StandardOpenOption.CREATE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void printErrorToTerminal() {
        System.out.println("ERROR");
        System.out.println("Please check out the following file: " +
                m_rootPath + folderPath + currentDateTime + "log.txt");
    }
}
