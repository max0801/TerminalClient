package de.hhu.bsinfo.restTerminal.request;

import com.google.gson.annotations.SerializedName;

/**
 * Class for saving the parameters of a namereg request
 */
public class NameRegRequest {
    @SerializedName("cid")
    private long m_cid;
    @SerializedName("name")
    private String m_name;

    public NameRegRequest(long p_cid, String p_name) {
        m_cid = p_cid;
        m_name = p_name;
    }
}
