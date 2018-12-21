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
    private String folderPath = "NodeInfo" + File.separator;
    private String currentDateTime;
    private NodeInfoResponse nodeInfoResponse;
    private String errorMessage;
    private String onSuccessMessage;
    private NodeService nodeService;
    private static final String NODE_REGEX = "(0x(.{4}?))|(.{4}?)";

    /**
     * requests information about a specific node
     * @param nid node of which the information is requested
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
                    String nid) {

        currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                m_rootPath + folderPath, true);
        nodeService = m_retrofit.create(NodeService.class);
        Call<NodeInfoResponse> call = nodeService.nodeInfo(new NodeInfoRequest(nid));
        Response<NodeInfoResponse> response = null;
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
            onSuccessMessage = "Nodeinfo of node " + nid + " has been received";
            nodeInfoResponse = response.body();
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
//            Files.write(dataFilePath, ("NodeInfo of Node: " + nid
//                    + System.lineSeparator()).getBytes(), StandardOpenOption.APPEND);

            Files.write(dataFilePath, ("nid: " + nodeInfoResponse.getNid()).getBytes(),
                    StandardOpenOption.APPEND);
            Files.write(dataFilePath, System.lineSeparator().getBytes(),
                    StandardOpenOption.APPEND);
            Files.write(dataFilePath, ("role: " + nodeInfoResponse.getRole()).getBytes(),
                    StandardOpenOption.APPEND);
            Files.write(dataFilePath, System.lineSeparator().getBytes(),
                    StandardOpenOption.APPEND);
            Files.write(dataFilePath, ("address: " + nodeInfoResponse.getAddress()).getBytes(),
                    StandardOpenOption.APPEND);
            Files.write(dataFilePath, System.lineSeparator().getBytes(),
                    StandardOpenOption.APPEND);
            Files.write(dataFilePath, ("capabilities: " +
                    nodeInfoResponse.getCapabilities()).getBytes(), StandardOpenOption.APPEND);
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
                + m_rootPath + folderPath + currentDateTime + "log.txt");
    }
}