package de.hhu.bsinfo.restTerminal.data;

import com.google.gson.annotations.SerializedName;

/**
 * This class is used to save the metadata response body
 */
public class MetadataEntry {
    @SerializedName("nid")
    private String m_nid;
    @SerializedName("metadata")
    private String m_metadata;

    public MetadataEntry(String p_nid, String p_metadata) {
        this.m_nid = p_nid;
        this.m_metadata = p_metadata;
    }

    public String getNid() {
        return m_nid;
    }

    public String getMetadata() {
        return m_metadata;
    }
}
