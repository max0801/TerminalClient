package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.data.Message;
import de.hhu.bsinfo.restTerminal.data.NameListData;
import de.hhu.bsinfo.restTerminal.data.NameListEntry;
import de.hhu.bsinfo.restTerminal.error.APIError;
import de.hhu.bsinfo.restTerminal.error.ErrorUtils;
import de.hhu.bsinfo.restTerminal.files.FileSaving;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.rest.NameService;
import de.hhu.bsinfo.restTerminal.rest.NodeService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;


@ShellComponent
public class NodeList extends AbstractCommand implements FileSaving {
    private String currentDateTime;
    private List<String> NODELIST_RESPONSE;
    private String ERROR_MESSAGE;
    private String ON_SUCCESS_MESSAGE;
    private String FOLDER_PATH = "NodeList" + File.separator;
    private NodeService nodeService = retrofit.create(NodeService.class);

    @ShellMethod(value = "get nodelist", group = "Node Commands")
    public void nodelist(
            @ShellOption(value = {"--print", "-p"}, help = "print nodelist to stdout",
                    defaultValue = "false") boolean print) {

        currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                ROOT_PATH + FOLDER_PATH, true);
        Call<List<String>> call = nodeService.nodeList();
        Response<List<String>> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!response.isSuccessful()) {
            APIError error = ErrorUtils.parseError(response, retrofit);
            ERROR_MESSAGE = error.getError();
            saveErrorResponse();
        } else {
            NODELIST_RESPONSE = response.body();
            saveSuccessfulResponse();
        }
    }

    @Override
    public void saveErrorResponse() {
        try {
            Path logFilePath = Paths.get(ROOT_PATH + FOLDER_PATH + currentDateTime + "log.txt");
            Files.write(logFilePath, ERROR_MESSAGE.getBytes(), StandardOpenOption.CREATE);
            printErrorToTerminal();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveSuccessfulResponse() {
        try {
            Path logFilePath = Paths.get(ROOT_PATH + FOLDER_PATH + currentDateTime + "log.txt");
            Files.write(logFilePath, ON_SUCCESS_MESSAGE.getBytes(), StandardOpenOption.CREATE);

            Path dataFilePath = Paths.get(ROOT_PATH + FOLDER_PATH + currentDateTime + "data.txt");
            Files.write(dataFilePath, ("NodeList:" + System.lineSeparator()).getBytes(),
                    StandardOpenOption.APPEND);

            for (String node : NODELIST_RESPONSE) {
                Files.write(dataFilePath, node.getBytes(), StandardOpenOption.APPEND);
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
                + ROOT_PATH + FOLDER_PATH + currentDateTime + "log.txt");
    }
}





