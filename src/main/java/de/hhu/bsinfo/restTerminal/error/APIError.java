package de.hhu.bsinfo.restTerminal.error;

public class APIError {
    private String message;

    public static boolean isError(String response) {
        //wenn der String mit "error" annotiert ist, ist es ein Fehler
        return false;
    }

    public void printError(){
        System.out.println(message);
    }
}
