package com.pwr.model.nodes;

import com.pwr.fxfiles.node.NodeController;
import com.pwr.model.message.Message;
import com.pwr.model.util.SoapUtil;

import javax.xml.bind.JAXBException;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.IOException;


public class RouterNode extends AbstractNode {
    private Integer nextRouterNodePort;
    private String nextRouterNodeHost;

    public RouterNode(NodeController nodeController, String layerNumber, String nodeName, Integer port,
                      Integer nextLayerNodePort, String nextLayerNodeHost, Integer nextRouterNodePort,
                      String nextRouterNodeHost) {
        super(nodeController, layerNumber, nodeName, port, nextLayerNodePort, nextLayerNodeHost);
        this.nextRouterNodePort = nextRouterNodePort;
        this.nextRouterNodeHost = nextRouterNodeHost;
    }

    public Integer getNextRouterNodePort() {
        return nextRouterNodePort;
    }

    public String getNextRouterNodeHost() {
        return nextRouterNodeHost;
    }

    @Override
    protected void onSoapMessageReadyToSend(SOAPMessage soapMessage) {
        try {
            Message messageHeader = SoapUtil.extractMessageHeader(soapMessage);

            if (messageHeader.isLocalBroadcast() && messageHeader.getReceiverLayerNumber().equals(getLayerNumber())) {
                System.out.println("chuj6");
                forwardTo(soapMessage, getNextLayerNodeHost(), getNextLayerNodePort());
            } else if (messageHeader.isLocalBroadcast() && !messageHeader.getReceiverLayerNumber().equals(getLayerNumber())) {
                System.out.println("chuj5");
                forwardTo(soapMessage, nextRouterNodeHost, nextRouterNodePort);
            } else if (messageHeader.isUnicast() && messageHeader.getReceiverLayerNumber().equals(getLayerNumber())) {
                System.out.println("chuj4");
                forwardTo(soapMessage, getNextLayerNodeHost(), getNextLayerNodePort());
            } else if (messageHeader.isUnicast() && !messageHeader.getReceiverLayerNumber().equals(getLayerNumber())) {
                System.out.println("chuj2");
                forwardTo(soapMessage, nextRouterNodeHost, nextRouterNodePort);
            } else {
                forwardTo(soapMessage, getNextLayerNodeHost(), getNextLayerNodePort());
                forwardTo(soapMessage, nextRouterNodeHost, nextRouterNodePort);
            }
        } catch (SOAPException | IOException | JAXBException e) {
            e.printStackTrace();
            getNodeController().showWarning(e.getMessage());
        }
    }
}
