package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.rest.NameService;
import de.hhu.bsinfo.restTerminal.rest.NodeService;
import picocli.CommandLine;

@CommandLine.Command(
        name = "nodelist",
        description = "get nodelist"
)
public class NodeList extends AbstractCommand implements Runnable {
    private NodeService nodeService;

    @Override
    public void run() {
        //REST-Server is not set up yet
        //nodeService = retrofit.create(NodeService.class);
        //nodeService.nodeList();
        System.out.println("print the nodelist");
    }
}
