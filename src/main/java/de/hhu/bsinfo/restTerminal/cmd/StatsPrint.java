package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.rest.MonitoringService;
import de.hhu.bsinfo.restTerminal.rest.StatsService;
import picocli.CommandLine;

@CommandLine.Command(
        name = "statsprint",
        description = "get debug information every <interval> seconds"
)
public class StatsPrint extends AbstractCommand implements Runnable {
    private StatsService statsService;

    @CommandLine.Option(
            names = {"-i", "--interval"},
            paramLabel = "INTERVAL",
            description = "Refresh interval parameter in seconds")
    private int interval = 10;

    @Override
    public void run() {
        //REST-Server is not set up yet
        //statsService = retrofit.create(StatsService.class);
        //statsService.printStats(interval);
        System.out.println("printing stats every "+ interval+" seconds");
    }
}

