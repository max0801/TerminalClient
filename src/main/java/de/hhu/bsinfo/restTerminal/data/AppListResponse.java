package de.hhu.bsinfo.restTerminal.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AppListResponse {
    private List<String> m_applist;

    public List<String> getApplist() {
        return m_applist;
    }
}
