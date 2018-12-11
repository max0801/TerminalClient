package de.hhu.bsinfo.restTerminal.data;

import com.google.gson.annotations.SerializedName;

/**
 * This class is used to save the content of a chunklist response body
 */
public class ChunkRange {
    @SerializedName("localChunkRanges")
    private String m_localChunkRanges;
    @SerializedName("migratedChunkRanges")
    private String m_migratedChunkRanges;

    public String getMigratedChunkRanges() {
        return m_migratedChunkRanges;
    }

    public String getLocalChunkRanges() {
        return m_localChunkRanges;
    }
}
