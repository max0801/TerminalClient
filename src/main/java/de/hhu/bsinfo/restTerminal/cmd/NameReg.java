package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.error.APIError;
import de.hhu.bsinfo.restTerminal.error.ErrorUtils;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.parsing.ParsingCid;
import de.hhu.bsinfo.restTerminal.request.NameRegRequest;
import de.hhu.bsinfo.restTerminal.rest.NameService;
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
 * Class for handling the namereg command
 */
@ShellComponent
public class NameReg extends AbstractCommand {
    private NameService m_nameService = m_retrofit.create(NameService.class);
    private String m_folderPath = "NameReg" + File.separator;
    private String m_currentDateTime;
    private String m_nameRegResponse;
    private String m_errorMessage;
    private static final String CHUNK_REGEX = "(0x(.{16}?))|(.{16}?)";

    /**
     * registers a name for a specific chunk in order to address it easier
     *
     * @param p_cid  chunk
     * @param p_name registered name for the chunk
     */
    @ShellMethod(value = "Registers chunk <cid> with name <name>.",
            group = "Name Commands")
    public void namereg(
            @ShellOption(
                    value = {"--cid", "-c"}, help = "chunk <cid> which is named")
            @Pattern(
                    regexp = CHUNK_REGEX,
                    message = "Regex Pattern: " + CHUNK_REGEX)
                    String p_cid,
            @ShellOption(
                    value = {"--name", "-n"}, help = "name of the chunk")
                    String p_name) {
        long cidLong = ParsingCid.parse(p_cid);

        m_currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                m_rootPath + m_folderPath, false);
        Call<Void> call = m_nameService.nameReg(new NameRegRequest(cidLong, p_name));
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
            m_nameRegResponse = "Registered chunk " + p_cid + " as " + p_name;
            saveSuccessfulResponse();
        }
    }

    @Override
    public void printErrorToTerminal() {
        System.out.println("ERROR");
        System.out.println("Please check out the following file: "
                + m_rootPath + m_folderPath + m_currentDateTime + "log.txt");
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
            Files.write(logFilePath, m_nameRegResponse.getBytes(),
                    StandardOpenOption.CREATE);
            //Files.write(logFilePath, (" for the following chunk id: " + cid).getBytes(),
            //      StandardOpenOption.APPEND);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}




