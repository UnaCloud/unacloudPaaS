
package unacloud.iaas.ws.production;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for getTotalUnaCloudResources complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="getTotalUnaCloudResources">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="machineDisk" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="machineCores" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="machineRam" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getTotalUnaCloudResources", propOrder = {
    "machineDisk",
    "machineCores",
    "machineRam"
})
public class GetTotalUnaCloudResources {

    protected int machineDisk;
    protected int machineCores;
    protected int machineRam;

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

}
