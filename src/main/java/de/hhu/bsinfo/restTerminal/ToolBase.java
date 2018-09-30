package de.hhu.bsinfo.restTerminal;


import de.hhu.bsinfo.restTerminal.cmd.ChunkCreate;
import picocli.CommandLine;

@CommandLine.Command(
        name = "dxterm",
        description = "Terminal Program in order to work with DXRAM",
        subcommands = {ChunkCreate.class}
        )

public class ToolBase implements Runnable {
    @Override
    public void run() {
        CommandLine.usage(this, System.out);
    }
}
