//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.04 at 06:55:47 PM IST 
//


package com.webmail.task;

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
 *         &lt;element name="response" type="{http://www.w3.org/2001/XMLSchema}base64Binary"/>
 *         &lt;element name="updateSuccess" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "response",
    "updateSuccess"
})
@XmlRootElement(name = "getUpdateTaskResponse")
public class GetUpdateTaskResponse {

    @XmlElement(required = true)
    protected byte[] response;
    protected boolean updateSuccess;

    /**
     * Gets the value of the response property.
     * 
     * @return
     *     possible object is
     *     byte[]
     */
    public byte[] getResponse() {
        return response;
    }

    /**
     * Sets the value of the response property.
     * 
     * @param value
     *     allowed object is
     *     byte[]
     */
    public void setResponse(byte[] value) {
        this.response = value;
    }

    /**
     * Gets the value of the updateSuccess property.
     * 
     */
    public boolean isUpdateSuccess() {
        return updateSuccess;
    }

    /**
     * Sets the value of the updateSuccess property.
     * 
     */
    public void setUpdateSuccess(boolean value) {
        this.updateSuccess = value;
    }

}
