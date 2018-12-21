package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.data.MetadataResponseAllPeers;
import de.hhu.bsinfo.restTerminal.data.MetadataResponseOnePeer;
import de.hhu.bsinfo.restTerminal.error.APIError;
import de.hhu.bsinfo.restTerminal.error.ErrorUtils;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.request.MetadataRequest;
import de.hhu.bsinfo.restTerminal.rest.MetadataService;
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
 * Class for handling the metadata command
 */
@ShellComponent
public class Metadata extends AbstractCommand  {
    private MetadataService metadataService = m_retrofit.create(MetadataService.class);
    private String folderPath = "Metadata" + File.separator;
    private String currentDateTime;
    private MetadataResponseOnePeer metadataResponseOnePeer;
    private MetadataResponseAllPeers metadataResponseAllPeers;
    private String onSuccessMessage;
    private String errorMessage;
    private boolean allPeers = false;
    private boolean print;
    private static final String NODE_REGEX = "(0x(.{4}?))|(.{4}?)";

    /**
     * requests metadata from either one specific superpeer or from all superpeers
     * @param nid specific superpeer (optional)
     */
    @ShellMethod(value = "Gets summary of all or one superpeer's metadata.",
            group = "Metadata Commands")
    public void metadata(
            @ShellOption(
                    value = {"--nid", "-n"}, defaultValue = "0000",
                    help = "Node <nid> where the metadata is requested from")
            @Pattern(regexp = NODE_REGEX,
                    message = "Regex Pattern: " + NODE_REGEX)
                    String nid) {

        currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                m_rootPath + folderPath, true);
        if (nid.equals("0000")) {
            allPeers = true;
            Call<MetadataResponseAllPeers> allEntries = metadataService.metadataFromAllPeers();
            Response<MetadataResponseAllPeers> response = null;
            try {
                response = allEntries.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!response.isSuccessful()) {
                APIError error = ErrorUtils.parseError(response, m_retrofit);
                errorMessage = error.getError();
                saveErrorResponse();
            } else {
                onSuccessMessage = "Metadata of all superpeers has been received";
                metadataResponseAllPeers = response.body();
                saveSuccessfulResponse();
            }
        } else {
            Call<MetadataResponseOnePeer> singleEntry = metadataService.metadataFromOnePeer(new MetadataRequest(nid));
            Response<MetadataResponseOnePeer> response = null;
            try {
                response = singleEntry.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!response.isSuccessful()) {
                APIError error = ErrorUtils.parseError(response, m_retrofit);
                errorMessage = error.getError();
                saveErrorResponse();
            } else {
                onSuccessMessage = "Metadata of superpeer " + nid + " has been received";
                metadataResponseOnePeer = response.body();
                saveSuccessfulResponse();
            }
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

            if (allPeers) {
                Path dataFilePath = Paths.get(m_rootPath + folderPath
                        + currentDateTime + "data.txt");
                for (MetadataResponseOnePeer entry : metadataResponseAllPeers.getMetadata()) {
                    Files.write(dataFilePath, ("nid: " + entry.getNid()).getBytes(),
                            StandardOpenOption.APPEND);
                    Files.write(dataFilePath, ("metadata: " + entry.getMetadata()).getBytes(),
                            StandardOpenOption.APPEND);
                }
            } else {
                Path dataFilePath = Paths.get(m_rootPath + folderPath
                        + currentDateTime + "data.txt");
                Files.write(dataFilePath, ("nid: " + metadataResponseOnePeer
                                .getNid()).getBytes(), StandardOpenOption.APPEND);
                Files.write(dataFilePath, ("metadata: " + metadataResponseOnePeer
                                .getMetadata()).getBytes(), StandardOpenOption.APPEND);
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