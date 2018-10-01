package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.rest.ChunkService;
import picocli.CommandLine;

@CommandLine.Command(
        name = "chunkput",
        description = "put <data> with <type> on chunk <cid>"
)
public class ChunkPut extends AbstractCommand implements Runnable {
    @CommandLine.Option(
            names = {"-c","--cid"},
            required = true,
            paramLabel = "CID",
            description = "chunk id of the submitted chunk")
    private int cid;
    @CommandLine.Option(
            names = {"-d", "--data"},
            required = true,
            paramLabel = "DATA",
            description = "data that is saved in the chunk")
    private Object data;

    @CommandLine.Option(
            names = {"-t", "--type"},
            paramLabel = "TYPE",
            description = "type of the submitted chunk [str,byte,short,int,long]")
    private String type = "str";
    private ChunkService chunkService;

    @Override
    public void run() {
        //REST-Server is not set up yet
        //chunkService = retrofit.create(ChunkService.class);
        //chunkService.chunkPut(cid, type, data);
        System.out.println("put Chunk on Chunk with id "+cid+" with type "+type+" with data"+data);
    }
}
