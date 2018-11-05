package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.data.MetadataEntry;
import de.hhu.bsinfo.restTerminal.error.APIError;
import de.hhu.bsinfo.restTerminal.error.ErrorUtils;
import de.hhu.bsinfo.restTerminal.files.FileSaving;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
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
import java.util.List;

@ShellComponent
public class Metadata extends AbstractCommand implements FileSaving {
    private MetadataService metadataService = retrofit.create(MetadataService.class);
    private String nid;
    private String folderPath = "Metadata" + File.separator;
    private String currentDateTime;
    private MetadataEntry metadataResponseOnePeer;
    private List<MetadataEntry> metadataResponseAllPeers;
    private String onSuccessMessage;
    private String errorMessage;
    private boolean print;
    private static final String NODE_REGEX = "(0x(.{4}?))|(.{4}?)";


    @ShellMethod(value = "Get summary of all or one superper's metadata", group = "Metadata Commands")
    public void metadata(
            @ShellOption(value = {"--nid", "-n"}, defaultValue = "",
                    help = "Node <nid> where the metadata is requested from")
                @Pattern(regexp = NODE_REGEX, message = "Invalid NodeID") String nid) {
        this.nid = nid;
        currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                rootPath + folderPath, true);
        if (nid.isEmpty()) {
            Call<List<MetadataEntry>> allEntries = metadataService.metadataFromAllPeers();
            Response<List<MetadataEntry>> response = null;
            try {
                response = allEntries.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!response.isSuccessful()) {
                APIError error = ErrorUtils.parseError(response, retrofit);
                errorMessage = error.getError();
                saveErrorResponse();
            } else {
                onSuccessMessage = "Metadata of all superpeers has been received";
                metadataResponseAllPeers = response.body();
                saveSuccessfulResponse();
            }
        } else {
            Call<MetadataEntry> singleEntry = metadataService.metadataFromOnePeer(nid);
            Response<MetadataEntry> response = null;
            try {
                response = singleEntry.execute();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (!response.isSuccessful()) {
                APIError error = ErrorUtils.parseError(response, retrofit);
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
            Path logFilePath = Paths.get(rootPath + folderPath + currentDateTime + "log.txt");
            Files.write(logFilePath, errorMessage.getBytes(), StandardOpenOption.CREATE);
            printErrorToTerminal();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveSuccessfulResponse() {
        try {
            Path logFilePath = Paths.get(rootPath + folderPath + currentDateTime + "log.txt");
            Files.write(logFilePath, onSuccessMessage.getBytes(), StandardOpenOption.CREATE);

            if (nid.isEmpty()) {
                Path dataFilePath = Paths.get(rootPath + folderPath + currentDateTime + "data.txt");
                for (MetadataEntry entry : metadataResponseAllPeers) {
                    Files.write(dataFilePath, ("nid: " + entry.getNid()).getBytes(),
                            StandardOpenOption.APPEND);
                    Files.write(dataFilePath, ("metadata: " + entry.getMetadata()).getBytes(),
                            StandardOpenOption.APPEND);
                }
            } else {
                Path dataFilePath = Paths.get(rootPath + folderPath + currentDateTime + "data.txt");
                Files.write(dataFilePath, ("nid: " + metadataResponseOnePeer.getNid()).getBytes(),
                        StandardOpenOption.APPEND);
                Files.write(dataFilePath, ("metadata: " + metadataResponseOnePeer.getMetadata()).getBytes(),
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
                + rootPath + folderPath + currentDateTime + "log.txt");

    }
}