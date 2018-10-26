package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.data.NodeInfoRest;
import de.hhu.bsinfo.restTerminal.error.APIError;
import de.hhu.bsinfo.restTerminal.error.ErrorUtils;
import de.hhu.bsinfo.restTerminal.files.FileSaving;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.rest.NodeService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import javax.validation.constraints.Pattern;
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
public class NodeInfo extends AbstractCommand implements FileSaving {
    private String ON_FAILURE_MESSAGE = "NO RESPONSE";
    private String FOLDER_PATH = "NodeInfo" + File.separator;
    private String currentDateTime;
    private NodeInfoRest NODEINFO_RESPONSE;
    private String ERROR_MESSAGE;
    private String ON_SUCCESS_MESSAGE;
    private String nid;
    private NodeService nodeService;
    private static final String NODE_REGEX = "(0x(.{4}?))|(.{4}?)";

    @ShellMethod(value = "Get information about a node in the network", group = "Node Commands")
    public void nodeinfo(
            @ShellOption(value = {"--nid", "-n"}, help = "Node <nid> which info is requested")
                @Pattern(regexp = NODE_REGEX, message = "Invalid NodeID") String nid) {

        this.nid = nid;

        currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                ROOT_PATH + FOLDER_PATH, true);
        nodeService = retrofit.create(NodeService.class);
        Call<NodeInfoRest> call = nodeService.nodeInfo(nid);
        Response<NodeInfoRest> response = null;
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
            ON_SUCCESS_MESSAGE = "Nodeinfo of node " + nid + " has been received";
            NODEINFO_RESPONSE = response.body();
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
            Files.write(dataFilePath, ("NodeInfo of Node: " + nid + System.lineSeparator()).getBytes(),
                    StandardOpenOption.APPEND);

            Files.write(dataFilePath, ("nid: " + NODEINFO_RESPONSE.getNid()).getBytes(),
                    StandardOpenOption.APPEND);
            Files.write(dataFilePath, System.lineSeparator().getBytes(),
                    StandardOpenOption.APPEND);
            Files.write(dataFilePath, ("role: " + NODEINFO_RESPONSE.getRole()).getBytes(),
                    StandardOpenOption.APPEND);
            Files.write(dataFilePath, System.lineSeparator().getBytes(),
                    StandardOpenOption.APPEND);
            Files.write(dataFilePath, ("address: " + NODEINFO_RESPONSE.getAddress()).getBytes(),
                    StandardOpenOption.APPEND);
            Files.write(dataFilePath, System.lineSeparator().getBytes(),
                    StandardOpenOption.APPEND);
            Files.write(dataFilePath, ("capabilities: " + NODEINFO_RESPONSE.getCapabilities()).getBytes(),
                    StandardOpenOption.APPEND);
            Files.write(dataFilePath, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
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