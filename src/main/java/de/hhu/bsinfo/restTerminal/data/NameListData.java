package de.hhu.bsinfo.restTerminal.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * This class is used to save the response body of the namelist command
 */
public class NameListData {
    @SerializedName("namelist")
    private List<NameListEntry> m_namelist;

    public List<NameListEntry> getEntries() {
        return m_namelist;
    }
}
