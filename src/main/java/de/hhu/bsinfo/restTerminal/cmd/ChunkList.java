package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.rest.ChunkService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class ChunkList extends AbstractCommand {
    private ChunkService chunkService = retrofit.create(ChunkService.class);

    @ShellMethod(value = "list all chunks on node <nid>", group = "Chunk Commands")
    public void chunklist(
            @ShellOption(value = {"--nid", "-n"}, help = "The node <nid> where the list of chunks is referring to") int nid,
            @ShellOption(value = {"--print", "-p"}, help = "print chunklist to stdout", defaultValue = "false") boolean print) {

        System.out.println("list of all Chunks on NID " + nid);

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



