package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.files.LogFileSaver;
import de.hhu.bsinfo.restTerminal.rest.ChunkService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import picocli.CommandLine;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


@ShellComponent
public class ChunkPut extends AbstractCommand  implements LogFileSaver<String> {
    private ChunkService chunkService = retrofit.create(ChunkService.class);
    private static String FOLDER_PATH = "ChunkPut"+ File.separator;

    @ShellMethod(value = "put <data> with <type> on chunk <cid>", group = "Chunk Commands")
    public void chunkput(
            @ShellOption(value = {"--cid", "-c"}, help = "chunk ID of the submitted chunk") int cid,
            @ShellOption(value = {"--data", "-d"}, help = "data that is saved in the chunk") Object data,
            @ShellOption(value = {"--type", "-t"}, defaultValue = "str",
                    help = "type of the submitted chunk [str,byte,short,int,long]") String type) {

        //for testing purposes till the REST-API is set up
        String message = "put Chunk on Chunk with id " + cid + " with type " + type + " with data" + data;
        System.out.println(message);
        saveToLogFile(message);

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
    public void saveToLogFile(String chunkPut) {
        try {
            String dateTime = FolderHierarchy.createDateTimeFolderHierarchy(ROOT_PATH + FOLDER_PATH, false);
            Path logFilePath = Paths.get(ROOT_PATH + FOLDER_PATH + dateTime + "log.txt");
            Files.write(logFilePath, chunkPut.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }


    }

}


