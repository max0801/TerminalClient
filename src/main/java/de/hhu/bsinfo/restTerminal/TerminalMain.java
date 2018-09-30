package de.hhu.bsinfo.restTerminal;


import picocli.CommandLine;

import java.util.Locale;

public class TerminalMain {

    public static void main(final String[] p_args) {
        Locale.setDefault(new Locale("en", "US"));
        CommandLine.run(new ToolBase(), p_args);
        System.exit(0);
    }

}
