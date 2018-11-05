package de.hhu.bsinfo.restTerminal.files;

import java.io.IOException;

public interface FileSaving {
    void saveErrorResponse() throws IOException;
    void saveSuccessfulResponse() throws IOException;
}
