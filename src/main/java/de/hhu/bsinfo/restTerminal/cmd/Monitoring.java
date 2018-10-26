package de.hhu.bsinfo.restTerminal.cmd;

import de.hhu.bsinfo.dxram.monitoring.MonitoringDataStructure;
import de.hhu.bsinfo.restTerminal.AbstractCommand;
import de.hhu.bsinfo.restTerminal.files.FileSaving;
import de.hhu.bsinfo.restTerminal.rest.MonitoringService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;


//@ShellComponent
//public class Monitoring extends AbstractCommand implements FileSaving {
//    private MonitoringService monitoringService = retrofit.create(MonitoringService.class);
//    private  String currentDateTime;
//    private  MonitoringDataStructure MONITORING_RESPONSE;
//    private  String ERROR_MESSAGE;
//    private  String ON_SUCCESS_MESSAGE;
//    private  String FOLDER_PATH = "Monitoring" + File.separator;
//          private static final String NODE_REGEX = "(0x(.{4}?))|(.{4}?)";
//
//    @ShellMethod(value = "gets monitoring data from node <nid>", group = "Monitoring Commands")
//    public void monitor(
//            @ShellOption(value = {"--nid", "-n"}, help = "node <nid> which is monitored") int nid,
//            @ShellOption(value = {"--print", "-p"}, help = "print monitoring data to stdout",
//                    defaultValue = "false") boolean print) {
//
//        System.out.println("got monitor data from Node " + nid);
//
//        /*
//        //chunkService = retrofit.create(ChunkService.class);
//        //chunkService.chunkGet(cid,type);
//        Call<String> call = chunkService.chunkDump(cid, filename);
//        call.enqueue(new Callback<String>() {
//            @Override
//            public void onResponse(Call<String> call, Response<String> response) {
//                if(APIError.isError(response.body())){
//                    APIError error = ErrorUtils.parseError(response);
//                    error.printError();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<String> call, Throwable t) {
//                System.out.println("Something else happenend");
//            }
//        });*/
//    }
//
//
//
//    @Override
//    public void saveErrorResponse() throws IOException {
//        Path logFilePath = Paths.get(ROOT_PATH + FOLDER_PATH + currentDateTime + "log.txt");
//        Files.write(logFilePath, ERROR_MESSAGE.getBytes(), StandardOpenOption.CREATE);
//                        printErrorToTerminal();

//    }
//
//    @Override
//    public void saveSuccessfulResponse() throws IOException {
//        Path logFilePath = Paths.get(ROOT_PATH + FOLDER_PATH + currentDateTime + "log.txt");
//        Files.write(logFilePath, ON_SUCCESS_MESSAGE.getBytes(), StandardOpenOption.CREATE);
//
////        Path dataFilePath = Paths.get(ROOT_PATH + FOLDER_PATH + currentDateTime + "data.txt");
////        Files.write(dataFilePath, MONITORING_RESPONSE.getCpuLoads().toString().getBytes(), StandardOpenOption.CREATE);
////        Files.write(dataFilePath, String.valueOf(MONITORING_RESPONSE.getCpuUsage()).getBytes(), StandardOpenOption.CREATE);
////        Files.write(dataFilePath, MONITORING_RESPONSE.getDiskStats().toString().getBytes(), StandardOpenOption.CREATE);
////        Files.write(dataFilePath, MONITORING_RESPONSE.getJvmMemStats().toString().getBytes(), StandardOpenOption.CREATE);
////        Files.write(dataFilePath, MONITORING_RESPONSE.getJvmThreadStats().toString().getBytes(), StandardOpenOption.CREATE);
////        Files.write(dataFilePath, String.valueOf(MONITORING_RESPONSE.getMemoryUsage()).getBytes(), StandardOpenOption.CREATE);
////        Files.write(dataFilePath, MONITORING_RESPONSE.getNetworkStats().toString().getBytes(), StandardOpenOption.CREATE);
////        Files.write(dataFilePath, String.valueOf(MONITORING_RESPONSE.getNid()).getBytes(), StandardOpenOption.CREATE);
////        Files.write(dataFilePath, String.valueOf(MONITORING_RESPONSE.getTimestamp()).getBytes(), StandardOpenOption.CREATE);
//    }
//}
//
//
//
