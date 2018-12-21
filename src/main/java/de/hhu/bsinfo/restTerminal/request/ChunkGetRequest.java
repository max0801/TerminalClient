package de.hhu.bsinfo.restTerminal.request;

import com.google.gson.annotations.SerializedName;

/**
 * Class for saving the parameters of a chunkget request
 */
public class ChunkGetRequest {
    @SerializedName("cid")
    private long m_cid;
    @SerializedName("type")
    private String m_type;

    public ChunkGetRequest(long p_cid, String p_type) {
        m_cid = p_cid;
        m_type = p_type;
    }
}
