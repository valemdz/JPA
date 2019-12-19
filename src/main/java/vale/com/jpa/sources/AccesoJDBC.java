package vale.com.jpa.sources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AccesoJDBC {
	
	public static void insertarPersonaConJDBC() {
    	try {
    		
			Connection conexion =  DriverManager.getConnection("jdbc:h2:mem:test");
			conexion.prepareStatement( "insert into Persona ( nombre, dni )"
									   + " values ( 'Vale','26686660' )" ).executeUpdate();
			
			conexion.prepareStatement( "insert into Persona ( nombre, dni )"
					   + " values ( 'Julia', '12345678' )" ).executeUpdate();
			
			conexion.commit();
			conexion.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	
    	
    }	

}
