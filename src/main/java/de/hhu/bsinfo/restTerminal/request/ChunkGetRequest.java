package de.hhu.bsinfo.restTerminal.request;

import com.google.gson.annotations.SerializedName;

public class ChunkGetRequest {
    @SerializedName("cid")
    private String m_cid;
    @SerializedName("type")
    private String m_type;

    public ChunkGetRequest(String p_cid, String p_type) {
        m_cid = p_cid;
        m_type = p_type;
    }
}
