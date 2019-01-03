package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.response.StatsPrintResponse;
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

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import javax.validation.constraints.Positive;

/**
 * Class for handling the statsprint command
 */
@ShellComponent
public class StatsPrint extends AbstractCommand  {
    private StatsService m_statsService = m_retrofit.create(StatsService.class);
    private String m_currentDateTime;
    private StatsPrintResponse m_statsPrintResponse;
    private String m_errorMessage;
    private String m_onSuccessMessage;
    private String m_folderPath = "StatsPrint" + File.separator;

    /**
     * shows dxram statistics in a browser window
     * @param p_interval refresh interval parameter
     */
    @ShellMethod(value = "Gets debug information every <interval> seconds.",
            group = "Statistics Commands")
    public void statsprint(
            @ShellOption(
                    value = {"--interval", "-i"}, defaultValue = "10",
                    help = "Refresh interval parameter in seconds")
                    @Positive int p_interval) {

        String stringInterval = String.valueOf(p_interval);
        m_currentDateTime = FolderHierarchy.createDateTimeFolderHierarchy(
                m_rootPath + m_folderPath, false);
        Call<StatsPrintResponse> call = m_statsService.printStats(new StatsPrintRequest(stringInterval));
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
            m_errorMessage = error.getError();
            saveErrorResponse();
        } else {
            m_onSuccessMessage = "Statistics are printed every " + p_interval + " seconds";
            m_statsPrintResponse = response.body();
            saveSuccessfulResponse();
        }
    }

    @Override
    public void saveErrorResponse() {
        try {
            File file = new File(m_rootPath + m_folderPath
                    + m_currentDateTime + "error.html");
            Files.write(file.toPath(), m_errorMessage.getBytes());
            Desktop.getDesktop().browse(file.toURI());
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
            File file = new File(m_rootPath + m_folderPath
                    + m_currentDateTime + "response.html");
            Files.write(file.toPath(), m_statsPrintResponse.getHtmlResponse().getBytes());
            Desktop.getDesktop().browse(file.toURI());
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





