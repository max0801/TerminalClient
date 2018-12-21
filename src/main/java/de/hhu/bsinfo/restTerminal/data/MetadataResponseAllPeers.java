package de.hhu.bsinfo.restTerminal.data;

import java.util.List;

public class MetadataResponseAllPeers {
    private List<MetadataResponseOnePeer> m_metadata;

    public MetadataResponseAllPeers(List<MetadataResponseOnePeer> p_metadata) {
        this.m_metadata = p_metadata;
    }

    public List<MetadataResponseOnePeer> getMetadata() {
        return m_metadata;
    }
}
