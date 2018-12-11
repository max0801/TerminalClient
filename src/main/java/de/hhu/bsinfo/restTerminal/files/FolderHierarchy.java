package de.hhu.bsinfo.restTerminal.files;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Class for the organization of data storage
 */
public class FolderHierarchy {
    /**
     * creates the folder system in the user's home directory
     */
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
            Path m_rootPath = Paths.get(root);
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

            Files.createDirectories(m_rootPath);
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
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * creates a folder with a log file and as appropriate a data file
     * @param p_path path where the folder is created
     * @param p_dataAndLog determines if a log and a data file is created
     * @return name of the created folder which is the current DateTime
     */
    public static String createDateTimeFolderHierarchy(String p_path, boolean p_dataAndLog) {
        String dateTime = null;
        try {
            dateTime = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss").
                    format(new Date()) + File.separator;
            String pathDateTime = p_path + dateTime + File.separator;
            Path directory = Paths.get(pathDateTime);
            Files.createDirectories(directory);
            Files.createFile(Paths.get(pathDateTime + "log.txt"));
            if (p_dataAndLog) {
                Files.createFile(Paths.get(pathDateTime + "data.txt"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dateTime;
    }


}
