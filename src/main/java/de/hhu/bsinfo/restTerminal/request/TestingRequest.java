package de.hhu.bsinfo.restTerminal.request;

import com.google.gson.annotations.SerializedName;

public class TestingRequest {
    @SerializedName("nid")
    private String m_nid;

    public TestingRequest(String p_nid) {
        m_nid = p_nid;
    }
}
