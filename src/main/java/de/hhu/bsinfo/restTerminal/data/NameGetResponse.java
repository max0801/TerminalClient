package de.hhu.bsinfo.restTerminal.data;

import com.google.gson.annotations.SerializedName;

public class NameGetResponse {
    @SerializedName("cid")
    private long m_cid;

    public NameGetResponse(long p_cid) {
        this.m_cid = m_cid;
    }

    public long getCid() {
        return m_cid;
    }
}
