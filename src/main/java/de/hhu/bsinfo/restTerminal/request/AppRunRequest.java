package de.hhu.bsinfo.restTerminal.request;

import com.google.gson.annotations.SerializedName;

public class AppRunRequest {
    @SerializedName("nid")
    private String m_nid;
    @SerializedName("app")
    private String m_appName;

    public AppRunRequest(String p_nid, String p_appName) {
        m_nid = p_nid;
        m_appName = p_appName;
    }
}
