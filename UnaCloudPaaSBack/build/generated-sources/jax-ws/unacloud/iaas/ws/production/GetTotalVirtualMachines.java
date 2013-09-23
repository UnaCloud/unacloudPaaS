
package unacloud.iaas.ws.production;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getTotalVirtualMachines complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getTotalVirtualMachines">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="machineDisk" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="machineCores" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="machineRam" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="templateCode" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="username" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pass" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTotalVirtualMachines", propOrder = {
    "machineDisk",
    "machineCores",
    "machineRam",
    "templateCode",
    "username",
    "pass"
})
public class GetTotalVirtualMachines {

    protected int machineDisk;
    protected int machineCores;
    protected int machineRam;
    protected int templateCode;
    protected String username;
    protected String pass;

    /**
     * Gets the value of the machineDisk property.
     * 
     */
    public int getMachineDisk() {
        return machineDisk;
    }

    /**
     * Sets the value of the machineDisk property.
     * 
     */
    public void setMachineDisk(int value) {
        this.machineDisk = value;
    }

    /**
     * Gets the value of the machineCores property.
     * 
     */
    public int getMachineCores() {
        return machineCores;
    }

    /**
     * Sets the value of the machineCores property.
     * 
     */
    public void setMachineCores(int value) {
        this.machineCores = value;
    }

    /**
     * Gets the value of the machineRam property.
     * 
     */
    public int getMachineRam() {
        return machineRam;
    }

    /**
     * Sets the value of the machineRam property.
     * 
     */
    public void setMachineRam(int value) {
        this.machineRam = value;
    }

    /**
     * Gets the value of the templateCode property.
     * 
     */
    public int getTemplateCode() {
        return templateCode;
    }

    /**
     * Sets the value of the templateCode property.
     * 
     */
    public void setTemplateCode(int value) {
        this.templateCode = value;
    }

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

}
