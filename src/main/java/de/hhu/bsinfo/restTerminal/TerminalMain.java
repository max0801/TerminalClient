package de.hhu.bsinfo.restTerminal;

import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import de.hhu.bsinfo.restTerminal.AbstractCommand.*;
import java.awt.*;
import java.util.Locale;

/**
 * Starting point of the Spring Shell Application
 */
@SpringBootApplication
public class TerminalMain {
    public static void main(final String[] p_args) {
        //System.out.println(System.getProperty("BASE_URL"));
        Locale.setDefault(new Locale("en", "US"));
       FolderHierarchy.createFolderHierarchy();
       //GraphicsEnvironment needs to be refreshed in order for the app to be headless
        GraphicsEnvironment.isHeadless();
        SpringApplication.run(TerminalMain.class);
        System.exit(0);
    }
}



