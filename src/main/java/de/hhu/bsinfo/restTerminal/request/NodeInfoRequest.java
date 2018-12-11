package de.hhu.bsinfo.restTerminal.request;

import com.google.gson.annotations.SerializedName;

/**
 * Class for saving the parameters of a nodeinfo request
 */
public class NodeInfoRequest {
    @SerializedName("nid")
    private String m_nid;

    public NodeInfoRequest(String p_nid) {
        m_nid = p_nid;
    }
}
