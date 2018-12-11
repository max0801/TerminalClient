package de.hhu.bsinfo.restTerminal.request;

import com.google.gson.annotations.SerializedName;

/**
 * Class for saving the parameters of a nameget request
 */
public class NameGetRequest {
    @SerializedName("name")
    private String m_name;

    public NameGetRequest(String p_name) {
        m_name = p_name;
    }
}
