package de.hhu.bsinfo.restTerminal.files;

import java.io.File;

public interface DataFileSaver <T> {
    void saveToDataFile(T t);
}
