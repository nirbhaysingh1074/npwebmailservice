//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.04 at 06:55:47 PM IST 
//


package com.webmail.ldapattribute;

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
 *         &lt;element name="webmailDirId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="webmailDirPassword" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="webmailDirUrl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="webmailDirBase" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="webmailSearchBase" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "webmailDirId",
    "webmailDirPassword",
    "webmailDirUrl",
    "webmailDirBase",
    "webmailSearchBase"
})
@XmlRootElement(name = "getVCFLdapDirRequest")
public class GetVCFLdapDirRequest {

    @XmlElement(required = true)
    protected String webmailDirId;
    @XmlElement(required = true)
    protected String webmailDirPassword;
    @XmlElement(required = true)
    protected String webmailDirUrl;
    @XmlElement(required = true)
    protected String webmailDirBase;
    @XmlElement(required = true)
    protected String webmailSearchBase;

    /**
     * Gets the value of the webmailDirId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWebmailDirId() {
        return webmailDirId;
    }

    /**
     * Sets the value of the webmailDirId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWebmailDirId(String value) {
        this.webmailDirId = value;
    }

    /**
     * Gets the value of the webmailDirPassword property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWebmailDirPassword() {
        return webmailDirPassword;
    }

    /**
     * Sets the value of the webmailDirPassword property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWebmailDirPassword(String value) {
        this.webmailDirPassword = value;
    }

    /**
     * Gets the value of the webmailDirUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWebmailDirUrl() {
        return webmailDirUrl;
    }

    /**
     * Sets the value of the webmailDirUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWebmailDirUrl(String value) {
        this.webmailDirUrl = value;
    }

    /**
     * Gets the value of the webmailDirBase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWebmailDirBase() {
        return webmailDirBase;
    }

    /**
     * Sets the value of the webmailDirBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWebmailDirBase(String value) {
        this.webmailDirBase = value;
    }

    /**
     * Gets the value of the webmailSearchBase property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWebmailSearchBase() {
        return webmailSearchBase;
    }

    /**
     * Sets the value of the webmailSearchBase property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWebmailSearchBase(String value) {
        this.webmailSearchBase = value;
    }

}
