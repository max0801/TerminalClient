package de.hhu.bsinfo.restTerminal.response;

import com.google.gson.annotations.SerializedName;

public class ChunkListResponse {
    @SerializedName("localChunkRanges")
    private String m_localChunkRanges;
    @SerializedName("migratedChunkRanges")
    private String m_migratedChunkRanges;

    public ChunkListResponse(String p_localChunkRanges, String p_migratedChunkRanges) {
        m_localChunkRanges = p_localChunkRanges;
        m_migratedChunkRanges = p_migratedChunkRanges;
    }

    public String getLocalChunkRanges() {
        return m_localChunkRanges;
    }

    public String getMigratedChunkRanges() {
        return m_migratedChunkRanges;
    }
}
