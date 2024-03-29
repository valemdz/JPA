 package vale.com.jpa.main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;


import javax.persistence.EntityManagerFactory;

import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;

import org.hibernate.engine.jdbc.connections.internal.DriverManagerConnectionCreator;
import org.hibernate.jpa.HibernatePersistenceProvider;

import vale.com.jpa.domain.Persona;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class Main {
	
	private final static Logger logger = LoggerFactory.getLogger(Main.class);
	
	
	public static void main( String args[] ) {
		
		// Haremos de Contenedor de aplicaciones	
		
		
		PersistenceProvider persistence = new HibernatePersistenceProvider();
		
		
		// En el Map van las propiedades el persistence.xml
		
		PersistenceUnitInfo info = new SimplePersistenceInfo("vale_hibernate"); 
		
		EntityManagerFactory emf = persistence.createContainerEntityManagerFactory(info,  getMapPropertiesPersistenceUnit() );
		EntityManager em = emf.createEntityManager();
		
		em.getTransaction().begin();	
	
		
		
		insertarConJDBC();		
			
		Persona reference  =  em.getReference( Persona.class, "SocoXXX" );		
		System.out.println( "reference " + reference.getClass().getName() );		
		reference.getNombre();
		System.out.println( "Llamo nombre");	
		reference.getAficion();
		System.out.println( "Llamo aficion");
		
		
		
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
	
    private static void insertarConJDBC() {
    	try {
    		
			Connection conexion =  DriverManager.getConnection("jdbc:h2:mem:test");
			conexion.prepareStatement( "insert into Persona ( nombre, aficion )"
									   + " values ( 'Soco', 'runing')" ).executeUpdate();
			
			conexion.commit();
			conexion.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
    	
    }	
	

}
