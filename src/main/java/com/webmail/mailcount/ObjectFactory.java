//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.04 at 06:55:47 PM IST 
//


package com.webmail.mailcount;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.webmail.mailcount package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.webmail.mailcount
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetWebmailUnreadMailCountResponse }
     * 
     */
    public GetWebmailUnreadMailCountResponse createGetWebmailUnreadMailCountResponse() {
        return new GetWebmailUnreadMailCountResponse();
    }

    /**
     * Create an instance of {@link GetWebmailUnreadMailCountRequest }
     * 
     */
    public GetWebmailUnreadMailCountRequest createGetWebmailUnreadMailCountRequest() {
        return new GetWebmailUnreadMailCountRequest();
    }

    /**
     * Create an instance of {@link GetWebmailAllMailCountResponse }
     * 
     */
    public GetWebmailAllMailCountResponse createGetWebmailAllMailCountResponse() {
        return new GetWebmailAllMailCountResponse();
    }

    /**
     * Create an instance of {@link GetWebmailAllMailCountRequest }
     * 
     */
    public GetWebmailAllMailCountRequest createGetWebmailAllMailCountRequest() {
        return new GetWebmailAllMailCountRequest();
    }

}
