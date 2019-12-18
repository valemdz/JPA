 package vale.com.jpa.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;


import javax.persistence.EntityManagerFactory;

import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;

import org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionCreator;
import org.hibernate.jpa.HibernatePersistenceProvider;

import vale.com.jpa.domain.Coche;
import vale.com.jpa.domain.Persona;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;


public class Main {
	
	//private final static Logger logger = LoggerFactory.getLogger(Main.class);
	
	
	public static void main( String args[] ) {
		
		//executePropagacionEager();
		
		//executePropagacionModificacionesPersist();
		
		executePropagacionModificacionesMerge();
		
	} 
	
	
	
	private static void executePropagacionEager() {
		
		// Haremos de Contenedor de aplicaciones	
			PersistenceProvider persistence = new HibernatePersistenceProvider();	
			// En el Map van las propiedades el persistence.xml
			
			PersistenceUnitInfo info = new SimplePersistenceInfo("vale_hibernate"); 
			
			EntityManagerFactory emf = persistence.createContainerEntityManagerFactory(info,  getMapPropertiesPersistenceUnit() );
			
			insertarConJDBC();
			
			Persona ana = new Persona("Ana");
			
			Coche coche = new Coche();
			coche.setModelo( "Suzuki" );
			coche.setPropietario( ana );
			
			Persona juan = new Persona( "Juan" );
			juan.getCoches().add( coche );
			
			
			EntityManager em = emf.createEntityManager();		
			em.getTransaction().begin();	
		
			em.persist( ana );
			em.persist( coche );
			em.persist( juan );
			
			em.refresh( coche );		
			System.out.println( "Propietario " +  coche.getPropietario().getNombre() );
			
			em.refresh( juan );
			System.out.println( "Cuantos coches tiene Juan? " + juan.getCoches().size() );
			
			em.refresh( ana );	
			System.out.println( "Cuantos coches tiene Ana? " + ana.getCoches().size() );
			
			
			em.getTransaction().commit();
			
			/*System.out.println( "Busca Coche " );
			
			Coche otro = em.find( Coche.class, 1L );
			
			System.out.println( " Propietario " + otro.getPropietario().getNombre() 
								+ " ID " + otro.getPropietario().getId() );*/
			
			System.out.println( "Busca Persona " );
			
			Persona otraPersona = em.find( Persona.class,3L );
			
			em.close();
		
			// cierra el pool de conexion y permite finalizar el proceso 
			// Esto es necesario xq	 el pool de conexiones se crea en un hilo separado del main 
			emf.close();
				
		
	}
	
	
	private static void executePropagacionModificacionesPersist() {
			
		// Haremos de Contenedor de aplicaciones	
		PersistenceProvider persistence = new HibernatePersistenceProvider();	
		// En el Map van las propiedades el persistence.xml
		
		PersistenceUnitInfo info = new SimplePersistenceInfo("vale_hibernate"); 
		
		EntityManagerFactory emf = persistence.createContainerEntityManagerFactory(info,  getMapPropertiesPersistenceUnit() );
					
		
		Coche coche = new Coche();
		coche.setModelo( "Suzuki" );
		Persona ana = new Persona("Ana");
		coche.setPropietario( ana );			
		
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();	
		
		em.persist( coche );				
		
		em.getTransaction().commit();	
		em.close();
		
		System.out.println(  "Propietario " + coche.getPropietario().getNombre()  );
	
		// cierra el pool de conexion y permite finalizar el proceso 
		// Esto es necesario xq	 el pool de conexiones se crea en un hilo separado del main 
		emf.close();
				
			
	}
	
	
	
	private static void executePropagacionModificacionesMerge() {
		
		// Haremos de Contenedor de aplicaciones	
		PersistenceProvider persistence = new HibernatePersistenceProvider();	
		// En el Map van las propiedades el persistence.xml
		
		PersistenceUnitInfo info = new SimplePersistenceInfo("vale_hibernate"); 
		
		EntityManagerFactory emf = persistence.createContainerEntityManagerFactory(info,  getMapPropertiesPersistenceUnit() );
				
		insertarConJDBC();					
		
		
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		
		Coche coche = em.find(Coche.class,1L);
		coche.setPropietario(new Persona("vale"));
		
		em.merge( coche );				
		
		em.getTransaction().commit();	
		em.close();
		
		System.out.println(  "Propietario " + coche.getPropietario().getNombre()  );
	
		// cierra el pool de conexion y permite finalizar el proceso 
		// Esto es necesario xq	 el pool de conexiones se crea en un hilo separado del main 
		emf.close();				
		
    }
	
	
	private static void insertarConJDBC() {
    	try {
    		
			Connection conexion =  DriverManager.getConnection("jdbc:h2:mem:test");	
			
			conexion.prepareStatement( "insert into Persona ( nombre, persona_id )"
			+ " values ( 'Cacho', null)" ).executeUpdate();						
			
			conexion.prepareStatement( "insert into Persona ( nombre, persona_id )"
								   + " values ( 'Lola', 1 )" ).executeUpdate();
			
			conexion.prepareStatement( "insert into Persona ( nombre, persona_id )"
					   + " values ( 'Lola', 2 )" ).executeUpdate();
			
			conexion.prepareStatement( "insert into Coche ( modelo, propietario_id )"
									   + " values ( 'FOX', 3)" ).executeUpdate();
				
			
			conexion.commit();
			conexion.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
    	
    }
	
	private static Map<String, String> getMapPropertiesPersistenceUnit(){
		
		Map<String, String> properties = new HashMap<>();
		properties.put( "javax.persistence.jdbc.url", "jdbc:h2:mem:test" );
		properties.put( "javax.persistence.schema-generation.database.action", "create" );
		properties.put( "hibernate.show_sql", "true" );
		return properties;
		
	}

}
