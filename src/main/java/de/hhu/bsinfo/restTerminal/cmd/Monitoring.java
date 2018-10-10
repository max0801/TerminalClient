package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.rest.MonitoringService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;


@ShellComponent
public class Monitoring extends AbstractCommand {
    private MonitoringService monitoringService = retrofit.create(MonitoringService.class);

    @ShellMethod(value = "gets monitoring data from node <nid>", group = "Monitoring Commands")
    public void monitor(
            @ShellOption(value = {"--nid", "-n"}, help = "node <nid> which is monitored") int nid,
            @ShellOption(value = {"--print", "-p"}, help = "print monitoring data to stdout",
                    defaultValue = "false") boolean print) {

        System.out.println("got monitor data from Node " + nid);

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




