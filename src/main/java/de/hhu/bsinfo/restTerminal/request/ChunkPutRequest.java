package de.hhu.bsinfo.restTerminal.request;

import com.google.gson.annotations.SerializedName;

/**
 * Class for saving the parameters of a chunkput request
 */
public class ChunkPutRequest {
    @SerializedName("cid")
    private long m_cid;
    @SerializedName("response")
    private Object m_data;
    @SerializedName("type")
    private String m_type;


    public ChunkPutRequest(long p_cid, Object p_data, String p_type) {
        m_cid = p_cid;
        m_data = p_data;
        m_type = p_type;

    }
}
