package de.hhu.bsinfo.restTerminal.data;

import com.google.gson.annotations.SerializedName;

/**
 * This class represents an entry in a namelist
 */
public class NameListEntry {
    @SerializedName("name")
    private String m_name;
    @SerializedName("cid")
    private String m_cid;

    public String getName() {
        return m_name;
    }

    public String getCid() {
        return m_cid;
    }
}
