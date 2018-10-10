package de.hhu.bsinfo.restTerminal;


import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Locale;

@SpringBootApplication
public class TerminalMain {

    public static void main(final String[] p_args) {
        Locale.setDefault(new Locale("en", "US"));
        FolderHierarchy.createFolderHierarchy();
        SpringApplication.run(TerminalMain.class, p_args);
        System.exit(0);
    }


}



