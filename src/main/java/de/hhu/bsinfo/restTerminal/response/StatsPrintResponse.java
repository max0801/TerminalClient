package de.hhu.bsinfo.restTerminal.response;

import com.google.gson.annotations.SerializedName;

public class StatsPrintResponse {
    @SerializedName("stats")
    private String m_htmlResponse;

    public StatsPrintResponse(String p_message, String p_interval) {
        this.m_htmlResponse = "<html><head><title>DXRAM Statistics</title><meta " +
                "http-equiv=\"refresh\" content=\"" + p_interval +
                "\" ></head><body> <pre> <code>" + p_message + "</code> </pre> </body> </html>";
    }

    public String getHtmlResponse() {
        return m_htmlResponse;
    }
}
