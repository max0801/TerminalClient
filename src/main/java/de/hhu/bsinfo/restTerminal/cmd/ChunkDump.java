package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.rest.ChunkService;
import picocli.CommandLine;
import retrofit2.Retrofit;

@CommandLine.Command(
        name = "chunkdump",
        description = "creates a filedump of <cid> saved as <name>."
)
public class ChunkDump extends AbstractCommand implements Runnable {
    @CommandLine.Option(
            names = {"-f","--filename"},
            required = true,
            paramLabel = "FILENAME",
            description = "filename of the dump")
    private String filename;
    @CommandLine.Option(
            names = {"-c", "--cid"},
            required = true,
            paramLabel = "CID",
            description = "chunk of which a filedump is created")
    private int cid;
    private ChunkService chunkService;

    @Override
    public void run() {
        //REST-Server is not set up yet
        //chunkService = retrofit.create(ChunkService.class);
        //chunkService.chunkDump(cid, filename);
        System.out.println("created Filedump with name "+filename+" of Chunk "+cid);
    }
}