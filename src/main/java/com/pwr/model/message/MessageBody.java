package com.pwr.model.message;

import com.pwr.model.listNode.ListNode;
import javax.xml.bind.annotation.*;
import java.util.Set;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class MessageBody {
    @XmlAttribute(name = "message")
    private String message;

    @XmlElementWrapper(name = "onlineNodes")
    @XmlElement(name = "onlineNode")
    private Set<ListNode> onlineNodeSet;

    @XmlElementWrapper(name = "onlineLayers")
    @XmlElement(name = "onlineLayer")
    private Set<String> onlineLayers;

    public MessageBody() {
    }

    public MessageBody(String message) {
        this.message = message;
    }

    public MessageBody(Set<ListNode> onlineNodeSet,Set<String> onlineLayers) {
        this.onlineNodeSet = onlineNodeSet;
        this.onlineLayers = onlineLayers;
    }

    public MessageBody(String message, Set<ListNode> onlineNodeSet) {
        this.message = message;
        this.onlineNodeSet = onlineNodeSet;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Set<ListNode> getOnlineNodeSet() {
        return onlineNodeSet;
    }

    public void setOnlineNodeSet(Set<ListNode> onlineNodeSet) {
        this.onlineNodeSet = onlineNodeSet;
    }

    public Set<String> getOnlineLayers() {
        return onlineLayers;
    }

    public void setOnlineLayers(Set<String> onlineLayers) {
        this.onlineLayers = onlineLayers;
    }
}

