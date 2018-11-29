package de.hhu.bsinfo.restTerminal.request;

import com.google.gson.annotations.SerializedName;

public class ChunkCreateRequest {
    @SerializedName("nid")
    private String m_nid;
    @SerializedName("size")
    private int m_size;

    public ChunkCreateRequest(String p_nid, int p_size) {
        m_nid = p_nid;
        m_size = p_size;
    }
}
