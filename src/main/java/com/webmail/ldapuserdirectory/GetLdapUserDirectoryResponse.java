//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.7 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.03.04 at 06:55:47 PM IST 
//


package com.webmail.ldapuserdirectory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getLdapUserDirectoryResponse complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getLdapUserDirectoryResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DirUserListReturn" type="{http://webmail.com/LdapUserDirectory}ArrayOfLdapUserDir" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getLdapUserDirectoryResponse", propOrder = {
    "dirUserListReturn"
})
public class GetLdapUserDirectoryResponse {

    @XmlElement(name = "DirUserListReturn")
    protected ArrayOfLdapUserDir dirUserListReturn;

    /**
     * Gets the value of the dirUserListReturn property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfLdapUserDir }
     *     
     */
    public ArrayOfLdapUserDir getDirUserListReturn() {
        return dirUserListReturn;
    }

    /**
     * Sets the value of the dirUserListReturn property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfLdapUserDir }
     *     
     */
    public void setDirUserListReturn(ArrayOfLdapUserDir value) {
        this.dirUserListReturn = value;
    }

}
