package vale.com.jpa.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public  class Persona {
	
	@Id
	String nombre;
	String aficion="";

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAficion() {
		return aficion;
	}

	public void setAficion(String aficion) {
		this.aficion = aficion;
	}

	@Override
	public String toString() {
		return "Persona [nombre=" + nombre + ", aficion=" + aficion + "]";
	}	

}
