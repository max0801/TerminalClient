package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.response.NameGetResponse;
import de.hhu.bsinfo.restTerminal.error.APIError;
import de.hhu.bsinfo.restTerminal.error.ErrorUtils;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.request.NameGetRequest;
import de.hhu.bsinfo.restTerminal.rest.NameService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import retrofit2.Call;
import retrofit2.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Class for handling the nameget command
 */
@ShellComponent
public class NameGet extends AbstractCommand  {
    private NameService m_nameService = m_retrofit.create(NameService.class);
    private String m_folderPath = "NameGet" + File.separator;
    private String m_currentDateTime;
    private NameGetResponse m_nameGetResponse;
    private String m_errorMessage;
    private String m_onSuccessMessage;
    private boolean m_print;

    /**
     * returns the corresponding chunk id of a named chunk
     * @param p_name name of the chunk
     * @param p_print if true, prints the chunk id to stdout
     * @see de.hhu.bsinfo.restTerminal.cmd.NameReg
     */
    @ShellMethod(value = "Gets chunk id <cid> of a chunk with name <name>.",
            group = "Name Commands")
    public void nameget(
            @ShellOption(
                    value = {"--name", "-n"},
                    help = "name of the chunk which is requested") String p_name,
            @ShellOption(
                    value = {"--print", "-p"}, help = "prints chunk id to stdout",
                    defaultValue = "false") boolean p_print) {

        this.m_print = p_print;
        m_currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                m_rootPath + m_folderPath, true);
        Call<NameGetResponse> call = m_nameService.nameGet(new NameGetRequest(p_name));
        Response<NameGetResponse> response = null;
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
            m_onSuccessMessage = "Chunk id of chunk" + p_name + " has been received";
            m_nameGetResponse = response.body();
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
            Path logFilePath = Paths.get(m_rootPath +
                    m_folderPath + m_currentDateTime + "log.txt");
            Files.write(logFilePath, m_onSuccessMessage.getBytes(),
                    StandardOpenOption.CREATE);
            Path dataFilePath = Paths.get(m_rootPath + m_folderPath
                    + m_currentDateTime + "response.txt");
            Files.write(dataFilePath, (Long.toHexString(m_nameGetResponse.getCid())).getBytes(),
                    StandardOpenOption.CREATE);
            if(m_print){
                System.out.println(Long.toHexString(m_nameGetResponse.getCid()));
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
