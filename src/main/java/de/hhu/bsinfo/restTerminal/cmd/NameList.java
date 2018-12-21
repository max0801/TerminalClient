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
    private NameService nameService = m_retrofit.create(NameService.class);
    private String currentDateTime;
    private NameListResponse nameListResponse;
    private String errorMessage;
    private String onSuccessMessage;
    private String folderPath = "NameList" + File.separator;
    private boolean print;

    /**
     * returns all chunks with their registered names
     */
    @ShellMethod(value = "Gets namelist.", group = "Name Commands")
    public void namelist() {
        currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                m_rootPath + folderPath, true);
        Call<NameListResponse> call = nameService.nameList();
        Response<NameListResponse> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!response.isSuccessful()) {
            APIError error = ErrorUtils.parseError(response, m_retrofit);
            errorMessage = error.getError();
            saveErrorResponse();
        } else {
            onSuccessMessage = "Namelist of all nodes has been received";
            nameListResponse = response.body();
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
            Files.write(logFilePath, onSuccessMessage.getBytes(),
                    StandardOpenOption.CREATE);

            Path dataFilePath = Paths.get(m_rootPath + folderPath
                    + currentDateTime + "response.txt");
            Files.write(dataFilePath, ("NameList:" + System.lineSeparator()).getBytes(),
                    StandardOpenOption.APPEND);

            for (NameListResponse.NamelistEntryRest entry: nameListResponse.getNamelist()) {
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
                + m_rootPath + folderPath + currentDateTime + "log.txt");
    }
}


