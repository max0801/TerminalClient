package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.error.APIError;
import de.hhu.bsinfo.restTerminal.error.ErrorUtils;
import de.hhu.bsinfo.restTerminal.files.FileSaving;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.rest.StatsService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


@ShellComponent
public class StatsPrint extends AbstractCommand implements FileSaving {
    private StatsService statsService;//= retrofit.create(StatsService.class);
    private String currentDateTime;
    private String STATSPRINT_RESPONSE;
    private String ERROR_MESSAGE;
    private String ON_SUCCESS_MESSAGE;
    private String FOLDER_PATH = "StatsPrint" + File.separator;

    @ShellMethod(value = "get debug information every <interval> seconds", group = "Statistics Commands")
    public void statsprint(
            @ShellOption(value = {"--interval", "-i"}, defaultValue = "10",
                    help = "Refresh interval parameter in seconds") int interval,
            @ShellOption(value = {"--print", "-p"}, help = "print statistics to stdout",
                    defaultValue = "false") boolean print) {

        retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8009/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        statsService = retrofit.create(StatsService.class);
        currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                ROOT_PATH + FOLDER_PATH, true);
        Call<String> call = statsService.printStats(interval);
        Response<String> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        if (!response.isSuccessful()) {
            APIError error = ErrorUtils.parseError(response, retrofit);
            ERROR_MESSAGE = error.getError();
            saveErrorResponse();
        } else {
            ON_SUCCESS_MESSAGE = "Statistics are printed every " + interval + " seconds";
            STATSPRINT_RESPONSE = response.body();
            saveSuccessfulResponse();
            File file = new File(ROOT_PATH + FOLDER_PATH + currentDateTime + "data.html");
            try {
                Files.write(file.toPath(), STATSPRINT_RESPONSE.getBytes());
                Desktop.getDesktop().browse(file.toURI());
            } catch (IOException e) {
                e.printStackTrace();
            }

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
//
//            Path dataFilePath = Paths.get(ROOT_PATH + FOLDER_PATH + currentDateTime + "data.html");
//            Files.write(dataFilePath, STATSPRINT_RESPONSE.getBytes(),
//                    StandardOpenOption.APPEND);
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





