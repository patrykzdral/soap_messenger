package com.pwr.model.util;

import com.pwr.model.message.Message;
import com.pwr.model.message.MessageBody;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import java.io.StringReader;
import java.io.StringWriter;

public class SoapUtil {
    public static SOAPMessage createEnvelope(Message messageHeader, MessageBody messageBody) throws SOAPException, JAXBException {
        SOAPMessage soapMessage = MessageFactory.newInstance().createMessage();
        StringWriter messageHeaderXml = new StringWriter();
        JAXBContext.newInstance(Message.class).createMarshaller().marshal(messageHeader, messageHeaderXml);
        soapMessage.getSOAPHeader().addChildElement(new QName("head", "messageHeader"))
                .setTextContent(String.valueOf(messageHeaderXml));

        StringWriter messageBodyXml = new StringWriter();
        JAXBContext.newInstance(MessageBody.class).createMarshaller().marshal(messageBody, messageBodyXml);
        soapMessage.getSOAPBody().addBodyElement(new QName( "message"))
                .setTextContent(String.valueOf(messageBodyXml));

        return soapMessage;
    }

    public static Message extractMessageHeader(SOAPMessage soapMessage) throws SOAPException, JAXBException {
        StringReader messageHeaderXml = new StringReader (((SOAPElement)soapMessage.getSOAPHeader()
                .getChildElements(new QName("head", "messageHeader")).next()).getTextContent());
        return (Message) JAXBContext.newInstance(Message.class)
                .createUnmarshaller().unmarshal(messageHeaderXml);
    }

    public static MessageBody extractMessage(SOAPMessage soapMessage) throws SOAPException, JAXBException {
        StringReader messageBodyXml = new StringReader (((SOAPElement)soapMessage.getSOAPBody()
                .getChildElements(new QName("message")).next()).getTextContent());
        return (MessageBody) JAXBContext.newInstance(MessageBody.class)
                .createUnmarshaller().unmarshal(messageBodyXml);
    }

}
