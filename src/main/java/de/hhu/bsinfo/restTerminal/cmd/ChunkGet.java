package de.hhu.bsinfo.restTerminal.cmd;

import picocli.CommandLine;

@CommandLine.Command(
        name = "chunkget",
        description = "Requests a Chunk with id <cid> and type <type>"
)
public class ChunkGet implements Runnable {
    @CommandLine.Parameters(
            index = "0",
            paramLabel = "cid",
            description = "chunk ID of the requested chunk")
    private int cid;
    @CommandLine.Parameters(
            index = "1",
            paramLabel = "type",
            description = "type of the requested chunk")
    private String type;


    @Override
    public void run() {
        System.out.println("requested Chunk on Node " + cid + " with size " + type);
    }
}

