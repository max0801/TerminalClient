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

import javax.swing.text.GapContent;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


@ShellComponent
public class StatsPrint extends AbstractCommand implements FileSaving {
    private StatsService statsService;
    private String currentDateTime;
    private String statsPrintResponse;
    private String errorMessage;
    private String onSuccessMessage;
    private String folderPath = "StatsPrint" + File.separator;

    @ShellMethod(value = "get debug information every <interval> seconds", group = "Statistics Commands")
    public void statsprint(
            @ShellOption(value = {"--interval", "-i"}, defaultValue = "10",
                    help = "Refresh interval parameter in seconds") int interval) {

        retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8009/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
        statsService = retrofit.create(StatsService.class);
        currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                rootPath + folderPath, false);
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
            errorMessage = error.getError();
            saveErrorResponse();
        } else {
            onSuccessMessage = "Statistics are printed every " + interval + " seconds";
            statsPrintResponse = response.body();
            saveSuccessfulResponse();
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
            File file = new File(rootPath + folderPath + currentDateTime + "data.html");
            Files.write(file.toPath(), statsPrintResponse.getBytes());
            Desktop.getDesktop().browse(file.toURI());
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





