package de.hhu.bsinfo.restTerminal.error;

import com.google.gson.annotations.SerializedName;

/**
 * This class is used to save error messages from the server.
 */
public class APIError {
    @SerializedName("error")
    private String m_error;

    public String getError() {
        return m_error;
    }

    public APIError(String p_error) {
        m_error = p_error;
    }
}
