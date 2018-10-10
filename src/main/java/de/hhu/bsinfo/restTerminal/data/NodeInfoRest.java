package de.hhu.bsinfo.restTerminal.data;

public class NodeInfoRest {
    private String nid;
    private String role;
    private String address;
    private String capabilities;

    public NodeInfoRest(String nid, String role, String address, String capabilities){
        this.nid = nid;
        this.role = role;
        this.address = address;
        this.capabilities = capabilities;
    }
}

