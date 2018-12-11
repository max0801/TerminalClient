package de.hhu.bsinfo.restTerminal.request;

import com.google.gson.annotations.SerializedName;

/**
 * Class for saving the parameters of a chunklist request
 */
public class ChunkListRequest {
    @SerializedName("nid")
    private String m_nid;

    public ChunkListRequest(String p_nid) {
        m_nid = p_nid;
    }
}
