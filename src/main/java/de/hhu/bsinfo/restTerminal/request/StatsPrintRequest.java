package de.hhu.bsinfo.restTerminal.request;

import com.google.gson.annotations.SerializedName;

/**
 * Class for saving the parameters of a statsprint request
 */
public class StatsPrintRequest {
    @SerializedName("interval")
    private int m_interval;

    public StatsPrintRequest(int p_interval) {
        m_interval = p_interval;
    }
}
