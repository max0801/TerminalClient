package de.hhu.bsinfo.restTerminal.cmd;

//@ShellComponent
//public class LogInfo extends AbstractCommand  {
//    private LogService logService = retrofit.create(LogService.class);
//    private String nid;
//    private  String folderPath = "LogInfo"+ File.separator ;
//    private  String currentDateTime;
//    private  String logInfoResponse;
//    private  String errorMessage;
//    private  String onSuccessMessage;
//    private static final String NODE_REGEX = "(0x(.{4}?))|(.{4}?)";
//    private boolean print;
//    @ShellMethod(value = "Prints the log utilization of given peer", group = "Log Commands")
//    public void loginfo(
//            @ShellOption(value = {"--nid", "-n"},
//                    help = "Node <nid> where the log info is requested from") String nid,
//            @ShellOption(value = {"--print", "-p"},
//                    help = "print loginfo to stdout", defaultValue = "false") boolean print) {
//        this.nid = nid;
//        this.print = print;
//        String message = "Log Info of node "+nid;
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
//        Path logFilePath = Paths.get(m_rootPath + folderPath + currentDateTime + "log.txt");
//        Files.write(logFilePath, errorMessage.getBytes(), StandardOpenOption.CREATE);
//        printErrorToTerminal();
//    }
//
//    @Override
//    public void saveSuccessfulResponse() throws IOException {
//        Path logFilePath = Paths.get(m_rootPath + folderPath + currentDateTime + "log.txt");
//        Files.write(logFilePath, onSuccessMessage.getBytes(), StandardOpenOption.CREATE);
//
//        Path dataFilePath = Paths.get(m_rootPath + folderPath + currentDateTime + "data.txt");
//        Files.write(dataFilePath, logInfoResponse.getBytes(), StandardOpenOption.CREATE);
//    }
//
//    @Override
//    public void printErrorToTerminal() {
//        System.out.println("ERROR");
//        System.out.println("Please check out the following file: "
//                + m_rootPath + folderPath + currentDateTime + "log.txt");
//    }
//}
