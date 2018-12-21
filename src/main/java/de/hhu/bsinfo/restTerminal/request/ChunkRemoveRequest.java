package de.hhu.bsinfo.restTerminal.request;

import com.google.gson.annotations.SerializedName;

/**
 * Class for saving the parameters of a chunkremove request
 */
public class ChunkRemoveRequest {
    @SerializedName("cid")
    private long m_cid;

    public ChunkRemoveRequest(long p_cid) {
        m_cid = p_cid;
    }
}
