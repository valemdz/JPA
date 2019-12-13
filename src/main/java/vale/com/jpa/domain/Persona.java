package vale.com.jpa.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Persona {
	
	@Id
	String nombre;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	

}
