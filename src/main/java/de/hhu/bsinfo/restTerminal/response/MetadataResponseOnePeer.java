package de.hhu.bsinfo.restTerminal.response;

import com.google.gson.annotations.SerializedName;

public class MetadataResponseOnePeer {
    @SerializedName("nid")
    private String m_nid;
    @SerializedName("metadata")
    private String m_metadata;

    public MetadataResponseOnePeer(String p_nid, String p_metadata) {
        m_nid = p_nid;
        m_metadata = p_metadata;
    }

    public String getNid() {
        return m_nid;
    }

    public String getMetadata() {
        return m_metadata;
    }
}

