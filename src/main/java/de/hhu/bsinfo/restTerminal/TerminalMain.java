package de.hhu.bsinfo.restTerminal;


import de.hhu.bsinfo.restTerminal.files.FolderHierarchy;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.awt.*;
import java.util.Locale;

import static org.springframework.boot.SpringApplication.run;

@SpringBootApplication
public class TerminalMain {
    public static void main(final String[] p_args) {
        Locale.setDefault(new Locale("en", "US"));
        FolderHierarchy.createFolderHierarchy();
        SpringApplication app = new SpringApplication();
        app.setHeadless(false);
        run(TerminalMain.class, p_args);
        System.exit(0);
    }
}



