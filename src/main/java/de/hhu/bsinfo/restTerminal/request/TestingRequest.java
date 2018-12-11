package de.hhu.bsinfo.restTerminal.request;

import com.google.gson.annotations.SerializedName;

/**
 * Class for saving the parameters of a loginfo, monitor or lookup request
 */
public class TestingRequest {
    @SerializedName("nid")
    private String m_nid;

    public TestingRequest(String p_nid) {
        m_nid = p_nid;
    }
}
