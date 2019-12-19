package vale.com.jpa.sources;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import vale.com.jpa.domain.Persona;

class PersistenceContextTest {
	
	@Disabled
	@Test
	void testPersistenceContextActualizaEntidadSinMerge() {	
		
		EntityManagerFactory emf = PersistenceContext.getEntityManagerFactory();
		
		AccesoJDBC.insertarPersonaConJDBC();
		
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();
		
		Persona vale = em.find( Persona.class , "Vale" );
		vale.setMunicipio("Guaymallen");
		em.getTransaction().commit();
		em.close();
	
		// cierra el pool de conexion y permite finalizar el proceso 
		// Esto es necesario xq	 el pool de conexiones se crea en un hilo separado del main 
		emf.close();
		
	}
	
	@Disabled
	@Test
	void actualizaEntidadFueraDeLaTransaccionTest() {	
		
		EntityManagerFactory emf = PersistenceContext.getEntityManagerFactory();
		
		AccesoJDBC.insertarPersonaConJDBC();
		
		EntityManager em = emf.createEntityManager();
		
		Persona vale = em.find( Persona.class , "Vale" );
		vale.setMunicipio("Guaymallen");
		
		em.getTransaction().begin();	
		em.getTransaction().commit();		
		
		em.close();
	
		// cierra el pool de conexion y permite finalizar el proceso 
		// Esto es necesario xq	 el pool de conexiones se crea en un hilo separado del main 
		emf.close();
		
	}
	
	@Disabled
	@Test
	void actualizaEntidadPosteriorATransaccionTest() {	
		
		EntityManagerFactory emf = PersistenceContext.getEntityManagerFactory();
		
		AccesoJDBC.insertarPersonaConJDBC();
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();	
		em.getTransaction().commit();
		
		Persona vale = em.find( Persona.class , "Vale" );
		vale.setMunicipio("Guaymallen");
		
		
		
		em.close();
	
		// cierra el pool de conexion y permite finalizar el proceso 
		// Esto es necesario xq	 el pool de conexiones se crea en un hilo separado del main 
		emf.close();
		
	}
	
	
	///Arroja error debe tener una transaccion activa
	@Disabled
	@Test
	void actualizarByFlushExplicitoTest() {
	EntityManagerFactory emf = PersistenceContext.getEntityManagerFactory();
		
		AccesoJDBC.insertarPersonaConJDBC();
		
		EntityManager em = emf.createEntityManager();
		
		Persona vale = em.find( Persona.class , "Vale" );
		vale.setMunicipio("Guaymallen");
		
		em.flush();		
		em.close();
	
		// cierra el pool de conexion y permite finalizar el proceso 
		// Esto es necesario xq	 el pool de conexiones se crea en un hilo separado del main 
		emf.close();
		
	}
	
	@Disabled
	@Test
	void ordenOperaciones() {		
	
		EntityManagerFactory emf = PersistenceContext.getEntityManagerFactory();
		
		AccesoJDBC.insertarPersonaConJDBC();
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		em.remove( em.find( Persona.class, "Vale" ) );	
		//queremos que el remove se ejecute primero
		em.flush();
		
		em.persist( new Persona("Tonia") );
		
		
		Persona julia = em.find( Persona.class, "Julia" );
		julia.setMunicipio("Buena nueva");
		
		em.getTransaction().commit();		
		em.close();
	
		// cierra el pool de conexion y permite finalizar el proceso 
		// Esto es necesario xq	 el pool de conexiones se crea en un hilo separado del main 
		emf.close();		
	}
	
	@Disabled
	@Test	
	void flushAntesDelQuery() {
		
		// El flush se ejecuta automaticamente
		
		EntityManagerFactory emf = PersistenceContext.getEntityManagerFactory();
		
		AccesoJDBC.insertarPersonaConJDBC();
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();		
		
		em.persist( new Persona("Tonia") );
		
		List <Persona> personas = em.createQuery("Select p from Persona p ").getResultList();
		
		personas.forEach( System.out::println );
		
		em.getTransaction().commit();		
		em.close();
	
		// cierra el pool de conexion y permite finalizar el proceso 
		// Esto es necesario xq	 el pool de conexiones se crea en un hilo separado del main 
		emf.close();		
	}
	
	@Test
	void ordenOperacionesYProblemasIntegridad(){

		EntityManagerFactory emf = PersistenceContext.getEntityManagerFactory();
		
		AccesoJDBC.insertarPersonaConJDBC();
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();		
		
		Persona vale = em.find( Persona.class, "Vale");
		em.remove( vale );
		
		// Sino fuera por este flush fallaria debido a la violacion de clave unica
		//xq como sabemos los sql no se ejecutan en orden de codigo sino primero insert, update y delete
		em.flush();
		
		
		Persona cacho = new Persona("Cacho", vale.getDni());
		
		em.persist( cacho);
	
		em.getTransaction().commit();		
		em.close();
	
		// cierra el pool de conexion y permite finalizar el proceso 
		// Esto es necesario xq	 el pool de conexiones se crea en un hilo separado del main 
		emf.close();		
		
	}
}
