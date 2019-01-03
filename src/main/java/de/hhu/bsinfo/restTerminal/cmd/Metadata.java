package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.response.MetadataResponseAllPeers;
import de.hhu.bsinfo.restTerminal.response.MetadataResponseOnePeer;
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
    private MetadataService m_metadataService = m_retrofit.create(MetadataService.class);
    private String m_folderPath = "Metadata" + File.separator;
    private String m_currentDateTime;
    private MetadataResponseOnePeer m_metadataResponseOnePeer;
    private MetadataResponseAllPeers m_metadataResponseAllPeers;
    private String m_onSuccessMessage;
    private String m_errorMessage;
    private boolean m_allPeers = false;
    private static final String NODE_REGEX = "(0x(.{4}?))|(.{4}?)";

    /**
     * requests metadata from either one specific superpeer or from all superpeers
     * @param p_nid specific superpeer (optional)
     */
    @ShellMethod(value = "Gets summary of all or one superpeer's metadata.",
            group = "Metadata Commands")
    public void metadata(
            @ShellOption(
                    value = {"--nid", "-n"}, defaultValue = "0000",
                    help = "Node <nid> where the metadata is requested from")
            @Pattern(regexp = NODE_REGEX,
                    message = "Regex Pattern: " + NODE_REGEX)
                    String p_nid) {

        m_currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                m_rootPath + m_folderPath, true);
        if (p_nid.equals("0000")) {
            m_allPeers = true;
            Call<MetadataResponseAllPeers> allEntries = m_metadataService.metadataFromAllPeers();
            Response<MetadataResponseAllPeers> response = null;
            try {
                response = allEntries.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!response.isSuccessful()) {
                APIError error = ErrorUtils.parseError(response, m_retrofit);
                m_errorMessage = error.getError();
                saveErrorResponse();
            } else {
                m_onSuccessMessage = "Metadata of all superpeers has been received";
                m_metadataResponseAllPeers = response.body();
                saveSuccessfulResponse();
            }
        } else {
            Call<MetadataResponseOnePeer> singleEntry = m_metadataService.metadataFromOnePeer(new MetadataRequest(p_nid));
            Response<MetadataResponseOnePeer> response = null;
            try {
                response = singleEntry.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!response.isSuccessful()) {
                APIError error = ErrorUtils.parseError(response, m_retrofit);
                m_errorMessage = error.getError();
                saveErrorResponse();
            } else {
                m_onSuccessMessage = "Metadata of superpeer " + p_nid + " has been received";
                m_metadataResponseOnePeer = response.body();
                saveSuccessfulResponse();
            }
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

            if (m_allPeers) {
                Path dataFilePath = Paths.get(m_rootPath + m_folderPath
                        + m_currentDateTime + "response.txt");
                for (MetadataResponseOnePeer entry : m_metadataResponseAllPeers.getMetadata()) {
                    Files.write(dataFilePath, ("nid: " + entry.getNid()).getBytes(),
                            StandardOpenOption.APPEND);
                    Files.write(dataFilePath, ("metadata: " + entry.getMetadata()).getBytes(),
                            StandardOpenOption.APPEND);
                }
            } else {
                Path dataFilePath = Paths.get(m_rootPath + m_folderPath
                        + m_currentDateTime + "response.txt");
                Files.write(dataFilePath, ("nid: " + m_metadataResponseOnePeer
                                .getNid()).getBytes(), StandardOpenOption.APPEND);
                Files.write(dataFilePath, ("metadata: " + m_metadataResponseOnePeer
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
                + m_rootPath + m_folderPath + m_currentDateTime + "log.txt");

    }
}