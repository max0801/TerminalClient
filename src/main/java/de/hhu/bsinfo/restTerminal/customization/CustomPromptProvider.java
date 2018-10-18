package de.hhu.bsinfo.restTerminal.customization;


import org.jline.utils.AttributedStyle;
import org.springframework.boot.ansi.AnsiColor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.stereotype.Component;

import java.text.AttributedString;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CustomPromptProvider implements PromptProvider {
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RESET = "\u001B[0m";
    @Override
    public org.jline.utils.AttributedString getPrompt() {
        return new org.jline.utils.AttributedString(ANSI_GREEN+"dxterm-rest:>"+ ANSI_RESET);
    }
}