package de.hhu.bsinfo.restTerminal;

import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.awt.*;
import java.util.Locale;

@SpringBootApplication
public class TerminalMain {
    public static void main(final String[] p_args) {
       Locale.setDefault(new Locale("en", "US"));
       FolderHierarchy.createFolderHierarchy();
       //GraphicsEnvironment needs to be refreshed in order for the app to be headless
        GraphicsEnvironment.isHeadless();
        SpringApplication.run(TerminalMain.class);
        System.exit(0);
    }
}



