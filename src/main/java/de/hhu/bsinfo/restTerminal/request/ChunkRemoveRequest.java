package de.hhu.bsinfo.restTerminal.request;

import com.google.gson.annotations.SerializedName;

/**
 * Class for saving the parameters of a chunkremove request
 */
public class ChunkRemoveRequest {
    @SerializedName("cid")
    private String m_cid;

    public ChunkRemoveRequest(String p_cid) {
        m_cid = p_cid;
    }
}
