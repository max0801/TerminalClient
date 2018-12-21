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
    private NameService nameService = m_retrofit.create(NameService.class);
    private String folderPath = "NameGet" + File.separator;
    private String currentDateTime;
    private NameGetResponse nameGetResponse;
    private String errorMessage;
    private String onSuccessMessage;
    private boolean print;

    /**
     * returns the corresponding chunk id of a named chunk
     * @param name name of the chunk
     * @param print if true, prints the chunk id to stdout
     * @see de.hhu.bsinfo.restTerminal.cmd.NameReg
     */
    @ShellMethod(value = "Gets chunk id <cid> of a chunk with name <name>.",
            group = "Name Commands")
    public void nameget(
            @ShellOption(
                    value = {"--name", "-n"},
                    help = "name of the chunk which is requested") String name,
            @ShellOption(
                    value = {"--print", "-p"}, help = "prints chunk id to stdout",
                    defaultValue = "false") boolean print) {

        this.print = print;
        currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                m_rootPath + folderPath, true);
        Call<NameGetResponse> call = nameService.nameGet(new NameGetRequest(name));
        Response<NameGetResponse> response = null;
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
            onSuccessMessage = "Chunk id of chunk" + name + " has been received";
            nameGetResponse = response.body();
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
            Path logFilePath = Paths.get(m_rootPath +
                    folderPath + currentDateTime + "log.txt");
            Files.write(logFilePath, onSuccessMessage.getBytes(),
                    StandardOpenOption.CREATE);
            Path dataFilePath = Paths.get(m_rootPath + folderPath
                    + currentDateTime + "response.txt");
            Files.write(dataFilePath, (Long.toHexString(nameGetResponse.getCid())).getBytes(),
                    StandardOpenOption.CREATE);
            if(print){
                System.out.println(Long.toHexString(nameGetResponse.getCid()));
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
