package de.hhu.bsinfo.restTerminal;


import de.hhu.bsinfo.restTerminal.cmd.ChunkCreate;
import de.hhu.bsinfo.restTerminal.cmd.ChunkGet;
import picocli.CommandLine;

@CommandLine.Command(
        name = "dxterm",
        description = "Terminal Program in order to work with DXRAM",
        subcommands = {ChunkCreate.class, ChunkGet.class}
        )

public class ToolBase implements Runnable {
    @Override
    public void run() {
        CommandLine.usage(this, System.out);
    }
}
