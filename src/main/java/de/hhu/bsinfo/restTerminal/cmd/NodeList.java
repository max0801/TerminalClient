package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.response.NodeListResponse;
import de.hhu.bsinfo.restTerminal.error.APIError;
import de.hhu.bsinfo.restTerminal.error.ErrorUtils;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.rest.NodeService;
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
 * Class for handling the nodelist command
 */
@ShellComponent
public class NodeList extends AbstractCommand {
    private String m_currentDateTime;
    private NodeListResponse m_nodeListResponse;
    private String m_errorMessage;
    private String m_onSuccessMessage;
    private String m_folderPath = "NodeList" + File.separator;
    private NodeService m_nodeService = m_retrofit.create(NodeService.class);
    private boolean m_print;
    /**
     * shows all nodes that are present in the current dxram instance
     */
    @ShellMethod(value = "Gets nodelist.", group = "Node Commands")
    public void nodelist(
            @ShellOption(
                    value = {"--print", "-p"}, help = "print nodelist to stdout",
                    defaultValue = "false") boolean p_print) {

        this.m_print = p_print;
        m_currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                m_rootPath + m_folderPath, true);
        Call<NodeListResponse> call = m_nodeService.nodeList();
        Response<NodeListResponse> response = null;
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
            m_onSuccessMessage = "Nodelist has been received";
            m_nodeListResponse = response.body();
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
            Files.write(dataFilePath, ("NodeList:" + System.lineSeparator()).getBytes(),
                    StandardOpenOption.APPEND);

            for (String node : m_nodeListResponse.getNodes()) {
                if(m_print){
                    System.out.println(node);
                }
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
                + m_rootPath + m_folderPath + m_currentDateTime + "log.txt");
    }
}





