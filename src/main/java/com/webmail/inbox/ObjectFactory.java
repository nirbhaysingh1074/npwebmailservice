//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.04 at 06:55:47 PM IST 
//


package com.webmail.inbox;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.webmail.inbox package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.webmail.inbox
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetInboxMailRequest }
     * 
     */
    public GetInboxMailRequest createGetInboxMailRequest() {
        return new GetInboxMailRequest();
    }

    /**
     * Create an instance of {@link GetMailInboxResponse }
     * 
     */
    public GetMailInboxResponse createGetMailInboxResponse() {
        return new GetMailInboxResponse();
    }

    /**
     * Create an instance of {@link InboxListReturn }
     * 
     */
    public InboxListReturn createInboxListReturn() {
        return new InboxListReturn();
    }

    /**
     * Create an instance of {@link GetInboxMailDescRequest }
     * 
     */
    public GetInboxMailDescRequest createGetInboxMailDescRequest() {
        return new GetInboxMailDescRequest();
    }

    /**
     * Create an instance of {@link GetMailInboxDescResponse }
     * 
     */
    public GetMailInboxDescResponse createGetMailInboxDescResponse() {
        return new GetMailInboxDescResponse();
    }

    /**
     * Create an instance of {@link InboxListDescReturn }
     * 
     */
    public InboxListDescReturn createInboxListDescReturn() {
        return new InboxListDescReturn();
    }

    /**
     * Create an instance of {@link InboxDesc }
     * 
     */
    public InboxDesc createInboxDesc() {
        return new InboxDesc();
    }

    /**
     * Create an instance of {@link ArrayOfInboxMailDesc }
     * 
     */
    public ArrayOfInboxMailDesc createArrayOfInboxMailDesc() {
        return new ArrayOfInboxMailDesc();
    }

    /**
     * Create an instance of {@link ArrayOfInboxMail }
     * 
     */
    public ArrayOfInboxMail createArrayOfInboxMail() {
        return new ArrayOfInboxMail();
    }

    /**
     * Create an instance of {@link Inbox }
     * 
     */
    public Inbox createInbox() {
        return new Inbox();
    }

}
