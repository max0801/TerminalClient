package de.hhu.bsinfo.restTerminal.request;

import com.google.gson.annotations.SerializedName;

/**
 * Class for saving the parameters of a chunkdump request
 */
public class ChunkDumpRequest {
    @SerializedName("cid")
    private String m_cid;
    @SerializedName("name")
    private String m_name;

    public ChunkDumpRequest(String p_cid, String p_name) {
        m_cid = p_cid;
        m_name = p_name;
    }
}
