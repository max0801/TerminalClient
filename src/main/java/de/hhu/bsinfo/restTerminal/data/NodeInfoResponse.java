package de.hhu.bsinfo.restTerminal.data;

import com.google.gson.annotations.SerializedName;

public class NodeInfoResponse {
    @SerializedName("nid")
    private String m_nid;
    @SerializedName("role")
    private String m_role;
    @SerializedName("address")
    private String m_address;
    @SerializedName("capabilities")
    private String m_capabilities;

    public NodeInfoResponse(String p_nid, String p_role, String p_address, String p_capabilities) {
        this.m_nid = p_nid;
        this.m_role = p_role;
        this.m_address = p_address;
        this.m_capabilities = p_capabilities;
    }

    public String getNid() {
        return m_nid;
    }

    public String getRole() {
        return m_role;
    }

    public String getAddress() {
        return m_address;
    }

    public String getCapabilities() {
        return m_capabilities;
    }
}

