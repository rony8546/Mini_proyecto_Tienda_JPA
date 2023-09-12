package model;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class CategoriaId implements Serializable {

    private static final long serialVersionUID = 4198020985304539350L;

    private String nombre;
    private String password;
    public CategoriaId() {
    }
    public CategoriaId(String nombre, String password) {
        this.nombre = nombre;
        this.password = password;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
