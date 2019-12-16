package vale.com.jpa.domain;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public  class Persona {

	@Id
	String nombre;
	

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getNombre() == null) ? 0 : getNombre().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Persona))
			return false;
		Persona other = (Persona) obj;
		if (getNombre() == null) {
			if (other.getNombre() != null)
				return false;
		} else if (!getNombre().equals(other.getNombre()))
			return false;
		return true;
	}

	
	
	
}
