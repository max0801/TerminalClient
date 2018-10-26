//package de.hhu.bsinfo.restTerminal.cmd;
//
//import de.hhu.bsinfo.restTerminal.AbstractCommand;
//import de.hhu.bsinfo.restTerminal.files.FileSaving;
//import de.hhu.bsinfo.restTerminal.rest.LookupService;
//import org.springframework.shell.standard.ShellComponent;
//import org.springframework.shell.standard.ShellMethod;
//import org.springframework.shell.standard.ShellOption;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardOpenOption;
//
//@ShellComponent
//public class LookupTree extends AbstractCommand implements FileSaving {
//    private LookupService lookupService = retrofit.create(LookupService.class);
//    private int nid;
//    private  String ON_FAILURE_MESSAGE = "NO RESPONSE";
//    private  String FOLDER_PATH = "LookupTree"+ File.separator ;
//    private  String currentDateTime;
//    private  de.hhu.bsinfo.dxram.lookup.overlay.storage.LookupTree LOOKUPTREE_RESPONSE;
//    private  String ERROR_MESSAGE;
//    private  String ON_SUCCESS_MESSAGE;
        //private static final String NODE_REGEX = "(0x(.{4}?))|(.{4}?)";

//
//    @ShellMethod(value = "Get the look up tree of a specified node", group = "Lookup Tree Commands")
//    public void lookuptree(
//            @ShellOption(value = {"--nid", "-n"}, help = "Node <nid> of the created chunk") int nid) {
//        this.nid = nid;
//        String message = "print Lookup tree of node "+nid;
//        System.out.println(message);
////        Call<String> call = chunkService.chunkCreate(nid, size);
////        call.enqueue(new Callback<String>() {
////            @Override
////            public void onResponse(Call<String> call, Response<String> response) {
////                if (APIError.isError(response.body())) {
////                    APIError error = ErrorUtils.parseError(response);
////                    saveToLogFile(error.getMessage());
////                } else {
////                    String answer = response.body();
////                    saveToLogFile(answer);
////                }
////            }
////
////            @Override
////            public void onFailure(Call<String> call, Throwable t) {
////                saveToLogFile(ON_FAILURE_MESSAGE);
////            }
////        });
//    }
//
//    @Override
//    public void saveErrorResponse() throws IOException {
//        Path logFilePath = Paths.get(ROOT_PATH + FOLDER_PATH + currentDateTime + "log.txt");
//        Files.write(logFilePath, ERROR_MESSAGE.getBytes(), StandardOpenOption.CREATE);
//                    printErrorToTerminal();
//    }
//
//    @Override
//    public void saveSuccessfulResponse() throws IOException {
//        Path logFilePath = Paths.get(ROOT_PATH + FOLDER_PATH + currentDateTime + "log.txt");
//        //Files.write(logFilePath, ON_SUCCESS_MESSAGE.getBytes(), StandardOpenOption.CREATE);
//
//        Path dataFilePath = Paths.get(ROOT_PATH + FOLDER_PATH + currentDateTime + "data.txt");
//        //Files.write(dataFilePath, LOOKUPTREE_RESPONSE.toString().getBytes(), StandardOpenOption.CREATE);
//    }
//}
