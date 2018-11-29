package de.hhu.bsinfo.restTerminal.request;

import com.google.gson.annotations.SerializedName;

public class ChunkPutRequest {
    @SerializedName("cid")
    private String m_cid;
    @SerializedName("data")
    private Object m_data;
    @SerializedName("type")
    private String m_type;


    public ChunkPutRequest(String p_cid, Object p_data, String p_type) {
        m_cid = p_cid;
        m_data = p_data;
        m_type = p_type;

    }
}
