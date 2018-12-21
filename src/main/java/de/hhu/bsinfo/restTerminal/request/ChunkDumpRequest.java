package de.hhu.bsinfo.restTerminal.request;

import com.google.gson.annotations.SerializedName;

/**
 * Class for saving the parameters of a chunkdump request
 */
public class ChunkDumpRequest {
    @SerializedName("name")
    private String m_name;
    @SerializedName("cid")
    private long m_cid;

    public ChunkDumpRequest(String p_name, long p_cid) {
        m_name = p_name;
        m_cid = p_cid;
    }
}
