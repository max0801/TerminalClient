package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.rest.NodeService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;


@ShellComponent
public class NodeList extends AbstractCommand{
    private NodeService nodeService;

    @ShellMethod("get nodelist")
    public void nodelist() {

        System.out.println("print the nodelist");

        /*
        //chunkService = retrofit.create(ChunkService.class);
        //chunkService.chunkGet(cid,type);
        Call<String> call = chunkService.chunkDump(cid, filename);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(APIError.isError(response.body())){
                    APIError error = ErrorUtils.parseError(response);
                    error.printError();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                System.out.println("Something else happenend");
            }
        });*/
    }


}



