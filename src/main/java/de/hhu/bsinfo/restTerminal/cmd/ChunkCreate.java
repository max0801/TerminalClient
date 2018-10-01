package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.rest.ChunkService;
import picocli.CommandLine;
import retrofit2.Retrofit;

@CommandLine.Command(
        name = "chunkcreate",
        description = "creates a chunk on node <nid> with size <size>"
)
public class ChunkCreate extends AbstractCommand implements Runnable {
    @CommandLine.Option(
            names = {"-n","--nid"},
            required = true,
            paramLabel = "NID",
            description = "chunk id of the created chunk")
    private int nid;
    @CommandLine.Option(
            names = {"-s", "--size"},
            paramLabel = "SIZE",
            description = "size of the created chunk in byte")
    private int size = 16;
    private ChunkService chunkService;

    @Override
    public void run() {
        //REST-Server is not set up yet
        //chunkService = retrofit.create(ChunkService.class);
        //chunkService.chunkCreate(nid,size);
        System.out.println("created Chunk on Node "+nid+" with size "+size);
    }
}
