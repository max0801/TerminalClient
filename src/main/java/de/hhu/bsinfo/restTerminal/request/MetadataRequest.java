package de.hhu.bsinfo.restTerminal.request;

import com.google.gson.annotations.SerializedName;

/**
 * Class for saving the parameters of a metadata request
 */
public class MetadataRequest {
    @SerializedName("nid")
    private String m_nid;

    public MetadataRequest(String p_nid) {
        m_nid = p_nid;
    }
}
