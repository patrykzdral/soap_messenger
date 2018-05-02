package com.pwr.model.listNode;

import java.util.Objects;

public class ListNode {
    private String layerNumberAndNodeName;
    private Integer port;
    private String nodeType;
    private String nextHost;
    private Integer nextPort;

    public ListNode() {
    }

    public ListNode(String layerNumberAndNodeName, Integer port, String nodeType, String nextHost, Integer nextPort) {
        this.layerNumberAndNodeName = layerNumberAndNodeName;
        this.port = port;
        this.nodeType = nodeType;
        this.nextHost = nextHost;
        this.nextPort = nextPort;
    }

    public String getLayerNumberAndNodeName() {
        return layerNumberAndNodeName;
    }

    public void setLayerNumberAndNodeName(String layerNumberAndNodeName) {
        this.layerNumberAndNodeName = layerNumberAndNodeName;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType;
    }

    public String getNextHost() {
        return nextHost;
    }

    public void setNextHost(String nextHost) {
        this.nextHost = nextHost;
    }

    public Integer getNextPort() {
        return nextPort;
    }

    public void setNextPort(Integer nextPort) {
        this.nextPort = nextPort;
    }

    @Override
    public String toString() {
        return "OnlineNode{" +
                "layerNumberAndNodeName='" + layerNumberAndNodeName + '\'' +
                ", port=" + port +
                ", nodeType='" + nodeType + '\'' +
                ", nextHost='" + nextHost + '\'' +
                ", nextPort=" + nextPort +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListNode listNode = (ListNode) o;
        return Objects.equals(layerNumberAndNodeName, listNode.layerNumberAndNodeName) && Objects.equals(port, listNode.port) && Objects.equals(nodeType, listNode.nodeType) && Objects.equals(nextHost, listNode.nextHost) && Objects.equals(nextPort, listNode.nextPort);
    }

    @Override
    public int hashCode() {

        return Objects.hash(layerNumberAndNodeName, port, nodeType, nextHost, nextPort);
    }
}
