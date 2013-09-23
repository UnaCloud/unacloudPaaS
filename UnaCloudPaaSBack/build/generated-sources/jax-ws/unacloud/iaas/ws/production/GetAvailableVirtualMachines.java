
package unacloud.iaas.ws.production;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getAvailableVirtualMachines complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getAvailableVirtualMachines">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="templateSelected" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="virtualMachineDisk" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="virtualMachineCores" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="virtualMachineRAM" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="user" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="password" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getAvailableVirtualMachines", propOrder = {
    "templateSelected",
    "virtualMachineDisk",
    "virtualMachineCores",
    "virtualMachineRAM",
    "user",
    "password"
})
public class GetAvailableVirtualMachines {

    protected int templateSelected;
    protected int virtualMachineDisk;
    protected int virtualMachineCores;
    protected int virtualMachineRAM;
    protected String user;
    protected String password;

    /**
     * Gets the value of the templateSelected property.
     * 
     */
    public int getTemplateSelected() {
        return templateSelected;
    }

    /**
     * Sets the value of the templateSelected property.
     * 
     */
    public void setTemplateSelected(int value) {
        this.templateSelected = value;
    }

    /**
     * Gets the value of the virtualMachineDisk property.
     * 
     */
    public int getVirtualMachineDisk() {
        return virtualMachineDisk;
    }

    /**
     * Sets the value of the virtualMachineDisk property.
     * 
     */
    public void setVirtualMachineDisk(int value) {
        this.virtualMachineDisk = value;
    }

    /**
     * Gets the value of the virtualMachineCores property.
     * 
     */
    public int getVirtualMachineCores() {
        return virtualMachineCores;
    }

    /**
     * Sets the value of the virtualMachineCores property.
     * 
     */
    public void setVirtualMachineCores(int value) {
        this.virtualMachineCores = value;
    }

    /**
     * Gets the value of the virtualMachineRAM property.
     * 
     */
    public int getVirtualMachineRAM() {
        return virtualMachineRAM;
    }

    /**
     * Sets the value of the virtualMachineRAM property.
     * 
     */
    public void setVirtualMachineRAM(int value) {
        this.virtualMachineRAM = value;
    }

    /**
     * Gets the value of the user property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUser() {
        return user;
    }

    /**
     * Sets the value of the user property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUser(String value) {
        this.user = value;
    }

    /**
     * Gets the value of the password property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the value of the password property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPassword(String value) {
        this.password = value;
    }

}
