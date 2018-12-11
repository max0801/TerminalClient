package de.hhu.bsinfo.restTerminal.data;

import com.google.gson.annotations.SerializedName;

/**
 * This class is used to save the response body of a nodeinfo command
 */
public class NodeInfoRest {
    @SerializedName("nid")
    private String m_nid;

    @SerializedName("role")
    private String m_role;

    @SerializedName("address")
    private String m_address;

    @SerializedName("capabilities")
    private String m_capabilities;

    public NodeInfoRest(String nid, String role, String address, String capabilities) {
        this.m_nid = nid;
        this.m_role = role;
        this.m_address = address;
        this.m_capabilities = capabilities;
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

