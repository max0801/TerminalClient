package de.hhu.bsinfo.restTerminal.data;

public class MetadataEntry {
    private String nid;
    private String metadata;

    public MetadataEntry(String nid, String metadata) {
        this.nid = nid;
        this.metadata = metadata;
    }

    public String getNid() {
        return nid;
    }

    public String getMetadata() {
        return metadata;
    }
}
