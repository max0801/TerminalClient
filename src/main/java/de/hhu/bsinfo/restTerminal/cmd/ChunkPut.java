package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.rest.ChunkService;
import picocli.CommandLine;

@CommandLine.Command(
        name = "chunkput",
        description = "Put <data> with <type> on Chunk with <cid>"
)
public class ChunkPut extends AbstractCommand implements Runnable {
    @CommandLine.Option(
            names = {"-c","--cid"},
            required = true,
            paramLabel = "CID",
            description = "Chunk ID where the chunk is put")
    private int cid;
    @CommandLine.Option(
            names = {"-d", "--data"},
            required = true,
            paramLabel = "DATA",
            description = "data that is put on the chunk")
    private Object data;

    @CommandLine.Option(
            names = {"-t", "--type"},
            paramLabel = "TYPE",
            description = "type of the put chunk [str,byte,short,int,long]")
    private String type = "str";
    private ChunkService chunkService;

    @Override
    public void run() {
        //REST-Server is not set up yet
        //chunkService = retrofit.create(ChunkService.class);
        //chunkService.chunkCreate(nid,size);
        System.out.println("put Chunk on Chunk with id "+cid+" with type "+type+" with data"+data);
    }
}
