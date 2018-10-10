package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.files.LogFileSaver;
import de.hhu.bsinfo.restTerminal.rest.ChunkService;
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
public class ChunkDump extends AbstractCommand implements LogFileSaver<String> {
    private ChunkService chunkService = retrofit.create(ChunkService.class);
    private static String FOLDER_PATH = "ChunkDump"+ File.separator;

    @ShellMethod(value = "creates a filedump of <cid> saved as <name>", group = "Chunk Commands")
    public void chunkdump(
            @ShellOption(value = {"--filename", "-f"}, help = "filename of the dump") String filename,
            @ShellOption(value = {"--cid", "-c"}, help = "chunk of which a filedump is created") int cid) {

        String message = "created Filedump with name " + filename + " of Chunk " + cid;
        System.out.println(message);
        saveToLogFile(message);
        /*
        chunkService = retrofit.create(ChunkService.class);
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
    public void saveToLogFile(String chunkDump) {
        try {
            String dateTime = FolderHierarchy.createDateTimeFolderHierarchy(ROOT_PATH + FOLDER_PATH, false);
            Path logFilePath = Paths.get(ROOT_PATH + FOLDER_PATH + dateTime + "log.txt");
            Files.write(logFilePath, chunkDump.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }


    }

}
