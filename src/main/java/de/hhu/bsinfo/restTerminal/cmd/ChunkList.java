package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.rest.ChunkService;
import picocli.CommandLine;

@CommandLine.Command(
        name = "chunklist",
        description = "list all chunks on node <nid>"
)
public class ChunkList extends AbstractCommand implements Runnable {
    @CommandLine.Option(
            names = {"-n","--nid"},
            required = true,
            paramLabel = "NID",
            description = "The node <nid> where the list of chunks is referring to")
    private int nid;
    private ChunkService chunkService;

    @Override
    public void run() {
        //REST-Server is not set up yet
        //chunkService = retrofit.create(ChunkService.class);
        //chunkService.chunkList(nid);
        System.out.println("list of all Chunks on NID "+nid);
    }
}
