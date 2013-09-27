
package unacloud.iaas.ws.production;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for templateWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="templateWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="applications" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="customizable" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="highAvailability" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="operatingSystem" type="{http://wss.losandes.com/}operatingSystemWS" minOccurs="0"/>
 *         &lt;element name="templateCode" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="templateName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="templateType" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "templateWS", propOrder = {
    "applications",
    "customizable",
    "highAvailability",
    "operatingSystem",
    "templateCode",
    "templateName",
    "templateType"
})
public class TemplateWS {

    @XmlElement(nillable = true)
    protected List<String> applications;
    protected boolean customizable;
    protected boolean highAvailability;
    protected OperatingSystemWS operatingSystem;
    protected Integer templateCode;
    protected String templateName;
    protected String templateType;

    /**
     * Gets the value of the applications property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the applications property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getApplications().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getApplications() {
        if (applications == null) {
            applications = new ArrayList<String>();
        }
        return this.applications;
    }

    /**
     * Gets the value of the customizable property.
     * 
     */
    public boolean isCustomizable() {
        return customizable;
    }

    /**
     * Sets the value of the customizable property.
     * 
     */
    public void setCustomizable(boolean value) {
        this.customizable = value;
    }

    /**
     * Gets the value of the highAvailability property.
     * 
     */
    public boolean isHighAvailability() {
        return highAvailability;
    }

    /**
     * Sets the value of the highAvailability property.
     * 
     */
    public void setHighAvailability(boolean value) {
        this.highAvailability = value;
    }

    /**
     * Gets the value of the operatingSystem property.
     * 
     * @return
     *     possible object is
     *     {@link OperatingSystemWS }
     *     
     */
    public OperatingSystemWS getOperatingSystem() {
        return operatingSystem;
    }

    /**
     * Sets the value of the operatingSystem property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperatingSystemWS }
     *     
     */
    public void setOperatingSystem(OperatingSystemWS value) {
        this.operatingSystem = value;
    }

    /**
     * Gets the value of the templateCode property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTemplateCode() {
        return templateCode;
    }

    /**
     * Sets the value of the templateCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTemplateCode(Integer value) {
        this.templateCode = value;
    }

    /**
     * Gets the value of the templateName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * Sets the value of the templateName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemplateName(String value) {
        this.templateName = value;
    }

    /**
     * Gets the value of the templateType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTemplateType() {
        return templateType;
    }

    /**
     * Sets the value of the templateType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTemplateType(String value) {
        this.templateType = value;
    }

}
