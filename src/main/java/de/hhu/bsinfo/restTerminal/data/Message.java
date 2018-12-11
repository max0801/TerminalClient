package de.hhu.bsinfo.restTerminal.data;

import com.google.gson.annotations.SerializedName;

/**
 * This class is used to save responses from various
 * commands that only contain status messages.
 */
public class Message {
    @SerializedName("message")
    private String m_message;

    public String getMessage() {
        return m_message;
    }

    public Message(String p_message) {
        m_message = p_message;
    }
}
