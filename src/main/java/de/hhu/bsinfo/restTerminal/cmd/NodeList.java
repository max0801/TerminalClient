package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.error.APIError;
import de.hhu.bsinfo.restTerminal.error.ErrorUtils;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.rest.NodeService;
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
import java.util.List;

/**
 * Class for handling the nodelist command
 */
@ShellComponent
public class NodeList extends AbstractCommand  {
    private String currentDateTime;
    private List<String> nodeListResponse;
    private String errorMessage;
    private String onSuccessMessage;
    private String folderPath = "NodeList" + File.separator;
    private NodeService nodeService = m_retrofit.create(NodeService.class);

    /**
     * shows all nodes that are present in the current dxram instance
     */
    @ShellMethod(value = "Gets nodelist.", group = "Node Commands")
    public void nodelist() {
        currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                m_rootPath + folderPath, true);
        Call<List<String>> call = nodeService.nodeList();
        Response<List<String>> response = null;
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
            onSuccessMessage = "Nodelist has been received";
            nodeListResponse = response.body();
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
                    + currentDateTime + "data.txt");
            Files.write(dataFilePath, ("NodeList:" + System.lineSeparator()).getBytes(),
                    StandardOpenOption.APPEND);

            for (String node : nodeListResponse) {
                Files.write(dataFilePath, node.getBytes(),
                        StandardOpenOption.APPEND);
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





