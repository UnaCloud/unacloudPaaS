
package unacloud.iaas.ws.production;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para turnOnVirtualClusterCCGrid complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="turnOnVirtualClusterCCGrid">
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
 *         &lt;element name="arg8" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="arg9" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "turnOnVirtualClusterCCGrid", propOrder = {
    "username",
    "pass",
    "templateID",
    "size",
    "ram",
    "cores",
    "hdSize",
    "time",
    "arg8",
    "arg9"
})
public class TurnOnVirtualClusterCCGrid {

    protected String username;
    protected String pass;
    protected int templateID;
    protected int size;
    protected int ram;
    protected int cores;
    protected int hdSize;
    protected int time;
    protected int arg8;
    protected int arg9;

    /**
     * Obtiene el valor de la propiedad username.
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
     * Define el valor de la propiedad username.
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
     * Obtiene el valor de la propiedad pass.
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
     * Define el valor de la propiedad pass.
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
     * Obtiene el valor de la propiedad templateID.
     * 
     */
    public int getTemplateID() {
        return templateID;
    }

    /**
     * Define el valor de la propiedad templateID.
     * 
     */
    public void setTemplateID(int value) {
        this.templateID = value;
    }

    /**
     * Obtiene el valor de la propiedad size.
     * 
     */
    public int getSize() {
        return size;
    }

    /**
     * Define el valor de la propiedad size.
     * 
     */
    public void setSize(int value) {
        this.size = value;
    }

    /**
     * Obtiene el valor de la propiedad ram.
     * 
     */
    public int getRam() {
        return ram;
    }

    /**
     * Define el valor de la propiedad ram.
     * 
     */
    public void setRam(int value) {
        this.ram = value;
    }

    /**
     * Obtiene el valor de la propiedad cores.
     * 
     */
    public int getCores() {
        return cores;
    }

    /**
     * Define el valor de la propiedad cores.
     * 
     */
    public void setCores(int value) {
        this.cores = value;
    }

    /**
     * Obtiene el valor de la propiedad hdSize.
     * 
     */
    public int getHdSize() {
        return hdSize;
    }

    /**
     * Define el valor de la propiedad hdSize.
     * 
     */
    public void setHdSize(int value) {
        this.hdSize = value;
    }

    /**
     * Obtiene el valor de la propiedad time.
     * 
     */
    public int getTime() {
        return time;
    }

    /**
     * Define el valor de la propiedad time.
     * 
     */
    public void setTime(int value) {
        this.time = value;
    }

    /**
     * Obtiene el valor de la propiedad arg8.
     * 
     */
    public int getArg8() {
        return arg8;
    }

    /**
     * Define el valor de la propiedad arg8.
     * 
     */
    public void setArg8(int value) {
        this.arg8 = value;
    }

    /**
     * Obtiene el valor de la propiedad arg9.
     * 
     */
    public int getArg9() {
        return arg9;
    }

    /**
     * Define el valor de la propiedad arg9.
     * 
     */
    public void setArg9(int value) {
        this.arg9 = value;
    }

}
