
package unacloud.iaas.ws.production;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for virtualMachineExecutionWS complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="virtualMachineExecutionWS">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="systemUser" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="template" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="virtualMachine" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="virtualMachineExecutionCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="virtualMachineExecutionCores" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="virtualMachineExecutionHardDisk" type="{http://www.w3.org/2001/XMLSchema}long"/>
 *         &lt;element name="virtualMachineExecutionIP" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="virtualMachineExecutionRAMMemory" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="virtualMachineExecutionStart" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="virtualMachineExecutionStatus" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="virtualMachineExecutionStatusMessage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="virtualMachineExecutionStop" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="virtualMachineName" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "virtualMachineExecutionWS", propOrder = {
    "systemUser",
    "template",
    "virtualMachine",
    "virtualMachineExecutionCode",
    "virtualMachineExecutionCores",
    "virtualMachineExecutionHardDisk",
    "virtualMachineExecutionIP",
    "virtualMachineExecutionRAMMemory",
    "virtualMachineExecutionStart",
    "virtualMachineExecutionStatus",
    "virtualMachineExecutionStatusMessage",
    "virtualMachineExecutionStop",
    "virtualMachineName"
})
public class VirtualMachineExecutionWS {

    protected String systemUser;
    protected Integer template;
    protected Integer virtualMachine;
    protected String virtualMachineExecutionCode;
    protected int virtualMachineExecutionCores;
    protected long virtualMachineExecutionHardDisk;
    protected String virtualMachineExecutionIP;
    protected int virtualMachineExecutionRAMMemory;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar virtualMachineExecutionStart;
    protected int virtualMachineExecutionStatus;
    protected String virtualMachineExecutionStatusMessage;
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar virtualMachineExecutionStop;
    protected String virtualMachineName;

    /**
     * Gets the value of the systemUser property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSystemUser() {
        return systemUser;
    }

    /**
     * Sets the value of the systemUser property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSystemUser(String value) {
        this.systemUser = value;
    }

    /**
     * Gets the value of the template property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getTemplate() {
        return template;
    }

    /**
     * Sets the value of the template property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setTemplate(Integer value) {
        this.template = value;
    }

    /**
     * Gets the value of the virtualMachine property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getVirtualMachine() {
        return virtualMachine;
    }

    /**
     * Sets the value of the virtualMachine property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setVirtualMachine(Integer value) {
        this.virtualMachine = value;
    }

    /**
     * Gets the value of the virtualMachineExecutionCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVirtualMachineExecutionCode() {
        return virtualMachineExecutionCode;
    }

    /**
     * Sets the value of the virtualMachineExecutionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVirtualMachineExecutionCode(String value) {
        this.virtualMachineExecutionCode = value;
    }

    /**
     * Gets the value of the virtualMachineExecutionCores property.
     * 
     */
    public int getVirtualMachineExecutionCores() {
        return virtualMachineExecutionCores;
    }

    /**
     * Sets the value of the virtualMachineExecutionCores property.
     * 
     */
    public void setVirtualMachineExecutionCores(int value) {
        this.virtualMachineExecutionCores = value;
    }

    /**
     * Gets the value of the virtualMachineExecutionHardDisk property.
     * 
     */
    public long getVirtualMachineExecutionHardDisk() {
        return virtualMachineExecutionHardDisk;
    }

    /**
     * Sets the value of the virtualMachineExecutionHardDisk property.
     * 
     */
    public void setVirtualMachineExecutionHardDisk(long value) {
        this.virtualMachineExecutionHardDisk = value;
    }

    /**
     * Gets the value of the virtualMachineExecutionIP property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVirtualMachineExecutionIP() {
        return virtualMachineExecutionIP;
    }

    /**
     * Sets the value of the virtualMachineExecutionIP property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVirtualMachineExecutionIP(String value) {
        this.virtualMachineExecutionIP = value;
    }

    /**
     * Gets the value of the virtualMachineExecutionRAMMemory property.
     * 
     */
    public int getVirtualMachineExecutionRAMMemory() {
        return virtualMachineExecutionRAMMemory;
    }

    /**
     * Sets the value of the virtualMachineExecutionRAMMemory property.
     * 
     */
    public void setVirtualMachineExecutionRAMMemory(int value) {
        this.virtualMachineExecutionRAMMemory = value;
    }

    /**
     * Gets the value of the virtualMachineExecutionStart property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getVirtualMachineExecutionStart() {
        return virtualMachineExecutionStart;
    }

    /**
     * Sets the value of the virtualMachineExecutionStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setVirtualMachineExecutionStart(XMLGregorianCalendar value) {
        this.virtualMachineExecutionStart = value;
    }

    /**
     * Gets the value of the virtualMachineExecutionStatus property.
     * 
     */
    public int getVirtualMachineExecutionStatus() {
        return virtualMachineExecutionStatus;
    }

    /**
     * Sets the value of the virtualMachineExecutionStatus property.
     * 
     */
    public void setVirtualMachineExecutionStatus(int value) {
        this.virtualMachineExecutionStatus = value;
    }

    /**
     * Gets the value of the virtualMachineExecutionStatusMessage property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVirtualMachineExecutionStatusMessage() {
        return virtualMachineExecutionStatusMessage;
    }

    /**
     * Sets the value of the virtualMachineExecutionStatusMessage property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVirtualMachineExecutionStatusMessage(String value) {
        this.virtualMachineExecutionStatusMessage = value;
    }

    /**
     * Gets the value of the virtualMachineExecutionStop property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getVirtualMachineExecutionStop() {
        return virtualMachineExecutionStop;
    }

    /**
     * Sets the value of the virtualMachineExecutionStop property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setVirtualMachineExecutionStop(XMLGregorianCalendar value) {
        this.virtualMachineExecutionStop = value;
    }

    /**
     * Gets the value of the virtualMachineName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVirtualMachineName() {
        return virtualMachineName;
    }

    /**
     * Sets the value of the virtualMachineName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVirtualMachineName(String value) {
        this.virtualMachineName = value;
    }

}
