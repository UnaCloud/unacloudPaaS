
package unacloud.iaas.ws.production;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for turnOnVirtualCluster complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="turnOnVirtualCluster">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="username" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="templateID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="size" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ram" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="cores" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="hdSize" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="time" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "turnOnVirtualCluster", propOrder = {
    "username",
    "pass",
    "templateID",
    "size",
    "ram",
    "cores",
    "hdSize",
    "time"
})
public class TurnOnVirtualCluster {

    protected String username;
    protected String pass;
    protected int templateID;
    protected int size;
    protected int ram;
    protected int cores;
    protected int hdSize;
    protected int time;

    /**
     * Gets the value of the username property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the value of the username property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUsername(String value) {
        this.username = value;
    }

    /**
     * Gets the value of the pass property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPass() {
        return pass;
    }

    /**
     * Sets the value of the pass property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPass(String value) {
        this.pass = value;
    }

    /**
     * Gets the value of the templateID property.
     * 
     */
    public int getTemplateID() {
        return templateID;
    }

    /**
     * Sets the value of the templateID property.
     * 
     */
    public void setTemplateID(int value) {
        this.templateID = value;
    }

    /**
     * Gets the value of the size property.
     * 
     */
    public int getSize() {
        return size;
    }

    /**
     * Sets the value of the size property.
     * 
     */
    public void setSize(int value) {
        this.size = value;
    }

    /**
     * Gets the value of the ram property.
     * 
     */
    public int getRam() {
        return ram;
    }

    /**
     * Sets the value of the ram property.
     * 
     */
    public void setRam(int value) {
        this.ram = value;
    }

    /**
     * Gets the value of the cores property.
     * 
     */
    public int getCores() {
        return cores;
    }

    /**
     * Sets the value of the cores property.
     * 
     */
    public void setCores(int value) {
        this.cores = value;
    }

    /**
     * Gets the value of the hdSize property.
     * 
     */
    public int getHdSize() {
        return hdSize;
    }

    /**
     * Sets the value of the hdSize property.
     * 
     */
    public void setHdSize(int value) {
        this.hdSize = value;
    }

    /**
     * Gets the value of the time property.
     * 
     */
    public int getTime() {
        return time;
    }

    /**
     * Sets the value of the time property.
     * 
     */
    public void setTime(int value) {
        this.time = value;
    }

}
