package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.files.LogFileSaver;
import de.hhu.bsinfo.restTerminal.rest.NameService;
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
public class NameReg extends AbstractCommand implements LogFileSaver<String> {
    private NameService nameService = retrofit.create(NameService.class);
    private static String FOLDER_PATH = "NameReg"+ File.separator;


    @ShellMethod(value = "register chunk <cid> with <name>", group = "Name Commands")
    public void namereg(
            @ShellOption(value = {"--cid", "-c"}, help = "chunk <cid> which is named") int cid,
            @ShellOption(value = {"--name", "-n"}, help = "name of the chunk") String name) {

        String message = "named chunk " + cid + " to " + name;
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
    public void saveToLogFile(String nameReg) {
        try {
            String dateTime = FolderHierarchy.createDateTimeFolderHierarchy(ROOT_PATH + FOLDER_PATH, false);
            Path logFilePath = Paths.get(ROOT_PATH + FOLDER_PATH + dateTime + "log.txt");
            Files.write(logFilePath, nameReg.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}




