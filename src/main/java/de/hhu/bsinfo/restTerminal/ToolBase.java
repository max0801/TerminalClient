package de.hhu.bsinfo.restTerminal;


import de.hhu.bsinfo.restTerminal.cmd.*;
import picocli.CommandLine;

@CommandLine.Command(
        name = "dxterm",
        description = "Terminal Program in order to work with DXRAM",
        subcommands = {ChunkCreate.class, ChunkGet.class, ChunkDump.class, ChunkList.class,
                ChunkPut.class, Monitoring.class, NameList.class, NameReg.class, NodeList.class, StatsPrint.class}
        )

public class ToolBase implements Runnable {
    @Override
    public void run() {
        CommandLine.usage(this, System.out);
    }
}
