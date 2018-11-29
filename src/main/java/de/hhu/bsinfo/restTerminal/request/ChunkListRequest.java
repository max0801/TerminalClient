package de.hhu.bsinfo.restTerminal.request;

import com.google.gson.annotations.SerializedName;

public class ChunkListRequest {
    @SerializedName("nid")
    private String m_nid;

    public ChunkListRequest(String p_nid) {
        m_nid = p_nid;
    }
}
