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
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VCFLdapDirListReturn complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="VCFLdapDirListReturn">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VCFLdapDirSuccess" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="VCFLdapDirListResult" type="{http://webmail.com/LdapAttribute}ArrayOfVCFLdapDir" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VCFLdapDirListReturn", propOrder = {
    "vcfLdapDirSuccess",
    "vcfLdapDirListResult"
})
public class VCFLdapDirListReturn {

    @XmlElement(name = "VCFLdapDirSuccess")
    protected boolean vcfLdapDirSuccess;
    @XmlElement(name = "VCFLdapDirListResult")
    protected ArrayOfVCFLdapDir vcfLdapDirListResult;

    /**
     * Gets the value of the vcfLdapDirSuccess property.
     * 
     */
    public boolean isVCFLdapDirSuccess() {
        return vcfLdapDirSuccess;
    }

    /**
     * Sets the value of the vcfLdapDirSuccess property.
     * 
     */
    public void setVCFLdapDirSuccess(boolean value) {
        this.vcfLdapDirSuccess = value;
    }

    /**
     * Gets the value of the vcfLdapDirListResult property.
     * 
     * @return
     *     possible object is
     *     {@link ArrayOfVCFLdapDir }
     *     
     */
    public ArrayOfVCFLdapDir getVCFLdapDirListResult() {
        return vcfLdapDirListResult;
    }

    /**
     * Sets the value of the vcfLdapDirListResult property.
     * 
     * @param value
     *     allowed object is
     *     {@link ArrayOfVCFLdapDir }
     *     
     */
    public void setVCFLdapDirListResult(ArrayOfVCFLdapDir value) {
        this.vcfLdapDirListResult = value;
    }

}