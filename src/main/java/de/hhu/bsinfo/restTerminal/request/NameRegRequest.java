package de.hhu.bsinfo.restTerminal.request;

import com.google.gson.annotations.SerializedName;

public class NameRegRequest {
    @SerializedName("cid")
    private String m_cid;
    @SerializedName("name")
    private String m_name;

    public NameRegRequest(String p_cid, String p_name) {
        m_cid = p_cid;
        m_name = p_name;
    }
}
