package de.hhu.bsinfo.restTerminal.error;

public class APIError {
    private String message;
    private static String ERROR_PATTERN = "{ \"error\"";
    public static boolean isError(String response) {
        //wenn der String mit "error" annotiert ist, ist es ein Fehler
        if(response.startsWith(ERROR_PATTERN)) {
            return true;
        }
        return false;
    }

    public String getMessage() {
        return message;
    }
}
