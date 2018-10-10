package de.hhu.bsinfo.restTerminal.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FolderHierarchy {
    public static void createFolderHierarchy() {
        String root = System.getProperty("user.home") + File.separator + "RestTerminal";
        String dirChunkCreate = root + File.separator + "ChunkCreate";
        String dirChunkDump = root + File.separator + "ChunkDump";
        String dirChunkGet = root + File.separator + "ChunkGet";
        String dirChunkList = root + File.separator + "ChunkList";
        String dirChunkPut = root + File.separator + "ChunkPut";
        String dirMonitoring = root + File.separator + "Monitoring";
        String dirNameList = root + File.separator + "NameList";
        String dirNameReg = root + File.separator + "NameReg";
        String dirNodeList = root + File.separator + "NodeList";
        String dirStatsPrint = root + File.separator + "StatsPrint";

        String LOG = File.separator + "log";
        String DATA = File.separator + "data";

        try {
            Path rootPath = Paths.get(root);
            Path chunkCreate = Paths.get(dirChunkCreate);
            Path chunkDump = Paths.get(dirChunkDump);
            Path chunkGet = Paths.get(dirChunkGet);
            Path chunkList = Paths.get(dirChunkList);
            Path chunkPut = Paths.get(dirChunkPut);
            Path monitoring = Paths.get(dirMonitoring);
            Path nameList = Paths.get(dirNameList);
            Path nameReg = Paths.get(dirNameReg);
            Path nodeList = Paths.get(dirNodeList);
            Path statsPrint = Paths.get(dirStatsPrint);

//            Path chunkCreateLog = Paths.get(dirChunkCreate + LOG);
//            Path chunkDumpLog = Paths.get(dirChunkDump + LOG);
//            Path chunkGetLog = Paths.get(dirChunkGet + LOG);
//            Path chunkListLog = Paths.get(dirChunkList + LOG);
//            Path chunkPutLog = Paths.get(dirChunkPut + LOG);
//            Path monitoringLog = Paths.get(dirMonitoring);
//            Path nameListLog = Paths.get(dirNameList + LOG);
//            Path nameRegLog = Paths.get(dirNameReg + LOG);
//            Path nodeListLog = Paths.get(dirNodeList + LOG);
//            Path statsPrintLog = Paths.get(dirStatsPrint + LOG);
//
//            Path chunkGetData = Paths.get(dirChunkGet + DATA);
//            Path chunkListData = Paths.get(dirChunkList + DATA);
//            Path monitoringData = Paths.get(dirMonitoring + DATA);
//            Path nameListData = Paths.get(dirNameList + DATA);
//            Path nodeListData = Paths.get(dirNodeList + DATA);
//            Path statsPrintData = Paths.get(dirStatsPrint + DATA);


            Files.createDirectories(rootPath);
            Files.createDirectories(chunkCreate);
            Files.createDirectories(chunkDump);
            Files.createDirectories(chunkGet);
            Files.createDirectories(chunkList);
            Files.createDirectories(chunkPut);
            Files.createDirectories(monitoring);
            Files.createDirectories(nameList);
            Files.createDirectories(nameReg);
            Files.createDirectories(nodeList);
            Files.createDirectories(statsPrint);

//            Files.createDirectories(chunkCreateLog);
//            Files.createDirectories(chunkDumpLog);
//            Files.createDirectories(chunkGetLog);
//            Files.createDirectories(chunkListLog);
//            Files.createDirectories(chunkPutLog);
//            Files.createDirectories(monitoringLog);
//            Files.createDirectories(nameListLog);
//            Files.createDirectories(nameRegLog);
//            Files.createDirectories(nodeListLog);
//            Files.createDirectories(statsPrintLog);
//
//            Files.createDirectories(chunkGetData);
//            Files.createDirectories(chunkListData);
//            Files.createDirectories(monitoringData);
//            Files.createDirectories(nameListData);
//            Files.createDirectories(nodeListData);
//            Files.createDirectories(statsPrintData);


        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static String createDateTimeFolderHierarchy(String path, boolean dataAndLog) throws IOException {
        String dateTime = new SimpleDateFormat("yyyy-MM-dd hh-mm-ss").format(new Date())+File.separator;
        String pathDateTime = path + dateTime + File.separator;
        Path directory = Paths.get(pathDateTime);
        Files.createDirectories(directory);
        Files.createFile(Paths.get(pathDateTime+"log.txt"));
        if (dataAndLog) {
            Files.createFile(Paths.get(pathDateTime+"data.txt"));
        }
        return dateTime;
    }


}
