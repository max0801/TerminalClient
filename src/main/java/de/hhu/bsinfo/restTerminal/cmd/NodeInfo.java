package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.files.LogFileSaver;
import de.hhu.bsinfo.restTerminal.rest.NodeService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@ShellComponent
public class NodeInfo extends AbstractCommand  implements LogFileSaver<String> {
    private NodeService nodeService = retrofit.create(NodeService.class);
    private static String ON_FAILURE_MESSAGE = "NO RESPONSE";
    private static String FOLDER_PATH = "NodeInfo"+ File.separator ;

    @ShellMethod(value = "Get information about a node in the network", group = "Node Commands")
    public void nodeinfo(
            @ShellOption(value = {"--nid", "-n"}, help = "Node <nid> which info is requested") int nid) {

        System.out.println("print the nodeinfo");

        /*
        //chunkService = retrofit.create(ChunkService.class);
        //chunkService.chunkGet(cid,type);
        Call<String> call = chunkService.chunkDump(cid, filename);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(APIError.isError(response.body())){
                    APIError error = ErrorUtils.parseError(response);
                    error.printError();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println("Something else happenend");
            }
        });*/
    }


    @Override
    public void saveToLogFile(String chunkCreate) {
        try {
            String dateTime = FolderHierarchy.createDateTimeFolderHierarchy(ROOT_PATH + FOLDER_PATH, false);
            Path logFilePath = Paths.get(ROOT_PATH + FOLDER_PATH + dateTime + "log.txt");
            Files.write(logFilePath, chunkCreate.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }


    }


}



