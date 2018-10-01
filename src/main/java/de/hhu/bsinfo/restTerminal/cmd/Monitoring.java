package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.rest.MonitoringService;
import de.hhu.bsinfo.restTerminal.rest.NameService;
import picocli.CommandLine;

@CommandLine.Command(
        name = "monitor",
        description = "gets monitoring data from node <nid>"
)
public class Monitoring extends AbstractCommand implements Runnable {
    private MonitoringService monitoringService;

    @CommandLine.Option(
            names = {"-n", "--nid"},
            required = true,
            paramLabel = "NID",
            description = "node <nid> which is monitored")
    private int nid;

    @Override
    public void run() {
        //REST-Server is not set up yet
        //monitoringService = retrofit.create(MonitoringService.class);
        //monitoringService.monitor(nid);
        System.out.println("got monitor data from Node " + nid);
    }
}

