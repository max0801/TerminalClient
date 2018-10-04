package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.rest.ChunkService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;


@ShellComponent
public class ChunkGet extends AbstractCommand{
    private ChunkService chunkService;

    @ShellMethod("requests a chunk with id <cid> and type <type>")
    public void chunkget(@ShellOption(value = {"--cid", "-c"}, help = "chunk ID of the requested chunk") int cid,
                          @ShellOption(value = {"--type","-t"}, defaultValue = "str",
                                  help ="type of the requested chunk [str,byte,short,int,long]" ) String type){

        System.out.println("requested Chunk on Node "+cid+" with type "+type);

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

