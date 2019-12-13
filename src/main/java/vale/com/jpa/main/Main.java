 package vale.com.jpa.main;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import vale.com.jpa.domain.Persona;

public class Main {
	
	
	public static void main( String args[] ) {
		
		
		//jpa_main es el nombre del persistence unit especificado en persistence.xml
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("jpa_main");
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		Persona nueva = new Persona();
		nueva.setNombre("Valeria del Valle ");
		
		em.persist(nueva);
		em.getTransaction().commit();
		em.close();
	
		// cierra el pool de conexion y permite finalizar el proceso 
		// Esto es necesario xq	 el pool de conexiones se crea en un hilo separado del main 
		emf.close();
		
		
	} 
	

	
	
	

}
