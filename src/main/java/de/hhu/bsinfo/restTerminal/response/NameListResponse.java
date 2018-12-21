package de.hhu.bsinfo.restTerminal.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NameListResponse {
    private List<NamelistEntryRest> namelist;

    public List<NamelistEntryRest> getNamelist() {
        return namelist;
    }

    public class NamelistEntryRest {
        @SerializedName("name")
        private String m_name;
        @SerializedName("cid")
        private long m_cid;

        public String getName() {
            return m_name;
        }

        public long getCid() {
            return m_cid;
        }
    }
}

