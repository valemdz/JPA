package vale.com.jpa.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Coche {

	@Id 
	@GeneratedValue( strategy = GenerationType.IDENTITY)
	private Long id;	
	private String modelo;
	
	// Por defecto es eager 
	
	@ManyToOne( cascade = CascadeType.PERSIST  ) Persona propietario;

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public Persona getPropietario() {
		return propietario;
	}

	public void setPropietario(Persona propietario) {
		this.propietario = propietario;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((getModelo() == null) ? 0 : getModelo().hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!(obj instanceof Coche))
			return false;
		Coche other = (Coche) obj;
		if (getModelo() == null) {
			if (other.getModelo() != null)
				return false;
		} else if (!getModelo().equals(other.getModelo()))
			return false;
		return true;
	}

	
	
	
	
}
