package de.hhu.bsinfo.restTerminal.request;

import com.google.gson.annotations.SerializedName;

public class MetadataRequest {
    @SerializedName("nid")
    private String m_nid;

    public MetadataRequest(String p_nid) {
        m_nid = p_nid;
    }
}
