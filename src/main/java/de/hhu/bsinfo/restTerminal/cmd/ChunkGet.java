package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.rest.ChunkService;
import picocli.CommandLine;
import retrofit2.Retrofit;

@CommandLine.Command(
        name = "chunkget",
        description = "requests a chunk with id <cid> and type <type>"
)
public class ChunkGet extends AbstractCommand implements Runnable {
    @CommandLine.Option(
            names = {"-c","--cid"},
            required = true,
            paramLabel = "CID",
            description = "chunk ID of the requested chunk")
    private int cid;
    @CommandLine.Option(
            names = {"-t", "--type"},
            paramLabel = "TYPE",
            description = "type of the requested chunk [str,byte,short,int,long]")
    private String type = "str";
    private ChunkService chunkService;

    @Override
    public void run() {
        //REST-Server is not set up yet
        //chunkService = retrofit.create(ChunkService.class);
        //chunkService.chunkGet(cid,type);
        System.out.println("requested Chunk on Node "+cid+" with type "+type);

    }
}

