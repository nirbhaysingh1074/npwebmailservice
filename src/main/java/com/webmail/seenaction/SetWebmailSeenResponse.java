//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.04 at 06:55:47 PM IST 
//


package com.webmail.seenaction;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SetWebmailSeenStatus" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "setWebmailSeenStatus"
})
@XmlRootElement(name = "setWebmailSeenResponse")
public class SetWebmailSeenResponse {

    @XmlElement(name = "SetWebmailSeenStatus")
    protected boolean setWebmailSeenStatus;

    /**
     * Gets the value of the setWebmailSeenStatus property.
     * 
     */
    public boolean isSetWebmailSeenStatus() {
        return setWebmailSeenStatus;
    }

    /**
     * Sets the value of the setWebmailSeenStatus property.
     * 
     */
    public void setSetWebmailSeenStatus(boolean value) {
        this.setWebmailSeenStatus = value;
    }

}
