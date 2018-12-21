package de.hhu.bsinfo.restTerminal.data;

import com.google.gson.annotations.SerializedName;

public class ChunkGetResponse {
    @SerializedName("content")
    private Object m_content;

    public ChunkGetResponse(Object m_content) {
        this.m_content = m_content;
    }

    public Object getContent() {
        return m_content;
    }
}
