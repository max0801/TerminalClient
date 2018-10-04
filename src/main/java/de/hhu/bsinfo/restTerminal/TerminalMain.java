package de.hhu.bsinfo.restTerminal;


import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import picocli.CommandLine;

import java.util.Locale;
@SpringBootApplication
public class TerminalMain {

    public static void main(final String[] p_args) {
        Locale.setDefault(new Locale("en", "US"));
        //CommandLine.run(new ToolBase(), p_args);
        SpringApplication.run(TerminalMain.class, p_args);
        System.exit(0);
    }
}



