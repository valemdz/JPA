 package vale.com.jpa.main;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;


import javax.persistence.EntityManagerFactory;

import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;

import org.hibernate.jpa.HibernatePersistenceProvider;

import vale.com.jpa.domain.Persona;



public class Main {
	
	
	public static void main( String args[] ) {
		
		// Haremos de Contenedor de aplicaciones	
		
		PersistenceProvider persistence = new HibernatePersistenceProvider();
		
		
		// En el Map van las propiedades el persistence.xml
		
		PersistenceUnitInfo info = new SimplePersistenceInfo("vale_hibernate"); 
		
		EntityManagerFactory emf = persistence.createContainerEntityManagerFactory(info,  getMapPropertiesPersistenceUnit() );
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
	
	private static Map<String, String> getMapPropertiesPersistenceUnit(){
		
		Map<String, String> properties = new HashMap<>();
		properties.put( "javax.persistence.jdbc.url", "jdbc:h2:mem:test" );
		properties.put( "javax.persistence.schema-generation.database.action", "create" );
		properties.put( "hibernate.show_sql", "true" );
		return properties;
		
	}
	

	
	
	

}
