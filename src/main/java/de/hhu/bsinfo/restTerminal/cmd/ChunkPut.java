package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.rest.ChunkService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import picocli.CommandLine;


@ShellComponent
public class ChunkPut extends AbstractCommand{
    private ChunkService chunkService;

    @ShellMethod("put <data> with <type> on chunk <cid>")
    public void chunkput(@ShellOption(value = {"--cid", "-c"}, help = "chunk ID of the submitted chunk") int cid,
                         @ShellOption(value = {"--data", "-d"}, help = "data that is saved in the chunk") Object data,
                         @ShellOption(value = {"--type","-t"}, defaultValue = "str",
                                 help ="type of the submitted chunk [str,byte,short,int,long]" ) String type){

        System.out.println("put Chunk on Chunk with id "+cid+" with type "+type+" with data"+data);

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


