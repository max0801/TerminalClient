package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.response.NodeInfoResponse;
import de.hhu.bsinfo.restTerminal.error.APIError;
import de.hhu.bsinfo.restTerminal.error.ErrorUtils;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.request.NodeInfoRequest;
import de.hhu.bsinfo.restTerminal.rest.NodeService;
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
 * Class for handling the nodeinfo command
 */
@ShellComponent
public class NodeInfo extends AbstractCommand {
    private String m_folderPath = "NodeInfo" + File.separator;
    private String m_currentDateTime;
    private NodeInfoResponse m_nodeInfoResponse;
    private String m_errorMessage;
    private String m_onSuccessMessage;
    private NodeService m_nodeService;
    private static final String NODE_REGEX = "(0x(.{4}?))|(.{4}?)";

    /**
     * requests information about a specific node
     * @param p_nid node of which the information is requested
     */
    @ShellMethod(value = "Gets information about node <nid> in the network.",
            group = "Node Commands")
    public void nodeinfo(
            @ShellOption(
                    value = {"--nid", "-n"},
                    help = "Node <nid> which info is requested")
            @Pattern(
                    regexp = NODE_REGEX,
                    message = "Regex Pattern: " + NODE_REGEX)
                    String p_nid) {

        m_currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                m_rootPath + m_folderPath, true);
        m_nodeService = m_retrofit.create(NodeService.class);
        Call<NodeInfoResponse> call = m_nodeService.nodeInfo(new NodeInfoRequest(p_nid));
        Response<NodeInfoResponse> response = null;
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
            m_onSuccessMessage = "Nodeinfo of node " + p_nid + " has been received";
            m_nodeInfoResponse = response.body();
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
            Files.write(dataFilePath, ("nid: " + m_nodeInfoResponse.getNid()).getBytes(),
                    StandardOpenOption.APPEND);
            Files.write(dataFilePath, System.lineSeparator().getBytes(),
                    StandardOpenOption.APPEND);
            Files.write(dataFilePath, ("role: " + m_nodeInfoResponse.getRole()).getBytes(),
                    StandardOpenOption.APPEND);
            Files.write(dataFilePath, System.lineSeparator().getBytes(),
                    StandardOpenOption.APPEND);
            Files.write(dataFilePath, ("address: " + m_nodeInfoResponse.getAddress()).getBytes(),
                    StandardOpenOption.APPEND);
            Files.write(dataFilePath, System.lineSeparator().getBytes(),
                    StandardOpenOption.APPEND);
            Files.write(dataFilePath, ("capabilities: " +
                    m_nodeInfoResponse.getCapabilities()).getBytes(), StandardOpenOption.APPEND);
            Files.write(dataFilePath, System.lineSeparator().getBytes(),
                    StandardOpenOption.APPEND);
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