package de.hhu.bsinfo.restTerminal.error;

public class APIError {
    private String error;

    public String getError() {
        return error;
    }

    public APIError(String p_error) {
        error = p_error;
    }
}
