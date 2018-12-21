package de.hhu.bsinfo.restTerminal.data;

import com.google.gson.annotations.SerializedName;
import de.hhu.bsinfo.dxram.nameservice.NameserviceEntryStr;

import java.util.ArrayList;
import java.util.List;

public class NameListResponse {
    private List<NamelistEntryRest> namelist;

    public NameListResponse(ArrayList<NameserviceEntryStr> entries) {
        namelist = new ArrayList<NamelistEntryRest>();
        for (NameserviceEntryStr entry : entries) {
            namelist.add(new NamelistEntryRest(entry));
        }
    }

    public List<NamelistEntryRest> getNamelist() {
        return namelist;
    }

    public class NamelistEntryRest {
        @SerializedName("name")
        private String m_name;
        @SerializedName("cid")
        private long m_cid;

        public NamelistEntryRest(NameserviceEntryStr entry) {
            this.m_name = entry.getName();
            this.m_cid = entry.getValue();
        }

        public String getName() {
            return m_name;
        }

        public long getCid() {
            return m_cid;
        }
    }
}

