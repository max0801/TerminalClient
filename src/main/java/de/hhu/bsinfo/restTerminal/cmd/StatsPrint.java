package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.data.StatsPrintResponse;
import de.hhu.bsinfo.restTerminal.error.APIError;
import de.hhu.bsinfo.restTerminal.error.ErrorUtils;
import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import de.hhu.bsinfo.restTerminal.request.StatsPrintRequest;
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
import javax.json.Json;
import javax.json.JsonObject;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

/**
 * Class for handling the statsprint command
 */
@ShellComponent
public class StatsPrint extends AbstractCommand  {
    private StatsService statsService = m_retrofit.create(StatsService.class);
    private String currentDateTime;
    private StatsPrintResponse statsPrintResponse;
    private String errorMessage;
    private String onSuccessMessage;
    private String folderPath = "StatsPrint" + File.separator;

    /**
     * shows dxram statistics in a browser window
     * @param interval refresh interval parameter
     */
    @ShellMethod(value = "Gets debug information every <interval> seconds.",
            group = "Statistics Commands")
    public void statsprint(
            @ShellOption(
                    value = {"--interval", "-i"}, defaultValue = "10",
                    help = "Refresh interval parameter in seconds")
                    @Positive int interval) {

        String stringInterval = String.valueOf(interval);
        currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                m_rootPath + folderPath, false);
        Call<StatsPrintResponse> call = statsService.printStats(new StatsPrintRequest(stringInterval));
        Response<StatsPrintResponse> response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        if (!response.isSuccessful()) {
            try {
                System.out.println(response.errorBody().string());
            } catch (IOException p_e) {
                p_e.printStackTrace();
            }
            APIError error = ErrorUtils.parseError(response, m_retrofit);
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
            File file = new File(m_rootPath + folderPath
                    + currentDateTime + "error.html");
            Files.write(file.toPath(), errorMessage.getBytes());
            Desktop.getDesktop().browse(file.toURI());
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
            File file = new File(m_rootPath + folderPath
                    + currentDateTime + "data.html");
            Files.write(file.toPath(), statsPrintResponse.getHtmlResponse().getBytes());
            Desktop.getDesktop().browse(file.toURI());
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





