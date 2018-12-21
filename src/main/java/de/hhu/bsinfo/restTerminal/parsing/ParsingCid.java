package de.hhu.bsinfo.restTerminal.parsing;

public class ParsingCid {
    public static long parse(String p_str) {
        String str = p_str;
        if (p_str.startsWith("0x")) {
            str = p_str.substring(2);
        }

        return Long.parseUnsignedLong(str, 16);
    }
}
