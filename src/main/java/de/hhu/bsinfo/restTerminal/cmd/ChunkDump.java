package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.rest.ChunkService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class ChunkDump extends AbstractCommand{
    private ChunkService chunkService;

    @ShellMethod("creates a filedump of <cid> saved as <name>")
    public void chunkdump(@ShellOption(value = {"--filename", "-f"}, help = "filename of the dump")String filename,
                            @ShellOption(value = {"--cid","-c"}, help ="chunk of which a filedump is created" ) int cid){

        System.out.println("created Filedump with name "+filename+" of Chunk "+cid);
        /*
        chunkService = retrofit.create(ChunkService.class);
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
