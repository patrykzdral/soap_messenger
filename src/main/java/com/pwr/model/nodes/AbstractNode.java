package com.pwr.model.nodes;

import com.pwr.fxfiles.node.NodeController;
import com.pwr.model.listNode.ListNode;
import com.pwr.model.message.Message;
import com.pwr.model.message.MessageBody;
import com.pwr.model.util.SoapUtil;
import javax.xml.bind.JAXBException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AbstractNode {
    private NodeController nodeController;
    private String layerNumber;
    private String nodeName;
    private Integer port;
    private Integer nextLayerNodePort;
    private String nextLayerNodeHost;

    private volatile boolean threadRunning = true;
    private ServerSocket serverSocket;

    public AbstractNode(NodeController nodeController, String layerNumber, String nodeName, Integer port, Integer nextLayerNodePort, String nextLayerNodeHost) {
        this.nodeController = nodeController;
        this.layerNumber = layerNumber;
        this.nodeName = nodeName;
        this.port = port;
        this.nextLayerNodePort = nextLayerNodePort;
        this.nextLayerNodeHost = nextLayerNodeHost;
    }

    public NodeController getNodeController() {
        return nodeController;
    }

    public String getLayerNumber() {
        return layerNumber;
    }

    public String getNodeName() {
        return nodeName;
    }

    public String getNodeFullName() {
        return layerNumber + "|" + nodeName;
    }

    public Integer getPort() {
        return port;
    }

    public Integer getNextLayerNodePort() {
        return nextLayerNodePort;
    }

    public String getNextLayerNodeHost() {
        return nextLayerNodeHost;
    }



    public void startListening() {
        final ExecutorService clientProcessingPool = Executors.newFixedThreadPool(10);

        int nodePort = this.port;

        Thread thread = new Thread(() -> {
            try {
                serverSocket = new ServerSocket(nodePort);
                while (threadRunning) {
                    Socket clientSocket = serverSocket.accept();
                    clientProcessingPool.submit(() -> {
                        try {
                            SOAPMessage soapMessage = MessageFactory.newInstance().createMessage(null,
                                    clientSocket.getInputStream());
                            this.onSoapMessageReceived(soapMessage);
                            clientSocket.close();
                        } catch (IOException | SOAPException ignored) {
                        }

                    });
                }
            } catch (IOException ignored) {
            } finally {
                clientProcessingPool.shutdown();
            }
        });

        thread.start();
    }

    public void stopListening() throws IOException {
        serverSocket.close();
        this.threadRunning = false;
    }

    public void sendMessage(String receiverLayerNumber, String receiverNodeName, String messageType, MessageBody messageBody) throws SOAPException, JAXBException {
        SOAPMessage soapMessage = SoapUtil.createEnvelope(new Message(layerNumber, nodeName, receiverLayerNumber, receiverNodeName, messageType), messageBody);

        onSoapMessageReadyToSend(soapMessage);
    }

    public void sendMessage(SOAPMessage soapMessage) throws SOAPException, UnknownHostException, IOException {
        onSoapMessageReadyToSend(soapMessage);
    }

    protected void forwardTo(SOAPMessage soapMessage, String host, int port) throws SOAPException, IOException {
        System.out.println(host);
        System.out.println(port);
        try {
            Socket socket = new Socket(host, port);
            soapMessage.writeTo(socket.getOutputStream());
            socket.getOutputStream().flush();
            socket.close();
        }catch (Exception e){
        }
    }

    public void onSoapMessageReceived(SOAPMessage soapMessage) {
        try {
            Message messageHeader = SoapUtil.extractMessageHeader(soapMessage);

            System.out.println("1");
            if (messageHeader.checkIfVisitedNodesContainsNode(getNodeFullName()))
                return;

            System.out.println("2");
            if (messageHeader.isReceiver(getLayerNumber(), getNodeName())){
                System.out.println("2.5");
                MessageBody messageBody = SoapUtil.extractMessage(soapMessage);
                System.out.println("3");
                if (messageBody.getOnlineNodeSet()==null){
                    System.out.println("4");
                    getNodeController().showReceivedMessage(messageHeader.getSender(), messageBody.getMessage());
                }
                else {
                    Set<ListNode> onlineNodeSet = nodeController.getOnlineNodes();
                    Set<String> onlineLayers = nodeController.getOnlineLayers();
                    System.out.println(onlineLayers);
                    System.out.println(messageBody.getOnlineNodeSet());
                    onlineNodeSet.addAll(messageBody.getOnlineNodeSet());
                    onlineLayers.addAll(messageBody.getOnlineLayers());


                    if (onlineNodeSet.size()>messageBody.getOnlineNodeSet().size()) {
                        messageBody.setOnlineNodeSet(onlineNodeSet);
                        messageBody.setOnlineLayers(onlineLayers);
                        soapMessage = SoapUtil.createEnvelope(messageHeader,messageBody);
                        messageHeader.setVisitedNodes(null);
                    }
                    else {
                        System.out.println("dodanie");
                        messageHeader.addVisitedNode(getNodeFullName());
                        messageBody = SoapUtil.extractMessage(soapMessage);
                        soapMessage = SoapUtil.createEnvelope(messageHeader,messageBody);
                    }
                    nodeController.setOnlineNodes(messageBody.getOnlineNodeSet());
                    System.out.println("LISTA KURWA "+messageBody.getOnlineLayers());
                    nodeController.setOnlineLayers(messageBody.getOnlineLayers());
                    sendMessage(soapMessage);
                }
            }
            System.out.println("5");
            System.out.println(messageHeader.isGlobalBroadcast() );
            System.out.println(messageHeader.isLocalBroadcast() );
            System.out.println(messageHeader.isUnicast() );

            if (messageHeader.isGlobalBroadcast() || messageHeader.isLocalBroadcast() || !messageHeader.isReceiver(getLayerNumber(), getNodeName()))
                System.out.println("LUJ");
                if (!messageHeader.checkIfVisitedNodesContainsNode(getNodeFullName())) {

                    System.out.println("moj");
                    System.out.println("LISTA JEST :"+ messageHeader.getVisitedNodes());
                    messageHeader.addVisitedNode(getNodeFullName());
                    MessageBody messageBody = SoapUtil.extractMessage(soapMessage);
                    soapMessage = SoapUtil.createEnvelope(messageHeader,messageBody);
                    sendMessage(soapMessage);

                }
        } catch (SOAPException | IOException | JAXBException e) {
            e.printStackTrace();
            getNodeController().showWarning(e.getMessage());
        }
    }

    abstract protected void onSoapMessageReadyToSend(SOAPMessage soapMessage);
}
