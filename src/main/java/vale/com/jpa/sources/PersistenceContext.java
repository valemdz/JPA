package vale.com.jpa.sources;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManagerFactory;
import javax.persistence.spi.PersistenceProvider;
import javax.persistence.spi.PersistenceUnitInfo;

import org.hibernate.jpa.HibernatePersistenceProvider;

public class PersistenceContext {
	
	
	public static EntityManagerFactory getEntityManagerFactory() {
		
		PersistenceProvider persistence = new HibernatePersistenceProvider();		
		PersistenceUnitInfo info = new SimplePersistenceInfo("vale_hibernate"); 
		
		EntityManagerFactory emf = persistence.createContainerEntityManagerFactory(info,
					getMapPropertiesPersistenceUnit() );
		
		return emf;
	}
	
	private static Map<String, String> getMapPropertiesPersistenceUnit(){
		
		Map<String, String> properties = new HashMap<>();
		properties.put( "javax.persistence.jdbc.url", "jdbc:h2:mem:test" );
		properties.put( "javax.persistence.schema-generation.database.action", "create" );
		properties.put( "hibernate.show_sql", "true" );
		return properties;
		
	}

}
