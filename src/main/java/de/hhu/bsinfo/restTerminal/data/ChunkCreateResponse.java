package de.hhu.bsinfo.restTerminal.data;

import com.google.gson.annotations.SerializedName;

public class ChunkCreateResponse {
    @SerializedName("cid")
    private long m_cid;

    public ChunkCreateResponse(long p_cid) {
        this.m_cid = p_cid;
    }

    public long getCid() {
        return m_cid;
    }
}
