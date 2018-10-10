package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.rest.ChunkService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;


@ShellComponent
public class ChunkGet extends AbstractCommand {
    private ChunkService chunkService = retrofit.create(ChunkService.class);

    @ShellMethod(value = "requests a chunk with id <cid> and type <type>", group = "Chunk Commands")
    public void chunkget(
            @ShellOption(value = {"--cid", "-c"}, help = "chunk ID of the requested chunk") int cid,
            @ShellOption(value = {"--type", "-t"}, defaultValue = "str",
                    help = "type of the requested chunk [str,byte,short,int,long]") String type,
            @ShellOption(value = {"--print", "-p"}, help = "print chunk to stdout", defaultValue = "false") boolean print) {

        System.out.println("requested Chunk on Node " + cid + " with type " + type);
        System.out.println("print: " + print);

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

        String response = "Chunk: 53454322304203894";
        //SaveFile.saveFileToWorkingDirectory(response);

    }


}

