package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.data.ChunkRange;
import de.hhu.bsinfo.restTerminal.error.APIError;
import de.hhu.bsinfo.restTerminal.error.ErrorUtils;
import de.hhu.bsinfo.restTerminal.files.FileSaving;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.rest.ChunkService;
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

//@ShellComponent
//public class ChunkLockList extends AbstractCommand implements FileSaving {
//    private ChunkService chunkService = retrofit.create(ChunkService.class);
//    private String currentDateTime;
//    private ChunkRange chunkListResponse;
//    private String errorMessage;
//    private String onSuccessMessage;
//    private String folderPath = "ChunkLockList" + File.separator;
//    private String nid;
//    private boolean print;
//    private static final String NODE_REGEX = "(0x(.{4}?))|(.{4}?)";
//
//    @ShellMethod(value = "Get the list of all locked chunks of a node", group = "Chunk Commands")
//    public void chunklocklist(
//            @ShellOption(value = {"--nid", "-n"},
//                    help = "The node <nid> where the list of locked chunks is referring to")
//            @Pattern(regexp = NODE_REGEX, message = "Invalid NodeID") String nid,
//            @ShellOption(value = {"--print", "-p"},
//                    help = "print chunklocklist to stdout", defaultValue = "false") boolean print) {
//        this.print = print;
//        this.nid = nid;
//
//        currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
//                rootPath + folderPath, true);
//        Call<ChunkRange> call = chunkService.chunkList(nid);
//        Response<ChunkRange> response = null;
//        try {
//            response = call.execute();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        if (!response.isSuccessful()) {
//            APIError error = ErrorUtils.parseError(response, retrofit);
//            errorMessage = error.getError();
//            saveErrorResponse();
//        } else {
//            onSuccessMessage = "Chunklocklist of node "+nid+ " has been received";
//            chunkListResponse = response.body();
//            saveSuccessfulResponse();
//        }
//    }
//    @Override
//    public void saveErrorResponse() {
//        try {
//            Path logFilePath = Paths.get(rootPath + folderPath + currentDateTime + "log.txt");
//            Files.write(logFilePath, errorMessage.getBytes(), StandardOpenOption.CREATE);
//            printErrorToTerminal();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void saveSuccessfulResponse() {
//        try {
//            Path logFilePath = Paths.get(rootPath + folderPath + currentDateTime + "log.txt");
//            Files.write(logFilePath, onSuccessMessage.getBytes(), StandardOpenOption.CREATE);
//            Path dataFilePath = Paths.get(rootPath + folderPath + currentDateTime + "data.txt");
//            Files.write(dataFilePath, chunkListResponse.getLocalChunkRanges().getBytes(),
//                    StandardOpenOption.APPEND);
//            Files.write(dataFilePath, System.lineSeparator().getBytes(), StandardOpenOption.APPEND);
//            Files.write(dataFilePath, chunkListResponse.getMigratedChunkRanges().getBytes(),
//                    StandardOpenOption.APPEND);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Override
//    public void printErrorToTerminal() {
//        System.out.println("ERROR");
//        System.out.println("Please check out the following file: "
//                + rootPath + folderPath + currentDateTime + "log.txt");
//    }
//}
//
//
//



