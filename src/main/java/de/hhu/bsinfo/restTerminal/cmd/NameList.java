package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.rest.ChunkService;
import de.hhu.bsinfo.restTerminal.rest.NameService;
import picocli.CommandLine;

@CommandLine.Command(
        name = "namelist",
        description = "get namelist"
)
public class NameList extends AbstractCommand implements Runnable {
    private NameService nameService;

    @Override
    public void run() {
        //REST-Server is not set up yet
        //nameService = retrofit.create(NameService.class);
        //nameService.nameList();
        System.out.println("print the namelist");
    }
}
