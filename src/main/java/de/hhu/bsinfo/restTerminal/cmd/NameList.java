package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.response.NameListResponse;
import de.hhu.bsinfo.restTerminal.error.APIError;
import de.hhu.bsinfo.restTerminal.error.ErrorUtils;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.rest.NameService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import retrofit2.Call;
import retrofit2.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


/**
 * Class for handling the namelist command
 */
@ShellComponent
public class NameList extends AbstractCommand {
    private NameService m_nameService = m_retrofit.create(NameService.class);
    private String m_currentDateTime;
    private NameListResponse m_nameListResponse;
    private String m_errorMessage;
    private String m_onSuccessMessage;
    private String m_folderPath = "NameList" + File.separator;

    /**
     * returns all chunks with their registered names
     */
    @ShellMethod(value = "Gets namelist.", group = "Name Commands")
    public void namelist() {
        m_currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                m_rootPath + m_folderPath, true);
        Call<NameListResponse> call = m_nameService.nameList();
        Response<NameListResponse> response = null;
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
            m_onSuccessMessage = "Namelist of all nodes has been received";
            m_nameListResponse = response.body();
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
            Files.write(dataFilePath, ("NameList:" + System.lineSeparator()).getBytes(),
                    StandardOpenOption.APPEND);

            for (NameListResponse.NamelistEntryRest entry: m_nameListResponse.getNamelist()) {
                Files.write(dataFilePath, (entry.getName() + " -> ").getBytes(),
                        StandardOpenOption.APPEND);
                Files.write(dataFilePath, (Long.toHexString(entry.getCid()) +
                        System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);
                Files.write(dataFilePath, System.lineSeparator().getBytes(),
                        StandardOpenOption.APPEND);

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


