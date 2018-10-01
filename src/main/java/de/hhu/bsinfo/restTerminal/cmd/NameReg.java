package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.rest.NameService;
import picocli.CommandLine;

@CommandLine.Command(
        name = "namereg",
        description = "register chunk <cid> with <name>"
)
public class NameReg extends AbstractCommand implements Runnable {
    private NameService nameService;

    @CommandLine.Option(
            names = {"-c","--cid"},
            required = true,
            paramLabel = "CID",
            description = "chunk <cid> which is named")
    private int cid;
    @CommandLine.Option(
            names = {"-n", "--name"},
            required = true,
            paramLabel = "NAME",
            description = "name of the chunk")
    private String name ;

    @Override
    public void run() {
        //REST-Server is not set up yet
        //nameService = retrofit.create(NameService.class);
        //nameService.nameReg();
        System.out.println("named chunk "+ cid+ " to "+name);
    }
}
