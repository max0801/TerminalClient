package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.rest.ChunkService;
import picocli.CommandLine;
import retrofit2.Retrofit;

@CommandLine.Command(
        name = "chunkcreate",
        description = "Creates a Chunk on Node <nid> with Size <size>"
)
public class ChunkCreate implements Runnable {
    @CommandLine.Parameters(
            index = "0",
            paramLabel = "nid",
            description = "node ID of dxram peer where the chunk is created")
    private int nid;
    @CommandLine.Parameters(
            index = "1",
            paramLabel = "chunkSize",
            description = "size of the created chunk in byte")
    private int size;
    private ChunkService chunkService;

    @Override
    public void run() {
        //add rest-api function use here
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://")
                .build();

        ChunkService service = retrofit.create(ChunkService.class);
        service.chunkCreate(nid,size);
        System.out.println("created Chunk on Node "+nid+" with size "+size);
    }
}
