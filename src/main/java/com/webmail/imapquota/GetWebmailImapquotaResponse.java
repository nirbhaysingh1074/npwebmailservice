//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.04 at 06:55:47 PM IST 
//


package com.webmail.imapquota;

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
 *         &lt;element name="imapquota" type="{http://webmail.com/Imapquota}imapquota"/>
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
    "imapquota"
})
@XmlRootElement(name = "getWebmailImapquotaResponse")
public class GetWebmailImapquotaResponse {

    @XmlElement(required = true)
    protected Imapquota imapquota;

    /**
     * Gets the value of the imapquota property.
     * 
     * @return
     *     possible object is
     *     {@link Imapquota }
     *     
     */
    public Imapquota getImapquota() {
        return imapquota;
    }

    /**
     * Sets the value of the imapquota property.
     * 
     * @param value
     *     allowed object is
     *     {@link Imapquota }
     *     
     */
    public void setImapquota(Imapquota value) {
        this.imapquota = value;
    }

}
