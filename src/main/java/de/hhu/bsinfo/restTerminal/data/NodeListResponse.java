package de.hhu.bsinfo.restTerminal.data;

import java.util.List;

public class NodeListResponse {
    private List<String> m_nodes;

    public NodeListResponse(List<String> p_nodes) {
        this.m_nodes = p_nodes;
    }

    public List<String> getNodes() {
        return m_nodes;
    }
}
