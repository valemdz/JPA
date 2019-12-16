package vale.com.jpa.main;

import java.util.Collection;
import java.util.HashSet;

import vale.com.jpa.domain.Persona;

public class MainErroresHashEquals {
	
	
	public static void main( String args[] ) {
		SinModificarPropiedadNatural();
		ModificandoPropiedadNatural();
		
	}
	
	static void SinModificarPropiedadNatural() {
		Collection<Persona> personas = new HashSet<>();		
		Persona pepe =  new Persona( "Pepe" );
		personas.add( pepe );
		
		System.out.println( personas.contains(  pepe )  ); // true;
	}
	
	static void ModificandoPropiedadNatural() {
		
		Collection<Persona> personas = new HashSet<>();		
		Persona pepe =  new Persona( "Pepe" );
		personas.add( pepe );
		
		// Le cambio la propiedad  natural a pepe
		
		pepe.setNombre("cacho");
		
		System.out.println( personas.contains(  pepe )  ); // false;
		
		// Da false xq se uso hashCode con el valor pepe y se almaceno 
		// en el hashSet. Luego se uso el hashCOde de cacho para buscar
	}

}
