package de.hhu.bsinfo.restTerminal.request;

import com.google.gson.annotations.SerializedName;

public class NodeInfoRequest {
    @SerializedName("nid")
    private String m_nid;

    public NodeInfoRequest(String p_nid) {
        m_nid = p_nid;
    }
}
