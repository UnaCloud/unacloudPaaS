package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * Created by ga.sotelo69 on 25/04/2014.
 */
@Entity
public class PuppetParamValue  extends Model {
    @Id
    public Long id;
    public String name;
    public String valor;

    public PuppetParamValue(String name, String valor) {
        this.name = name;
        this.valor = valor;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getValor() {
        return valor;
    }

}
