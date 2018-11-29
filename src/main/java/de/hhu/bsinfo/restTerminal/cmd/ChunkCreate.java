package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.data.Message;
import de.hhu.bsinfo.restTerminal.error.APIError;
import de.hhu.bsinfo.restTerminal.error.ErrorUtils;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.request.ChunkCreateRequest;
import de.hhu.bsinfo.restTerminal.rest.ChunkService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import retrofit2.Call;
import retrofit2.Response;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@ShellComponent
public class ChunkCreate extends AbstractCommand {
    private ChunkService m_chunkService = m_retrofit.create(ChunkService.class);
    private String m_folderPath = "ChunkCreate" + File.separator;
    private String m_currentDateTime;
    private Message m_chunkCreateResponse;
    private String m_errorMessage;
    private static final String NODE_REGEX = "(0x(.{4}?))|(.{4}?)";

    @ShellMethod(value = "Creates a chunk on node <nid> with size <size>.",
            group = "Chunk Commands")
    public void chunkcreate(
            @ShellOption(
                    value = {"--nid", "-n"},
                    help = "Node <nid> of the created chunk")
            @Pattern(
                    regexp = NODE_REGEX,
                    message = "Regex Pattern: " + NODE_REGEX)
                    String p_nid,
            @ShellOption(
                    value = {"--size", "-s"},
                    defaultValue = "16",
                    help = "size of the created chunk in byte")
            @Positive int p_size) {

        m_currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                m_rootPath + m_folderPath, false);
        Call<Message> call = m_chunkService.chunkCreate(new ChunkCreateRequest(p_nid, p_size));
        Response<Message> response = null;
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
            m_chunkCreateResponse = response.body();
            saveSuccessfulResponse();
        }
    }


    @Override
    public void saveErrorResponse() {
        try {
            Path logFilePath = Paths.get(m_rootPath + m_folderPath +
                    m_currentDateTime + "log.txt");
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
            Files.write(logFilePath,
                    m_chunkCreateResponse.getMessage().getBytes(),
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
