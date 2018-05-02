package com.pwr.model.nodes;

import com.pwr.fxfiles.node.NodeController;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.IOException;

public class SimpleNode extends AbstractNode {
    public SimpleNode(NodeController nodeController, String layerNumber, String nodeName, Integer port, Integer nextLayerNodePort, String nextLayerNodeHost) {
        super(nodeController, layerNumber, nodeName, port, nextLayerNodePort, nextLayerNodeHost);
    }

    @Override
    protected void onSoapMessageReadyToSend(SOAPMessage soapMessage) {
        try {
            forwardTo(soapMessage, getNextLayerNodeHost(), getNextLayerNodePort());
        } catch (IOException | SOAPException e) {
            e.printStackTrace();
            getNodeController().showWarning(e.getMessage());
        }
    }
}
