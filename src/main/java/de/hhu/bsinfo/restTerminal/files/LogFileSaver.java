package de.hhu.bsinfo.restTerminal.files;

import java.io.File;

public interface LogFileSaver<T> {
    void saveToLogFile(T t);
    String ROOT_PATH = System.getProperty("user.home") + File.separator + "RestTerminal"+ File.separator;
}
