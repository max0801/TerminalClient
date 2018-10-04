package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.error.APIError;
import de.hhu.bsinfo.restTerminal.error.ErrorUtils;
import de.hhu.bsinfo.restTerminal.rest.ChunkService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@ShellComponent
public class ChunkCreate extends AbstractCommand{
    private ChunkService chunkService;

    @ShellMethod("creates a chunk on node <nid> with size <size>")
    public void createchunk(@ShellOption(value = {"--nid", "-n"}, help = "node id of the created chunk")int nid,
                            @ShellOption(value = {"--size","-s"}, defaultValue = "16",
                                    help ="size of the created chunk in byte" ) int size){

        System.out.println("created Chunk on Node "+nid+" with size "+size);

        /*chunkService = retrofit.create(ChunkService.class);
        Call<String> call = chunkService.chunkCreate(nid,size);
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
